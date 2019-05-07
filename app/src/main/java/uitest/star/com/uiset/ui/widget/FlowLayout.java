package uitest.star.com.uiset.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import uitest.star.com.uiset.utils.ToastUtil;
import uitest.star.com.uiset.utils.UiUtils;

/**
 * Created by yexing on 2019/1/14.
 */

public class FlowLayout extends ViewGroup {


    private int horizotalSpacing = UiUtils.dp2px(6);
    private int verticalSpacing = UiUtils.dp2px(6);

    //所有行 装载
    private ArrayList<ArrayList<View>> allLines = new ArrayList<>();

    public FlowLayout(Context context) {
        super(context);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    /**
     * 测量自己的宽高，并且给子view传递测量规格(子view完成自己的测量0)   加上padding宽高
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        //  super.onMeasure(widthMeasureSpec, heightMeasureSpec);  这里面没有做测量的事情，只是保存了一个默认值

        allLines.clear(); //onMeasure 会被父容器调用多次，为数据不重复，所以先清空


        ArrayList<View> oneLine = null; //用于保存一行View的集合

        int containerMeasuredWidth = MeasureSpec.getSize(widthMeasureSpec);  //容器的宽度

        //遍历所有的子view
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            int unspecifiedMeasureSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED); //不确定这个TextView 具体宽高，传递不确定测量规格
            child.measure(unspecifiedMeasureSpec, unspecifiedMeasureSpec);

            // 如果是第1个View或者子View的宽大于了可用的宽度，则需要使用一个新行来装
            if (i == 0 || child.getMeasuredWidth() > getUsableWidth(containerMeasuredWidth, oneLine, oneLine.size())) {
                oneLine = new ArrayList<>();
                allLines.add(oneLine);
            }

            oneLine.add(child);

        }

        int rowCount = allLines.size();
        int allRowChildHeight = getChildAt(0).getMeasuredHeight() * rowCount;


        //垂直方向的padding
        int verticalTotalPadding = getPaddingBottom() + getPaddingTop();

        //垂直方向的spacing
        int verticalTotalSpacing = verticalSpacing * (rowCount - 1);
        //容器的高就是所有子view的高
        int containerMeasureHeight = allRowChildHeight + verticalTotalPadding + verticalTotalSpacing;
        setMeasuredDimension(containerMeasuredWidth, containerMeasureHeight); //用于保存测量出来的宽和高

    }


    /**
     * 获取容器中可用宽度
     *
     * @param containerMeasuredWidth
     * @param oneLine
     * @param needSpacingCount       有spacing的个数
     * @return
     */
    private int getUsableWidth(int containerMeasuredWidth, ArrayList<View> oneLine, int needSpacingCount) {

        int oneLineWidth = 0;

        for (View view : oneLine) {
            oneLineWidth += view.getMeasuredWidth();          //计算出这一行中 每个view  累加之后 最后的宽度
        }

        int horizontalTotalPadding = getPaddingLeft() + getPaddingRight();

        int horizontalTotalSpacing = horizotalSpacing * needSpacingCount; // 水平方向总的spacing
        //可用宽度 = 存在的view总宽度-水平方向的 padding-水平方向的总spacing
        int usableWidth = containerMeasuredWidth - oneLineWidth - horizontalTotalPadding - horizontalTotalSpacing;

        return usableWidth;
    }

    /**
     * 对View的位置进行排版       容器可能有padding ,需要把它算进去
     *
     * @param b      参数b代表view 是否有新的尺寸或位置
     * @param left   相对与父View的Left位置
     * @param top    相对与父View的top位置
     * @param right  相对与父View的right位置
     * @param bottom 相对与父View的bottom位置
     */
    @Override
    protected void onLayout(boolean b, int left, int top, int right, int bottom) {

        int rightTemp = 0; //用于保存一行view中上一个View的Right坐标

        int bottomTemp = 0; //用于保存上一行View的bottom坐标

        for (int rowIndex = 0; rowIndex < allLines.size(); rowIndex++) {
            ArrayList<View> oneLine = allLines.get(rowIndex);  //取出一行


            //计算一行View中 每一个view平局可分到的 多余的宽
            int averageUsableWidth = getUsableWidth(getMeasuredWidth(), oneLine, oneLine.size() - 1) / oneLine.size();     //平分剩余空间   (可能会造成精度丢失)

            //遍历一行View
            for (int columnIndex = 0; columnIndex < oneLine.size(); columnIndex++) {


                View child = oneLine.get(columnIndex);
                int measuredWidth = child.getMeasuredWidth();//获取测量宽度
                int measuredHeight = child.getMeasuredHeight();//获取测量高度

                int childLeft = columnIndex == 0 ? getPaddingLeft() : rightTemp + horizotalSpacing;  //如果索引是0, left坐标就放在0的位置， 否则安排在上一个View的right位置,  还要加上间距

                //如果是第0行，top就安排在0的位置，否则的话按照上一行的bottom位置
                int childTop = rowIndex == 0 ? getPaddingTop() : bottomTemp + verticalSpacing;
                int childRight = childLeft + measuredWidth + averageUsableWidth;           //view的右边位置，是上个view的右边位置 + 自己的宽度
                if (columnIndex == oneLine.size() - 1) {
                    //如果是一行view中的最后一个view,让他排列到paddingRight的位置
                    childRight = getMeasuredWidth() - getPaddingRight();
                }

                int childBottom = childTop + measuredHeight;

                child.layout(childLeft, childTop, childRight, childBottom);

                rightTemp = childRight; //保存当前列的view的right的坐标， 用于下一个view的left坐标


                //我们把view的宽高  改成和测量时候不一样了,文本居中的属性是在测量时候完成的
                // 要想居中属性重新计算，则重新调用测量即可
                int widthMeasureSpec = MeasureSpec.makeMeasureSpec(child.getWidth(), MeasureSpec.EXACTLY);      //在layout之后，就能拿到 宽高
                int heightMeasureSpec = MeasureSpec.makeMeasureSpec(child.getHeight(), MeasureSpec.EXACTLY);
                child.measure(widthMeasureSpec, heightMeasureSpec);
            }

            bottomTemp = oneLine.get(0).getBottom(); // 保存当前行的Bottom坐标，用于下一行的view Top坐标

        }

    }

}
