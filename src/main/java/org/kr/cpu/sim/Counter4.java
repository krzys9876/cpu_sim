package org.kr.cpu.sim;

public class Counter4 extends Component {
    static final InputPin PIN_CLK = new InputPin("CLK", 0);
    static final OutputPin[] PIN_Q = new OutputPin[] {
            new OutputPin("Q", 0), new OutputPin("Q", 1),
            new OutputPin("Q", 2), new OutputPin("Q", 3)};

    static final OutputPin PIN_C = new OutputPin("C", 4);

    public Counter4(String id) { super(id, new boolean[1], new boolean[5]); }

    private final DFlipFlop[] state = new DFlipFlop[] {
            new DFlipFlop("FF1"), new DFlipFlop("FF2"), new DFlipFlop("FF3"), new DFlipFlop("FF4")
    };

    private void updateOutput() {
        output[PIN_Q[0].order] = state[0].output[DFlipFlop.PIN_Q.order];
        output[PIN_Q[1].order] = state[1].output[DFlipFlop.PIN_Q.order];
        output[PIN_Q[2].order] = state[2].output[DFlipFlop.PIN_Q.order];
        output[PIN_Q[3].order] = state[3].output[DFlipFlop.PIN_Q.order];
        output[PIN_C.order] = state[3].output[DFlipFlop.PIN_nQ.order];
    }

    @Override
    public Component setInput(int pinNo, boolean value) {
        assert pinNo == PIN_CLK.order;

        state[0]
                .setInput(DFlipFlop.PIN_D, state[0].output[DFlipFlop.PIN_nQ.order])
                .setInput(DFlipFlop.PIN_CLK, value);
        state[1]
                .setInput(DFlipFlop.PIN_D, state[1].output[DFlipFlop.PIN_nQ.order])
                .setInput(DFlipFlop.PIN_CLK, state[0].output[DFlipFlop.PIN_nQ.order]);
        state[2]
                .setInput(DFlipFlop.PIN_D, state[2].output[DFlipFlop.PIN_nQ.order])
                .setInput(DFlipFlop.PIN_CLK, state[1].output[DFlipFlop.PIN_nQ.order]);
        state[3]
                .setInput(DFlipFlop.PIN_D, state[3].output[DFlipFlop.PIN_nQ.order])
                .setInput(DFlipFlop.PIN_CLK, state[2].output[DFlipFlop.PIN_nQ.order]);
        input[pinNo] = value;
        updateOutput();
        return this;
    }
}
