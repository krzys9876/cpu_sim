package org.kr.cpu.sim;

public abstract class Component {
    final String id;
    private final boolean[] input;
    private final boolean[] output;
    protected final int inputSize;

    public boolean getOutput(int index) { return output[index];}
    public void setOutput(int index, boolean value) { output[index] = value;}

    Component(String id, boolean[] input, boolean[] output) {
        this.id = id;
        this.input = input;
        this.output = output;
        this.inputSize = input.length;
    }

    public String inputTxt() { return arrTxt(input); }
    public String outputTxt() { return arrTxt(output);}
    public String fullTxt() {
        return id + " " + inputTxt() + " " + outputTxt();
    }

    private String arrTxt(boolean[] arr) {
        StringBuilder txt = new StringBuilder();
        for (boolean b : arr) txt.append(b ? "1" : "0");
        return txt.toString();
    }

    public abstract void updateOutput();
    public void setInput(int pinNo, boolean value, boolean shouldRefresh) {
        // basic implementation
        assert pinNo >= 0 && pinNo < inputSize;
        setInputDirect(pinNo, value);
        if(shouldRefresh) updateOutput();
    }
    private void setInputDirect(int pinNo, boolean value) { input[pinNo] = value; }
    public boolean getInput(int index) { return input[index]; }

    public static InputPin[] initInputPins(String prefix, int startPin, int count) {
        InputPin[] arr = new InputPin[count];
        for(int i = 0; i < arr.length; i++) arr[i] = new InputPin(prefix + i, startPin + i);
        return arr;
    }

    public static OutputPin[] initOutputPins(String prefix, int startPin, int count) {
        OutputPin[] arr = new OutputPin[count];
        for(int i = 0; i < arr.length; i++) arr[i] = new OutputPin(prefix + i, startPin + i);
        return arr;
    }
}