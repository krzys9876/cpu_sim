package org.kr.cpu.sim;

public class ZeroChecker extends Component {
    static final InputPin[] PIN_A = initInputPins("A", 0, 16);
    static final OutputPin PIN_Z = new OutputPin("Z", 0);

    public ZeroChecker(String id) {
        super(id, new boolean[16], new boolean[1]);
        for(int i=0; i<16; i++) setInput(i, false);
    }

    private final GateOr2x4[] gateOr = new GateOr2x4[] {new GateOr2x4("OR1"), new GateOr2x4("OR2")};
    private final GateOr2x4 gateOrResult = new GateOr2x4("ORR");
    private final GateNot gateNot = new GateNot("NOT");

    @Override
    protected void updateOutput() {
        //NOTE: the testability of below connections highly depends on input data. To actually fully test it we would need full truth table
        gateOrResult.setInput(GateOr2x4.PIN_A[0].order, gateOr[0].getOutput(GateOr2x4.PIN_Y[0].order));
        gateOrResult.setInput(GateOr2x4.PIN_B[0].order, gateOr[0].getOutput(GateOr2x4.PIN_Y[1].order));
        gateOrResult.setInput(GateOr2x4.PIN_C[0].order, gateOr[1].getOutput(GateOr2x4.PIN_Y[0].order));
        gateOrResult.setInput(GateOr2x4.PIN_D[0].order, gateOr[1].getOutput(GateOr2x4.PIN_Y[1].order));
        gateNot.setInput(0, gateOrResult.getOutput(GateOr2x4.PIN_Y[0].order));
        setOutput(PIN_Z.order, gateNot.getOutput(0));
    }

    @Override
    public void setInput(int pinNo, boolean value) {
        super.setInput(pinNo, value);

        if(pinNo<8) gateOr[0].setInput(pinNo, value);
        else gateOr[1].setInput(pinNo-8, value);

        updateOutput();
    }

    @Override
    public void setInput(int[] pinNo, boolean[] value) {
        super.setInput(pinNo, value);

        for(int i=0; i<pinNo.length; i++) {
            if (pinNo[i] < 8) gateOr[0].setInput(pinNo[i], value[i]);
            else gateOr[1].setInput(pinNo[i] - 8, value[i]);
        }
        updateOutput();
    }
}
