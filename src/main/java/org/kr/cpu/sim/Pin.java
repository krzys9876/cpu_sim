package org.kr.cpu.sim;

abstract class Pin {
    public final String id;
    public final int order;

    public Pin(String id, int order) {
        this.id = id;
        this.order = order;
    }
}
