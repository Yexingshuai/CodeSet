package uitest.star.com.uiset.ui.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.storage.StorageManager;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import uitest.star.com.uiset.R;

/**
 * Created by yexing on 2018/12/24.
 */

public class ShapeView extends View {
    private Shape mDefaultShape = Shape.Circle;
    private Paint paint;
    private Path path;


    public ShapeView(Context context) {
        this(context, null);
    }

    public ShapeView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ShapeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint();
        paint.setColor(Color.RED);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //保证是正方形
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(Math.min(width, height), Math.min(width, height));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //画正方形

        switch (mDefaultShape) {
            case Circle:
                int center = getWidth() / 2;
                paint.setColor(getResources().getColor(R.color.circle));
                canvas.drawCircle(center, center, center, paint);
                break;
            case Square:
                paint.setColor(getResources().getColor(R.color.rect));
                canvas.drawRect(0, 0, getWidth(), getHeight(), paint);
                break;
            case Triangle:
                paint.setColor(getResources().getColor(R.color.triangle));
                if (path == null) {
                    //画三角路径
                    path = new Path();
                    path.moveTo(getWidth() / 2, 0);
                    path.lineTo(0, (float) (getWidth() / 2 * Math.sqrt(3)));
                    path.lineTo(getWidth(), (float) (getWidth() / 2 * Math.sqrt(3)));
                    path.close();  //路径闭合
                }
                canvas.drawPath(path, paint);
                break;
            default:
                break;
        }

        //画三角形
    }


    /**
     * 改变形状
     */
    public void exChange() {
        switch (mDefaultShape) {
            case Circle:
                mDefaultShape = Shape.Square;
                break;
            case Square:
                mDefaultShape = Shape.Triangle;
                break;
            case Triangle:
                mDefaultShape = Shape.Circle;
                break;
            default:
                break;
        }
        invalidate();
    }

    public Shape getCurrentShape() {
        return mDefaultShape;
    }

    public enum Shape {
        Circle, Square, Triangle


    }
}
