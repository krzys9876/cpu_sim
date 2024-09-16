package org.kr.cpu.sim;

abstract class Pin {
    final String id;
    final int order;

    Pin(String id, int order) {
        this.id = id;
        this.order = order;
    }
}
