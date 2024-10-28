package org.kr.cpu.sim.component.derrived;

import org.kr.cpu.sim.Component;
import org.kr.cpu.sim.InputPin;
import org.kr.cpu.sim.OutputPin;
import org.kr.cpu.sim.component.basic.GateNot6;

public class Inverter18 extends Component {
    public static final InputPin[] PIN_A = initInputPins("A", 0, 18);
    public static final OutputPin[] PIN_Y = initOutputPins("Y", 0, 18);

    public Inverter18(String id) {
        super(id, new boolean[18], new boolean[18]);
        for(int i=0; i<18; i++) setInput(i, false, i==17);
    }

    private final GateNot6[] state = new GateNot6[] {new GateNot6("NOT1"), new GateNot6("NOT2"), new GateNot6("NOT3")};

    @Override
    public void updateOutput() {
        for(int i=0; i<state.length; i++)
            for(int j=0; j<GateNot6.PIN_Y.length; j++)
                state[i].setInput(GateNot6.PIN_A[j].order, getInput(PIN_A[i*6+j].order), j == GateNot6.PIN_A.length-1);

        for(int i=0; i<state.length; i++)
            for(int j=0; j<GateNot6.PIN_Y.length; j++)
                setOutput(PIN_Y[i*6+j].order, state[i].getOutput(GateNot6.PIN_A[j].order));
    }
}
