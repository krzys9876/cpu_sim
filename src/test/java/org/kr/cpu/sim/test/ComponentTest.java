package org.kr.cpu.sim.test;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.kr.cpu.sim.*;

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

    @Test
    @DisplayName(("Truth table for 4-to-16 decoder (74154)"))
    void testDecoder4To16() {
        Decoder416 dec = new Decoder416("DEC");
        testComponent(dec, new TestReference("Decoder4To16.txt", 6, 16));
    }

    @Test
    @DisplayName(("Truth table for 4-bit adder (7483)"))
    void testAdder4() {
        Adder4 adder = new Adder4("ADD4");
        testComponent(adder, new TestReference("Adder4.txt", 9, 5));
    }

    @Test
    @DisplayName(("Truth table for 8-bit adder (2 x 7483)"))
    void testAdder8() {
        Adder8 adder = new Adder8("ADD8");
        testComponent(adder, new TestReference("Adder8.txt", 17, 9));
    }

    @Test
    @DisplayName(("Truth table for 16-bit adder (2 x 2 x 7483)"))
    void testAdder16() {
        Adder16 adder = new Adder16("ADD16");
        testComponent(adder, new TestReference("Adder16.txt", 2*16+1, 16+1));
    }

    @Test
    @DisplayName(("Truth table for 1-bit inverter"))
    void testGateNot() {
        GateNot not1 = new GateNot("NOT1");
        testComponent(not1, new TestReference("GateNot.txt", 1, 1));
    }

    @Test
    @DisplayName(("Truth table for 1-bit AND gate"))
    void testGateAnd() {
        GateAnd and1 = new GateAnd("AND1");
        testComponent(and1, new TestReference("GateAnd.txt", 2, 1));
    }

    @Test
    @DisplayName(("Truth table for 6-bit inverter (7404)"))
    void testGateNot6() {
        GateNot6 not6 = new GateNot6("NOT6");
        testComponent(not6, new TestReference("GateNot6.txt", 6, 6));
    }

    @Test
    @DisplayName(("Truth table for 18-bit inverter (3 x 7404)"))
    void testInverter18() {
        Inverter18 inv = new Inverter18("NOT18");
        testComponent(inv, new TestReference("Inverter18.txt", 18, 18));
    }

    @Test
    @DisplayName(("Truth table for 16-bit incrementer"))
    void testIncrementer16() {
        Incrementer16 inc = new Incrementer16("INC16");
        testComponent(inc, new TestReference("Incrementer16.txt", 16, 17));
    }

    @Test
    @DisplayName(("Truth table for 16-bit sign flipper"))
    void testSignFlipper16() {
        SignFlipper16 flip = new SignFlipper16("FLIP16");
        testComponent(flip, new TestReference("SignFlipper16.txt", 16, 16));
    }

    private void testComponent(Component comp, TestReference reference) {
        //reference.print();
        for (int i = 0; i < reference.size(); i++) {
            TestReferenceRow row = reference.getRow(i);
            //row.print();
            for (int j = 0; j < row.getInputSize(); j++) comp.setInput(j, row.getInput(j));
            for (int j = 0; j < row.getOutputSize(); j++) assert (comp.getOutput(j) == row.getOutput(j));
        }
    }
}