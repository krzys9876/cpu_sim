package org.kr.cpu.sim.component;

import org.kr.cpu.sim.Component;
import org.kr.cpu.sim.InputPin;
import org.kr.cpu.sim.OutputPin;

public class Counter4 extends Component {
    public static final InputPin PIN_CLK = new InputPin("CLK", 0);
    public static final OutputPin[] PIN_Q = initOutputPins("Q", 0,4);
    public static final OutputPin PIN_C = new OutputPin("C", 4);

    public Counter4(String id) { super(id, new boolean[1], new boolean[5]); }

    private final DFlipFlop[] state = new DFlipFlop[] {
            new DFlipFlop("FF1"), new DFlipFlop("FF2"), new DFlipFlop("FF3"), new DFlipFlop("FF4")
    };

    @Override
    public void updateOutput() {
        state[0].setInput(DFlipFlop.PIN_D.order, state[0].getOutput(DFlipFlop.PIN_nQ.order), false);
        state[0].setInput(DFlipFlop.PIN_CLK.order, getInput(PIN_CLK.order), true);
        state[1].setInput(DFlipFlop.PIN_D.order, state[1].getOutput(DFlipFlop.PIN_nQ.order), false);
        state[1].setInput(DFlipFlop.PIN_CLK.order, state[0].getOutput(DFlipFlop.PIN_nQ.order), true);
        state[2].setInput(DFlipFlop.PIN_D.order, state[2].getOutput(DFlipFlop.PIN_nQ.order), false);
        state[2].setInput(DFlipFlop.PIN_CLK.order, state[1].getOutput(DFlipFlop.PIN_nQ.order), true);
        state[3].setInput(DFlipFlop.PIN_D.order, state[3].getOutput(DFlipFlop.PIN_nQ.order), false);
        state[3].setInput(DFlipFlop.PIN_CLK.order, state[2].getOutput(DFlipFlop.PIN_nQ.order), true);

        setOutput(PIN_Q[0].order, state[0].getOutput(DFlipFlop.PIN_Q.order));
        setOutput(PIN_Q[1].order, state[1].getOutput(DFlipFlop.PIN_Q.order));
        setOutput(PIN_Q[2].order, state[2].getOutput(DFlipFlop.PIN_Q.order));
        setOutput(PIN_Q[3].order, state[3].getOutput(DFlipFlop.PIN_Q.order));
        setOutput(PIN_C.order, state[3].getOutput(DFlipFlop.PIN_nQ.order));
    }
}
