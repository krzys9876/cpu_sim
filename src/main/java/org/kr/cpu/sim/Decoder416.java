package org.kr.cpu.sim;

// 74154
public class Decoder416 extends Component {
    static final InputPin[] PIN_D = initInputPins("D", 0, 4);
    static final InputPin PIN_EN1 = new InputPin("EN1", 4); // ~EN1 - active low
    static final InputPin PIN_EN2 = new InputPin("EN2", 5); // ~EN2 - active low
    static final OutputPin[] PIN_Q = initOutputPins("Q", 0, 16);

    public Decoder416(String id) {
        super(id, new boolean[6], new boolean[16]);
    }

    @Override
    public void updateOutput() {
        int decoded = (getInput(PIN_D[0].order) ? 1 : 0) +
                (getInput(PIN_D[1].order) ? 2 : 0) +
                (getInput(PIN_D[2].order) ? 4 : 0) +
                (getInput(PIN_D[3].order) ? 8 : 0);
        // NOTE: output active low
        for(int i = 0; i < PIN_Q.length; i++) {
            setOutput(PIN_Q[i].order, i != decoded || getInput(PIN_EN1.order) || getInput(PIN_EN2.order));
        }
    }
}
