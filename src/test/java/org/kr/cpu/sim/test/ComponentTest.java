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
        DFlipFlop dff = new DFlipFlop("FF1");
        testComponent(dff, new TestReference("DFlipFlop.txt", 2,2));
    }

    @Test
    @DisplayName("Truth table for Counter4")
    void testCounter4() {
        Counter4 cnt = new Counter4("CNT4");
        testComponent(cnt, new TestReference("Counter4.txt", 1, 5));
    }

    private void testComponent(Component comp, TestReference reference) {
        //reference.print();
        for (int i = 0; i < reference.size(); i++) {
            TestReferenceRow row = reference.getRow(i);
            for (int j = 0; j < row.getInputSize(); j++) comp.setInput(j, row.getInput(j));
            for (int j = 0; j < row.getOutputSize(); j++) assert (comp.getOutput(j) == row.getOutput(j));
        }
    }
}