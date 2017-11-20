package vn.enclave.iramovies.ui.activities.base.Profile;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;
import vn.enclave.iramovies.R;
import vn.enclave.iramovies.ui.activities.base.BaseView;
import vn.enclave.iramovies.utilities.Constants;
import vn.enclave.iramovies.utilities.Utils;

/**
 * Created by lorence on 10/11/2017.
 *
 * @Run: https://stackoverflow.com/questions/4181774/show-image-view-from-file-path
 * => Done
 *
 * @Run: https://stackoverflow.com/questions/5364746/pass-argument-to-previous-activity
 * => Done
 *
 * @Run: https://stackoverflow.com/questions/5432495/cut-the-portion-of-bitmap
 * => Done
 */

public class PlaceHolderView extends BaseView{

    @BindView(R.id.imgViewGallery)
    public ImageView imgViewGallery;

    @BindView(R.id.tvCancel)
    public TextView tvCancel;

    @BindView(R.id.tvChoose)
    public TextView tvChoose;

    private String mPathImage;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_view_image;
    }

    @Override
    public void activityCreated() {
        mPathImage = getIntent().getStringExtra(Constants.IMAGE_PATH);
        loadBitMapFromFile(mPathImage);
    }

    @OnClick({R.id.tvChoose, R.id.tvCancel})
    public void onClick(View view) {
        if (Utils.isDoubleClick()) {
            return;
        }
        switch (view.getId()) {
            case R.id.tvChoose:
                Intent data = new Intent();
                data.putExtra(Constants.IMAGE_PATH, mPathImage);
                setResult(RESULT_OK, data);
                finish();
                break;
            case R.id.tvCancel:
                finish();
                break;
        }
    }

    private void loadBitMapFromFile(String pathImage) {
        if(checkExistedImageFile(mPathImage)) {
            File imgFile = new  File(pathImage);
            if(imgFile.exists()){
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                imgViewGallery.setImageBitmap(myBitmap);
            }
        }
    }

    private boolean checkExistedImageFile(String pathImage) {
        if (pathImage != null) {
            if (!new File(pathImage).exists()) {
                Utils.Toast.showToast(mContext, "Error when pick file");
                finish();
            }
        }
        return true;
    }
}
