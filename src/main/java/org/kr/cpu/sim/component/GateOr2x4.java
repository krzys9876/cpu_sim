package org.kr.cpu.sim.component;

import org.kr.cpu.sim.Component;
import org.kr.cpu.sim.InputPin;
import org.kr.cpu.sim.OutputPin;

//74x4072
public class GateOr2x4 extends Component {
    public static final InputPin[] PIN_A = initInputPins("A", 0, 2);
    public static final InputPin[] PIN_B = initInputPins("B", 2, 2);
    public static final InputPin[] PIN_C = initInputPins("C", 4, 2);
    public static final InputPin[] PIN_D = initInputPins("D", 6, 2);
    public static final OutputPin[] PIN_Y = initOutputPins("Y", 0, 2);

    public GateOr2x4(String id) { super(id, new boolean[8], new boolean[2]);}

    @Override
    public void updateOutput() {
        setOutput(PIN_Y[0].order, getInput(PIN_A[0].order) || getInput(PIN_B[0].order) || getInput(PIN_C[0].order) || getInput(PIN_D[0].order));
        setOutput(PIN_Y[1].order, getInput(PIN_A[1].order) || getInput(PIN_B[1].order) || getInput(PIN_C[1].order) || getInput(PIN_D[1].order));
    }
}
