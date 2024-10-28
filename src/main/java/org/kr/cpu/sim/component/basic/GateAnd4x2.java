package org.kr.cpu.sim.component.basic;

import org.kr.cpu.sim.Component;
import org.kr.cpu.sim.InputPin;
import org.kr.cpu.sim.OutputPin;

// 7408
public class GateAnd4x2 extends Component {
    public static final InputPin[] PIN_A = initInputPins("A", 0, 4);
    public static final InputPin[] PIN_B = initInputPins("B", 4, 4);
    public static final OutputPin[] PIN_Y = initOutputPins("Y", 0, 4);

    public GateAnd4x2(String id) {
        super(id, new boolean[8], new boolean[4]);
        for(int i=0; i<8; i++) setInput(i, false, i==7);
    }

    @Override
    public void updateOutput() {
        for(int i=0; i<PIN_A.length; i++)
            setOutput(PIN_Y[i].order, getInput(PIN_A[i].order) && getInput(PIN_B[i].order));
    }
}
