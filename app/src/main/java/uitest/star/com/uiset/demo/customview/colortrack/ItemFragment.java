package uitest.star.com.uiset.demo.customview.colortrack;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by yexing on 2018/12/20.
 */

public class ItemFragment extends Fragment {

    public static final String TITLE = "title";

    public static ItemFragment newInstance(String title) {
        Bundle bundle = new Bundle();
        bundle.putString(TITLE, title);
        ItemFragment itemFragment = new ItemFragment();
        itemFragment.setArguments(bundle);
        return itemFragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        TextView textView = new TextView(getActivity());
        textView.setTextSize(18);
        textView.setTextColor(Color.BLACK);
        textView.setText(getArguments().getString(TITLE));
        textView.setGravity(Gravity.CENTER);
        return textView;
    }
}
