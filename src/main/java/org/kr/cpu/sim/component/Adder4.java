package org.kr.cpu.sim.component;

import org.kr.cpu.sim.Component;
import org.kr.cpu.sim.InputPin;
import org.kr.cpu.sim.OutputPin;

// 7483
public class Adder4 extends Component {
    public static final InputPin PIN_C0 = new InputPin("C0", 0);
    public static final InputPin[] PIN_A = initInputPins("A", 1, 4);
    public static final InputPin[] PIN_B = initInputPins("B", 5,4);
    public static final OutputPin[] PIN_S = initOutputPins("S", 0,4);
    public static final OutputPin PIN_C4 = new OutputPin("C4", 4);

    public Adder4(String id) { super(id, new boolean[9], new boolean[5]); }

    private int decode4(InputPin[] pins) {
        return (getInput(pins[0].order) ? 1 : 0) +
                (getInput(pins[1].order) ? 2 : 0) +
                (getInput(pins[2].order) ? 4 : 0) +
                (getInput(pins[3].order) ? 8 : 0);
    }

    private int decode1(InputPin pin) { return getInput(pin.order) ? 1 : 0;}

    @Override
    public void updateOutput() {
        int decodedA = decode4(PIN_A);
        int decodedB = decode4(PIN_B);
        int sum = decodedA + decodedB + decode1(PIN_C0);
        setOutput(PIN_C4.order, sum>15);
        for(int i = 0; i < PIN_S.length; i++) {
            setOutput(PIN_S[i].order, (sum & (1 << i)) > 0);
        }
    }
}
