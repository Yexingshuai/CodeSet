package uitest.star.com.uiset.demo.view.okdialog.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

import uitest.star.com.uiset.R;

/**
 * Created by yexing on 2019/1/18.
 * 简单dialog
 */

public class AlertDialog extends Dialog {

    private AlertController mAlert;




    //系统
    public AlertDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        mAlert = new AlertController(this, getWindow());
    }


    /**
     * 设置文本
     *
     * @param id
     * @param text
     */
    public void setText(int id, CharSequence text) {
        mAlert.setText(id, text);
    }


    /**
     * 设置点击
     *
     * @param id
     * @param onClickListener
     */
    public void setOnClickListener(int id, View.OnClickListener onClickListener) {
        mAlert.setOnClickListener(id, onClickListener);
    }

    /**
     * 获取view
     *
     * @param id
     * @param <T>
     * @return
     */
    public <T extends View> T getView(int id) {

        return   mAlert.getView(id);
    }


    //Builder
    public static class Builder {

        private final AlertController.AlertParams P;

        public Builder(Context context) {
            this(context, R.style.dialog);
        }

        public Builder(Context context, int themeResId) {
            P = new AlertController.AlertParams(context, themeResId);
        }


        public Builder setContentView(View view) {
            P.mView = view;
            P.mViewLayoutId = 0;
            return this;
        }

        /**
         * 设置布局
         *
         * @param layoutId
         * @return
         */
        public Builder setContentView(int layoutId) {
            P.mView = null;
            P.mViewLayoutId = layoutId;
            return this;
        }

        /**
         * 设置文本
         *
         * @param viewId
         * @param text
         * @return
         */
        public Builder setText(int viewId, CharSequence text) {
            P.mTextArray.put(viewId, text);
            return this;
        }

        /**
         * 设置点击事件
         *
         * @param viewId
         * @param listener
         * @return
         */
        public Builder setOnClickListener(int viewId, View.OnClickListener listener) {
            P.mClickArray.put(viewId, listener);
            return this;
        }

        /**
         * 配置全屏
         *
         * @return
         */
        public Builder fullWidth() {
            P.mWidth = ViewGroup.LayoutParams.MATCH_PARENT;
            return this;
        }

        /**
         * 添加默认动画
         *
         * @return
         */
        public Builder addDefaultAnimation() {
            P.mAnimations = R.style.dialog_scale_anim;
            return this;
        }

        /**
         * 设置默认动画
         *
         * @param styleAnim
         * @return
         */
        public Builder setAnimations(int styleAnim) {
            P.mAnimations = styleAnim;
            return this;
        }

        /**
         * 底部弹出
         *
         * @param isAnimation 是否开启动画
         * @return
         */
        public Builder fromBottom(boolean isAnimation) {
            if (isAnimation) {
                P.mAnimations = R.style.dialog_from_bottom_anim;
            }
            P.mGravity = Gravity.BOTTOM;
            return this;
        }

        /**
         * 设置宽高
         *
         * @param width
         * @param height
         * @return
         */
        public Builder setWidthAndHeight(int width, int height) {
            P.mWidth = width;
            P.mHeight = height;
            return this;
        }


        public Builder setCancelable(boolean cancelable) {
            P.mCancelable = cancelable;
            return this;
        }

        public Builder setOnCancelListener(OnCancelListener onCancelListener) {
            P.mOnCancelListener = onCancelListener;
            return this;
        }


        public Builder setOnDismissListener(OnDismissListener onDismissListener) {
            P.mOnDismissListener = onDismissListener;
            return this;
        }

        public Builder setOnKeyListener(OnKeyListener onKeyListener) {
            P.mOnKeyListener = onKeyListener;
            return this;
        }


        public AlertDialog create() {
//           // 调用new AlertDialog构造对象， 并且将参数传递个体AlertDialog
            final AlertDialog dialog = new AlertDialog(P.mContext, P.mThemeResId);
            // 将P中的参数应用的dialog中的mAlert对象中
            P.apply(dialog.mAlert);
            dialog.setCancelable(P.mCancelable);
            if (P.mCancelable) {
                dialog.setCanceledOnTouchOutside(true);
            }
            dialog.setOnCancelListener(P.mOnCancelListener);
            if (P.mOnKeyListener != null) {
                dialog.setOnKeyListener(P.mOnKeyListener);
            }
            return dialog;
        }


        public AlertDialog show() {
            AlertDialog dialog = create();
            dialog.show();
            return dialog;
        }
    }

}
