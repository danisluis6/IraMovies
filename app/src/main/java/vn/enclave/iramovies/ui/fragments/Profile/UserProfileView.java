package vn.enclave.iramovies.ui.fragments.Profile;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import vn.enclave.iramovies.R;
import vn.enclave.iramovies.local.storage.entity.User;
import vn.enclave.iramovies.ui.activities.base.Profile.EditUserProfilePresenter;
import vn.enclave.iramovies.ui.activities.base.Profile.EditUserProfileView;
import vn.enclave.iramovies.ui.fragments.IRBaseFragment;
import vn.enclave.iramovies.utilities.Constants;
import vn.enclave.iramovies.utilities.OverrideFonts;
import vn.enclave.iramovies.utilities.Utils;

import static android.app.Activity.RESULT_OK;

/**
 * Created by lorence on 08/11/2017.
 *
 * @Run: http://o7planning.org/vi/10491/huong-dan-su-dung-android-fragment
 * => Done
 * @Run: https://stackoverflow.com/questions/5608720/android-preventing-double-click-on-a-button
 * => Done
 * @Run: https://stackoverflow.com/questions/37861575/what-is-pick-contact-argument-in-startactivityforresult-function
 * => Done
 * @Run: https://stackoverflow.com/questions/23003867/android-passing-an-object-to-another-activity
 * => Done
 * @Run: https://stackoverflow.com/questions/38893042/pass-interface-between-activities-in-intent-interface-fails-to-be-serializable
 * => Done
 */

public class UserProfileView extends IRBaseFragment implements IUserProfileView {

    @BindView(R.id.imvAvatar)
    ImageView imvAvatar;

    @BindView(R.id.tvName)
    TextView tvName;

    @BindView(R.id.tvDateOfTheDate)
    TextView tvDateOfTheDate;

    @BindView(R.id.tvEmail)
    TextView tvEmail;

    @BindView(R.id.tvGender)
    TextView tvGender;

    @BindView(R.id.btnEdit)
    Button btnEdit;

    @BindView(R.id.btnShowAll)
    Button btnShowAll;

    @BindView(R.id.tvReminderList)
    TextView tvReminderList;

    @BindView(R.id.tvCopyright)
    TextView tvCopyright;

    private User mUser;

    /**
     * Work with MVP
     */
    private UserProfilePresenter mUserProfilePresenter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }


    @Override
    public View getViewLayout(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user_profile, container, false);
    }

    @Override
    public void fragmentCreated() {
        mUserProfilePresenter = new UserProfilePresenter(mActivity);
        mUserProfilePresenter.attachView(this);
        loadUserFromStorage();
    }

    @Override
    protected void initAtributes(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.initAtributes(inflater, container, savedInstanceState);
        tvName.setTypeface(OverrideFonts.getTypeFace(mActivity, OverrideFonts.TYPE_FONT_NAME.HELVETICANEUE, OverrideFonts.TYPE_STYLE.REGULAR));
        tvDateOfTheDate.setTypeface(OverrideFonts.getTypeFace(mActivity, OverrideFonts.TYPE_FONT_NAME.HELVETICANEUE, OverrideFonts.TYPE_STYLE.LIGHT));
        tvEmail.setTypeface(OverrideFonts.getTypeFace(mActivity, OverrideFonts.TYPE_FONT_NAME.HELVETICANEUE, OverrideFonts.TYPE_STYLE.LIGHT));
        tvGender.setTypeface(OverrideFonts.getTypeFace(mActivity, OverrideFonts.TYPE_FONT_NAME.HELVETICANEUE, OverrideFonts.TYPE_STYLE.LIGHT));
        tvReminderList.setTypeface(OverrideFonts.getTypeFace(mActivity, OverrideFonts.TYPE_FONT_NAME.HELVETICANEUE, OverrideFonts.TYPE_STYLE.LIGHT));
        tvCopyright.setTypeface(OverrideFonts.getTypeFace(mActivity, OverrideFonts.TYPE_FONT_NAME.HELVETICANEUE, OverrideFonts.TYPE_STYLE.LIGHT));
        btnEdit.setTypeface(OverrideFonts.getTypeFace(mActivity, OverrideFonts.TYPE_FONT_NAME.HELVETICANEUE, OverrideFonts.TYPE_STYLE.BLACK));
        btnShowAll.setTypeface(OverrideFonts.getTypeFace(mActivity, OverrideFonts.TYPE_FONT_NAME.HELVETICANEUE, OverrideFonts.TYPE_STYLE.BLACK));
    }

    private void loadUserFromStorage() {
        mUserProfilePresenter.getUser();
    }

    @OnClick({R.id.btnEdit, R.id.btnShowAll})
    public void onClick(View view) {
        if (Utils.isDoubleClick()) {
            return;
        }
        switch (view.getId()) {
            case R.id.btnEdit:
                Intent intent = new Intent(mActivity, EditUserProfileView.class);
                intent.putExtra(Constants.Parcelable.USER, getUser());
                startActivityForResult(intent, Constants.Activities_Result.USER);
                break;
            case R.id.btnShowAll:
                break;
        }
    }

    private void updateUserOnUI(User user) {
        tvName.setText(user.getName());
        tvEmail.setText(user.getEmail());
        tvGender.setText(Utils.convertIntToGender(mActivity, user.getMale()));
        tvDateOfTheDate.setText(user.getBirthday());
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        if (user.getAvatar() != null) {
            Utils.convertToBitmap(user.getAvatar()).compress(Bitmap.CompressFormat.PNG, 100, stream);
            Glide.with(mActivity)
                .load(stream.toByteArray())
                .asBitmap().centerCrop()
                .error(R.drawable.placeholder)
                .into(new BitmapImageViewTarget(imvAvatar) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable circularBitmapDrawable =
                                RoundedBitmapDrawableFactory.create(mActivity.getResources(), resource);
                        circularBitmapDrawable.setCircular(true);
                        imvAvatar.setImageDrawable(circularBitmapDrawable);
                    }
            });
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case Constants.Activities_Result.USER:
                    updateUserOnUI((User) data.getParcelableExtra(Constants.Parcelable.USER));
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onSuccess(List<User> users) {
        setUser(users.get(0));
        updateUserOnUI(users.get(0));
    }

    @Override
    public void onFailure(String message) {
        Utils.Toast.showToast(mActivity, message);
    }

    public User getUser() {
        return mUser;
    }

    public void setUser(User mUser) {
        this.mUser = mUser;
    }
}
