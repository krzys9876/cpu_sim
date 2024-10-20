package org.kr.cpu.sim;

import java.util.Arrays;

public class ZeroChecker extends Component {
    static final InputPin[] PIN_A = initInputPins("A", 0, 16);
    static final OutputPin PIN_Z = new OutputPin("Z", 0);

    public ZeroChecker(String id) {
        super(id, new boolean[16], new boolean[1]);
        for(int i=0; i<16; i++) setInput(i, false);
    }
    @Override
    public Component setInput(int pinNo, boolean value) {
        assert pinNo < 16;

        setInputDirect(pinNo, value);
        setOutput(PIN_Z.order, !Arrays.stream(PIN_A).anyMatch(i -> getInput(i.order)));
        return this;
    }
}
