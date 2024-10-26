package org.kr.cpu.sim;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Main {
    public static void main(String[] args) {
        System.out.println("START");

        DFlipFlop df = new DFlipFlop("FF1");
        System.out.println(df.fullTxt());
        df.setInput(DFlipFlop.PIN_CLK.order, true);
        System.out.println(df.fullTxt());
        df.setInput(DFlipFlop.PIN_CLK.order, false);
        df.setInput(DFlipFlop.PIN_D.order, true);
        System.out.println(df.fullTxt());
        df.setInput(DFlipFlop.PIN_CLK.order, true);
        System.out.println(df.fullTxt());
        df.setInput(DFlipFlop.PIN_CLK.order, false);
        df.setInput(DFlipFlop.PIN_D.order, false);
        System.out.println(df.fullTxt());
        df.setInput(DFlipFlop.PIN_CLK.order, true);
        System.out.println(df.fullTxt());

        System.out.println("------------------");

        Decoder416 dec = new Decoder416("DEC1");
        dec.setInput(Decoder416.PIN_EN1.order, false); // active low
        dec.setInput(Decoder416.PIN_EN2.order, false); // active low
        for(int i=0; i<16; i++) {
            dec.setInput(Decoder416.PIN_D[0].order, (i & 1) > 0);
            dec.setInput(Decoder416.PIN_D[1].order, ((i >> 1) & 1) > 0);
            dec.setInput(Decoder416.PIN_D[2].order, ((i >> 2) & 1) > 0);
            dec.setInput(Decoder416.PIN_D[3].order, ((i >> 3) & 1) > 0);
            System.out.println(i + " " + dec.fullTxt());
        }
        dec.setInput(Decoder416.PIN_EN1.order, true); // active low
        for(int i=0; i<16; i++) {
            dec.setInput(Decoder416.PIN_D[0].order, (i & 1) > 0);
            dec.setInput(Decoder416.PIN_D[1].order, ((i >> 1) & 1) > 0);
            dec.setInput(Decoder416.PIN_D[2].order, ((i >> 2) & 1) > 0);
            dec.setInput(Decoder416.PIN_D[3].order, ((i >> 3) & 1) > 0);
            System.out.println(i + " " + dec.fullTxt());
        }

        System.out.println("------------------");

        Counter16 counter = new Counter16("CNT1");

        LocalDateTime startTimestamp = LocalDateTime.now();
        System.out.println(startTimestamp);
        int max = 1000000;
        int print = 2;
        for(int i = 0; i < max; i++) {
            boolean clk = i % 2 == 0;
            counter.setInput(Counter16.PIN_CLK.order, clk);
            if(i<print || i>=(max-print)) {
                System.out.println(i + " " + counter.fullTxt() + " " + LocalDateTime.now());
            }
        }
        LocalDateTime endTimestamp = LocalDateTime.now();
        System.out.println(endTimestamp);
        long millis = startTimestamp.until(endTimestamp, ChronoUnit.MILLIS);
        System.out.println("elapsed: " + millis+" ms");


        System.out.println("END");
    }
}