package org.kr.cpu.sim;

// 7404
public class GateNot6 extends Component {
    static final InputPin[] PIN_A = initInputPins("A", 0, 6);
    static final OutputPin[] PIN_Y = initOutputPins("Y", 0, 6);

    public GateNot6(String id) {
        super(id, new boolean[6], new boolean[6]);
        for(int i=0; i<6; i++) setInput(i, false, i==5);
    }

    @Override
    public void updateOutput() {
        for(int i=0; i<PIN_A.length; i++) setOutput(PIN_Y[i].order, !getInput(PIN_A[i].order));
    }
}
