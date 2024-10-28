package org.kr.cpu.sim;

public class Adder16 extends Component {
    static final InputPin PIN_C0 = new InputPin("C0", 0);
    static final InputPin[] PIN_A = initInputPins("A", 1, 16);
    static final InputPin[] PIN_B = initInputPins("B", 17, 16);
    static final OutputPin[] PIN_S = initOutputPins("S", 0, 16);

    static final OutputPin PIN_C16 = new OutputPin("C8", 16);

    private final Adder8[] state = new Adder8[] {new Adder8("ADD1"), new Adder8("ADD2")};

    public Adder16(String id) { super(id, new boolean[2*16+1], new boolean[16+1]); }

    @Override
    protected void updateOutput() {
        state[0].setInput(Adder8.PIN_C0.order, getInput(Adder16.PIN_C0.order), false);
        for(int i=0; i<8; i++) {
            state[0].setInput(Adder8.PIN_A[i].order, getInput(Adder16.PIN_A[i].order), false);
            state[0].setInput(Adder8.PIN_B[i].order, getInput(Adder16.PIN_B[i].order), i==7);
        }
        // internal carry between ADD1 and ADD2
        state[1].setInput(Adder8.PIN_C0.order, state[0].getOutput(Adder8.PIN_C8.order), false);
        for(int i=0; i<8; i++) {
            state[1].setInput(Adder8.PIN_A[i].order, getInput(Adder16.PIN_A[i+8].order), false);
            state[1].setInput(Adder8.PIN_B[i].order, getInput(Adder16.PIN_B[i+8].order), i==7);
        }

        for(int i = 0; i < 8; i++) {
            setOutput(PIN_S[i].order, state[0].getOutput(Adder8.PIN_S[i].order));
            setOutput(PIN_S[i+8].order, state[1].getOutput(Adder8.PIN_S[i].order));
        }
        setOutput(PIN_C16.order, state[1].getOutput(Adder8.PIN_C8.order));
    }
}

