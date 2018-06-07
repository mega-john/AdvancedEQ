package cs2c.EQ.Controls;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.widget.SeekBar;

import cs2c.EQ.EQActivity;


public class VerticalSeekBar extends CorrectedSeekBar {

    public VerticalSeekBar(Context context) { super(context); }

    public VerticalSeekBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public VerticalSeekBar(Context context, AttributeSet attrs, int defStyle) { super(context, attrs, defStyle); }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(h, w, oldh, oldw);
    }

    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(heightMeasureSpec, widthMeasureSpec);
        setMeasuredDimension(getMeasuredHeight(), getMeasuredWidth());
    }

    @Override
    public synchronized void setProgress(int progress) {
        super.setProgress(progress);
        onSizeChanged(getWidth(), getHeight(), 0, 0); // a bug workaround for thumb visually resetting to 0
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.rotate(-90);
        canvas.translate(-getHeight(), 0);

        super.onDraw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!isEnabled()) {
            return false;
        }

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_UP:
                int realHeight = getHeight() - getPaddingLeft() - getPaddingRight();
                int realStart = getPaddingLeft();
                if (isPaddingOffsetRequired()){
                    realHeight -= (getLeftPaddingOffset() + getRightPaddingOffset());
                    realStart += (getLeftPaddingOffset());
                }
                int thumbOffsetCorrection = getThumb().getBounds().width() / 2 - getThumbOffset();
                realHeight -= thumbOffsetCorrection * 2;
                realStart += thumbOffsetCorrection;

                float touchPos = (event.getY() - realStart) / (realHeight);
                int progress = getMax() - Math.round((float)getMax() * touchPos);
                setProgressPatch(progress, true);

                onSizeChanged(getWidth(), getHeight(), 0, 0);
                break;
        }
        return true;
    }

}


