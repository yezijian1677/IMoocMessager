package net.qiujuer.italker.common.widget;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import net.qiujuer.italker.common.R;
import net.qiujuer.italker.common.widget.recycler.RecyclerAdapter;

import java.util.Objects;


public class GalleyView extends RecyclerView {

    private Adapter mAdapter = new Adapter();

    public GalleyView(Context context) {
        super(context);
        init();
    }

    public GalleyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public GalleyView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        setLayoutManager(new GridLayoutManager(getContext(), 4));
        setAdapter(mAdapter);
        mAdapter.setListener(new RecyclerAdapter.AdapterListenerImpl<Image>() {
            @Override
            public void onItemClick(RecyclerAdapter.ViewHolder holder, Image image) {
                super.onItemClick(holder, image);
            }
        });
    }

    /**
     * 内部的数据结构
     */
    private static class Image {
        int id;//图片id
        String path;//路径
        long date;//创建日期
        boolean isSelect;//是否选中

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Image image = (Image) o;
            return Objects.equals(path, image.path);
        }

        @Override
        public int hashCode() {
            return Objects.hash(path);
        }
    }

    private class Adapter extends RecyclerAdapter<Image>{
        @Override
        protected int getItemViewType(int position, Image image) {
            return R.layout.cell_galley;
        }

        @Override
        protected ViewHolder<Image> onCreateViewHolder(View root, int viewType) {
            return new GalleyView.ViewHolder(root);
        }
    }

    private class ViewHolder extends RecyclerAdapter.ViewHolder<Image>{

        public ViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void onBind(Image image) {

        }
    }
}
