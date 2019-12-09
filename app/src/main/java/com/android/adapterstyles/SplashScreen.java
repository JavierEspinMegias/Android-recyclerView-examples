package com.android.adapterstyles;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Property;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.Random;

public class SplashScreen extends AppCompatActivity {

    private ImageView splashLoader;
    private TextView startText;
    private int[] animationsId;
    private Animation animation;
    private Runnable runner = new Runnable() {
        @Override
        public void run() {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        splashLoader = (ImageView)findViewById(R.id.imageViewSplash);
        startText = (TextView)findViewById(R.id.startText);

        animationsId = new int[]{R.anim.title_shaking_start, R.anim.title_shaking_right, R.anim.title_shaking_down, R.anim.title_shaking_left, R.anim.title_shaking_up};
        animation = AnimationUtils.loadAnimation(startText.getContext(), animationsId[0]);

        Glide.with(this).asGif().load("https://media.giphy.com/media/kaCgjbPPLoHaxmely7/giphy.gif").into(splashLoader);


        startText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            Animation animation = AnimationUtils.loadAnimation(view.getContext(), R.anim.open_title);
            view.startAnimation(animation);

            view.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(SplashScreen.this, MainActivity.class));
                }
            }, animation.getDuration());
            }
        });





    }

    @Override
    public void onResume(){
        super.onResume();

        this.startText.clearAnimation();
        setTitleAnimation(startText);

        skullAnimations();
    }

    @Override
    public void onStop(){
        super.onStop();
        this.startText.clearAnimation();
        this.startText.removeCallbacks(runner);

    }



    private void setTitleAnimation(final View viewToAnimate){
        Animation animation = AnimationUtils.loadAnimation(viewToAnimate.getContext(), R.anim.user_left_to_right_from_big_x);
        viewToAnimate.startAnimation(animation);

        viewToAnimate.postDelayed(new Runnable() {
            @Override
            public void run() {
            viewToAnimate.clearAnimation();

            setTitleShakingAnimation(viewToAnimate, 0);

            }
        }, animation.getDuration());
    }

    private void setTitleShakingAnimation(final View viewToAnimate, final int count){

        viewToAnimate.removeCallbacks(runner);
        runner = null;
        runner = new Runnable() {
            @Override
            public void run() {

                viewToAnimate.clearAnimation();

                animation = AnimationUtils.loadAnimation(viewToAnimate.getContext(), animationsId[count]);
                viewToAnimate.startAnimation(animation);


                if (count == animationsId.length-1){
                    setTitleShakingAnimation(viewToAnimate, 0);
                }else{
                    setTitleShakingAnimation(viewToAnimate, count+1);
                }
            }
        };

        viewToAnimate.postDelayed(runner, animation.getDuration());
    }


    private void skullAnimations(){
        Random rnd = new Random();
        int totalAnimations = 50;
        LinearLayout main_layer = (LinearLayout) findViewById(R.id.linear_skull);

        for (int j = 0; j < totalAnimations; j++){
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
            LinearLayout layout = new LinearLayout(getApplicationContext());
            layout.setOrientation(LinearLayout.HORIZONTAL);

            params.gravity = Gravity.BOTTOM;
            layout.setGravity(Gravity.BOTTOM);
            layout.setLayoutParams(params);


            int size = rnd.nextInt(100);
            LinearLayout.LayoutParams paramsButton = new LinearLayout.LayoutParams(size+64,size+64);

            final Button button1 = new Button(getApplicationContext());
            button1.setLayoutParams(paramsButton);
            button1.setBackgroundResource(R.mipmap.skull_icon);

            final ObjectAnimator oa = ObjectAnimator.ofFloat(button1,"translationY", rnd.nextInt(15)*-1, rnd.nextInt(2500)*-1);
            final ObjectAnimator oaFade = ObjectAnimator.ofFloat(button1, "alpha", 1f, 0f);
            final ObjectAnimator oaSizeX = ObjectAnimator.ofFloat(button1, "scaleX", 0.3f, 1.1f);
            final ObjectAnimator oaSizeY = ObjectAnimator.ofFloat(button1, "scaleY", 0.3f, 1.1f);

            final int duration = rnd.nextInt(1500)+2500;
            final int delay = rnd.nextInt(2500)+1500;

            oa.setDuration(duration);
            oa.setStartDelay(delay);
            oa.setRepeatCount(ValueAnimator.INFINITE);

            oaFade.setDuration(duration);
            oaFade.setStartDelay(delay);
            oaFade.setRepeatCount(ValueAnimator.INFINITE);

            oaSizeX.setDuration(duration);
            oaSizeX.setStartDelay(delay);
            oaSizeX.setRepeatCount(ValueAnimator.INFINITE);
            oaSizeY.setDuration(duration);
            oaSizeY.setStartDelay(delay);
            oaSizeY.setRepeatCount(ValueAnimator.INFINITE);

            button1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!oa.isPaused()){
                        oa.pause();
                    }else{
                        button1.setVisibility(View.GONE);
                    }
                }
            });

            layout.addView(button1);
            main_layer.addView(layout);
            oaFade.start();
            oa.start();
            oaSizeX.start();
            oaSizeY.start();

        }
    }


    public int getRandomColor() {
        Random rnd = new Random();
        int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
        return color;
    }

}
