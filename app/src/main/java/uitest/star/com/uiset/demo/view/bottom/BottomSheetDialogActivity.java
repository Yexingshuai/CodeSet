package uitest.star.com.uiset.demo.view.bottom;

import android.support.design.widget.BottomSheetDialog;
import android.view.View;
import android.widget.Button;

import uitest.star.com.uiset.R;
import uitest.star.com.uiset.base.BaseActivity;
import uitest.star.com.uiset.base.UIActivity;

public class BottomSheetDialogActivity extends UIActivity {


    private BottomSheetDialog bottomSheetDialog;
    private MyBottomDialog myBottomDialog;

    @Override
    protected int inflateLayout() {
        return R.layout.activity_bottom_sheet_dialog;
    }

    @Override
    protected void initialize() {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        bottomSheetDialog = new BottomSheetDialog(this);
//        bottomSheetDialog.setCancelable(false);
        bottomSheetDialog.setCanceledOnTouchOutside(false);
        bottomSheetDialog.setContentView(R.layout.dialog_goods_sku_layout);
        //给布局设置透明背景色
        bottomSheetDialog.getDelegate().findViewById(android.support.design.R.id.design_bottom_sheet)
                .setBackgroundColor(getResources().getColor(android.R.color.transparent));

        //
        myBottomDialog = new MyBottomDialog();





    }

    public void button1(View view) {
        bottomSheetDialog.show();
    }

    public void button2(View view) {
        myBottomDialog.show(getSupportFragmentManager(), "dialog");
    }
}
