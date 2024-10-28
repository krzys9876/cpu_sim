package org.kr.cpu.sim.component.basic;

import org.kr.cpu.sim.Component;
import org.kr.cpu.sim.InputPin;
import org.kr.cpu.sim.OutputPin;

public class GateAnd extends Component {
    public static final InputPin PIN_A = new InputPin("A", 0);
    public static final InputPin PIN_B = new InputPin("B", 1);
    public static final OutputPin PIN_Y = new OutputPin("Y", 0);

    public GateAnd(String id) { super(id, new boolean[2], new boolean[1]);}

    @Override
    public void updateOutput() {
        setOutput(PIN_Y.order, getInput(PIN_A.order) && getInput(PIN_B.order));
    }
}
