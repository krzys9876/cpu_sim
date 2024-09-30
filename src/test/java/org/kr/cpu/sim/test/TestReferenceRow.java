package org.kr.cpu.sim.test;

import java.util.ArrayList;

public class TestReferenceRow {
    private final ArrayList<Boolean> input = new ArrayList<>();
    private final ArrayList<Boolean> output = new ArrayList<>();

    public void addInput(boolean b) { input.add(b); }
    public void addOutput(boolean b) { output.add(b); }
    public boolean getInput(int i) { return input.get(i); }
    public boolean getOutput(int i) { return output.get(i); }
    public int getInputSize() { return input.size(); }
    public int getOutputSize() { return output.size(); }

    public void print() {
        System.out.println("input");
        for (int j=0; j<getInputSize(); j++) { System.out.print(getInput(j) + " "); }
        System.out.println();
        System.out.println("output");
        for(int j=0; j<getOutputSize(); j++) { System.out.print(getOutput(j) + " "); }
        System.out.println();
    }

}
