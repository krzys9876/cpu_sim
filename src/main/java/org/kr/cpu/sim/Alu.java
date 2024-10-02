package org.kr.cpu.sim;

public class Alu extends Component {
    static final InputPin[] PIN_O = initInputPins("O", 0, 4);
    static final InputPin[] PIN_A = initInputPins("A", 4, 16);
    static final InputPin[] PIN_B = initInputPins("B", 4+16, 16);
    static final OutputPin[] PIN_Y = initOutputPins("Y", 0, 16);

    private final Decoder416 decoder = new Decoder416("OPD");
    private final SignFlipper16 signFlipper = new SignFlipper16("SF");
    private final LineDriver16 driverAdd = new LineDriver16("DRADD");
    private final LineDriver16 driverSub = new LineDriver16("DRSUB");
    private final LineDriver16 driverInc = new LineDriver16("DRINC"); // always 1
    private final LineDriver16 driverDec = new LineDriver16("DRINC"); // always -1
    private final Adder16 adder = new Adder16("AD");

    public Alu(String id)  {
        super(id, new boolean[4+2*16], new boolean[16]);
        for(int i=0; i<16; i++) {
            driverInc.setInput(LineDriver16.PIN_A[i].order, false);
            driverDec.setInput(LineDriver16.PIN_A[i].order, true);
        }
        driverInc.setInput(LineDriver16.PIN_A[0].order, true); // do not forget to set 1
    }

    private void updateOutput() {
        // update operation decoder
        decoder.setInput(Decoder416.PIN_nEN1.order, false); // active low
        decoder.setInput(Decoder416.PIN_nEN2.order, false); // active low
        for(int i=0; i<4; i++) decoder.setInput(Decoder416.PIN_D[i].order, input[PIN_O[i].order]);
        // update sign flipper (flip sign of B input)
        for(int i=0; i<16; i++) signFlipper.setInput(SignFlipper16.PIN_A[i].order, input[PIN_B[i].order]);
        // update line drivers
        driverAdd.setInput(LineDriver16.PIN_EN.order, decoder.getOutput(Decoder416.PIN_Q[8].order)); // ADD - 8
        for(int i=0; i<16; i++) driverAdd.setInput(LineDriver16.PIN_A[i].order, input[PIN_B[i].order]);
        driverSub.setInput(LineDriver16.PIN_EN.order, decoder.getOutput(Decoder416.PIN_Q[9].order)); // SUB - 9
        for(int i=0; i<16; i++) driverSub.setInput(LineDriver16.PIN_A[i].order, signFlipper.getOutput(SignFlipper16.PIN_Y[i].order));
        driverInc.setInput(LineDriver16.PIN_EN.order, decoder.getOutput(Decoder416.PIN_Q[10].order)); // INC - 10
        driverDec.setInput(LineDriver16.PIN_EN.order, decoder.getOutput(Decoder416.PIN_Q[11].order)); // DEC - 11
        // update adder
        adder.setInput(Adder16.PIN_C0, false);
        // select proper driver for B operand
        LineDriver16 driverB =
                !driverAdd.input[LineDriver16.PIN_EN.order] ? driverAdd :
                !driverSub.input[LineDriver16.PIN_EN.order] ? driverSub :
                !driverInc.input[LineDriver16.PIN_EN.order] ? driverInc : driverDec;
        for(int i=0; i<16; i++) {
            adder.setInput(Adder16.PIN_A[i].order, input[PIN_A[i].order]);
            adder.setInput(Adder16.PIN_B[i].order, driverB.getOutput(LineDriver16.PIN_Y[i].order));
        }
        // update output
        for(int i=0; i<16; i++) output[PIN_Y[i].order] = adder.getOutput(Adder16.PIN_S[i].order);
    }

    @Override
    public Component setInput(int pinNo, boolean value) {
        assert pinNo >= 0 && pinNo < 4+2*16;
        input[pinNo] = value;
        updateOutput();
        return this;
    }
}
