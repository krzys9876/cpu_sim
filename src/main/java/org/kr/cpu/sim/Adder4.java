package org.kr.cpu.sim;

// 7483
public class Adder4 extends Component {
    static final InputPin PIN_C0 = new InputPin("C0", 0);
    static final InputPin[] PIN_A = new InputPin[] {
            new InputPin("A1", 1), new InputPin("A2", 2), new InputPin("A3", 3), new InputPin("A4", 4)
    };
    static final InputPin[] PIN_B = new InputPin[] {
            new InputPin("B1", 5), new InputPin("B2", 6), new InputPin("B3", 7), new InputPin("B4", 8)
    };
    static final OutputPin[] PIN_S = new OutputPin[] {
            new OutputPin("Q1", 0), new OutputPin("Q2", 1), new OutputPin("Q3", 2), new OutputPin("Q4", 3)
    };
    static final OutputPin PIN_C4 = new OutputPin("C4", 4);

    public Adder4(String id) { super(id, new boolean[9], new boolean[5]); }

    private void updateOutput() {
        int decodedA = decode4(PIN_A);
        int decodedB = decode4(PIN_B);
        int sum = decodedA + decodedB + decode1(PIN_C0);
        output[PIN_C4.order] = sum>15;
        for(int i = 0; i < PIN_S.length; i++) {
            output[PIN_S[i].order] = (sum & (1 << i)) > 0;
        }
    }

    @Override
    public Component setInput(int pinNo, boolean value) {
        input[pinNo] = value;
        updateOutput();
        return this;
    }
}
