package uitest.star.com.uiset.demo.view.emoji;

import android.content.Context;
import android.content.res.AssetManager;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import uitest.star.com.uiset.R;
import uitest.star.com.uiset.base.UIActivity;
import uitest.star.com.uiset.demo.view.emoji.entity.EmojiBean;
import uitest.star.com.uiset.utils.DeviceUtils;
import uitest.star.com.uiset.utils.UiUtils;

public class EmojiActivity extends UIActivity implements ViewPager.OnPageChangeListener {

    private ViewPager mViewPager;
    private ArrayList<EmojiBean> emojiList = new ArrayList<>();
    private int spanCount;
    private int totalCount;
    private int mLastPos;
    private int pageNum;
    private LinearLayout mLLpointGroup;

    @Override
    protected int inflateLayout() {
        return R.layout.activity_emoji;
    }

    @Override
    protected void initialize() {

    }

    @Override
    protected void initView() {
        mViewPager = findViewById(R.id.viewPager);
        mViewPager.addOnPageChangeListener(this);
        mLLpointGroup = findViewById(R.id.ll_group);
    }

    @Override
    protected void initData() {
        String resultStr = getJson("emoji.json", this);
        Type type = new TypeToken<List<EmojiBean>>() {
        }.getType();
        List<EmojiBean> list = new Gson().fromJson(resultStr, type);

        emojiList.clear();
        emojiList.addAll(list);
        //每一个表情显示44dp
        int minFaceSize = UiUtils.dp2px(58);
        int totalScreen = DeviceUtils.getScreenWidth(this);
        spanCount = totalScreen / minFaceSize;

        //计算每一个ViewPager要显示图片的数量，最后一个是删除
        //一个删除按钮
        totalCount = spanCount * 3 - 1;
        pageNum = emojiList.size() / totalCount;
        if (emojiList.size() != totalCount * pageNum) { //说明正好填充每一页
            pageNum = pageNum + 1;
        }

        EmojiPageViewAdapter adapter = new EmojiPageViewAdapter();
        mViewPager.setOffscreenPageLimit(pageNum);
        mViewPager.setAdapter(adapter);

        for (int i = 0; i < pageNum; i++) {
            ImageView imageView = new ImageView(this);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            if (i == 0) {
                imageView.setBackgroundResource(R.drawable.shape_black_poinat);
            } else {
                imageView.setBackgroundResource(R.drawable.shape_normal_poinat);
                layoutParams.leftMargin = 24;
            }
            imageView.setLayoutParams(layoutParams);
            mLLpointGroup.addView(imageView);
        }


    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {
        mLLpointGroup.getChildAt(i).setBackgroundResource(R.drawable.shape_black_poinat);
        mLLpointGroup.getChildAt(mLastPos).setBackgroundResource(R.drawable.shape_normal_poinat);
        mLastPos = i;
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    public class EmojiPageViewAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return pageNum;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
            return view == o;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {

            EmojiPageView emojiPageView = new EmojiPageView(EmojiActivity.this, spanCount, totalCount, emojiList, mViewPager);
            container.addView(emojiPageView.getRootView());
            return emojiPageView.getRootView();

        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }
    }


    public static String getJson(String fileName, Context context) {
        //将json数据变成字符串
        StringBuilder stringBuilder = new StringBuilder();
        try {
            //获取assets资源管理器
            AssetManager assetManager = context.getAssets();
            //通过管理器打开文件并读取
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }
}
