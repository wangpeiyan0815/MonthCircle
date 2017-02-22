package com.yan.monthcircle;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Region;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

/**
 * Created by dell on 2017/2/21.
 */

public class CirCleView extends View {
    //自定义属性
    int circleRadius;
    int smallCircleRadius;
    int circleColor;
    String TextView;
    int textSize;

    private int width;
    private int height;
    //圆形
    Region circleRegion;
    Path circlePath;
    //矩形
    Region rectRegion;
    Path rectPath;
    //小圆
    Region SmallCircleRegion;
    Path SmallCirclePath;

    Paint circlePain;
    Paint rectPain;
    Paint smallPain;


    public CirCleView(Context context) {
        super(context);
        intiView();
    }

    public CirCleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        //得到属性
        TypedArray array = context.obtainStyledAttributes(attrs,R.styleable.CirCleView);
        circleRadius = array.getInteger(R.styleable.CirCleView_circleRadius, 300);
        smallCircleRadius = array.getInteger(R.styleable.CirCleView_smallCircleRadius, 150);
        textSize = array.getInteger(R.styleable.CirCleView_textSize, 50);
        circleColor = array.getInteger(R.styleable.CirCleView_circleColor, Color.RED);
        TextView = array.getString(R.styleable.CirCleView_TextView);
        intiView();
    }

    //初试化操作
    private void intiView() {
        circleRegion = new Region();
        circlePath = new Path();
        rectRegion = new Region();
        rectPath = new Path();
        SmallCircleRegion = new Region();
        SmallCirclePath = new Path();

        circlePain = new Paint();
        rectPain = new Paint();
        smallPain = new Paint();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
        // ▼在屏幕中间添加一个矩形
        rectPath.addRect(w / 2 - 300, h / 2 - 300, w / 2 + 300, h / 2 + 300, Path.Direction.CW);
        // ▼将剪裁边界设置为视图大小
        Region globalRegion = new Region(-w, -h, w, h);
        // ▼将 Path 添加到 Region 中
        rectRegion.setPath(rectPath, globalRegion);

        //绘出中圆
        circlePath.addCircle(w / 2, h / 2, circleRadius, Path.Direction.CW);
        // ▼将剪裁边界设置为视图大小
        Region globalRegionCircle = new Region(-w, -h, w, h);
        // ▼将 Path 添加到 Region 中
        circleRegion.setPath(circlePath, globalRegion);

        //绘出小圆
        SmallCirclePath.addCircle(w / 2, h / 2, smallCircleRadius, Path.Direction.CW);
        // ▼将剪裁边界设置为视图大小
        Region globalRegionCircle3 = new Region(-w, -h, w, h);
        // ▼将 Path 添加到 Region 中
        SmallCircleRegion.setPath(SmallCirclePath, globalRegion);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Path rect = rectPath;
        rectPain.setColor(Color.GREEN);
        // 绘制矩形
        canvas.drawPath(rect, rectPain);

        //绘制圆形
        Path circle = circlePath;
        circlePain.setColor(circleColor);
        canvas.drawPath(circle, circlePain);
        //绘制小圆
        Path small = SmallCirclePath;
        smallPain.setColor(Color.WHITE);
        canvas.drawPath(small, smallPain);

        smallPain.setColor(Color.BLACK);
        smallPain.setTextSize(textSize);
        float yuan = smallPain.measureText(TextView);
        Paint.FontMetrics metrics = smallPain.getFontMetrics();
        float ceil = (float) Math.ceil(metrics.descent - metrics.ascent);
        canvas.drawText("圆环", width / 2 - yuan / 2, height / 2 + ceil / 2, smallPain);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                int x = (int) event.getX();
                int y = (int) event.getY();
                // ▼点击区域判断
                if (SmallCircleRegion.contains(x, y)) {
                    Toast.makeText(this.getContext(), "在小圆内", Toast.LENGTH_SHORT).show();
                }else if (circleRegion.contains(x, y)) {
                    Toast.makeText(this.getContext(), "在圆环内", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(this.getContext(), "在圆环外", Toast.LENGTH_SHORT).show();
                }
                break;
        }
        return true;
    }
}
