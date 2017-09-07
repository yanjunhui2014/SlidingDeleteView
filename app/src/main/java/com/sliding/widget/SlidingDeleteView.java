package com.sliding.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.sliding.R;

/**
 * 滑动删除widget
 *
 * @author Junhui
 */

public class SlidingDeleteView extends HorizontalScrollView {
    private static final String TAG = "SlidingDeleteView";

    /**
     * 抽屉视图(注意：recyclerview/listview中不能使用button，button会抢夺焦点) - 父件
     */
    private LinearLayout slidingParent;
    /**
     * 是否开启滑动抽屉
     */
    public boolean isEnable = true;
    /**
     * 抽屉视图是否可见
     */
    public boolean deleteViewVisibile = false; //

    private boolean isFirst = true;

    private OnDeleteViewStateChangedListener onStateChangedListener;//监听器

    /**
     * 抽屉视图状态变化回调接口
     */
    public interface OnDeleteViewStateChangedListener {
        void onVisibile();

        void onGone();

        void onDownOrMove();
    }

    public SlidingDeleteView(Context context) {
        this(context, null);
    }

    public SlidingDeleteView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlidingDeleteView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (isFirst) {
            init();
            isFirst = false;
        }
    }

    private void init() {
        slidingParent = (LinearLayout) findViewById(R.id.lay_sliding);
    }

    public void setOnDeleteViewStateChangedListener(OnDeleteViewStateChangedListener onStateChangedListener) {
        this.onStateChangedListener = onStateChangedListener;
    }

    public boolean isEnable() {
        return isEnable;
    }

    public void setEnable(boolean enable) {
        isEnable = enable;
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_MOVE:
                if (!isEnable) {
                    return false;
                }
            case MotionEvent.ACTION_DOWN:
                if (onStateChangedListener != null) {
                    onStateChangedListener.onDownOrMove();
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                measureScrollX();
                return true;
            default:
                break;
        }
        return super.onTouchEvent(ev);
    }

    /**
     * 计算X轴滑动距离，并做出相应操作
     */
    private void measureScrollX() {
        if (getScrollX() < slidingParent.getWidth() / 3) {
            //TODO 当滑动距离小于 抽屉视图宽度 * 1/3 时，隐藏删除视图
            setDeleteViewGone();
        } else {
            setDeleteViewVisibile();
        }
    }

    public void setDeleteViewGone() {
        deleteViewVisibile = false;
        this.smoothScrollTo(0, 0);
        if (onStateChangedListener != null) {
            onStateChangedListener.onGone();
        }
    }

    public void setDeleteViewVisibile() {
        Log.d(TAG, "抽屉的固定宽度为 == " + slidingParent.getWidth());
        deleteViewVisibile = true;
        this.smoothScrollTo(slidingParent.getWidth(), 0);

        if (onStateChangedListener != null) {
            onStateChangedListener.onVisibile();
        }
    }

}
