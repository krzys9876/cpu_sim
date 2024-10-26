package org.kr.cpu.sim;

public class DFlipFlop extends Component {
    static final InputPin PIN_D = new InputPin("D", 0);
    static final InputPin PIN_CLK = new InputPin("CLK", 1);
    static final OutputPin PIN_Q = new OutputPin("Q", 0);
    static final OutputPin PIN_nQ = new OutputPin("nQ", 1);

    public DFlipFlop(String id) { super(id, new boolean[2], new boolean[] {true, false}); }

    private boolean state = true;

    @Override
    protected void updateOutput() {
        setOutput(PIN_Q.order,  state);
        setOutput(PIN_nQ.order, !state);
    }

    @Override
    public void setInput(int pinNo, boolean value) {
        boolean prevClk = getInput(PIN_CLK.order);
        boolean prevD = getInput(PIN_D.order);
        super.setInput(pinNo, value);

        boolean clkRisingEdge = pinNo == PIN_CLK.order && value && !prevClk;
        state = clkRisingEdge ? prevD : state;

        updateOutput();
    }

    @Override
    public String fullTxt() { return super.fullTxt() + " " + (state ? "1" : "0");}
}
