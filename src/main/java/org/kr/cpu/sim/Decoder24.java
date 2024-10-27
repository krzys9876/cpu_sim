package org.kr.cpu.sim;

// 74154
public class Decoder24 extends Component {
    static final InputPin[] PIN_D = initInputPins("D", 0, 2);
    static final InputPin PIN_EN = new InputPin("EN", 2); // ~EN - active low
    static final OutputPin[] PIN_Q = initOutputPins("Q", 0, 4);

    @Override
    protected void updateOutput() {
        int decoded = (getInput(PIN_D[0].order) ? 1 : 0) + (getInput(PIN_D[1].order) ? 2 : 0);
        // NOTE: output active low
        for(int i = 0; i < PIN_Q.length; i++) {
            setOutput(PIN_Q[i].order, i != decoded || getInput(PIN_EN.order));
        }
    }

    public Decoder24(String id) {
        super(id, new boolean[3], new boolean[4]);
    }

    @Override
    public void setInput(int pinNo, boolean value, boolean shouldRefresh) {
        super.setInput(pinNo, value, false);
        if(shouldRefresh) updateOutput();
    }
}
