package org.kr.cpu.sim;

public class Main {
    public static void main(String[] args) {
        System.out.println("START");

        DFlipFlop df = new DFlipFlop("FF1");
        System.out.println(df.fullTxt());
        df.setInput(DFlipFlop.PIN_CLK, true);
        System.out.println(df.fullTxt());
        df.setInput(DFlipFlop.PIN_CLK, false).setInput(DFlipFlop.PIN_D, true);
        System.out.println(df.fullTxt());
        df.setInput(DFlipFlop.PIN_CLK, true);
        System.out.println(df.fullTxt());
        df.setInput(DFlipFlop.PIN_CLK, false).setInput(DFlipFlop.PIN_D, false);
        System.out.println(df.fullTxt());
        df.setInput(DFlipFlop.PIN_CLK, true);
        System.out.println(df.fullTxt());

        System.out.println("END");
    }
}