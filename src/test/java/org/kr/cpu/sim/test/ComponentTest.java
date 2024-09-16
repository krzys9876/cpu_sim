package org.kr.cpu.sim.test;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.kr.cpu.sim.DFlipFlop;
import org.kr.cpu.sim.Component;

public class ComponentTest {
    @Test
    @DisplayName("Truth table for D-FLipFlop")
    void testDFLipFlop() {
        // case no, input/output , pin
        boolean[][][] referenceTable = new boolean[][][] {
                {{false, false},{false, true}},
                {{false, true},{false, true}},
                {{false, false},{false, true}},
                {{true, false},{false, true}},
                {{true, true},{true, false}},
                {{true, false},{true, false}},
                {{false, false},{true, false}},
                {{false, true},{false, true}}
        };
        DFlipFlop dff = new DFlipFlop("FF1");
        testComponent(dff, referenceTable);
    }

    private void testComponent(Component comp, boolean[][][] reference) {
        for(int i=0; i<reference.length; i++) {
            for(int j=0; j<reference[i][0].length; j++) comp.setInput(j, reference[i][0][j]);
            for(int j=0; j<reference[i][1].length; j++) assert(comp.getOutput(j) == reference[i][1][j]);
        };
    }
}
