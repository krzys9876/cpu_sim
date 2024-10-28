package org.kr.cpu.sim;

public class ZeroChecker extends Component {
    static final InputPin[] PIN_A = initInputPins("A", 0, 16);
    static final OutputPin PIN_Z = new OutputPin("Z", 0);

    public ZeroChecker(String id) {
        super(id, new boolean[16], new boolean[1]);
        for(int i=0; i<16; i++) setInput(i, false, i==15);
    }

    private final GateOr2x4[] gateOr = new GateOr2x4[] {new GateOr2x4("OR1"), new GateOr2x4("OR2")};
    private final GateOr2x4 gateOrResult = new GateOr2x4("ORR");
    private final GateNot gateNot = new GateNot("NOT");

    @Override
    protected void updateOutput() {
        //NOTE: the testability of below connections highly depends on input data. To actually fully test it we would need full truth table
        //NOTE2: GateOr2x4 has pins A, B, C, D, and it makes iteration over PIN arrays difficult, this is more readable
        for(int i=0; i<8; i++) {
            gateOr[0].setInput(i, getInput(PIN_A[i].order), i == 7);
            gateOr[1].setInput(i, getInput(PIN_A[i+8].order), i == 7);
        }

        gateOrResult.setInput(GateOr2x4.PIN_A[0].order, gateOr[0].getOutput(GateOr2x4.PIN_Y[0].order), true);
        gateOrResult.setInput(GateOr2x4.PIN_B[0].order, gateOr[0].getOutput(GateOr2x4.PIN_Y[1].order),true);
        gateOrResult.setInput(GateOr2x4.PIN_C[0].order, gateOr[1].getOutput(GateOr2x4.PIN_Y[0].order),true);
        gateOrResult.setInput(GateOr2x4.PIN_D[0].order, gateOr[1].getOutput(GateOr2x4.PIN_Y[1].order),true);
        gateNot.setInput(0, gateOrResult.getOutput(GateOr2x4.PIN_Y[0].order),true);
        setOutput(PIN_Z.order, gateNot.getOutput(0));
    }
}
