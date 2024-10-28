package org.kr.cpu.sim.component.derrived;

import org.kr.cpu.sim.Component;
import org.kr.cpu.sim.InputPin;
import org.kr.cpu.sim.OutputPin;
import org.kr.cpu.sim.component.basic.LineDriver8;

public class LineDriver16 extends Component {
    public static final InputPin PIN_EN = new InputPin("EN", 0);
    public static final InputPin[] PIN_A = initInputPins("A", 1, 16);
    public static final OutputPin[] PIN_Y = initOutputPins("Y", 0, 16);

    public LineDriver16(String id) { super(id, new boolean[17], new boolean[16]); }

    private final LineDriver8[] lines = new LineDriver8[] {new LineDriver8("L1"), new LineDriver8("L2")};

    @Override
    public void updateOutput() {
        lines[0].setInput(LineDriver8.PIN_EN.order, getInput(PIN_EN.order), false);
        lines[1].setInput(LineDriver8.PIN_EN.order, getInput(PIN_EN.order), false);
        if(!getInput(PIN_EN.order)) { // EN active low
            for (int i = 0; i < 8; i++) {
                lines[0].setInput(LineDriver8.PIN_A[i].order, getInput(PIN_A[i].order), i==7);
                lines[1].setInput(LineDriver8.PIN_A[i].order, getInput(PIN_A[i + 8].order), i==7);
            }
            for (int i = 0; i < 8; i++) {
                setOutput(PIN_Y[i].order, lines[0].getOutput(LineDriver8.PIN_Y[i].order));
                setOutput(PIN_Y[i + 8].order, lines[1].getOutput(LineDriver8.PIN_Y[i].order));
            }
        }
    }
}
