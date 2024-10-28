package org.kr.cpu.sim.component;

import org.kr.cpu.sim.Component;
import org.kr.cpu.sim.InputPin;
import org.kr.cpu.sim.OutputPin;

public class Xor16 extends Component {
    public static final InputPin[] PIN_A = initInputPins("A", 0, 16);
    public static final InputPin[] PIN_B = initInputPins("B", 16, 16);
    public static final OutputPin[] PIN_Y = initOutputPins("Y", 0, 16);

    public Xor16(String id) { super(id, new boolean[16*2], new boolean[16]);}

    private final GateXor4x2[] xors = new GateXor4x2[] {new GateXor4x2("XOR1"), new GateXor4x2("XOR2"),
            new GateXor4x2("XOR3"), new GateXor4x2("XOR4")};

    @Override
    public void updateOutput() {
        for(int i = 0; i< xors.length; i++) {
            for(int j=0; j<GateAnd4x2.PIN_A.length; j++) {
                xors[i].setInput(GateAnd4x2.PIN_A[j].order, getInput(PIN_A[i*4+j].order), false);
                xors[i].setInput(GateAnd4x2.PIN_B[j].order, getInput(PIN_B[i*4+j].order), true);
                setOutput(PIN_Y[i*4+j].order, xors[i].getOutput(GateAnd4x2.PIN_Y[j].order));
            }
        }
    }
}
