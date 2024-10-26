package org.kr.cpu.sim;

// 7483
public class Adder4 extends Component {
    static final InputPin PIN_C0 = new InputPin("C0", 0);
    static final InputPin[] PIN_A = initInputPins("A", 1, 4);
    static final InputPin[] PIN_B = initInputPins("B", 5,4);
    static final OutputPin[] PIN_S = initOutputPins("S", 0,4);
    static final OutputPin PIN_C4 = new OutputPin("C4", 4);

    public Adder4(String id) { super(id, new boolean[9], new boolean[5]); }

    @Override
    protected void updateOutput() {
        int decodedA = decode4(PIN_A);
        int decodedB = decode4(PIN_B);
        int sum = decodedA + decodedB + decode1(PIN_C0);
        setOutput(PIN_C4.order, sum>15);
        for(int i = 0; i < PIN_S.length; i++) {
            setOutput(PIN_S[i].order, (sum & (1 << i)) > 0);
        }
    }

    @Override
    public void setInput(int pin, boolean value) {
        super.setInput(pin, value);
        updateOutput();
    }

    @Override
    public void setInput(int[] pinNo, boolean[] value) {
        assert(pinNo.length == value.length);
        for (int i = 0; i < pinNo.length; i++) super.setInput(pinNo[i], value[i]);
        updateOutput();
    }
}
