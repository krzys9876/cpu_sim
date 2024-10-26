package org.kr.cpu.sim;

public class Counter16 extends Component {
    static final InputPin PIN_CLK = new InputPin("CLK", 0);
    static final OutputPin[] PIN_Q = initOutputPins("Q", 0, 16);
    static final OutputPin PIN_C = new OutputPin("C", 16);

    Counter16(String id) {
        super(id, new boolean[1], new boolean[17]);
    }

    private final Counter8[] state = new Counter8[] {
            new Counter8("CNT1"), new Counter8("CNT2")
    };

    @Override
    protected void updateOutput() {
        for(int i = 0; i < 8; i++) {
            setOutput(PIN_Q[i].order, state[0].getOutput(Counter8.PIN_Q[i].order));
            setOutput(PIN_Q[i+8].order, state[1].getOutput(Counter8.PIN_Q[i].order));
        }
        setOutput(PIN_C.order, state[1].getOutput(Counter8.PIN_C.order));
    }

    @Override
    public void setInput(int pinNo, boolean value) {
        super.setInput(pinNo, value);

        state[0].setInput(pinNo, value);
        state[1].setInput(Counter8.PIN_CLK.order, state[0].getOutput(Counter8.PIN_C.order));

        updateOutput();
    }
}
