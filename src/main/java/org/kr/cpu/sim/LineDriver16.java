package org.kr.cpu.sim;

public class LineDriver16 extends Component {
    static final InputPin PIN_EN = new InputPin("EN", 0);
    static final InputPin[] PIN_A = initInputPins("A", 1, 16);
    static final OutputPin[] PIN_Y = initOutputPins("Y", 0, 16);

    public LineDriver16(String id) { super(id, new boolean[17], new boolean[16]); }

    private final LineDriver8 lines[] = new LineDriver8[] {new LineDriver8("L1"), new LineDriver8("L2")};

    private void updateOutput() {
        for(int i=0; i<8; i++) {
            lines[0].setInput(LineDriver8.PIN_A[i].order, input[PIN_A[i].order]);
            lines[1].setInput(LineDriver8.PIN_A[i].order, input[PIN_A[i+8].order]);
        }
        for(int i=0; i<8; i++) {
            output[PIN_Y[i].order]=lines[0].getOutput(LineDriver8.PIN_Y[i].order);
            output[PIN_Y[i+8].order]=lines[1].getOutput(LineDriver8.PIN_Y[i].order);
        }
    }

    @Override
    public Component setInput(int pinNo, boolean value) {
        assert pinNo>=0 && pinNo<=17;

        input[pinNo] = value;
        lines[0].setInput(LineDriver8.PIN_EN.order, input[PIN_EN.order]);
        lines[1].setInput(LineDriver8.PIN_EN.order, input[PIN_EN.order]);
        if(!input[PIN_EN.order]) // EN active low
            updateOutput();
        return this;
    }
}
