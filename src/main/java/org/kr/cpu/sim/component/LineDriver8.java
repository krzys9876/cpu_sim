package org.kr.cpu.sim.component;

import org.kr.cpu.sim.Component;
import org.kr.cpu.sim.InputPin;
import org.kr.cpu.sim.OutputPin;

public class LineDriver8 extends Component {
    public static final InputPin PIN_EN = new InputPin("EN", 0);
    public static final InputPin[] PIN_A = initInputPins("A", 1, 8);
    public static final OutputPin[] PIN_Y = initOutputPins("Y", 0, 8);

    public LineDriver8(String id) { super(id, new boolean[9], new boolean[8]); }

    @Override
    public void updateOutput() {
        if(!getInput(PIN_EN.order)) // EN active low
            for (int i=0; i<8; i++) setOutput(PIN_Y[i].order, getInput(PIN_A[i].order));
    }
}
