package org.kr.cpu.sim;

public class GateAnd4 extends Component {
    static final InputPin[] PIN_A = initInputPins("A", 0, 4);
    static final InputPin[] PIN_B = initInputPins("B", 4, 4);
    static final OutputPin[] PIN_Y = initOutputPins("Y", 0, 4);

    public GateAnd4(String id) {
        super(id, new boolean[8], new boolean[4]);
        for(int i=0; i<8; i++) setInput(i, false);
    }

    private void updateGate(int gateNo) {
        setOutput(PIN_Y[gateNo].order, getInput(PIN_A[gateNo].order) && getInput(PIN_B[gateNo].order));
    }

    @Override
    public Component setInput(int pinNo, boolean value) {
        assert pinNo>=0 && pinNo<8;
        setInputDirect(pinNo, value);
        int gateNo = pinNo / 2;
        updateGate(gateNo);
        return this;
    }

}