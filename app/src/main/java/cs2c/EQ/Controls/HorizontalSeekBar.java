package cs2c.EQ1.Controls;

/**
 * Created by john on 07.11.2017.
 */

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class HorizontalSeekBar extends cs2c.EQ1.Controls.CorrectedSeekBar {
    public HorizontalSeekBar(Context context) {
        super(context);
    }

    public HorizontalSeekBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HorizontalSeekBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
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
                int realWidth = getWidth() - getPaddingLeft() - getPaddingRight();
                int realStart = getPaddingLeft();
                if (isPaddingOffsetRequired()) {
                    realWidth -= (getLeftPaddingOffset() + getRightPaddingOffset());
                    realStart += (getLeftPaddingOffset());
                }
                int thumbOffsetCorrection = getThumb().getBounds().width() / 2 - getThumbOffset();
                realWidth -= thumbOffsetCorrection * 2;
                realStart += thumbOffsetCorrection;

                float touchPos = (event.getX() - realStart) / (realWidth);
                int progress = Math.round((float) getMax() * touchPos);
                setProgressPatch(progress, true);

                onSizeChanged(getWidth(), getHeight(), 0, 0);
                break;
        }
        return true;
    }

    @Override
    public boolean performClick() {
        // do what you want
        return true;
    }
}