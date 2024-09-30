package org.kr.cpu.sim;

public class Adder8 extends Component {
    static final InputPin PIN_C0 = new InputPin("C0", 0);
    static final InputPin[] PIN_A = new InputPin[] {
            new InputPin("A1", 1), new InputPin("A2", 2), new InputPin("A3", 3), new InputPin("A4", 4),
            new InputPin("A5", 5), new InputPin("A6", 6), new InputPin("A7", 7), new InputPin("A8", 8)
    };
    static final InputPin[] PIN_B = new InputPin[] {
            new InputPin("B1", 9), new InputPin("B2", 10), new InputPin("B3", 11), new InputPin("B4", 12),
            new InputPin("B5", 13), new InputPin("B6", 14), new InputPin("B7", 15), new InputPin("B8", 16)
    };
    static final OutputPin[] PIN_S = new OutputPin[] {
            new OutputPin("Q1", 0), new OutputPin("Q2", 1), new OutputPin("Q3", 2), new OutputPin("Q4", 3),
            new OutputPin("Q5", 4), new OutputPin("Q6", 5), new OutputPin("Q7", 6), new OutputPin("Q8", 7)
    };
    static final OutputPin PIN_C8 = new OutputPin("C8", 8);

    private final Adder4[] state = new Adder4[] {new Adder4("ADD1"), new Adder4("ADD2")};

    public Adder8(String id) { super(id, new boolean[17], new boolean[9]); }

    // Always update internal carry between ADD1 and ADD2
    private void setInternalCarry() { state[1].setInput(0, state[0].getOutput(4)); }

    private void updateOutput() {
        for(int i = 0; i < 4; i++) {
            output[PIN_S[i].order] = state[0].output[Adder4.PIN_S[i].order];
            output[PIN_S[i+4].order] = state[1].output[Adder4.PIN_S[i].order];
        }
        output[PIN_C8.order] = state[1].output[Adder4.PIN_C4.order];
    }

    @Override
    public Component setInput(int pinNo, boolean value) {
        assert pinNo>=0 && pinNo<=16;

        if (pinNo == 0) { state[0].setInput(0, value); setInternalCarry(); }
        else if (pinNo <=4) { state[0].setInput(pinNo, value); setInternalCarry(); }
        else if (pinNo <=8) { state[1].setInput(pinNo-4, value); }
        else if (pinNo <=12) { state[0].setInput(pinNo-4, value); setInternalCarry(); }
        else  { state[1].setInput(pinNo-8, value); }

        updateOutput();
        return this;
    }
}
