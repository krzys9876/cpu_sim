package org.kr.cpu.sim;

public class Adder16 extends Component {
    static final InputPin PIN_C0 = new InputPin("C0", 0);
    static final InputPin[] PIN_A = initInputPins("A", 1, 16);
    static final InputPin[] PIN_B = initInputPins("B", 17, 16);
    static final OutputPin[] PIN_S = initOutputPins("S", 0, 16);

    static final OutputPin PIN_C16 = new OutputPin("C8", 16);

    private final Adder8[] state = new Adder8[] {new Adder8("ADD1"), new Adder8("ADD2")};

    public Adder16(String id) { super(id, new boolean[2*16+1], new boolean[16+1]); }

    // Always update internal carry between ADD1 and ADD2
    private void setInternalCarry() { state[1].setInput(Adder8.PIN_C0.order, state[0].getOutput(Adder8.PIN_C8.order)); }

    @Override
    protected void updateOutput() {
        for(int i = 0; i < 8; i++) {
            setOutput(PIN_S[i].order, state[0].getOutput(Adder8.PIN_S[i].order));
            setOutput(PIN_S[i+8].order, state[1].getOutput(Adder8.PIN_S[i].order));
        }
        setOutput(PIN_C16.order, state[1].getOutput(Adder8.PIN_C8.order));
    }

    @Override
    public void setInput(int pinNo, boolean value) {
        assert pinNo>=0 && pinNo<=33;

        if (pinNo == 0) { state[0].setInput(Adder8.PIN_C0.order, value); setInternalCarry(); } // ADD1 C0
        else if (pinNo <=8) { state[0].setInput(Adder8.PIN_A[pinNo-1].order, value); setInternalCarry(); } // ADD1 A
        else if (pinNo <=16) { state[1].setInput(Adder8.PIN_A[pinNo-9].order, value); } // ADD2 A
        else if (pinNo <=24) { state[0].setInput(Adder8.PIN_B[pinNo-17].order, value); setInternalCarry(); } // ADD1 B
        else  { state[1].setInput(Adder8.PIN_B[pinNo-25].order, value); } // ADD2 B

        updateOutput();
    }
}

