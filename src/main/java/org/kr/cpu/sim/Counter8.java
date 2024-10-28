package org.kr.cpu.sim;

public class Counter8 extends Component {
    static final InputPin PIN_CLK = new InputPin("CLK", 0);
    static final OutputPin[] PIN_Q = initOutputPins("Q", 0, 8);
    static final OutputPin PIN_C = new OutputPin("C", 8);

    public Counter8(String id) {
        super(id, new boolean[1], new boolean[9]);
    }

    private final Counter4[] state = new Counter4[] { new Counter4("CNT1"), new Counter4("CNT2") };

    @Override
    public void updateOutput() {
        state[0].setInput(Counter4.PIN_CLK.order, getInput(PIN_CLK.order), true);
        state[1].setInput(Counter4.PIN_CLK.order, state[0].getOutput(Counter4.PIN_C.order), true);

        for(int i = 0; i < 4; i++) {
            setOutput(PIN_Q[i].order, state[0].getOutput(Counter4.PIN_Q[i].order));
            setOutput(PIN_Q[i+4].order, state[1].getOutput(Counter4.PIN_Q[i].order));
        }
        setOutput(PIN_C.order, state[1].getOutput(Counter4.PIN_C.order));
    }
}
