package org.kr.cpu.sim;

public class LineSelector2 extends Component {
    static final InputPin PIN_S = new InputPin("S", 0); // 0 - Y=a, 1 - Y=B
    static final InputPin[] PIN_A = initInputPins("A", 1, 16);
    static final InputPin[] PIN_B = initInputPins("B", 17, 16);
    static final OutputPin[] PIN_Y = initOutputPins("Y", 0, 16);

    public LineSelector2(String id) { super(id, new boolean[1+2*16], new boolean[16]); }

    private final GateNot select = new GateNot("NOT");
    private final LineDriver8[] lines = new LineDriver8[] {
            new LineDriver8("LA1"), new LineDriver8("LA2"),new LineDriver8("LB1"), new LineDriver8("LB2"),
    };

    @Override
    protected void updateOutput() {
        boolean selected = getInput(PIN_S.order); // 1 -> B, 0 -> A
        for(int i=0; i<8; i++) {
            setOutput(PIN_Y[i].order, (selected ? lines[2] : lines[0]).getOutput(LineDriver8.PIN_Y[i].order));
            setOutput(PIN_Y[i+8].order, (selected ? lines[3] : lines[1]).getOutput(LineDriver8.PIN_Y[i].order));
        }
    }

    @Override
    public void setInput(int pinNo, boolean value) {
        super.setInput(pinNo, value);

        if(pinNo == PIN_S.order) {
            select.setInput(GateNot.PIN_A.order, value);
            lines[0].setInput(LineDriver8.PIN_EN.order, value); // S = 0 -> A
            lines[1].setInput(LineDriver8.PIN_EN.order, value);
            lines[2].setInput(LineDriver8.PIN_EN.order, select.getOutput(GateNot.PIN_Y.order)); // S=1 -> B
            lines[3].setInput(LineDriver8.PIN_EN.order, select.getOutput(GateNot.PIN_Y.order));
        }
        else if(pinNo<=PIN_A[7].order) lines[0].setInput(LineDriver8.PIN_A[pinNo-1].order, value);
        else if(pinNo<=PIN_A[15].order) lines[1].setInput(LineDriver8.PIN_A[pinNo-8-1].order, value);
        else if(pinNo<=PIN_B[7].order) lines[2].setInput(LineDriver8.PIN_A[pinNo-16-1].order, value);
        else lines[3].setInput(LineDriver8.PIN_A[pinNo-16-8-1].order, value);

        updateOutput();
    }

    @Override
    public void setInput(int[] pinNo, boolean[] value) {
        super.setInput(pinNo, value);

        for(int i=0; i<pinNo.length; i++) {
            if (pinNo[i] == PIN_S.order) {
                select.setInput(GateNot.PIN_A.order, value[i]);
                lines[0].setInput(LineDriver8.PIN_EN.order, value[i]); // S = 0 -> A
                lines[1].setInput(LineDriver8.PIN_EN.order, value[i]);
                lines[2].setInput(LineDriver8.PIN_EN.order, select.getOutput(GateNot.PIN_Y.order)); // S=1 -> B
                lines[3].setInput(LineDriver8.PIN_EN.order, select.getOutput(GateNot.PIN_Y.order));
            } else if (pinNo[i] <= PIN_A[7].order) lines[0].setInput(LineDriver8.PIN_A[pinNo[i] - 1].order, value[i]);
            else if (pinNo[i] <= PIN_A[15].order) lines[1].setInput(LineDriver8.PIN_A[pinNo[i] - 8 - 1].order, value[i]);
            else if (pinNo[i] <= PIN_B[7].order) lines[2].setInput(LineDriver8.PIN_A[pinNo[i] - 16 - 1].order, value[i]);
            else lines[3].setInput(LineDriver8.PIN_A[pinNo[i] - 16 - 8 - 1].order, value[i]);
        }

        updateOutput();
    }
}
