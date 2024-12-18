package org.kr.cpu.sim.component.derrived;

import org.kr.cpu.sim.Component;
import org.kr.cpu.sim.InputPin;
import org.kr.cpu.sim.OutputPin;
import org.kr.cpu.sim.component.basic.GateNot;
import org.kr.cpu.sim.component.basic.LineDriver8;

public class LineSelector2 extends Component {
    public static final InputPin PIN_S = new InputPin("S", 0); // 0 - Y=a, 1 - Y=B
    public static final InputPin[] PIN_A = initInputPins("A", 1, 16);
    public static final InputPin[] PIN_B = initInputPins("B", 17, 16);
    public static final OutputPin[] PIN_Y = initOutputPins("Y", 0, 16);

    public LineSelector2(String id) { super(id, new boolean[1+2*16], new boolean[16]); }

    private final GateNot select = new GateNot("NOT");
    private final LineDriver8[] lines = new LineDriver8[] {
            new LineDriver8("LA1"), new LineDriver8("LA2"),new LineDriver8("LB1"), new LineDriver8("LB2"),
    };

    @Override
    public void updateOutput() {
        select.setInput(GateNot.PIN_A.order, getInput(PIN_S.order), true);
        lines[0].setInput(LineDriver8.PIN_EN.order, getInput(PIN_S.order), true); // S = 0 -> A
        lines[1].setInput(LineDriver8.PIN_EN.order, getInput(PIN_S.order), true);
        lines[2].setInput(LineDriver8.PIN_EN.order, select.getOutput(GateNot.PIN_Y.order), true); // S=1 -> B
        lines[3].setInput(LineDriver8.PIN_EN.order, select.getOutput(GateNot.PIN_Y.order), true);

        for(int i=0; i<LineDriver8.PIN_A.length; i++) {
            lines[0].setInput(LineDriver8.PIN_A[i].order, getInput(PIN_A[i].order), i == LineDriver8.PIN_A.length-1);
            lines[1].setInput(LineDriver8.PIN_A[i].order, getInput(PIN_A[i+8].order), i == LineDriver8.PIN_A.length-1);
            lines[2].setInput(LineDriver8.PIN_A[i].order, getInput(PIN_B[i].order), i == LineDriver8.PIN_A.length-1);
            lines[3].setInput(LineDriver8.PIN_A[i].order, getInput(PIN_B[i+8].order), i == LineDriver8.PIN_A.length-1);
        }

        boolean selected = getInput(PIN_S.order); // 1 -> B, 0 -> A
        for(int i=0; i<8; i++) {
            setOutput(PIN_Y[i].order, (selected ? lines[2] : lines[0]).getOutput(LineDriver8.PIN_Y[i].order));
            setOutput(PIN_Y[i+8].order, (selected ? lines[3] : lines[1]).getOutput(LineDriver8.PIN_Y[i].order));
        }
    }
}
