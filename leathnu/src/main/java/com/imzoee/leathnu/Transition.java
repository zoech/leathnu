package com.imzoee.leathnu;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by zoey on 2016/5/23.
 *
 */
public class Transition {
    private static long DURATION = 300L;

    private View previousView;
    private View launchView;

    private List<View> dismissViews = null;
    private List<View> raiseViews = null;
    private List<View> transViews = null;
    private List<ViewGroup.LayoutParams> previousParams = null;
    private List<ViewGroup.LayoutParams> launchParams = null;

    private AnimatorSet animatorSet;

    private boolean expanded = false;

    private Transition(){
        this.dismissViews = new ArrayList<>();
        this.raiseViews = new ArrayList<>();
        this.transViews = new ArrayList<>();
        this.previousParams = new ArrayList<>();
        this.launchParams = new ArrayList<>();
        this.expanded = false;
    }

    /**
     * Create a {@link Transition} witch store the information of
     * animating from the previous Activity to the latter activity.
     *
     * @param previousView
     * The root view of the abstract item in the previous activity.
     *
     * @param launchView
     * the root view of the expansion activity.
     *
     * @return
     * Return the created Transition
     */
    public static Transition with(View previousView, View launchView){
        Transition transition = new Transition();
        transition.setPreviousView(previousView);
        transition.setLaunchView(launchView);
        return transition;
    }

    public static Transition baseView(View previousView){
        Transition transition = new Transition();
        transition.setPreviousView(previousView);
        transition.setLaunchView(null);
        return transition;
    }

    public Transition expandView(View launchView){
        this.setLaunchView(launchView);
        return this;
    }

    public Transition dismiss(View v){
        this.dismissViews.add(v);
        return this;
    }

    public Transition dismiss(int resId){
        View v = this.previousView.findViewById(resId);
        return dismiss(v);
    }

    public Transition raise(View v){
        this.raiseViews.add(v);
        return this;
    }

    public Transition raise(int resId){
        View v = this.launchView.findViewById(resId);
        return raise(v);
    }

    public Transition transfer(View previousItem, View launchItem){
        this.transViews.add(launchItem);
        ViewGroup.LayoutParams pParams = previousItem.getLayoutParams();
        this.previousParams.add(pParams);
        ViewGroup.LayoutParams lParams = launchItem.getLayoutParams();
        this.launchParams.add(lParams);
        return this;
    }

    public Transition transfer(View previousItem, int resId){
        View v = launchView.findViewById(resId);
        return transfer(previousItem, v);
    }

    public Transition transfer(int previousResId, int launchResId){
        View pv = previousView.findViewById(previousResId);
        View lv = launchView.findViewById(launchResId);
        return transfer(pv, lv);
    }

    public void prepare(){
        List<Animator> animators = new ArrayList<>();

        /**
         * Add the animation of the whole root view;
         */
        float transX = previousView.getTranslationX() - launchView.getTranslationX();
        float transY = previousView.getTranslationY() - launchView.getTranslationY();
        float scaleX = previousView.getWidth() / launchView.getWidth();
        float scaleY = previousView.getHeight() / launchView.getHeight();
        //float scaleX = 1f;
        //float scaleY = 0.2f;
        Log.i("-----------", String.valueOf(previousView.getWidth()));
        Log.i("-----------", String.valueOf(previousView.getHeight()));
        Log.i("-----------", String.valueOf(launchView.getWidth()));
        Log.i("-----------", String.valueOf(launchView.getHeight()));

        ObjectAnimator animatorTransX = ObjectAnimator.ofFloat(launchView, "translationX", transX, 0);
        ObjectAnimator animatorTransY = ObjectAnimator.ofFloat(launchView, "translationY", transY, 0);
        ObjectAnimator animatorScaleX = ObjectAnimator.ofFloat(launchView, "scaleX", scaleX, 1);
        ObjectAnimator animatorScaleY = ObjectAnimator.ofFloat(launchView, "scaleY", scaleY, 1);

        animators.add(animatorTransX);
        animators.add(animatorTransY);
        animators.add(animatorScaleX);
        animators.add(animatorScaleY);

        /**
         * Add the dismiss animation.
         */
        if(!dismissViews.isEmpty()){
            Iterator<View> iterator = dismissViews.iterator();
            while(iterator.hasNext()){
                View v = iterator.next();
                ObjectAnimator animatorAlpha = ObjectAnimator.ofFloat(v, "alpha", 1, 0);
                ObjectAnimator animatorSx = ObjectAnimator.ofFloat(v, "scaleX", 1, 0);
                ObjectAnimator animatorSy = ObjectAnimator.ofFloat(v, "scaleY", 1, 0);

                animators.add(animatorAlpha);
                animators.add(animatorSx);
                animators.add(animatorSy);
            }
        }

        /**
         * Add the raise animation.
         */
        if(!raiseViews.isEmpty()){
            Iterator<View> iterator = raiseViews.iterator();
            while(iterator.hasNext()){
                View v = iterator.next();
                ObjectAnimator animatorAlpha = ObjectAnimator.ofFloat(v, "alpha", 0, 1);
                ObjectAnimator animatorSx = ObjectAnimator.ofFloat(v, "scaleX", 0, 1);
                ObjectAnimator animatorSy = ObjectAnimator.ofFloat(v, "scaleY", 0, 1);

                animators.add(animatorAlpha);
                animators.add(animatorSx);
                animators.add(animatorSy);
            }
        }


        animatorSet = new AnimatorSet();
        animatorSet.playTogether(animators);
        animatorSet.setDuration(DURATION);
    }

    public void startTransition(){
        if(!expanded) {
            this.animatorSet.start();
            expanded = true;
        } else {
            Log.d("+++++++++++++++", "This transition is already expanded!");
        }
    }

    public View getLaunchView(){
        return launchView;
    }

    private void setPreviousView(View v){
        previousView = v;
    }

    private void setLaunchView(View v){
        launchView = v;
    }
}
