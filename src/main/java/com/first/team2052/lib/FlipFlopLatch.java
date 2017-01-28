package com.first.team2052.lib;

public class FlipFlopLatch {
    private boolean state = true;
    private boolean toggle = false;

    protected void flip() {
        state = !state;
    }

    public boolean get() {
        return state;
    }

    public void update(boolean button) {
        if (button && !toggle)
            flip();
        toggle = button;
    }
}
