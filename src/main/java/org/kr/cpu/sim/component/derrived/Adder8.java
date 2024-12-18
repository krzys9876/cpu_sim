package org.kr.cpu.sim.component.derrived;

import org.kr.cpu.sim.Component;
import org.kr.cpu.sim.InputPin;
import org.kr.cpu.sim.OutputPin;
import org.kr.cpu.sim.component.basic.Adder4;

public class Adder8 extends Component {
    public static final InputPin PIN_C0 = new InputPin("C0", 0);
    public static final InputPin[] PIN_A = initInputPins("A", 1, 8);
    public static final InputPin[] PIN_B = initInputPins("B", 9, 8);
    public static final OutputPin[] PIN_S = initOutputPins("S", 0, 8);
    public static final OutputPin PIN_C8 = new OutputPin("C8", 8);

    private final Adder4[] state = new Adder4[] {new Adder4("ADD1"), new Adder4("ADD2")};

    public Adder8(String id) { super(id, new boolean[17], new boolean[9]); }

    @Override
    public void updateOutput() {
        state[0].setInput(Adder4.PIN_C0.order, getInput(Adder8.PIN_C0.order), false);
        for(int i=0; i<4; i++) {
            state[0].setInput(Adder4.PIN_A[i].order, getInput(Adder8.PIN_A[i].order), false);
            state[0].setInput(Adder4.PIN_B[i].order, getInput(Adder8.PIN_B[i].order), i==3);
        }
        // internal carry between ADD1 and ADD2
        state[1].setInput(Adder4.PIN_C0.order, state[0].getOutput(Adder4.PIN_C4.order), false);
        for(int i=0; i<4; i++) {
            state[1].setInput(Adder4.PIN_A[i].order, getInput(Adder8.PIN_A[i+4].order), false);
            state[1].setInput(Adder4.PIN_B[i].order, getInput(Adder8.PIN_B[i+4].order), i==3);
        }

        for(int i = 0; i < 4; i++) {
            setOutput(PIN_S[i].order, state[0].getOutput(Adder4.PIN_S[i].order)); // ADD1 S
            setOutput(PIN_S[i+4].order, state[1].getOutput(Adder4.PIN_S[i].order)); // ADD2 S
        }
        setOutput(PIN_C8.order, state[1].getOutput(Adder4.PIN_C4.order)); // ADD2 C4
    }
}
