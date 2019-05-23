package uitest.star.com.uiset.ui.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

import uitest.star.com.uiset.utils.ToastUtil;

public class SelfAdaptionImageView extends AppCompatImageView {
    public SelfAdaptionImageView(Context context) {
        this(context, null);
    }

    public SelfAdaptionImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SelfAdaptionImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void loadImg(String url) {

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Drawable drawable = getDrawable();
        if (drawable != null) {
        } else {
            ToastUtil.showApp("-----");
        }
    }
}
