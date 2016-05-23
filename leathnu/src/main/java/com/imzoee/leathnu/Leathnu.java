package com.imzoee.leathnu;

import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zoey on 2016/5/23.
 *
 */
public class Leathnu {
    private static Leathnu instance = null;

    private List<Transition> transitions;

    public static Leathnu getInstance(){
        if (instance == null){
            instance = new Leathnu();
        }
        return instance;
    }

    private Leathnu(){
        transitions = new ArrayList<>();
    }

    /**
     * Add a transition into Leathnu stack.
     * @param transition
     * The added transition.
     */
    public void prepare(@NonNull Transition transition){
        this.transitions.add(transition);
        transition.prepare();
    }

    /**
     * Start the transition animation.
     */
    public void start(){
        if(transitions == null || transitions.isEmpty()){
            Log.d("++++++++++++", "Try to expand a transition, but no transition is prepared!");
        } else {
            transitions.get(transitions.size()-1).startTransition();
        }
    }

    public View getLaunchView(){
        return transitions.get(transitions.size()-1).getLaunchView();
    }

    /**
     * Called when the transition return, and it will play the
     * reverse animation of that played when call {@link #start}.
     */
    public void finish(){

    }
}
