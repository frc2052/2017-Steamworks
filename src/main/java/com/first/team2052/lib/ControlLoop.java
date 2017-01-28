package com.first.team2052.lib;

import com.google.common.collect.Lists;
import edu.wpi.first.wpilibj.Notifier;

import java.util.List;

public class ControlLoop {
    private final Object runningThread = new Object();
    private double period = 1.0 / 100.0;
    private List<Loopable> loopables = Lists.newArrayList();

    private Runnable runnable = () -> {
        synchronized (runningThread) {
            for (Loopable loopable : loopables) {
                loopable.update();
            }
        }
    };

    private Notifier notifier;

    public ControlLoop(double period) {
        this.period = period;
        notifier = new Notifier(runnable);
    }

    public synchronized void start() {
        synchronized (runningThread) {
            notifier.startPeriodic(period);
        }
    }

    public synchronized void stop() {
        synchronized (runningThread) {
            notifier.stop();
        }
    }

    public synchronized void addLoopable(Loopable loopable) {
        synchronized (runningThread) {
            loopables.add(loopable);
        }
    }
}
