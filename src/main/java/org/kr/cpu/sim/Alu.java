package org.kr.cpu.sim;

public class Alu extends Component {
    static final InputPin[] PIN_O = initInputPins("O", 0, 4);
    static final InputPin[] PIN_A = initInputPins("A", 4, 16);
    static final InputPin[] PIN_B = initInputPins("B", 4+16, 16);
    static final OutputPin[] PIN_Y = initOutputPins("Y", 0, 16);

    public Alu(String id)  { super(id, new boolean[4+2*16], new boolean[16]); }

    private Decoder416 decoder = new Decoder416("OPD");
    private SignFlipper16 signFlipper = new SignFlipper16("SF");
    private LineSelector2 lineSelector = new LineSelector2("LS");
    private Adder16 adder = new Adder16("AD");

    private void updateOutput() {
        // update operation decoder
        decoder.setInput(Decoder416.PIN_nEN1.order, false); // active low
        decoder.setInput(Decoder416.PIN_nEN2.order, false); // active low
        for(int i=0; i<4; i++) decoder.setInput(Decoder416.PIN_D[i].order, input[PIN_O[i].order]);
        // update sign flipper (flip sign of B input)
        for(int i=0; i<16; i++) signFlipper.setInput(SignFlipper16.PIN_A[i].order, input[PIN_B[i].order]);
        // update line selector
        // 1000 - ADD, 1001 - SUB
        lineSelector.setInput(LineSelector2.PIN_S.order, decoder.getOutput(Decoder416.PIN_Q[8].order)); // ADD operation selects input B, otherwise B with sign flipped
        for(int i=0; i<16; i++) {
            lineSelector.setInput(LineSelector2.PIN_A[i].order, input[PIN_B[i].order]);
            lineSelector.setInput(LineSelector2.PIN_B[i].order, signFlipper.getOutput(SignFlipper16.PIN_Y[i].order));
        }
        // update adder
        adder.setInput(Adder16.PIN_C0, false);
        for(int i=0; i<16; i++) {
            adder.setInput(Adder16.PIN_A[i].order, input[PIN_A[i].order]);
            adder.setInput(Adder16.PIN_B[i].order, lineSelector.getOutput(LineSelector2.PIN_Y[i].order));
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
