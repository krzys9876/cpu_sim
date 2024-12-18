package org.kr.cpu.sim.component.basic;

import org.kr.cpu.sim.Component;
import org.kr.cpu.sim.InputPin;
import org.kr.cpu.sim.OutputPin;

public class And16 extends Component {
    public static final InputPin[] PIN_A = initInputPins("A", 0, 16);
    public static final InputPin[] PIN_B = initInputPins("B", 16, 16);
    public static final OutputPin[] PIN_Y = initOutputPins("Y", 0, 16);

    public And16(String id) { super(id, new boolean[16*2], new boolean[16]);}

    private final GateAnd4x2[] ands = new GateAnd4x2[] {new GateAnd4x2("AND1"), new GateAnd4x2("AND2"),
            new GateAnd4x2("AND3"), new GateAnd4x2("AND4")};

    @Override
    public void updateOutput() {
        for(int i=0; i<ands.length; i++) {
            for(int j=0; j<GateAnd4x2.PIN_A.length; j++) {
                ands[i].setInput(GateAnd4x2.PIN_A[j].order, getInput(PIN_A[i*4+j].order), false);
                ands[i].setInput(GateAnd4x2.PIN_B[j].order, getInput(PIN_B[i*4+j].order), true);
                setOutput(PIN_Y[i*4+j].order, ands[i].getOutput(GateAnd4x2.PIN_Y[j].order));
            }
        }
    }
}
