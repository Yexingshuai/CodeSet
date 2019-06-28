package uitest.star.com.uiset.demo.view.emoji.preview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import uitest.star.com.uiset.R;


public class GifView extends RelativeLayout {

    private ImageView emojiImage;

    public GifView(Context context) {
        this(context, null);
    }

    public GifView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GifView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setViewAttributes(context, attrs, defStyleAttr);
        init();
    }

    @SuppressLint("NewApi")
    private void setViewAttributes(Context context, AttributeSet attrs,
                                   int defStyle) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }

    }

    public void setResourceId(int resourceId) {
        if (resourceId != -1 & emojiImage != null) {
            emojiImage.setImageResource(resourceId);
        }
    }


    private void init() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.emoji_pop_bg, this, true);
        emojiImage = view.findViewById(R.id.emoji_preview);
    }
}
