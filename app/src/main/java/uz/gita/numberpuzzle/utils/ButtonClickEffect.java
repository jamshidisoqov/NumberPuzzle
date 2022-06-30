package uz.gita.numberpuzzle.utils;// Created by Jamshid Isoqov an 7/1/2022

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import uz.gita.numberpuzzle.R;

public class ButtonClickEffect {

    Animation scaleUp, scaleDown;
    Context context;

    public ButtonClickEffect(Context context) {
        this.context = context;
    }

    @SuppressLint("ClickableViewAccessibility")
    public void effect(View view, MotionEvent event) {

        scaleUp = AnimationUtils.loadAnimation(context, R.anim.scale_up);

        scaleDown = AnimationUtils.loadAnimation(context, R.anim.scale_down);

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            view.startAnimation(scaleUp);
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            view.startAnimation(scaleDown);
        }

    }
}
