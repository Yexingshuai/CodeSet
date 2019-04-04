package uitest.star.com.uiset.demo.view.okdialog.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by yexing on 2019/1/18.
 */


class AlertController {


    private AlertDialog mDialog;
    private Window mWindow;
    private DialogViewHelper mViewHelper;

    public AlertController(AlertDialog dialog, Window window) {
        this.mDialog = dialog;
        this.mWindow = window;

    }


    public AlertDialog getDialog() {
        return mDialog;
    }

    /**
     * 获取dialog的Window
     *
     * @return
     */
    public Window getWindow() {
        return mWindow;
    }


    public void setViewHelper(DialogViewHelper viewHelper) {
        this.mViewHelper = viewHelper;
    }


    /**
     * 设置文本
     *
     * @param id
     * @param text
     */
    public void setText(int id, CharSequence text) {
        mViewHelper.setText(id, text);
    }


    /**
     * 设置点击
     *
     * @param id
     * @param onClickListener
     */
    public void setOnClickListener(int id, View.OnClickListener onClickListener) {
        mViewHelper.setOnClickListener(id, onClickListener);
    }

    /**
     * 获取view
     *
     * @param id
     * @param <T>
     * @return
     */
    public <T extends View> T getView(int id) {

        return mViewHelper.getView(id);
    }


    public static class AlertParams {
        private AlertController mAlert;
        public Context mContext;
        public int mThemeResId;
        //点击空白是否能取消  (默认可取消)
        public boolean mCancelable = true;
        //dialog cancel监听
        public DialogInterface.OnCancelListener mOnCancelListener;
        //dialog dismiss监听
        public DialogInterface.OnDismissListener mOnDismissListener;
        //dialog  按键监听
        public DialogInterface.OnKeyListener mOnKeyListener;
        //布局view
        public View mView;
        //布局id
        public int mViewLayoutId;
        //存储文本信息
        public SparseArray<CharSequence> mTextArray = new SparseArray<>();  //比hashMap 更高效  必须前面是integer
        //存放点击事件
//        public SparseArray<WeakReference<View.OnClickListener>> mClickArray = new SparseArray<>();  //比hashMap 更高效  必须前面是integer  点击事件有可能传递的是this, 也就是activity,可能会造成内存泄漏
        public SparseArray<View.OnClickListener> mClickArray = new SparseArray<>();  //比hashMap 更高效  必须前面是integer
        //宽度
        public int mWidth = ViewGroup.LayoutParams.WRAP_CONTENT;
        //高度
        public int mHeight = ViewGroup.LayoutParams.WRAP_CONTENT;
        //位置
        public int mGravity = Gravity.CENTER;
        //动画
        public int mAnimations = 0;


        public AlertParams(Context context, int themeResId) {
            this.mContext = context;
            this.mThemeResId = themeResId;
        }

        /**
         * 绑定设置参数
         *
         * @param alertController
         */
        public void apply(AlertController alertController) {
            mAlert = alertController;
            //设置布局
            DialogViewHelper viewHelper = null;
            if (mViewLayoutId != 0) {
                viewHelper = new DialogViewHelper(mContext, mViewLayoutId);
            }

            if (mView != null) {
                viewHelper = new DialogViewHelper();
                viewHelper.setContentView(mView);
            }

            if (viewHelper == null) {
                throw new IllegalArgumentException("请设置布局，setContentView");
            }

            //给dialog设置布局
            mAlert.getDialog().setContentView(viewHelper.getContentView());

            //设置Contontroller辅助类
            mAlert.setViewHelper(viewHelper);

            //2.设置文本
            int size = mTextArray.size();
            for (int i = 0; i < size; i++) {
                viewHelper.setText(mTextArray.keyAt(i), mTextArray.valueAt(i));
            }


            //3.设置点击
            int clickArraySize = mClickArray.size();
            for (int i = 0; i < clickArraySize; i++) {
                viewHelper.setOnClickListener(mClickArray.keyAt(i), mClickArray.valueAt(i));
            }

            //4.配置自定义的效果, 动画之类 位置
            Window window = mAlert.getWindow();
            //位置
            window.setGravity(mGravity);

            //设置动画
            if (mAnimations != 0) {
                window.setWindowAnimations(mAnimations);
            }

            //设置宽高
            WindowManager.LayoutParams attributes = window.getAttributes();
            attributes.width = mWidth;
            attributes.height = mHeight;
            window.setAttributes(attributes);


        }

    }


}
