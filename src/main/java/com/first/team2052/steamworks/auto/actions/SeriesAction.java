package com.first.team2052.steamworks.auto.actions;

import java.util.ArrayList;
import java.util.List;

/**
 * Executes one action at a time. Useful as a member of {@link ParallelAction}
 */
public class SeriesAction implements Action {

    private Action mCurAction;
    private final ArrayList<Action> mRemainingActions;

    public SeriesAction(List<Action> actions) {
        //We don't want a reference of the list, we a copy
        //Just in case we have to use the list elsewhere in auto
        mRemainingActions = new ArrayList<>(actions.size());
        for (Action action : actions) {
            mRemainingActions.add(action);
        }
        mCurAction = null;
    }

    @Override
    public boolean isFinished() {
        return mRemainingActions.isEmpty() && mCurAction == null;
    }

    @Override
    public void start() {
    }

    @Override
    public void update() {
        if (mCurAction == null) {
            if (mRemainingActions.isEmpty()) {
                return;
            }
            mCurAction = mRemainingActions.remove(0);
            mCurAction.start();
        }
        mCurAction.update();
        if (mCurAction.isFinished()) {
            mCurAction.done();
            mCurAction = null;
        }
    }

    @Override
    public void done() {
    }
}
