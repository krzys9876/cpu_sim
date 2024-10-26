package org.kr.cpu.sim;

public class Incrementer16 extends Component {
    static final InputPin[] PIN_A = initInputPins("A", 0, 16);
    static final OutputPin[] PIN_Y = initOutputPins("Y", 0, 16);
    static final OutputPin PIN_C = new OutputPin("C", 16);

    private final Adder16 adder = new Adder16("ADD");

    public Incrementer16(String id) {
        super(id, new boolean[16], new boolean[17]);
        // set C0 to 0 and B to 1 (add 1)
        adder.setInput(Adder16.PIN_C0.order, false);
        adder.setInput(Adder16.PIN_B[0].order, true);
        for (int i = Adder16.PIN_B[1].order; i <= Adder16.PIN_B[15].order; i++) adder.setInput(i, false);
    }

    @Override
    protected void updateOutput() {
        for (int i = 0; i < 16; i++) setOutput(PIN_Y[i].order, adder.getOutput(Adder16.PIN_S[i].order));
        setOutput(PIN_C.order, adder.getOutput(Adder16.PIN_C16.order)); // carry
    }

    @Override
    public void setInput(int pinNo, boolean value) {
        super.setInput(pinNo, value);
        adder.setInput(Adder16.PIN_A[pinNo].order, value);
        updateOutput();
    }
}
