package uitest.star.com.uiset.demo.view.okdialog;

import android.view.View;
import android.widget.EditText;

import uitest.star.com.uiset.R;
import uitest.star.com.uiset.base.BaseActivity;
import uitest.star.com.uiset.base.UIActivity;
import uitest.star.com.uiset.demo.view.okdialog.dialog.AlertDialog;
import uitest.star.com.uiset.utils.ToastUtil;

public class DialogActivity extends UIActivity {


    private AlertDialog dialog;

    @Override
    protected int inflateLayout() {
        return R.layout.activity_dialog;
    }

    @Override
    protected void initialize() {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {


        dialog = new AlertDialog.Builder(this)
                .setContentView(R.layout.dialog_commit)
                .addDefaultAnimation()
//                .setWidthAndHeight(1080, 600)
//                .fromBottom(true)
//                .fullWidth()
                .setOnClickListener(R.id.tv_receive, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ToastUtil.showApp(((EditText) dialog.getView(R.id.et_commit)).getText().toString().trim());
                    }
                })
                .create();

        //都可以

//        final EditText editText = dialog.getView(R.id.et_commit);  //rv,list 通过get拿到
//
//        dialog.setOnClickListener(R.id.tv_receive, new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                ToastUtil.showApp(editText.getText().toString().trim());
//            }
//        });
    }


    public void dialog(View view) {
        dialog.show();
    }


}
