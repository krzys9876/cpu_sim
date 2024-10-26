package org.kr.cpu.sim;

public class LineDriver8 extends Component {
    static final InputPin PIN_EN = new InputPin("EN", 0);
    static final InputPin[] PIN_A = initInputPins("A", 1, 8);
    static final OutputPin[] PIN_Y = initOutputPins("Y", 0, 8);

    public LineDriver8(String id) { super(id, new boolean[9], new boolean[8]); }

    @Override
    protected void updateOutput() {
        if(!getInput(PIN_EN.order)) // EN active low
            for (int i=0; i<8; i++) setOutput(PIN_Y[i].order, getInput(PIN_A[i].order));
    }

    @Override
    public void setInput(int pinNo, boolean value) {
        super.setInput(pinNo, value);
        updateOutput();
    }

    @Override
    public void setInput(int[] pinNo, boolean[] value) {
        super.setInput(pinNo, value);
        updateOutput();
    }
}
