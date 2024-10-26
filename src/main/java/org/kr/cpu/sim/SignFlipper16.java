package org.kr.cpu.sim;

public class SignFlipper16 extends Component {
    static final InputPin[] PIN_A = initInputPins("A", 0, 16);
    static final OutputPin[] PIN_Y = initOutputPins("Y", 0, 16);

    private final Inverter18 inverter = new Inverter18("INV");
    private final Incrementer16 incrementer = new Incrementer16("INC");

    public SignFlipper16(String id) { super(id, new boolean[16], new boolean[16]);}

    @Override
    protected void updateOutput() {
        for (int i = 0; i < 16; i++) incrementer.setInput(Incrementer16.PIN_A[i].order, inverter.getOutput(Incrementer16.PIN_Y[i].order));
        for (int i = 0; i < 16; i++) setOutput(i, incrementer.getOutput(Incrementer16.PIN_Y[i].order));
    }

    @Override
    public void setInput(int pinNo, boolean value) {
        super.setInput(pinNo, value);
        inverter.setInput(Inverter18.PIN_A[pinNo].order, value);
        updateOutput();
    }

    @Override
    public void setInput(int[] pinNo, boolean[] value) {
        super.setInput(pinNo, value);
        for(int i = 0; i < pinNo.length; i++)
            inverter.setInput(Inverter18.PIN_A[pinNo[i]].order, value[i]);
        updateOutput();
    }
}