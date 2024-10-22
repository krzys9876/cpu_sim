package org.kr.cpu.sim;

public class Alu extends Component {
    static final InputPin[] PIN_O = initInputPins("O", 0, 4);
    static final InputPin[] PIN_A = initInputPins("A", 4, 16);
    static final InputPin[] PIN_B = initInputPins("B", 4+16, 16);
    static final OutputPin[] PIN_Y = initOutputPins("Y", 0, 16);
    static final OutputPin PIN_Z = new OutputPin("Z", 16);
    static final OutputPin PIN_C = new OutputPin("C", 17);

    private final Decoder416 decoder = new Decoder416("OPD");
    private final SignFlipper16 signFlipper = new SignFlipper16("SF");
    private final LineDriver16 driverAdd = new LineDriver16("DRADD");
    private final LineDriver16 driverSub = new LineDriver16("DRSUB"); // SUB, CMP
    private final LineDriver16 driverInc = new LineDriver16("DRINC"); // always 1
    private final LineDriver16 driverDec = new LineDriver16("DRDEC"); // always -1
    private final Adder16 adder = new Adder16("AD");
    private final GateAnd2x4 adderSelector = new GateAnd2x4("ADDSEL"); // active-low convention
    private final ZeroChecker zero = new ZeroChecker("ZERO");
    private final LineDriver16 adderDriver = new LineDriver16("DRADDER");
    private final LineDriver16 compareDriver = new LineDriver16("DRCOMP");
    private final LineDriver16 flipBytesDriver = new LineDriver16("DRFLB");
    private final LineDriver16 shLeftDriver = new LineDriver16("DRSHL");
    private final LineDriver16 shRightDriver = new LineDriver16("DRSHR");
    private final LineDriver16 andDriver = new LineDriver16("DRAND");
    private final LineDriver16 orDriver = new LineDriver16("DROR");
    private final LineDriver16 xorDriver = new LineDriver16("DRXOR");
    private final And16 and16 = new And16("AND");
    private final Or16 or16 = new Or16("OR");
    private final Xor16 xor16 = new Xor16("XOR");
    private final GateAnd2x4 carryAnd = new GateAnd2x4("ANDC");

    public Alu(String id)  {
        super(id, new boolean[4+2*16], new boolean[16+2]);
        for(int i=0; i<16; i++) {
            driverInc.setInput(LineDriver16.PIN_A[i].order, false);
            driverDec.setInput(LineDriver16.PIN_A[i].order, true);
        }
        driverInc.setInput(LineDriver16.PIN_A[0].order, true); // do not forget to set 1
    }

