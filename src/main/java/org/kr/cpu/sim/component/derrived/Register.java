package org.kr.cpu.sim.component.derrived;

import org.kr.cpu.sim.Component;
import org.kr.cpu.sim.InputPin;
import org.kr.cpu.sim.OutputPin;

public class Register extends Component {
    public static final InputPin[] PIN_D = initInputPins("D", 0, 16);
    public static final InputPin PIN_RST = new InputPin("RST", 16);
    public static final InputPin PIN_RD = new InputPin("RD", 17);
    public static final InputPin PIN_WR = new InputPin("WR", 18);
    public static final InputPin PIN_CLK = new InputPin("CLK", 19);
    public static final OutputPin[] PIN_Q = initOutputPins("Q", 0, 16);

    public Register(String id) { super(id, new boolean[16+4], new boolean[16]); }

    @Override
    public void updateOutput() {
    }
}
