package net.qiujuer.italker.push.frags.user;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.yalantis.ucrop.UCrop;

import net.qiujuer.genius.ui.widget.Button;
import net.qiujuer.genius.ui.widget.Loading;
import net.qiujuer.italker.common.app.Application;
import net.qiujuer.italker.common.app.PresenterFragment;
import net.qiujuer.italker.common.widget.PortraitView;
import net.qiujuer.italker.factory.presenter.user.UpdateInfoContract;
import net.qiujuer.italker.factory.presenter.user.UserUpdateInfoPresenter;
import net.qiujuer.italker.push.R;
import net.qiujuer.italker.push.frags.activities.MainActivity;
import net.qiujuer.italker.push.frags.media.GalleryFragment;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;

/**
 * 更新信息
 */
public class UpdateInfoFragment extends PresenterFragment<UpdateInfoContract.Presenter>
implements UpdateInfoContract.View{

    @BindView(R.id.im_sex)
    ImageView mSex;

    @BindView(R.id.edit_desc)
    EditText mDesc;

    @BindView(R.id.loading)
    Loading mLoading;

    @BindView(R.id.btn_submit)
    Button mSubmit;

    @BindView(R.id.im_portrait)
    PortraitView mPortrait;

    //保存选中的变量
    private String mPortraitPath;
    private boolean isMan;

    public UpdateInfoFragment() {
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_update_info;
    }

    @OnClick(R.id.im_portrait)
    void onPortraitClick() {
        new GalleryFragment().setOnselectedListener(new GalleryFragment.OnselectedListener() {
            @Override
            public void onSelectedImage(String path) {
                UCrop.Options options = new UCrop.Options();
                options.setCompressionFormat(Bitmap.CompressFormat.JPEG);
                options.setCompressionQuality(96);
                File dPath = Application.getPortraitTmpFile();
                UCrop.of(Uri.fromFile(new File(path)), Uri.fromFile(dPath))
                        .withAspectRatio(1, 1)
                        .withMaxResultSize(520, 520)
                        .withOptions(options)
                        .start(getActivity());
            }
        }).show(getChildFragmentManager(), GalleryFragment.class.getName());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP) {
            final Uri resultUri = UCrop.getOutput(data);
            if (resultUri != null) {
                loadPortrait(resultUri);
            }
        } else if (resultCode == UCrop.RESULT_ERROR) {
            Application.showToast(R.string.data_rsp_error_unknown);
        }
    }

    private void loadPortrait(Uri uri) {
        mPortraitPath = uri.getPath();
        Glide.with(this).load(uri)
                .asBitmap()
                .centerCrop()
                .into(mPortrait);
    }

    @Override
    protected UpdateInfoContract.Presenter initPresenter() {
        return new UserUpdateInfoPresenter(this);
    }


    @OnClick(R.id.btn_submit)
    void onSubmitClick() {
        String desc = mDesc.getText().toString();
        //调用P层进行注册
        mPresenter.update(mPortraitPath, desc, isMan);
    }

    @OnClick(R.id.im_sex)
    void onSexClick() {

        isMan = !isMan;//反向性别
        Drawable drawable = getResources().getDrawable(isMan ? R.drawable.ic_sex_man : R.drawable.ic_sex_woman);
        mSex.setImageDrawable(drawable);
        //设置背景的层级，设置颜色
        mSex.getBackground().setLevel(isMan ? 0 : 1);
    }


    @Override
    public void updateSucceed() {
        MainActivity.show(getContext());
        getActivity().finish();
    }

    @Override
    public void showError(int str) {
        super.showError(str);
        //发生错误
        mLoading.stop();
        mPortrait.setEnabled(true);
        mDesc.setEnabled(true);
        mSex.setEnabled(true);

        mSubmit.setEnabled(true);

    }

    @Override
    public void showLoading() {
        super.showLoading();

        mLoading.start();
        //发生错误
        mPortrait.setEnabled(false);
        mDesc.setEnabled(false);
        mSex.setEnabled(false);

        mSubmit.setEnabled(false);
    }
}
