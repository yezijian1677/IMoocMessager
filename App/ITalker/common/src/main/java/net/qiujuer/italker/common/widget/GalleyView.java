package net.qiujuer.italker.common.widget;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import net.qiujuer.genius.ui.widget.CheckBox;
import net.qiujuer.genius.ui.widget.ImageView;
import net.qiujuer.italker.common.R;
import net.qiujuer.italker.common.widget.recycler.RecyclerAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;


public class GalleyView extends RecyclerView {

    private static final int LOADER_ID = 0x100;
    private static final int MAX_IMAGE_COUNT = 3;
    private static final int MIN_IMAGE_FILE_SIZE = 10 * 1024;
    private LoaderCallback loaderCallback = new LoaderCallback();
    private Adapter mAdapter = new Adapter();
    private List<Image> mSelectedImages = new LinkedList<>();
    private SelectedChangeListener mListener;

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
                //点击操作，如果点击是允许的，那么更新对应cell的状态
                //然后更新界面, 如果不能允许点击：达到最大的选中数的时候,那么就不刷新界面
                if (onItemSelectClick(image)) {
                    holder.updateData(image);
                }
            }
        });
    }

    public int setup(LoaderManager loaderManager, SelectedChangeListener listener) {

        mListener = listener;
        loaderManager.initLoader(LOADER_ID, null, loaderCallback);
        return LOADER_ID;
    }

    /**
     * Cell 点击的具体逻辑
     * @param image  image
     * @return  true 进行了数据变更
     */
    private boolean onItemSelectClick(Image image) {
        boolean notifyRefresh;

        if (mSelectedImages.contains(image)) {
            mSelectedImages.remove(image);
            image.isSelect = false;
            notifyRefresh = true;
        }else {
            if (mSelectedImages.size() >= MAX_IMAGE_COUNT) {
                notifyRefresh = false;
            } else {
                mSelectedImages.add(image);
                image.isSelect = true;
                notifyRefresh = true;
            }
        }

        if (notifyRefresh) {
            notifySelectChanged();
        }
        return true;
    }

    /**
     * 通知选中状态改变
     */
    private void notifySelectChanged() {
        SelectedChangeListener listener = mListener;
        if (listener != null) {
            listener.onSelectedChanged(mSelectedImages.size());
        }
    }

    /**
     * 通知adapter更换数据
     * @param images
     */
    private void updateSource(List<Image> images) {
        mAdapter.replace(images);
    }

    /**
     * 得到选中的图片的全部地址
     * @return
     */
    public String[] getSelectedPath() {
        String[] paths = new String[mSelectedImages.size()];
        int index = 0;
        for (Image image : mSelectedImages) {
            paths[index++] = image.path;
        }

        return paths;
    }

    /**
     * 清空选中的图片
     */
    public void clear() {
        for (Image image : mSelectedImages) {
            image.isSelect = false;
        }
        mSelectedImages.clear();
        //通知更新
        mAdapter.notifyDataSetChanged();
    }

    /**
     * 用于实际数据加载的loader
     */
    private class LoaderCallback implements LoaderManager.LoaderCallbacks<Cursor> {

        private final String[] IMAGE_PROJECTION = new String[]{
                MediaStore.Images.Media._ID,
                MediaStore.Images.Media.DATA,
                MediaStore.Images.Media.DATE_ADDED
        };
        @NonNull
        @Override
        public Loader<Cursor> onCreateLoader(int i, @Nullable Bundle bundle) {
            //创建一个loader
            if (i == LOADER_ID) {
                return new CursorLoader(getContext(),
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        IMAGE_PROJECTION,
                        null,
                        null,
                        IMAGE_PROJECTION[2] + " DESC");
            }
            return null;
        }

        @Override
        public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor cursor) {
            //loader加载完成时
            List<Image> images = new ArrayList<>();
            if (cursor != null) {
                int count = cursor.getCount();
                if (count > 0) {
                    cursor.moveToFirst();
                    int indexId = cursor.getColumnIndexOrThrow(IMAGE_PROJECTION[0]);
                    int indexPath = cursor.getColumnIndexOrThrow(IMAGE_PROJECTION[1]);
                    int indexDate = cursor.getColumnIndexOrThrow(IMAGE_PROJECTION[2]);
                    do {
                        int id = cursor.getInt(indexId);
                        String path = cursor.getString(indexPath);
                        long date = cursor.getLong(indexDate);

                        File file = new File(path);
                        if (!file.exists() || file.length() < MIN_IMAGE_FILE_SIZE) {
                            continue;
                        }

                        Image image = new Image();
                        image.id = id;
                        image.path = path;
                        image.date = date;
                        images.add(image);

                    } while (cursor.moveToNext());
                }
            }
            updateSource(images);
        }

        @Override
        public void onLoaderReset(@NonNull Loader<Cursor> loader) {
            //销毁或者重置
            updateSource(null);
        }
    }


    /**
     * 内部的数据结构 Image
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
        private ImageView mPic;
        private View mShade;
        private CheckBox mSelected;


        public ViewHolder(View itemView) {
            super(itemView);

            mPic = (ImageView) itemView.findViewById(R.id.im_image);
            mShade = (View) itemView.findViewById(R.id.view_shade);
            mSelected = (CheckBox) itemView.findViewById(R.id.cb_select);
        }

        @Override
        protected void onBind(Image image) {
            Glide.with(getContext())
                    .load(image.path)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .centerCrop()
                    .placeholder(R.color.grey_200)
                    .into(mPic);

            mShade.setVisibility(image.isSelect ? VISIBLE : INVISIBLE);

            mSelected.setChecked(image.isSelect);
        }
    }

    public interface SelectedChangeListener {
        void onSelectedChanged(int count);
    }
}
