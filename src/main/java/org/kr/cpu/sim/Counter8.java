package org.kr.cpu.sim;

public class Counter8 extends Component {
    static final InputPin PIN_CLK = new InputPin("CLK", 0);
    static final OutputPin[] PIN_Q = initOutputPins("Q", 0, 8);
    static final OutputPin PIN_C = new OutputPin("C", 8);

    Counter8(String id) {
        super(id, new boolean[1], new boolean[9]);
    }

    private final Counter4[] state = new Counter4[] { new Counter4("CNT1"), new Counter4("CNT2") };

    private void updateOutput() {
        for(int i = 0; i < 4; i++) {
            output[PIN_Q[i].order] = state[0].output[Counter4.PIN_Q[i].order];
            output[PIN_Q[i+4].order] = state[1].output[Counter4.PIN_Q[i].order];
        }
        output[PIN_C.order] = state[1].output[Counter4.PIN_C.order];
    }

    @Override
    public Component setInput(int pinNo, boolean value) {
        assert pinNo == PIN_CLK.order;

        state[0].setInput(pinNo, value);
        state[1].setInput(Counter4.PIN_CLK, state[0].output[Counter4.PIN_C.order]);

        input[pinNo] = value;
        updateOutput();
        return this;
    }
}
