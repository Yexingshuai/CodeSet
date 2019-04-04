package uitest.star.com.uiset.demo.customview.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by yexing on 2018/12/25.
 */

public class MyCustomView extends View {

    private Paint mPaint1;
    private Paint mPaint2;
    private Paint mPaint3;
    private Paint mPaint4;
    private Paint mPaint5;

    public MyCustomView(Context context) {
        this(context, null);
    }

    public MyCustomView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyCustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint1 = getPaint(Color.RED);
        mPaint2 = getPaint(Color.YELLOW);
        mPaint3 = getPaint(Color.parseColor("#9400D3"));
        mPaint4 = getPaint(Color.parseColor("#778899"));
        mPaint5 = getPaint(Color.parseColor("#00CED1"));
    }

    public Paint getPaint(int color) {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(color);
        return paint;

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    @Override
    protected void onDraw(Canvas canvas) {


//        RectF rect = new RectF(100, 100, 500, 500);
//        canvas.drawRect(rect, mPaint);
//        canvas.drawRect(600, 100, 1000, 500, mPaint);
//
//        mPaint.setStrokeWidth(20);
//        mPaint.setStrokeCap(Paint.Cap.BUTT);
//        canvas.drawPoint(50, 100, mPaint);

//        canvas.drawOval(50, 50, 350, 200, mPaint);
//        canvas.drawRoundRect(100, 100, 500, 300, 10, 200, mPaint);

//        mPaint.setStyle(Paint.Style.FILL); // 填充模式
//        canvas.drawArc(200, 100, 800, 500, -110, 100, true, mPaint); // 绘制扇形
//        canvas.drawArc(200, 100, 800, 500, 20, 140, false, mPaint); // 绘制弧形
//        mPaint.setStyle(Paint.Style.STROKE); // 画线模式
//        canvas.drawArc(200, 100, 800, 500, 180, 60, false, mPaint); // 绘制不封口的弧形
//        Path path = new Path();
//        path.lineTo(100, 100); // 由当前位置 (0, 0) 向 (100, 100) 画一条直线
//        canvas.drawPath(path, mPaint);

        RectF rect = new RectF(200, 300, 800, 900);
        canvas.drawArc(rect, -180, 110, true, mPaint1);
        canvas.drawArc(200, 300, 800, 900, -60, 60, true, mPaint2);
        canvas.drawArc(200, 300, 800, 900, 0, 15, true, mPaint3);
        canvas.drawArc(200, 300, 800, 900, 20, 65, true, mPaint4);
        canvas.drawArc(200, 300, 800, 900, 80, 95, true, mPaint5);

        canvas.drawText("因为你是里日天", 100, 100, mPaint1);
        canvas.drawRect(250, 100, 1000, 200, mPaint1);

    }
}
