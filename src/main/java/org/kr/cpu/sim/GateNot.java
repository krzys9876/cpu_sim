package org.kr.cpu.sim;

public class GateNot extends Component {
    static final InputPin PIN_A = new InputPin("A", 0);
    static final OutputPin PIN_Y = new OutputPin("Y", 0);

    public GateNot(String id) {
        super(id, new boolean[1], new boolean[1]);
        setInput(0,false);
    }

    @Override
    protected void updateOutput() {
        setOutput(0, !getInput(0));
    }
}
