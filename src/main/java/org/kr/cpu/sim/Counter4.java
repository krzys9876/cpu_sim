package org.kr.cpu.sim;

public class Counter4 extends Component {
    static final InputPin PIN_CLK = new InputPin("CLK", 0);
    static final OutputPin[] PIN_Q = initOutputPins("Q", 0,4);
    static final OutputPin PIN_C = new OutputPin("C", 4);

    public Counter4(String id) { super(id, new boolean[1], new boolean[5]); }

    private final DFlipFlop[] state = new DFlipFlop[] {
            new DFlipFlop("FF1"), new DFlipFlop("FF2"), new DFlipFlop("FF3"), new DFlipFlop("FF4")
    };

    @Override
    protected void updateOutput() {
        setOutput(PIN_Q[0].order, state[0].getOutput(DFlipFlop.PIN_Q.order));
        setOutput(PIN_Q[1].order, state[1].getOutput(DFlipFlop.PIN_Q.order));
        setOutput(PIN_Q[2].order, state[2].getOutput(DFlipFlop.PIN_Q.order));
        setOutput(PIN_Q[3].order, state[3].getOutput(DFlipFlop.PIN_Q.order));
        setOutput(PIN_C.order, state[3].getOutput(DFlipFlop.PIN_nQ.order));
    }

    @Override
    public void setInput(int pinNo, boolean value, boolean shouldRefresh) {
        super.setInput(pinNo, value, false);

        state[0].setInput(DFlipFlop.PIN_D.order, state[0].getOutput(DFlipFlop.PIN_nQ.order), shouldRefresh);
        state[0].setInput(DFlipFlop.PIN_CLK.order, value, shouldRefresh);
        state[1].setInput(DFlipFlop.PIN_D.order, state[1].getOutput(DFlipFlop.PIN_nQ.order), shouldRefresh);
        state[1].setInput(DFlipFlop.PIN_CLK.order, state[0].getOutput(DFlipFlop.PIN_nQ.order), shouldRefresh);
        state[2].setInput(DFlipFlop.PIN_D.order, state[2].getOutput(DFlipFlop.PIN_nQ.order), shouldRefresh);
        state[2].setInput(DFlipFlop.PIN_CLK.order, state[1].getOutput(DFlipFlop.PIN_nQ.order), shouldRefresh);
        state[3].setInput(DFlipFlop.PIN_D.order, state[3].getOutput(DFlipFlop.PIN_nQ.order), shouldRefresh);
        state[3].setInput(DFlipFlop.PIN_CLK.order, state[2].getOutput(DFlipFlop.PIN_nQ.order), shouldRefresh);

        if(shouldRefresh) updateOutput();
    }
}
