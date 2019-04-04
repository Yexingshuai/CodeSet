package uitest.star.com.uiset.utils;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

import uitest.star.com.uiset.App;

/**
 * 专门封装UI操作的工具类
 * Created by dzl on 2016/11/7.
 */

public class UiUtils {

    /**
     * 在屏幕的中央显示toast
     *
     * @param text
     */
    public static void showToast(CharSequence text) {
        Toast toast = Toast.makeText(App.getContext(), text, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0); // 设置Toast的居中属性
        toast.show();
    }

    /**
     * 把dp单位的值转换为px单位的值
     */
    public static int dp2px(int dp) {
        float density = App.getContext().getResources().getDisplayMetrics().density;
        int px = (int) (dp * density + 0.5);
        return px;
    }

    public static float sp2px(int px) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, px, App.getContext().getResources().getDisplayMetrics());
    }

    /**
     * 创建随机颜色
     */
    public static int createRandomColor() {
        Random random = new Random();
        int red = 50 + random.nextInt(151);         // 50 ~ 200
        int green = 50 + random.nextInt(151);       // 50 ~ 200
        int blue = 50 + random.nextInt(151);        // 50 ~ 200
        int color = Color.rgb(red, green, blue);
        return color;
    }

    /**
     * 创建带随机颜色选择器TextView
     */
    public static TextView createRandomColorSelectorTextView() {
        final TextView textView = new TextView(App.getContext());
        textView.setTextColor(Color.WHITE);
        textView.setTextSize(sp2px(7));
        textView.setPadding(dp2px(6), dp2px(6), dp2px(6), dp2px(6));
        textView.setGravity(Gravity.CENTER);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast(textView.getText());
            }
        });

        textView.setBackgroundDrawable(createRandomColorSelector());

        return textView;
    }

    /**
     * 创建随机颜色选择器
     */
    private static Drawable createRandomColorSelector() {
        StateListDrawable stateListDrawable = new StateListDrawable();  // 创建选择器类型的Drawable
        int[] pressState = {android.R.attr.state_pressed, android.R.attr.state_enabled};
        Drawable pressedDrawable = createRandomColorShape();
        stateListDrawable.addState(pressState, pressedDrawable);    // 设置按下状态和对应显示的Drawable

        int[] normalState = {};
        Drawable normalDrawable = createRandomColorShape();
        stateListDrawable.addState(normalState, normalDrawable);    // 设置正常状态和对应显示的Drawable
        return stateListDrawable;
    }

    private static Drawable createRandomColorShape() {
        GradientDrawable gradientDrawable = new GradientDrawable(); // 创建图形Drawable
        gradientDrawable.setShape(GradientDrawable.RECTANGLE);      // 设置形状为矩形
        gradientDrawable.setCornerRadius(dp2px(6));                 // 设置图形圆角
        gradientDrawable.setColor(createRandomColor());             // 设置图形颜色
        return gradientDrawable;
    }
}
