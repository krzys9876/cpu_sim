package org.kr.cpu.sim;

public class DFlipFlop extends Component {
    static final InputPin PIN_D = new InputPin("D", 0);
    static final InputPin PIN_CLK = new InputPin("CLK", 1);
    static final OutputPin PIN_Q = new OutputPin("Q", 0);
    static final OutputPin PIN_nQ = new OutputPin("nQ", 1);

    DFlipFlop(String id) { super(id, new boolean[2], new boolean[2]); }

    private boolean state = false;

    private void updateOutput() {
        output[PIN_Q.order] = state;
        output[PIN_nQ.order] = !state;
    }

    @Override
    Component setInput(InputPin pin, boolean value) {
        boolean clkRisingEdge = pin == PIN_CLK && value && !input[PIN_CLK.order];
        state = clkRisingEdge ? input[PIN_D.order] : state;
        input[pin.order] = value;
        updateOutput();
        return this;
    }

    @Override
    public String fullTxt() { return super.fullTxt() + " " + (state ? "1" : "0");}
}
