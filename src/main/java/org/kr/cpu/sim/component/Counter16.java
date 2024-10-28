package org.kr.cpu.sim.component;

import org.kr.cpu.sim.Component;
import org.kr.cpu.sim.InputPin;
import org.kr.cpu.sim.OutputPin;

public class Counter16 extends Component {
    public static final InputPin PIN_CLK = new InputPin("CLK", 0);
    public static final OutputPin[] PIN_Q = initOutputPins("Q", 0, 16);
    public static final OutputPin PIN_C = new OutputPin("C", 16);

    public Counter16(String id) {
        super(id, new boolean[1], new boolean[17]);
    }

    private final Counter8[] state = new Counter8[] {
            new Counter8("CNT1"), new Counter8("CNT2")
    };

    @Override
    public void updateOutput() {
        state[0].setInput(Counter8.PIN_CLK.order, getInput(PIN_CLK.order), true);
        state[1].setInput(Counter8.PIN_CLK.order, state[0].getOutput(Counter8.PIN_C.order), true);

        for(int i = 0; i < 8; i++) {
            setOutput(PIN_Q[i].order, state[0].getOutput(Counter8.PIN_Q[i].order));
            setOutput(PIN_Q[i+8].order, state[1].getOutput(Counter8.PIN_Q[i].order));
        }
        setOutput(PIN_C.order, state[1].getOutput(Counter8.PIN_C.order));
    }
}
