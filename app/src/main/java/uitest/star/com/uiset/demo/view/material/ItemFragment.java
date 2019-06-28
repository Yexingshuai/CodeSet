package uitest.star.com.uiset.demo.view.material;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import uitest.star.com.uiset.R;

/**
 * Created by yexing on 2019/1/4.
 */

public class ItemFragment extends Fragment {

    public static final String RECYCLER_LIST = "recycler_list";
    private RecyclerView mRecyclerView;


    public static ItemFragment newInstance() {
        Bundle bundle = new Bundle();
        ItemFragment itemFragment = new ItemFragment();
        itemFragment.setArguments(bundle);
        return itemFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.item_fragment, container, false);
        return view;

    }
}




