package org.kr.cpu.sim;

abstract class Component {
    final String id;
    final boolean[] input;
    final boolean[] output;

    Component(String id, boolean[] input, boolean[] output) {
        this.id = id;
        this.input = input;
        this.output = output;
    }

    public String inputTxt() {
        return arrTxt(input);
    }

    public String outputTxt() {
        return arrTxt(output);
    }

    public String fullTxt() {
        return id + " " + inputTxt() + " " + outputTxt();
    }

    private String arrTxt(boolean[] arr) {
        StringBuilder txt = new StringBuilder();
        for(int i = 0; i < arr.length; i++) {
            txt.append(arr[i] ? "1" : "0");
        }
        return txt.toString();
    }

    abstract Component setInput(InputPin pin, boolean value);
}
