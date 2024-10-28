package org.kr.cpu.sim;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Latch8 extends Component {
    // OE pin should be checked by components using latch (just like line driver)
    static final InputPin PIN_OE = new InputPin("OE", 0);
    static final InputPin[] PIN_D = initInputPins("D", 1, 8);
    static final InputPin PIN_CLK = new InputPin("CLK", 9);
    static final OutputPin[] PIN_Q = initOutputPins("Q", 0, 8);

    public Latch8(String id) {
        super(id, new boolean[10], new boolean[8]);
        for(int i=0; i<8; i++) {
            setOutput(PIN_Q[i].order, false);
            state[i] = false;
        }
    }

    // start with all zeros
    private final boolean[] state = new boolean[8];
    private boolean prevClk = false;

    @Override
    protected void updateOutput() {
        // NOTE: clock should be updated AFTER all data lines, otherwise changes to data would not be reflected in output
        boolean currClk = getInput(PIN_CLK.order);
        boolean clkRisingEdge = currClk && !prevClk;
        prevClk = currClk;
        if(clkRisingEdge) {
            for(int i=0; i<PIN_Q.length; i++) {
                state[i] = getInput(PIN_D[i].order);
                setOutput(PIN_Q[i].order,  state[i]);
            }
        }
    }

    @Override
    public void setInput(int pinNo, boolean value, boolean shouldRefresh) {
        super.setInput(pinNo, value, false);
        if(shouldRefresh) updateOutput();
    }

    @Override
    public String fullTxt() {
        List<String> l = IntStream.range(0, state.length).mapToObj(i -> state[i] ? "0" : "1").collect(Collectors.toList());
        return super.fullTxt() + " " + (String.join("", l));}
}
