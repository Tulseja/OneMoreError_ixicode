package ixigo.example.apple.ixigohack.widgets;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by ashish on 27/05/16.
 */
public class NonScrollingViewPager extends ViewPager {
    
    private boolean enabled;

    public NonScrollingViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.enabled = false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (this.enabled) {
            return super.onTouchEvent(event);
        }
        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        try {
            if (this.enabled) {
                return super.onInterceptTouchEvent(event);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void setPagingEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}