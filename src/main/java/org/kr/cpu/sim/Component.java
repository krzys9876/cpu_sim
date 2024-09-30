package org.kr.cpu.sim;

public abstract class Component {
    final String id;
    final boolean[] input;
    final boolean[] output;

    public boolean getOutput(int index) { return output[index];}

    Component(String id, boolean[] input, boolean[] output) {
        this.id = id;
        this.input = input;
        this.output = output;
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

    public Component setInput(InputPin pin, boolean value) { return setInput(pin.order, value); }
    public abstract Component setInput(int pinNo, boolean value);

    public int decode4(InputPin[] pins) {
        return (input[pins[0].order] ? 1 : 0) +
                (input[pins[1].order] ? 2 : 0) +
                (input[pins[2].order] ? 4 : 0) +
                (input[pins[3].order] ? 8 : 0);
    }

    public int decode1(InputPin pin) { return input[pin.order] ? 1 : 0;}

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
