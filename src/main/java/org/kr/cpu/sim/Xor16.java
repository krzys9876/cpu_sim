package org.kr.cpu.sim;

public class Xor16 extends Component {
    static final InputPin[] PIN_A = initInputPins("A", 0, 16);
    static final InputPin[] PIN_B = initInputPins("B", 16, 16);
    static final OutputPin[] PIN_Y = initOutputPins("Y", 0, 16);

    public Xor16(String id) { super(id, new boolean[16*2], new boolean[16]);}

    private final GateXor4x2[] ors = new GateXor4x2[] {new GateXor4x2("XOR1"), new GateXor4x2("XOR2"),
            new GateXor4x2("XOR3"), new GateXor4x2("XOR4")};

    @Override
    protected void updateOutput() {}

    @Override
    public void setInput(int pinNo, boolean value, boolean shouldRefresh) {
        super.setInput(pinNo, value, false);
        // identify which component should be affected
        int segmentNo = pinNo % 16;
        int andNum = segmentNo / 4;
        int gateNo = segmentNo % 4;
        boolean ab = pinNo < 16; // A true, B false
        ors[andNum].setInput(ab ? GateXor4x2.PIN_A[gateNo].order : GateXor4x2.PIN_B[gateNo].order, value);
        // set only affected output (always)
        setOutput(PIN_Y[segmentNo].order, ors[andNum].getOutput(GateXor4x2.PIN_Y[gateNo].order));
    }
}
