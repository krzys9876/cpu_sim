package org.kr.cpu.sim.component.derrived;

import org.kr.cpu.sim.Component;
import org.kr.cpu.sim.InputPin;
import org.kr.cpu.sim.OutputPin;

public class Incrementer16 extends Component {
    public static final InputPin[] PIN_A = initInputPins("A", 0, 16);
    public static final OutputPin[] PIN_Y = initOutputPins("Y", 0, 16);
    public static final OutputPin PIN_C = new OutputPin("C", 16);

    private final Adder16 adder = new Adder16("ADD");

    public Incrementer16(String id) {
        super(id, new boolean[16], new boolean[17]);
        // set C0 to 0 and B to 1 (add 1)
        adder.setInput(Adder16.PIN_C0.order, false, false);
        adder.setInput(Adder16.PIN_B[0].order, true, false);
        for (int i = Adder16.PIN_B[1].order; i <= Adder16.PIN_B[15].order; i++) adder.setInput(i, false, i == Adder16.PIN_B[15].order-1);
    }

    @Override
    public void updateOutput() {
        for (int i = 0; i < PIN_A.length; i++) adder.setInput(Adder16.PIN_A[i].order, getInput(PIN_A[i].order), i == PIN_A.length - 1);
        for (int i = 0; i < PIN_Y.length; i++) setOutput(PIN_Y[i].order, adder.getOutput(Adder16.PIN_S[i].order));
        setOutput(PIN_C.order, adder.getOutput(Adder16.PIN_C16.order)); // carry
    }
}
