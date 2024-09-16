package org.kr.cpu.sim;

public class DFlipFlop extends Component {
    static final InputPin PIN_D = new InputPin("D", 0);
    static final InputPin PIN_CLK = new InputPin("CLK", 1);
    static final OutputPin PIN_Q = new OutputPin("Q", 0);
    static final OutputPin PIN_nQ = new OutputPin("nQ", 1);

    public DFlipFlop(String id) { super(id, new boolean[2], new boolean[] {true, false}); }

    private boolean state = true;

    private void updateOutput() {
        output[PIN_Q.order] = state;
        output[PIN_nQ.order] = !state;
    }

    @Override
    public Component setInput(int pinNo, boolean value) {
        boolean clkRisingEdge = pinNo == PIN_CLK.order && value && !input[PIN_CLK.order];
        state = clkRisingEdge ? input[PIN_D.order] : state;
        input[pinNo] = value;
        updateOutput();
        return this;
    }

    @Override
    public String fullTxt() { return super.fullTxt() + " " + (state ? "1" : "0");}
}
