package org.kr.cpu.sim;

public class LineDriver8 extends Component {
    static final InputPin PIN_EN = new InputPin("EN", 0);
    static final InputPin[] PIN_A = initInputPins("A", 1, 8);
    static final OutputPin[] PIN_Y = initOutputPins("Y", 0, 8);

    public LineDriver8(String id) { super(id, new boolean[9], new boolean[8]); }

    @Override
    public Component setInput(int pinNo, boolean value) {
        assert pinNo>=0 && pinNo<=9;

        input[pinNo] = value;
        if(!input[PIN_EN.order]) // EN active low
            for (int i=0; i<8; i++) output[PIN_Y[i].order] = input[PIN_A[i].order];
        return this;
    }
}
