package org.kr.cpu.sim;

public class Or16 extends Component {
    static final InputPin[] PIN_A = initInputPins("A", 0, 16);
    static final InputPin[] PIN_B = initInputPins("B", 16, 16);
    static final OutputPin[] PIN_Y = initOutputPins("Y", 0, 16);

    public Or16(String id) { super(id, new boolean[16*2], new boolean[16]);}

    private final GateOr4x2[] ors = new GateOr4x2[] {new GateOr4x2("OR1"), new GateOr4x2("OR2"),
            new GateOr4x2("OR3"), new GateOr4x2("OR4")};

    @Override
    public Component setInput(int pinNo, boolean value) {
        assert pinNo>=0 && pinNo<=31;

        setInputDirect(pinNo, value);
        // identify which component should be affected
        int segmentNo = pinNo % 16;
        int andNum = segmentNo / 4;
        int gateNo = segmentNo % 4;
        boolean ab = pinNo < 16; // A true, B false
        ors[andNum].setInput(ab ? GateAnd4x2.PIN_A[gateNo].order : GateAnd4x2.PIN_B[gateNo].order, value);
        // set only affected output
        setOutput(PIN_Y[segmentNo].order, ors[andNum].getOutput(GateAnd4x2.PIN_Y[gateNo].order));

        return this;
    }
}
