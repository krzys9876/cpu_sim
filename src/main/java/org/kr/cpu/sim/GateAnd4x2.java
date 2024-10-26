package org.kr.cpu.sim;

// 7408
public class GateAnd4x2 extends Component {
    static final InputPin[] PIN_A = initInputPins("A", 0, 4);
    static final InputPin[] PIN_B = initInputPins("B", 4, 4);
    static final OutputPin[] PIN_Y = initOutputPins("Y", 0, 4);

    public GateAnd4x2(String id) {
        super(id, new boolean[8], new boolean[4]);
        for(int i=0; i<8; i++) setInput(i, false);
    }

    private void updateGate(int gateNo) {
        setOutput(PIN_Y[gateNo].order, getInput(PIN_A[gateNo].order) && getInput(PIN_B[gateNo].order));
    }

    @Override
    protected void updateOutput() {}

    @Override
    public void setInput(int pinNo, boolean value) {
        super.setInput(pinNo, value);
        int gateNo = pinNo % 4;
        updateGate(gateNo);
    }
}
