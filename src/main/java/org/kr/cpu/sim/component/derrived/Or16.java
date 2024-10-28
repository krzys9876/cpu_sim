package org.kr.cpu.sim.component.derrived;

import org.kr.cpu.sim.Component;
import org.kr.cpu.sim.InputPin;
import org.kr.cpu.sim.OutputPin;
import org.kr.cpu.sim.component.basic.GateAnd4x2;
import org.kr.cpu.sim.component.basic.GateOr4x2;

public class Or16 extends Component {
    public static final InputPin[] PIN_A = initInputPins("A", 0, 16);
    public static final InputPin[] PIN_B = initInputPins("B", 16, 16);
    public static final OutputPin[] PIN_Y = initOutputPins("Y", 0, 16);

    public Or16(String id) { super(id, new boolean[16*2], new boolean[16]);}

    private final GateOr4x2[] ors = new GateOr4x2[] {new GateOr4x2("OR1"), new GateOr4x2("OR2"),
            new GateOr4x2("OR3"), new GateOr4x2("OR4")};

    @Override
    public void updateOutput() {
        for(int i=0; i<ors.length; i++) {
            for(int j = 0; j< GateAnd4x2.PIN_A.length; j++) {
                ors[i].setInput(GateAnd4x2.PIN_A[j].order, getInput(PIN_A[i*4+j].order), false);
                ors[i].setInput(GateAnd4x2.PIN_B[j].order, getInput(PIN_B[i*4+j].order), true);
                setOutput(PIN_Y[i*4+j].order, ors[i].getOutput(GateAnd4x2.PIN_Y[j].order));
            }
        }
    }
}