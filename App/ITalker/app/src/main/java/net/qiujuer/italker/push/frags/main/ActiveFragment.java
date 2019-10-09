package net.qiujuer.italker.push.frags.main;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.qiujuer.italker.common.app.Fragment;
import net.qiujuer.italker.common.widget.GalleyView;
import net.qiujuer.italker.push.R;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ActiveFragment extends Fragment {

    @BindView(R.id.galleryView)
    GalleyView galleryView;

    public ActiveFragment() {
        // Required empty public constructor
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_active;
    }

    @Override
    protected void initData() {
        super.initData();
        galleryView.setup(getLoaderManager(), new GalleyView.SelectedChangeListener() {
            @Override
            public void onSelectedChanged(int count) {

            }
        });
    }
}
