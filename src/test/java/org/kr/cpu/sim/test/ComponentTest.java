package org.kr.cpu.sim.test;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.kr.cpu.sim.Counter4;
import org.kr.cpu.sim.DFlipFlop;
import org.kr.cpu.sim.Component;

public class ComponentTest {
    @Test
    @DisplayName("Sample sequence for D-FLipFlop")
    void testDFLipFlop() {
        // case no, input/output , pin
        boolean[][][] referenceTable = new boolean[][][] {
                {{false, false},{true, false}},
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

    @Test
    @DisplayName("Truth table for Counter4")
    void testCounter4() {
        // case no, input/output , pin
        boolean[][][] referenceTable = new boolean[][][] {
                {{false},{true, true, true, true, false}},
                {{true},{false, false, false, false, true}},
                {{false},{false, false, false, false, true}},
                {{true},{true, false, false, false, true}},
                {{false},{true, false, false, false, true}},
                {{true},{false, true, false, false, true}},
                {{false},{false, true, false, false, true}},
                {{true},{true, true, false, false, true}},
                {{false},{true, true, false, false, true}},
                {{true},{false, false, true, false, true}},
                {{false},{false, false, true, false, true}},
                {{true},{true, false, true, false, true}},
                {{false},{true, false, true, false, true}},
                {{true},{false, true, true, false, true}},
                {{false},{false, true, true, false, true}},
                {{true},{true, true, true, false, true}},
                {{false},{true, true, true, false, true}},
                {{true},{false, false, false, true, false}},
                {{false},{false, false, false, true, false}},
                {{true},{true, false, false, true, false}},
                {{false},{true, false, false, true, false}},
                {{true},{false, true, false, true, false}},
                {{false},{false, true, false, true, false}},
                {{true},{true, true, false, true, false}},
                {{false},{true, true, false, true, false}},
                {{true},{false, false, true, true, false}},
                {{false},{false, false, true, true, false}},
                {{true},{true, false, true, true, false}},
                {{false},{true, false, true, true, false}},
                {{true},{false, true, true, true, false}},
                {{false},{false, true, true, true, false}},
                {{true},{true, true, true, true, false}},
                {{false},{true, true, true, true, false}},
                {{true},{false, false, false, false, true}}
        };
        Counter4 cnt = new Counter4("CNT4");
        testComponent(cnt, referenceTable);
    }

    private void testComponent(Component comp, boolean[][][] reference) {
        for (boolean[][] testData : reference) {
            for (int j = 0; j < testData[0].length; j++) comp.setInput(j, testData[0][j]);
            for (int j = 0; j < testData[1].length; j++) assert (comp.getOutput(j) == testData[1][j]);
        }
    }
}
