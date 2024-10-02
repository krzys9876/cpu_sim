package org.kr.cpu.sim;

public class SignFlipper16 extends Component {
    static final InputPin[] PIN_A = initInputPins("A", 0, 16);
    static final OutputPin[] PIN_Y = initOutputPins("Y", 0, 16);

    private final Inverter18 inverter = new Inverter18("INV");
    private final Incrementer16 incrementer = new Incrementer16("INC");

    public SignFlipper16(String id) { super(id, new boolean[16], new boolean[16]);}

    @Override
    public Component setInput(int pinNo, boolean value) {
        assert pinNo>=0 && pinNo<=16;

        setInputDirect(pinNo, value);
        inverter.setInput(pinNo, value);
        for (int i = 0; i < 16; i++) incrementer.setInput(Incrementer16.PIN_A[i].order, inverter.getOutput(Incrementer16.PIN_Y[i].order));
        for (int i = 0; i < 16; i++) setOutput(i, incrementer.getOutput(Incrementer16.PIN_Y[i].order));
        return this;
    }
}