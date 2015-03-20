package com.zyh.slideview;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MoveLayout extends RelativeLayout implements OnClickListener {

    private Context context;
    private int downX;
    private TextView tv_top;
    private TextView delete;
    private TextView more;

    public MoveLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public MoveLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MoveLayout(Context context) {
        this(context, null);
    }

    private void init(Context context) {
        this.context = context;
        LayoutInflater.from(context).inflate(R.layout.move_layout, this, true);
        delete = (TextView) findViewById(R.id.delete);
        more = (TextView) findViewById(R.id.more);
        tv_top = (TextView) findViewById(R.id.tv_top);
        tv_top.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return handlerTouch(v, event);
            }
        });
        tv_top.setOnClickListener(this);
        more.setOnClickListener(this);
        delete.setOnClickListener(this);
    }

    /* ---------------------处理 Touch-------------------------- */
    boolean result = false;
    boolean isOpen = false;

    protected boolean handlerTouch(View v, MotionEvent event) {

        int bottomWidth = delete.getWidth() + delete.getWidth();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // Log.i("", "ACTION_DOWN");
                downX = (int) event.getRawX();
                break;
            case MotionEvent.ACTION_MOVE:
                // Log.i("", "ACTION_MOVE");
                // if (isAniming)
                // break;
                int dx = (int) (event.getRawX() - downX);
                // Log.i("", "dy___" + dx);
                if (isOpen) {
                    // 打开状态
                    // 向右滑动
                    if (dx > 0 && dx < bottomWidth) {
                        v.setTranslationX(dx - bottomWidth);
                        // 允许移动，阻止点击
                        result = true;
                    }
                } else {
                    // 闭合状态
                    // 向左移动
                    if (dx < 0 && Math.abs(dx) < bottomWidth) {
                        v.setTranslationX(dx);
                        // 允许移动，阻止点击
                        result = true;
                    }
                }
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:

                // Log.i("", "ACTION_UP" + v.getTranslationX());

                // 获取已经移动的
                float ddx = v.getTranslationX();

                // 判断打开还是关闭

                if (ddx <= 0 && ddx > -(bottomWidth / 2)) {
                    // 关闭
                    ObjectAnimator oa1 = ObjectAnimator.ofFloat(v, "translationX", ddx, 0).setDuration(100);
                    oa1.start();
                    oa1.addListener(new AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {
                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {
                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            isOpen = false;
                            result = false;
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {
                            isOpen = false;
                            result = false;
                        }
                    });

                }
                if (ddx <= -(bottomWidth / 2) && ddx > -bottomWidth) {
                    // 打开
                    ObjectAnimator oa1 = ObjectAnimator.ofFloat(v, "translationX", ddx, -bottomWidth)
                            .setDuration(100);
                    oa1.start();
                    result = true;
                    isOpen = true;
                }
                break;
        }
        return result;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_top:
                Toast.makeText(context, "item", 0).show();
                break;
            case R.id.more:
                Toast.makeText(context, "more", 0).show();
                break;
            case R.id.delete:
                Toast.makeText(context, "delete", 0).show();
                break;
        }
    }
}
