package org.kr.cpu.sim;

public class LineSelector2 extends Component {
    static final InputPin PIN_S = new InputPin("S", 0); // 0 - Y=a, 1 - Y=B
    static final InputPin[] PIN_A = initInputPins("A", 1, 16);
    static final InputPin[] PIN_B = initInputPins("B", 17, 16);
    static final OutputPin[] PIN_Y = initOutputPins("Y", 0, 16);

    public LineSelector2(String id) { super(id, new boolean[1+2*16], new boolean[16]); }

    @Override
    public Component setInput(int pinNo, boolean value) {
        assert pinNo>=0 && pinNo<=1+2*16;

        input[pinNo] = value;
        boolean selected = input[PIN_S.order];
        if(pinNo == PIN_S.order) {
            for(int i=0; i<16; i++) output[PIN_Y[i].order] = input[selected ? PIN_B[i].order : PIN_A[i].order];
        } else if(pinNo<=PIN_A[15].order) {
            if(!selected) output[PIN_Y[pinNo-1].order] = input[PIN_A[pinNo-1].order];
        } else {
            if(selected) output[PIN_Y[pinNo-17].order] = input[PIN_B[pinNo-17].order];
        }
        return this;
    }
}
