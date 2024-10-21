package org.kr.cpu.sim;

public class And16 extends Component {
    static final InputPin[] PIN_A = initInputPins("A", 0, 16);
    static final InputPin[] PIN_B = initInputPins("B", 16, 16);
    static final OutputPin[] PIN_Y = initOutputPins("Y", 0, 16);

    public And16(String id) { super(id, new boolean[16*2], new boolean[16]);}

    private final GateAnd4x2[] ands = new GateAnd4x2[] {new GateAnd4x2("AND1"), new GateAnd4x2("AND2"),
            new GateAnd4x2("AND3"), new GateAnd4x2("AND4")};

    @Override
    public Component setInput(int pinNo, boolean value) {
        assert pinNo>=0 && pinNo<=31;

        setInputDirect(pinNo, value);
        // identify which component should be affected
        int segmentNo = pinNo % 16;
        int andNum = segmentNo / 4;
        int gateNo = segmentNo % 4;
        boolean ab = pinNo < 16; // A true, B false
        ands[andNum].setInput(ab ? GateAnd4x2.PIN_A[gateNo].order : GateAnd4x2.PIN_B[gateNo].order, value);
        // set only affected output
        setOutput(PIN_Y[segmentNo].order, ands[andNum].getOutput(GateAnd4x2.PIN_Y[gateNo].order));

        //for(int i=0; i<16; i++) setOutput(PIN_Y[i].order, getInput(PIN_A[i].order) && getInput(PIN_B[i].order));
        return this;
    }
}