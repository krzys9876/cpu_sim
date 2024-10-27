package org.kr.cpu.sim;

public class Adder8 extends Component {
    static final InputPin PIN_C0 = new InputPin("C0", 0);
    static final InputPin[] PIN_A = initInputPins("A", 1, 8);
    static final InputPin[] PIN_B = initInputPins("B", 9, 8);
    static final OutputPin[] PIN_S = initOutputPins("S", 0, 8);
    static final OutputPin PIN_C8 = new OutputPin("C8", 8);

    private final Adder4[] state = new Adder4[] {new Adder4("ADD1"), new Adder4("ADD2")};

    public Adder8(String id) { super(id, new boolean[17], new boolean[9]); }

    // Always update internal carry between ADD1 and ADD2
    private void setInternalCarry() { state[1].setInput(Adder4.PIN_C0.order, state[0].getOutput(Adder4.PIN_C4.order)); }

    @Override
    protected void updateOutput() {
        for(int i = 0; i < 4; i++) {
            setOutput(PIN_S[i].order, state[0].getOutput(Adder4.PIN_S[i].order)); // ADD1 S
            setOutput(PIN_S[i+4].order, state[1].getOutput(Adder4.PIN_S[i].order)); // ADD2 S
        }
        setOutput(PIN_C8.order, state[1].getOutput(Adder4.PIN_C4.order)); // ADD2 C4
    }

    @Override
    public void setInput(int pinNo, boolean value, boolean shouldRefresh) {
        super.setInput(pinNo, value, false);

        if (pinNo == 0) { state[0].setInput(Adder4.PIN_C0.order, value); setInternalCarry(); } // ADD1 C0
        else if (pinNo <=4) { state[0].setInput(Adder4.PIN_A[pinNo-1].order, value); setInternalCarry(); } // ADD1 A
        else if (pinNo <=8) { state[1].setInput(Adder4.PIN_A[pinNo-5].order, value); } // ADD2 A
        else if (pinNo <=12) { state[0].setInput(Adder4.PIN_B[pinNo-9].order, value); setInternalCarry(); } // ADD1 B
        else  { state[1].setInput(Adder4.PIN_B[pinNo-13].order, value); } // ADD2 B

        if(shouldRefresh) updateOutput();
    }
}
