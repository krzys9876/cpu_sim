package org.kr.cpu.sim;

public class GateAnd extends Component {
    static final InputPin PIN_A = new InputPin("A", 0);
    static final InputPin PIN_B = new InputPin("B", 1);
    static final OutputPin PIN_Y = new OutputPin("Y", 0);

    public GateAnd(String id) { super(id, new boolean[2], new boolean[1]);}

    @Override
    protected void updateOutput() {
        setOutput(PIN_Y.order, getInput(PIN_A.order) && getInput(PIN_B.order));
    }
}
