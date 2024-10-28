package org.kr.cpu.sim;

public class Inverter18 extends Component {
    static final InputPin[] PIN_A = initInputPins("A", 0, 18);
    static final OutputPin[] PIN_Y = initOutputPins("Y", 0, 18);

    public Inverter18(String id) {
        super(id, new boolean[18], new boolean[18]);
        for(int i=0; i<18; i++) setInput(i, false);
    }

    private final GateNot6[] state = new GateNot6[] {new GateNot6("NOT1"), new GateNot6("NOT2"), new GateNot6("NOT3")};

    @Override
    protected void updateOutput() {}

    @Override
    public void setInput(int pinNo, boolean value, boolean shouldRefresh) {
        super.setInput(pinNo, value, false);

        // always refresh
        if (pinNo<6) {
            state[0].setInput(GateNot6.PIN_A[pinNo].order, value, true);
            setOutput(PIN_Y[pinNo].order, state[0].getOutput(GateNot6.PIN_Y[pinNo].order));
        } else if (pinNo<12) {
            state[1].setInput(GateNot6.PIN_A[pinNo-6].order, value, true);
            setOutput(PIN_Y[pinNo].order, state[1].getOutput(GateNot6.PIN_Y[pinNo-6].order));
        } else {
            state[2].setInput(GateNot6.PIN_A[pinNo-12].order, value, true);
            setOutput(PIN_Y[pinNo].order, state[2].getOutput(GateNot6.PIN_Y[pinNo-12].order));
        }
    }
}
