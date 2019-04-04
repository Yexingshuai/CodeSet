package uitest.star.com.uiset.demo.view.dialog;

import android.app.Dialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import uitest.star.com.base.BaseDialog;
import uitest.star.com.base.BaseDialogFragment;
import uitest.star.com.dialog.AddressDialog;
import uitest.star.com.dialog.DateDialog;
import uitest.star.com.dialog.InputDialog;
import uitest.star.com.dialog.MenuDialog;
import uitest.star.com.dialog.MessageDialog;
import uitest.star.com.dialog.PayPasswordDialog;
import uitest.star.com.dialog.ToastDialog;
import uitest.star.com.dialog.WaitDialog;
import uitest.star.com.uiset.R;
import uitest.star.com.uiset.base.UIActivity;
import uitest.star.com.uiset.ui.adapter.base.BaseRecyclerAdapter;
import uitest.star.com.uiset.ui.adapter.base.BaseViewHolder;
import uitest.star.com.uiset.utils.ToastUtil;


/**
 * <p>
 * 非原创！
 * </p>
 */
public class CommonDialogActivity extends UIActivity {


    private RecyclerView mRecyclerView;
    private String[] mData = {"弹出消息对话框", "弹出输入对话框", "弹出底部选择框", "弹出居中选择框", "弹出完成对话框", "弹出错误对话框", "弹出警告对话框", "弹出加载对话框", "弹出支付对话框", "弹出地区对话框"
            , "弹出日期对话框", "弹出自定义对话框"};

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int pos = (int) view.getTag();
            String type = mData[pos];
            switch (type) {
                case "弹出消息对话框":
                    new MessageDialog.Builder(CommonDialogActivity.this)
                            .setTitle("我是标题") // 标题可以不用填写
                            .setMessage("我是内容")
                            .setConfirm("确定")
                            .setCancel(null) // 设置 null 表示不显示取消按钮
                            //.setAutoDismiss(false) // 设置点击按钮后不关闭对话框
                            .setListener(new MessageDialog.OnListener() {

                                @Override
                                public void onConfirm(Dialog dialog) {
                                    toast("确定了");
                                }

                                @Override
                                public void onCancel(Dialog dialog) {
                                    toast("取消了");
                                }
                            })
                            .show();
                    break;

                case "弹出输入对话框":
                    new InputDialog.Builder(CommonDialogActivity.this)
                            .setTitle("我是标题") // 标题可以不用填写
                            .setContent("我是内容")
                            .setHint("我是提示")
                            .setConfirm("确定")
                            .setCancel("取消") // 设置 null 表示不显示取消按钮
                            //.setAutoDismiss(false) // 设置点击按钮后不关闭对话框
                            .setListener(new InputDialog.OnListener() {

                                @Override
                                public void onConfirm(Dialog dialog, String content) {
                                    toast("确定了：" + content);
                                }

                                @Override
                                public void onCancel(Dialog dialog) {
                                    toast("取消了");
                                }
                            })
                            .show();
                    break;
                case "弹出底部选择框":
                    List<String> data = new ArrayList<>();
                    for (int i = 0; i < 10; i++) {
                        data.add("我是数据" + i);
                    }
                    new MenuDialog.Builder(CommonDialogActivity.this)
                            .setCancel("取消") // 设置 null 表示不显示取消按钮
                            //.setAutoDismiss(false) // 设置点击按钮后不关闭对话框
                            .setList(data)
                            .setListener(new MenuDialog.OnListener() {

                                @Override
                                public void onSelected(Dialog dialog, int position, String text) {
                                    toast("位置：" + position + "，文本：" + text);
                                }

                                @Override
                                public void onCancel(Dialog dialog) {
                                    toast("取消了");
                                }
                            })
                            .setGravity(Gravity.BOTTOM)
                            .setAnimStyle(BaseDialog.AnimStyle.BOTTOM)
                            .show();
                    break;
                case "弹出居中选择框":
                    List<String> data1 = new ArrayList<>();
                    for (int i = 0; i < 10; i++) {
                        data1.add("我是数据" + i);
                    }
                    new MenuDialog.Builder(CommonDialogActivity.this)
                            .setCancel(null) // 设置 null 表示不显示取消按钮
                            //.setAutoDismiss(false) // 设置点击按钮后不关闭对话框
                            .setList(data1)
                            .setListener(new MenuDialog.OnListener() {

                                @Override
                                public void onSelected(Dialog dialog, int position, String text) {
                                    toast("位置：" + position + "，文本：" + text);
                                }

                                @Override
                                public void onCancel(Dialog dialog) {
                                    toast("取消了");
                                }
                            })
                            .setGravity(Gravity.CENTER)
                            .setAnimStyle(BaseDialog.AnimStyle.SCALE)
                            .show();
                    break;
                case "弹出完成对话框":
                    new ToastDialog.Builder(CommonDialogActivity.this)
                            .setType(ToastDialog.Type.FINISH)
                            .setMessage("完成")
                            .show();
                    break;
                case "弹出错误对话框":
                    new ToastDialog.Builder(CommonDialogActivity.this)
                            .setType(ToastDialog.Type.ERROR)
                            .setMessage("错误")
                            .show();
                    break;
                case "弹出警告对话框":
                    new ToastDialog.Builder(CommonDialogActivity.this)
                            .setType(ToastDialog.Type.WARN)
                            .setMessage("警告")
                            .show();
                    break;
                case "弹出加载对话框":
                    final BaseDialog dialog = new WaitDialog.Builder(CommonDialogActivity.this)
                            .setCancelable(true)
                            .setMessage("加载中...") // 消息文本可以不用填写
                            .show();
                    break;
                case "弹出支付对话框":
                    new PayPasswordDialog.Builder(CommonDialogActivity.this)
                            .setTitle("请输入支付密码")
                            .setSubTitle("用于购买一个女盆友")
                            .setMoney("￥ 100.00")
                            //.setAutoDismiss(false) // 设置点击按钮后不关闭对话框
                            .setListener(new PayPasswordDialog.OnListener() {

                                @Override
                                public void onCompleted(Dialog dialog, String password) {
                                    toast(password);
                                }

                                @Override
                                public void onCancel(Dialog dialog) {
                                    toast("取消了");
                                }
                            })
                            .show();
                    break;
                case "弹出地区对话框":
                    new AddressDialog.Builder(CommonDialogActivity.this)
                            .setTitle("选择地区")
                            //.setIgnoreArea() // 不选择县级区域
                            .setListener(new AddressDialog.OnListener() {

                                @Override
                                public void onSelected(Dialog dialog, String province, String city, String area) {
                                    toast(province + city + area);
                                }

                                @Override
                                public void onCancel(Dialog dialog) {
                                    toast("取消了");
                                }
                            })
                            .show();
                    break;
                case "弹出日期对话框":
                    new DateDialog.Builder(CommonDialogActivity.this)
                            .setTitle("请选择日期")
                            .setListener(new DateDialog.OnListener() {
                                @Override
                                public void onSelected(Dialog dialog, int year, int month, int day) {
                                    toast(year + "年" + month + "月" + day + "日");
                                }

                                @Override
                                public void onCancel(Dialog dialog) {
                                    toast("取消了");
                                }
                            })
                            .show();
                    break;
                case "弹出自定义对话框":
                    new BaseDialogFragment.Builder(CommonDialogActivity.this)
                            .setContentView(R.layout.dialog_custom)
                            .setAnimStyle(BaseDialog.AnimStyle.SCALE)
                            //.setText(id, "我是预设置的文本")
                            .setOnClickListener(R.id.btn_dialog_custom_ok, new BaseDialog.OnClickListener<ImageView>() {

                                @Override
                                public void onClick(Dialog dialog, ImageView view) {
                                    dialog.dismiss();
                                }
                            })
                            .show();
                    break;
            }
        }
    };

    private void toast(String s) {
        ToastUtil.showApp(s);
    }

    @Override
    protected int inflateLayout() {
        return R.layout.activity_common_dialog;
    }

    @Override
    protected void initialize() {

    }

    @Override
    protected void initView() {
        mRecyclerView = findViewById(R.id.recyclerview);

    }

    @Override
    protected void initData() {
        List<String> list = Arrays.asList(mData);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        BaseRecyclerAdapter<String> adapter = new BaseRecyclerAdapter<String>(this, R.layout.item_dialog_tab, list, null, new int[]{R.id.bt}, onClickListener) {

            @Override
            protected void onBindHolder(BaseViewHolder holder, String s, int position) {
                holder.setText(R.id.bt, s);
            }
        };
        mRecyclerView.setAdapter(adapter);
    }
}
