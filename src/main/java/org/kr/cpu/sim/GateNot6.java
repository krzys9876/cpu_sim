package org.kr.cpu.sim;

// 7404
public class GateNot6 extends Component {
    static final InputPin[] PIN_A = initInputPins("A", 0, 6);
    static final OutputPin[] PIN_Y = initOutputPins("Y", 0, 6);

    public GateNot6(String id) {
        super(id, new boolean[6], new boolean[6]);
        for(int i=0; i<6; i++) setInput(i, false);
    }

    @Override
    protected void updateOutput() {}

    @Override
    public void setInput(int pinNo, boolean value) {
        super.setInput(pinNo, value);
        setOutput(pinNo, !value);
    }
}
