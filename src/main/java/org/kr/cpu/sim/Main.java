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

        Decoder416 dec = new Decoder416("DEC1");
        dec.setInput(Decoder416.PIN_nEN, false); // active low
        for(int i=0; i<16; i++) {
            dec.setInput(Decoder416.PIN_D[0], ((i >> 0) & 1) > 0);
            dec.setInput(Decoder416.PIN_D[1], ((i >> 1) & 1) > 0);
            dec.setInput(Decoder416.PIN_D[2], ((i >> 2) & 1) > 0);
            dec.setInput(Decoder416.PIN_D[3], ((i >> 3) & 1) > 0);
            System.out.println(i + " " + dec.fullTxt());
        }
        dec.setInput(Decoder416.PIN_nEN, true); // active low
        for(int i=0; i<16; i++) {
            dec.setInput(Decoder416.PIN_D[0], ((i >> 0) & 1) > 0);
            dec.setInput(Decoder416.PIN_D[1], ((i >> 1) & 1) > 0);
            dec.setInput(Decoder416.PIN_D[2], ((i >> 2) & 1) > 0);
            dec.setInput(Decoder416.PIN_D[3], ((i >> 3) & 1) > 0);
            System.out.println(i + " " + dec.fullTxt());
        }

        System.out.println("------------------");

        Counter16 counter = new Counter16("CNT1");

        System.out.println(LocalDateTime.now());
        int max = 1000000;
        int print = 2;
        for(int i = 0; i < max; i++) {
            boolean clk = i % 2 == 0;
            counter.setInput(Counter16.PIN_CLK, clk);
            if(i<print || i>=(max-print)) {
                System.out.println(i + " " + counter.fullTxt() + " " + LocalDateTime.now());
            }
        }
        System.out.println(LocalDateTime.now());

        System.out.println("END");
    }
}