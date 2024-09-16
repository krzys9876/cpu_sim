package org.kr.cpu.sim;

public class Decoder416 extends Component {
    static final InputPin[] PIN_D = new InputPin[] {
            new InputPin("D0", 0), new InputPin("D1", 1), new InputPin("D2", 2), new InputPin("D3", 3)
    };
    static final InputPin PIN_nEN = new InputPin("EN", 4); // ~EN - active low
    static final OutputPin[] PIN_Q = initQ();

    private static OutputPin[] initQ() {
        OutputPin[] arr = new OutputPin[16];
        for(int i = 0; i < arr.length; i++) arr[i] = new OutputPin("Q"+i, i);
        return arr;
    }

    private void updateOutput() {
        int decoded = (input[PIN_D[0].order] ? 1 : 0) +
                (input[PIN_D[1].order] ? 2 : 0) +
                (input[PIN_D[2].order] ? 4 : 0) +
                (input[PIN_D[3].order] ? 8 : 0);
        for(int i = 0; i < PIN_Q.length; i++) {
            output[PIN_Q[i].order] = i == decoded;
        }
    }

    Decoder416(String id) {
        super(id, new boolean[5], new boolean[16]);
    }

    @Override
    Component setInput(InputPin pin, boolean value) {
        input[pin.order] = value;
        if(!input[PIN_nEN.order])
            updateOutput();
        return this;
    }
}
