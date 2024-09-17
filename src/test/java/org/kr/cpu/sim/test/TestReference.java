package org.kr.cpu.sim.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class TestReference {
    private ArrayList<TestReferenceRow> rows = new ArrayList<>();

    public void add(TestReferenceRow row) { rows.add(row); }
    public TestReferenceRow getRow(int i) { return rows.get(i); }
    public int size() { return rows.size(); }

    public TestReference(String filename, int inputSize, int outputSize) {
        InputStream ioStream = this.getClass()
                .getClassLoader()
                .getResourceAsStream(filename);
        assert(ioStream != null);

        ArrayList<String> refSrc = new ArrayList<>();
        try(InputStreamReader isr = new InputStreamReader(ioStream);
            BufferedReader reader = new BufferedReader(isr)) {
            String line;
            while((line = reader.readLine()) != null) refSrc.add(line);
            ioStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        for(String l: refSrc) {
            TestReferenceRow row = new TestReferenceRow();
            StringTokenizer tok = new StringTokenizer(l,"\t");
            for(int i=0; i<inputSize; i++) row.addInput(tok.nextToken().equals("1"));
            for(int i=0; i<outputSize; i++) row.addOutput(tok.nextToken().equals("1"));
            add(row);
        }
    }

    public void print() {
        for(int i=0; i< size(); i++) {
            TestReferenceRow row = getRow(i);
            System.out.println("input");
            for(int j=0; j<row.getInputSize(); j++) { System.out.print(row.getInput(j) + " "); }
            System.out.println();
            System.out.println("output");
            for(int j=0; j<row.getOutputSize(); j++) { System.out.print(row.getOutput(j) + " "); }
            System.out.println();
        }
    }
}
