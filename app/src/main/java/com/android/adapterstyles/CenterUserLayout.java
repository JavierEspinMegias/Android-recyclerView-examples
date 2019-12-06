package com.android.adapterstyles;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class CenterUserLayout extends LinearLayoutManager {


    private final float mShrinkAmount = 0.20f;
    private final float mShrinkDistance = 0.9f;


    public CenterUserLayout(Context context) {
        super(context);
    }

    public CenterUserLayout(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    public CenterUserLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {

        int orientation = getOrientation();

        if (orientation == VERTICAL) {
            int scrolled = super.scrollVerticallyBy(dy, recycler, state);


            // Cambiando getHeight por getWith elegimos la orientacion del zoom
            float midpoint = getHeight() / 2.f;

            float d0 = 0.f;
            float d1 = mShrinkDistance * midpoint;

            float s0 = 1.f;
            float s1 = 1.f - mShrinkAmount;

            for (int i = 0; i < getChildCount(); i++) {
                View child = getChildAt(i);
                float childMidpoint =
                        (getDecoratedBottom(child) + getDecoratedTop(child)) / 2.f;
                float d = Math.min(d1, Math.abs(midpoint - childMidpoint));
                float scale = s0 + (s1 - s0) * (d - d0) / (d1 - d0);
                child.setScaleX(scale);
                child.setScaleY(scale);
            }
            return scrolled;
        } else {
            return 0;
        }
    }

    @Override
    public int scrollHorizontallyBy(int dx, RecyclerView.Recycler recycler, RecyclerView.State state) {
        int orientation = getOrientation();

        // Cambiando getHeight por getWith elegimos la orientacion del zoom
        if (orientation == HORIZONTAL) {
            int scrolled = super.scrollHorizontallyBy(dx, recycler, state);

            float midpoint = getWidth() / 2.f;
            float d0 = 0.f;
            float d1 = mShrinkDistance * midpoint;
            float s0 = 1.f;
            float s1 = 1.f - mShrinkAmount;
            for (int i = 0; i < getChildCount(); i++) {
                View child = getChildAt(i);
                float childMidpoint =
                        (getDecoratedRight(child) + getDecoratedLeft(child)) / 2.f;
                float d = Math.min(d1, Math.abs(midpoint - childMidpoint));
                float scale = s0 + (s1 - s0) * (d - d0) / (d1 - d0);
                child.setScaleX(scale);
                child.setScaleY(scale);
            }
            return scrolled;
        } else {
            return 0;
        }
    }

}
