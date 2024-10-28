package org.kr.cpu.sim;

public class Latch16 extends Component {
    // OE pin should be checked by components using latch (just like line driver)
    static final InputPin PIN_OE = new InputPin("OE", 0);
    static final InputPin[] PIN_D = initInputPins("D", 1, 16);
    static final InputPin PIN_CLK = new InputPin("CLK", 17);
    static final OutputPin[] PIN_Q = initOutputPins("Q", 0, 16);

    public Latch16(String id) { super(id, new boolean[18], new boolean[16]); }

    // start with all zeros
    private final Latch8[] state = new Latch8[] {new Latch8("L1"), new Latch8("L2")};
    @Override
    protected void updateOutput() {
        state[0].setInput(Latch8.PIN_OE.order, getInput(PIN_OE.order), false);
        state[1].setInput(Latch8.PIN_OE.order, getInput(PIN_OE.order), false);

        for(int i=0; i<Latch8.PIN_D.length; i++) {
            state[0].setInput(Latch8.PIN_D[i].order, getInput(PIN_D[i].order));
            state[1].setInput(Latch8.PIN_D[i].order, getInput(PIN_D[i+8].order));
        }
        state[0].setInput(Latch8.PIN_CLK.order, getInput(PIN_CLK.order), true);
        state[1].setInput(Latch8.PIN_CLK.order, getInput(PIN_CLK.order), true);

        for(int i=0; i<Latch8.PIN_Q.length; i++) {
            setOutput(PIN_Q[i].order, state[0].getOutput(Latch8.PIN_Q[i].order));
            setOutput(PIN_Q[i+8].order, state[1].getOutput(Latch8.PIN_Q[i].order));
        }
    }
}
