package org.kr.cpu.sim.test;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.kr.cpu.sim.*;
import org.kr.cpu.sim.component.basic.*;
import org.kr.cpu.sim.component.derrived.*;

public class ComponentTest {
    @Test
    @DisplayName("Sample sequence for D-FLipFlop (half 7474)")
    void testDFLipFlop() {
        DFlipFlop dff = new DFlipFlop("FF1");
        testComponent(dff, new TestReference("DFlipFlop.txt", 2,2));
    }

    @Test
    @DisplayName("Sample sequence for 8-bit latch (74374)")
    void testLatch8() {
        Latch8 l8 = new Latch8("L8");
        testComponent(l8, new TestReference("Latch8.txt", 10,8));
    }

    @Test
    @DisplayName("Sample sequence for 16-bit latch (2x74374)")
    void testLatch16() {
        Latch16 l16 = new Latch16("L16");
        testComponent(l16, new TestReference("Latch16.txt", 18,16));
    }

    @Test
    @DisplayName("Truth table for Counter4")
    void testCounter4() {
        Counter4 cnt = new Counter4("CNT4");
        testComponent(cnt, new TestReference("Counter4.txt", 1, 5));
    }

    @Test
    @DisplayName("Truth table for Counter8")
    void testCounter8() {
        Counter8 cnt = new Counter8("CNT8");
        testComponent(cnt, new TestReference("Counter8.txt", 1, 5));
    }

    @Test
    @DisplayName("Truth table for Counter16")
    void testCounter16() {
        Counter16 cnt = new Counter16("CNT16");
        testComponent(cnt, new TestReference("Counter16.txt", 1, 5));
    }

    @Test
    @DisplayName(("Truth table for 4-to-16 decoder (74154)"))
    void testDecoder4To16() {
        Decoder416 dec = new Decoder416("DEC");
        testComponent(dec, new TestReference("Decoder4To16.txt", 6, 16));
    }

    @Test
    @DisplayName(("Truth table for 2-to-4 decoder (half 74139)"))
    void testDecoder2To4() {
        Decoder24 dec = new Decoder24("DEC");
        testComponent(dec, new TestReference("Decoder2To4.txt", 3, 4));
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
    @DisplayName(("Truth table for AND gate"))
    void testGateAnd() {
        GateAnd and1 = new GateAnd("AND1");
        testComponent(and1, new TestReference("GateAnd.txt", 2, 1));
    }

    @Test
    @DisplayName(("Truth table for OR gate"))
    void testGateOr() {
        GateOr or1 = new GateOr("OR1");
        testComponent(or1, new TestReference("GateOr.txt", 2, 1));
    }

    @Test
    @DisplayName(("Truth table for 6-bit inverter (7404)"))
    void testGateNot6() {
        GateNot6 not6 = new GateNot6("NOT6");
        testComponent(not6, new TestReference("GateNot6.txt", 6, 6));
    }

    @Test
    @DisplayName(("Truth table for quad AND gate (7408)"))
    void testGateAnd4() {
        GateAnd4x2 and4 = new GateAnd4x2("AND4");
        testComponent(and4, new TestReference("GateAnd4x2.txt", 8, 4));
    }

    @Test
    @DisplayName(("Truth table for 2x4 input AND gate (7421)"))
    void testGateAnd2() {
        GateAnd2x4 and2 = new GateAnd2x4("AND2");
        testComponent(and2, new TestReference("GateAnd2x4.txt", 8, 2));
    }

    @Test
    @DisplayName(("Truth table for 16-bit AND (4x7408)"))
    void testAnd16() {
        And16 and16 = new And16("AND16");
        testComponent(and16, new TestReference("And16.txt", 32, 16));
    }

    @Test
    @DisplayName(("Truth table for 18-bit inverter (3 x 7404)"))
    void testInverter18() {
        Inverter18 inv = new Inverter18("NOT18");
        testComponent(inv, new TestReference("Inverter18.txt", 18, 18));
    }

    @Test
    @DisplayName(("Truth table for 4x2 input OR gate (7432)"))
    void testGateOr4x2() {
        GateOr4x2 or42 = new GateOr4x2("OR42");
        testComponent(or42, new TestReference("GateOr4x2.txt", 8, 4));
    }

    @Test
    @DisplayName(("Truth table for 2x4 input OR gate (74x4072)"))
    void testGateOr2x4() {
        GateOr2x4 or24 = new GateOr2x4("OR24");
        testComponent(or24, new TestReference("GateOr2x4.txt", 8, 2));
    }

    @Test
    @DisplayName(("Truth table for 16-bit OR (4x7432)"))
    void testOr16() {
        Or16 or16 = new Or16("OR16");
        testComponent(or16, new TestReference("Or16.txt", 32, 16));
    }

    @Test
    @DisplayName(("Truth table for quad XOR gate (7486)"))
    void testGateXor4() {
        GateXor4x2 xor4 = new GateXor4x2("XOR4");
        testComponent(xor4, new TestReference("GateXor4x2.txt", 8, 4));
    }

    @Test
    @DisplayName(("Truth table for 16-bit XOR (4x7486)"))
    void testXor16() {
        Xor16 xor16 = new Xor16("XOR16");
        testComponent(xor16, new TestReference("Xor16.txt", 32, 16));
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

    @Test
    @DisplayName(("Truth table for 8-bit line driver (74245)"))
    void testLineDriver8() {
        LineDriver8 driver = new LineDriver8("LD8");
        testComponent(driver, new TestReference("LineDriver8.txt", 9, 8));
    }

    @Test
    @DisplayName(("Truth table for 16-bit line driver (2 x 74245)"))
    void testLineDriver16() {
        LineDriver16 driver = new LineDriver16("LD16");
        testComponent(driver, new TestReference("LineDriver16.txt", 17, 16));
    }

    @Test
    @DisplayName(("Truth table for 1 of 2 16-bit line selector (4 x 74245)"))
    void testLineSelector2() {
        LineSelector2 sel = new LineSelector2("SEL2");
        testComponent(sel, new TestReference("LineSelector2.txt", 1+2*16, 16));
    }

    @Test
    @DisplayName(("Truth table for ZeroChecker"))
    void testZeroChecker() {
        ZeroChecker zero = new ZeroChecker("ZCH");
        testComponent(zero, new TestReference("ZeroChecker.txt", 16, 1));
    }

    @Test
    @DisplayName(("Truth table for ALU"))
    void testAlu() {
        Alu alu = new Alu("ALU");
        for(int i=0; i<1; i++) testComponent(alu, new TestReference("Alu.txt", 4+2*16, 16+2));
    }

    @Test
    @DisplayName(("Truth table for Register"))
    void testRegister() {
        Register reg = new Register("R0");
        testComponent(reg, new TestReference("Register.txt", 16+4, 16));
    }

    private void testComponent(Component comp, TestReference reference) { doTestComponent(comp, reference, false); }
    private void testComponentDebug(Component comp, TestReference reference) { doTestComponent(comp, reference, true); }

    private void doTestComponent(Component comp, TestReference reference, boolean debug) {
        //reference.print();
        for (int i = 0; i < reference.size(); i++) {
            TestReferenceRow row = reference.getRow(i);
            if(debug) row.print(i);
            for (int j = 0; j < row.getInputSize(); j++)
                comp.setInput(j, row.getInput(j), j == row.getInputSize()-1);
            for (int j = 0; j < row.getOutputSize(); j++)
                assert (comp.getOutput(j) == row.getOutput(j));
        }
    }
}