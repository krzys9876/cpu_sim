package org.kr.cpu.sim.component.basic;

import org.kr.cpu.sim.Component;
import org.kr.cpu.sim.InputPin;
import org.kr.cpu.sim.OutputPin;

public class GateNot extends Component {
    public static final InputPin PIN_A = new InputPin("A", 0);
    public static final OutputPin PIN_Y = new OutputPin("Y", 0);

    public GateNot(String id) {
        super(id, new boolean[1], new boolean[1]);
        setInput(0,false, true);
    }

    @Override
    public void updateOutput() {
        setOutput(0, !getInput(0));
    }
}
