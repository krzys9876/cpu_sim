package org.kr.cpu.sim;

import java.time.LocalDateTime;

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

        System.out.println("------------------");

        Counter8 counter = new Counter8("CNT1");

        System.out.println(LocalDateTime.now());
        int max = 1000000;
        int print = 1024;
        for(int i = 0; i < max; i++) {
            boolean clk = i % 2 == 0;
            counter.setInput(Counter8.PIN_CLK, clk);
            if(i<print || i>=(max-print)) {
                System.out.println(i + " " + counter.fullTxt() + " " + LocalDateTime.now());
            }
        }
        System.out.println(LocalDateTime.now());


        System.out.println("END");
    }
}