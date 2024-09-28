package org.kr.cpu.sim;

// 74154
public class Decoder416 extends Component {
    static final InputPin[] PIN_D = new InputPin[] {
            new InputPin("D0", 0), new InputPin("D1", 1), new InputPin("D2", 2), new InputPin("D3", 3)
    };
    static final InputPin PIN_nEN1 = new InputPin("EN1", 4); // ~EN1 - active low
    static final InputPin PIN_nEN2 = new InputPin("EN2", 5); // ~EN2 - active low
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
        // NOTE: output active low
        for(int i = 0; i < PIN_Q.length; i++) {
            output[PIN_Q[i].order] = i != decoded || input[PIN_nEN1.order] || input[PIN_nEN2.order];
        }
    }

    public Decoder416(String id) {
        super(id, new boolean[6], new boolean[16]);
    }

    @Override
    public Component setInput(int pinNo, boolean value) {
        input[pinNo] = value;
        updateOutput();
        return this;
    }
}