//
//public class VerticalSeekBar extends CorrectedSeekBar {
//    private int height;
//    private OnSeekBarChangeListener mOnSeekBarChangeListener;
//    private float mScale;
//    private Drawable mThumb;
//    private int width;
//
//    public interface OnSeekBarChangeListener {
//        void onProgressChanged(VerticalSeekBar verticalSeekBar, int i, boolean z);
//
//        void onStartTrackingTouch(VerticalSeekBar verticalSeekBar);
//
//        void onStopTrackingTouch(VerticalSeekBar verticalSeekBar);
//    }
//
//    public VerticalSeekBar(Context context) {
//        this(context, null);
//    }
//
//    public VerticalSeekBar(Context context, AttributeSet attrs) {
//        this(context, attrs, 16842875);
//    }
//
//    public VerticalSeekBar(Context context, AttributeSet attrs, int defStyle) {
//        super(context, attrs, defStyle);
//    }
//
//    public void setOnSeekBarChangeListener(OnSeekBarChangeListener l) {
//        this.mOnSeekBarChangeListener = l;
//    }
//
//    void onStartTrackingTouch() {
//        if (this.mOnSeekBarChangeListener != null) {
//            this.mOnSeekBarChangeListener.onStartTrackingTouch(this);
//        }
//    }
//
//    void onStopTrackingTouch() {
//        if (this.mOnSeekBarChangeListener != null) {
//            this.mOnSeekBarChangeListener.onStopTrackingTouch(this);
//        }
//    }
//
//    private void setThumbPos(int w, Drawable thumb, float scale, int gap) {
//        int topBound;
//        int bottomBound;
//        int available = (getPaddingLeft() + w) - getPaddingRight();
//        int thumbWidth = thumb.getIntrinsicWidth();
//        int thumbHeight = thumb.getIntrinsicHeight();
//        int thumbPos = (int) (((float) ((available - thumbWidth) + (getThumbOffset() * 2))) * scale);
//        if (gap == Integer.MIN_VALUE) {
//            Rect oldBounds = thumb.getBounds();
//            topBound = oldBounds.top;
//            bottomBound = oldBounds.bottom;
//        } else {
//            topBound = gap;
//            bottomBound = gap + thumbHeight;
//        }
//        thumb.setBounds(thumbPos, topBound, thumbPos + thumbWidth, bottomBound);
//    }
//
//    protected void onDraw(Canvas c) {
//        c.rotate(-90.0f);
//        c.translate((float) (-this.height), 0.0f);
//        super.onDraw(c);
//    }
//
//    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        if (EQActivity.width > 800) {
//            this.width = 55;
//            this.height = 292;
//        } else {
//            this.width = 42;
//            this.height = 233;
//        }
//        setMeasuredDimension(this.width, this.height + 30);
//    }
//
//    public void setThumb(Drawable thumb) {
//        this.mThumb = thumb;
//        super.setThumb(thumb);
//    }
//
//    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
//        super.onSizeChanged(h, w, oldw, oldh);
//    }
//
//    public synchronized void setProgress(int progress) {
//        super.setProgress(progress);
//        Drawable thumb = this.mThumb;
//        if (thumb != null) {
//            setThumbPos(getHeight(), thumb, this.mScale, Integer.MIN_VALUE);
//            invalidate();
//        }
//        onSizeChanged(getWidth(), getHeight(), 0, 0);
//    }
//
//    public boolean onTouchEvent(MotionEvent event) {
//        if (!isEnabled()) {
//            return false;
//        }
//        switch (event.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                setPressed(true);
//                onStartTrackingTouch();
//                trackTouchEvent(event);
//                break;
//            case MotionEvent.ACTION_UP:
//                trackTouchEvent(event);
//                onStopTrackingTouch();
//                setPressed(false);
//                break;
//            case MotionEvent.ACTION_MOVE:
//                trackTouchEvent(event);
//                attemptClaimDrag();
//                break;
//            case MotionEvent.ACTION_CANCEL:
//                onStopTrackingTouch();
//                setPressed(false);
//                break;
//        }
//        return true;
//    }
//
//    private void trackTouchEvent(MotionEvent event) {
//        float scale;
//        int Height = getHeight();
//        int available = (Height - getPaddingBottom()) - getPaddingTop();
//        int Y = (int) event.getY();
//        if (Y > Height - getPaddingBottom()) {
//            scale = 0.0f;
//        } else if (Y < getPaddingTop()) {
//            scale = 1.0f;
//        } else {
//            scale = ((float) ((Height - getPaddingBottom()) - Y)) / ((float) available);
//        }
//        setProgress((int) (scale * ((float) getMax())));
//        Drawable thumb = this.mThumb;
//        if (thumb != null) {
//            setThumbPos(getHeight(), thumb, scale, Integer.MIN_VALUE);
//            invalidate();
//        }
//        this.mScale = scale;
//        if (this.mOnSeekBarChangeListener != null) {
//            this.mOnSeekBarChangeListener.onProgressChanged(this, getProgress(), true);
//        }
//    }
//
//    private void attemptClaimDrag() {
//        if (getParent() != null) {
//            getParent().requestDisallowInterceptTouchEvent(true);
//        }
//    }
//
//    public boolean dispatchKeyEvent(KeyEvent event) {
//        if (event.getAction() != 0) {
//            return false;
//        }
//        KeyEvent newEvent;
//        switch (event.getKeyCode()) {
//            case KeyEvent.KEYCODE_DPAD_UP:
//                newEvent = new KeyEvent(0, 22);
//                break;
//            case KeyEvent.KEYCODE_DPAD_DOWN:
//                newEvent = new KeyEvent(0, 21);
//                break;
//            case KeyEvent.KEYCODE_DPAD_LEFT:
//                newEvent = new KeyEvent(0, 20);
//                break;
//            case KeyEvent.KEYCODE_DPAD_RIGHT:
//                newEvent = new KeyEvent(0, 19);
//                break;
//            default:
//                newEvent = new KeyEvent(0, event.getKeyCode());
//                break;
//        }
//        return newEvent.dispatch(this);
//    }
//}