    private void updateOutput() {
        // update operation decoder
        decoder.setInput(Decoder416.PIN_EN1.order, false); // active low
        decoder.setInput(Decoder416.PIN_EN2.order, false); // active low
        for(int i=0; i<4; i++) decoder.setInput(Decoder416.PIN_D[i].order, getInput(PIN_O[i].order));
        // update sign flipper (flip sign of B input)
        for(int i=0; i<16; i++) signFlipper.setInput(SignFlipper16.PIN_A[i].order, getInput(PIN_B[i].order));
        // update line drivers for adder (operand B)
        driverAdd.setInput(LineDriver16.PIN_EN.order, decoder.getOutput(Decoder416.PIN_Q[8].order)); // ADD - 8
        for(int i=0; i<16; i++) driverAdd.setInput(LineDriver16.PIN_A[i].order, getInput(PIN_B[i].order));
        driverSub.setInput(LineDriver16.PIN_EN.order, decoder.getOutput(Decoder416.PIN_Q[9].order) && decoder.getOutput(Decoder416.PIN_Q[12].order)); // SUB - 9
        for(int i=0; i<16; i++) driverSub.setInput(LineDriver16.PIN_A[i].order, signFlipper.getOutput(SignFlipper16.PIN_Y[i].order));
        driverInc.setInput(LineDriver16.PIN_EN.order, decoder.getOutput(Decoder416.PIN_Q[10].order)); // INC - 10
        driverDec.setInput(LineDriver16.PIN_EN.order, decoder.getOutput(Decoder416.PIN_Q[11].order)); // DEC - 11
        // update adder
        adder.setInput(Adder16.PIN_C0, false);
        // select proper driver for B operand
        LineDriver16 driverB =
                !driverAdd.getInput(LineDriver16.PIN_EN.order) ? driverAdd :
                !driverSub.getInput(LineDriver16.PIN_EN.order) ? driverSub :
                !driverInc.getInput(LineDriver16.PIN_EN.order) ? driverInc : driverDec;
        for(int i=0; i<16; i++) {
            adder.setInput(Adder16.PIN_A[i].order, getInput(PIN_A[i].order));
            adder.setInput(Adder16.PIN_B[i].order, driverB.getOutput(LineDriver16.PIN_Y[i].order));
        }
        // update inputs for bitwise operations
        for(int i=0; i<16; i++) {
            and16.setInput(And16.PIN_A[i].order, getInput(PIN_A[i].order));
            and16.setInput(And16.PIN_B[i].order, getInput(PIN_B[i].order));
            or16.setInput(Or16.PIN_A[i].order, getInput(PIN_A[i].order));
            or16.setInput(Or16.PIN_B[i].order, getInput(PIN_B[i].order));
            xor16.setInput(Xor16.PIN_A[i].order, getInput(PIN_A[i].order));
            xor16.setInput(Xor16.PIN_B[i].order, getInput(PIN_B[i].order));
        }
        // set carry (0 for bitwise, adder otherwise)
        carryAnd.setInput(GateOr2x4.PIN_A[0].order, adder.getOutput(Adder16.PIN_C16.order));
        carryAnd.setInput(GateOr2x4.PIN_B[0].order, decoder.getOutput(Decoder416.PIN_Q[13].order));
        carryAnd.setInput(GateOr2x4.PIN_C[0].order, decoder.getOutput(Decoder416.PIN_Q[14].order));
        carryAnd.setInput(GateOr2x4.PIN_D[0].order, decoder.getOutput(Decoder416.PIN_Q[15].order));
        carryAnd.setInput(GateOr2x4.PIN_A[1].order, carryAnd.getOutput(GateAnd2x4.PIN_Y[0].order));
        carryAnd.setInput(GateOr2x4.PIN_B[1].order, decoder.getOutput(Decoder416.PIN_Q[4].order)); //SHL needs OR for carry
        carryAnd.setInput(GateOr2x4.PIN_C[1].order, decoder.getOutput(Decoder416.PIN_Q[5].order)); //SHR needs OR for carry
        carryAnd.setInput(GateOr2x4.PIN_D[1].order, decoder.getOutput(Decoder416.PIN_Q[6].order));
        setOutput(PIN_C.order, carryAnd.getOutput(GateOr2x4.PIN_Y[1].order));
        // get output from selected module
        // line driver for adder
        adderSelector.setInput(GateAnd2x4.PIN_A[0].order, decoder.getOutput(Decoder416.PIN_Q[8].order));
        adderSelector.setInput(GateAnd2x4.PIN_B[0].order, decoder.getOutput(Decoder416.PIN_Q[9].order));
        adderSelector.setInput(GateAnd2x4.PIN_C[0].order, decoder.getOutput(Decoder416.PIN_Q[10].order));
        adderSelector.setInput(GateAnd2x4.PIN_D[0].order, decoder.getOutput(Decoder416.PIN_Q[11].order));
        adderDriver.setInput(LineDriver16.PIN_EN.order, adderSelector.getOutput(GateAnd2x4.PIN_Y[0].order));
        // line drivers for other modules
        compareDriver.setInput(LineDriver16.PIN_EN.order, decoder.getOutput(Decoder416.PIN_Q[12].order));
        flipBytesDriver.setInput(LineDriver16.PIN_EN.order, decoder.getOutput(Decoder416.PIN_Q[6].order));
        shLeftDriver.setInput(LineDriver16.PIN_EN.order, decoder.getOutput(Decoder416.PIN_Q[4].order));
        shRightDriver.setInput(LineDriver16.PIN_EN.order, decoder.getOutput(Decoder416.PIN_Q[5].order));
        andDriver.setInput(LineDriver16.PIN_EN.order, decoder.getOutput(Decoder416.PIN_Q[13].order));
        orDriver.setInput(LineDriver16.PIN_EN.order, decoder.getOutput(Decoder416.PIN_Q[14].order));
        xorDriver.setInput(LineDriver16.PIN_EN.order, decoder.getOutput(Decoder416.PIN_Q[15].order));
        for(int i=0; i<16; i++) {
            adderDriver.setInput(LineDriver16.PIN_A[i].order, adder.getOutput(Adder16.PIN_S[i].order));
            compareDriver.setInput(LineDriver16.PIN_A[i].order, getInput(PIN_A[i].order)); // copy A input - compare does not change result
            andDriver.setInput(LineDriver16.PIN_A[i].order, and16.getOutput(And16.PIN_Y[i].order));
            orDriver.setInput(LineDriver16.PIN_A[i].order, or16.getOutput(Or16.PIN_Y[i].order));
            xorDriver.setInput(LineDriver16.PIN_A[i].order, xor16.getOutput(Xor16.PIN_Y[i].order));
        }
        for(int i=0; i<8; i++) {
            flipBytesDriver.setInput(LineDriver16.PIN_A[i].order, getInput(PIN_A[i+8].order)); // flip bytes in A input
            flipBytesDriver.setInput(LineDriver16.PIN_A[i+8].order, getInput(PIN_A[i].order)); // might use single line with (i+8) % 16, but this is more verbose
        }
        for(int i=0; i<15; i++) shLeftDriver.setInput(LineDriver16.PIN_A[i+1].order, getInput(PIN_A[i].order)); // shift A input left
        shLeftDriver.setInput(LineDriver16.PIN_A[0].order, false);
        for(int i=0; i<15; i++) shRightDriver.setInput(LineDriver16.PIN_A[i].order, getInput(PIN_A[i+1].order)); // shift A input right
        shRightDriver.setInput(LineDriver16.PIN_A[15].order, false);

        // update output
        LineDriver16 driverOut =
                !adderDriver.getInput(LineDriver16.PIN_EN.order) ? adderDriver :
                !compareDriver.getInput(LineDriver16.PIN_EN.order) ? compareDriver :
                !flipBytesDriver.getInput(LineDriver16.PIN_EN.order) ? flipBytesDriver :
                !shLeftDriver.getInput(LineDriver16.PIN_EN.order) ? shLeftDriver :
                !shRightDriver.getInput(LineDriver16.PIN_EN.order) ? shRightDriver :
                !andDriver.getInput(LineDriver16.PIN_EN.order) ? andDriver :
                !orDriver.getInput(LineDriver16.PIN_EN.order) ? orDriver :
                xorDriver;
        for(int i=0; i<16; i++) setOutput(PIN_Y[i].order, driverOut.getOutput(LineDriver16.PIN_Y[i].order));
        // check for zero
        for(int i=0; i<16; i++) zero.setInput(ZeroChecker.PIN_A[i].order, getOutput(PIN_Y[i].order));
        setOutput(PIN_Z.order, zero.getOutput(ZeroChecker.PIN_Z.order));
    }

    @Override
    public Component setInput(int pinNo, boolean value) {
        assert pinNo >= 0 && pinNo < 4+2*16;
        setInputDirect(pinNo, value);
        updateOutput();
        return this;
    }
}
