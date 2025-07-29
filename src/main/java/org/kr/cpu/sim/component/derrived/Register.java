package org.kr.cpu.sim.component.derrived;

import org.kr.cpu.sim.Component;
import org.kr.cpu.sim.InputPin;
import org.kr.cpu.sim.OutputPin;
import org.kr.cpu.sim.component.basic.GateAnd;
import org.kr.cpu.sim.component.basic.GateNot;
import org.kr.cpu.sim.component.basic.GateOr;

public class Register extends Component {
    public static final InputPin[] PIN_D = initInputPins("D", 0, 16);
    public static final InputPin PIN_RST = new InputPin("RST", 16);
    public static final InputPin PIN_RD = new InputPin("RD", 17);
    public static final InputPin PIN_WR = new InputPin("WR", 18);
    public static final InputPin PIN_CLK = new InputPin("CLK", 19);
    public static final OutputPin[] PIN_Q = initOutputPins("Q", 0, 16);

    public Register(String id) { super(id, new boolean[16+4], new boolean[16]); }

    private final LineDriver16 inputDriver = new LineDriver16("DRIN");
    private final LineDriver16 outputDriver = new LineDriver16("DROUT");
    private final Latch16 reg = new Latch16("REG");
    private final GateNot negRst = new GateNot("NRST"); // active low
    private final GateNot negWr = new GateNot("NWR"); // active low
    private final GateOr clkEn = new GateOr("CLKEN");
    private final GateAnd clk = new GateAnd("ACTCLK");

    @Override
    public void updateOutput() {
        // actual CLK = (!RST | !WR) & CLK, i.e. clk is active only during WR signal - active low
        negRst.setInput(GateNot.PIN_A.order, getInput(PIN_RST.order), true);
        negWr.setInput(GateNot.PIN_A.order, getInput(PIN_WR.order), true);
        clkEn.setInput(GateOr.PIN_A.order, negRst.getOutput(GateNot.PIN_Y.order), false);
        clkEn.setInput(GateOr.PIN_B.order, negWr.getOutput(GateNot.PIN_Y.order), true);
        clk.setInput(GateAnd.PIN_A.order, clkEn.getOutput(GateOr.PIN_Y.order), false);
        clk.setInput(GateAnd.PIN_B.order, getInput(PIN_CLK.order), true);

        for(int i = 0; i< LineDriver16.PIN_A.length; i++)
            inputDriver.setInput(LineDriver16.PIN_A[i].order, getInput(PIN_D[i].order), false);

        // reset logic - this is realized by pull-down resistors and setting EN high
        inputDriver.setInput(LineDriver16.PIN_EN.order, negRst.getOutput(GateNot.PIN_Y.order), true);

        for(int i=0; i< Latch16.PIN_D.length; i++)
            reg.setInput(Latch16.PIN_D[i].order,
                    inputDriver.getOutput(LineDriver16.PIN_Y[i].order) && !inputDriver.getInput(LineDriver16.PIN_EN.order), false);
        reg.setInput(Latch16.PIN_CLK.order, clk.getOutput(GateAnd.PIN_Y.order), false);
        reg.setInput(Latch16.PIN_OE.order, false, true);

        for(int i=0; i< Latch16.PIN_Q.length; i++)
            outputDriver.setInput(LineDriver16.PIN_A[i].order, reg.getOutput(Latch16.PIN_Q[i].order), false);

        outputDriver.setInput(LineDriver16.PIN_EN.order, getInput(PIN_RD.order), true);

        for(int i=0; i<PIN_Q.length; i++)
            setOutput(PIN_Q[i].order, outputDriver.getOutput(LineDriver16.PIN_Y[i].order));
    }
}
