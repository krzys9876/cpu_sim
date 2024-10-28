package org.kr.cpu.sim.component.basic;

import org.kr.cpu.sim.Component;
import org.kr.cpu.sim.InputPin;
import org.kr.cpu.sim.OutputPin;

public class DFlipFlop extends Component {
    public static final InputPin PIN_D = new InputPin("D", 0);
    public static final InputPin PIN_CLK = new InputPin("CLK", 1);
    public static final OutputPin PIN_Q = new OutputPin("Q", 0);
    public static final OutputPin PIN_nQ = new OutputPin("nQ", 1);

    public DFlipFlop(String id) { super(id, new boolean[2], new boolean[] {true, false}); }

    private boolean state = true;
    private boolean prevClk = false;

    @Override
    public void updateOutput() {
        boolean currClk = getInput(PIN_CLK.order);
        boolean clkRisingEdge = currClk && !prevClk;
        prevClk = currClk;
        if(clkRisingEdge) {
            state = getInput(PIN_D.order);
            setOutput(PIN_Q.order,  state);
            setOutput(PIN_nQ.order, !state);
        }
    }

    @Override
    public String fullTxt() { return super.fullTxt() + " " + (state ? "1" : "0");}
}
