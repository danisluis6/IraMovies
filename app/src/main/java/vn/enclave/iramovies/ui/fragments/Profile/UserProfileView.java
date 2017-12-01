package vn.enclave.iramovies.ui.fragments.Profile;


import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.OnClick;
import vn.enclave.iramovies.R;
import vn.enclave.iramovies.local.storage.entity.User;
import vn.enclave.iramovies.ui.activities.base.Profile.EditUserProfileView;
import vn.enclave.iramovies.ui.fragments.IRBaseFragment;
import vn.enclave.iramovies.utilities.Constants;
import vn.enclave.iramovies.utilities.OverrideFonts;
import vn.enclave.iramovies.utilities.Utils;

/**
 * Created by lorence on 08/11/2017.
 *
 * @Run: http://o7planning.org/vi/10491/huong-dan-su-dung-android-fragment
 * => Done
 * @Run: https://stackoverflow.com/questions/5608720/android-preventing-double-click-on-a-button
 * => Done
 *
 * @Run: https://stackoverflow.com/questions/37861575/what-is-pick-contact-argument-in-startactivityforresult-function
 * => Done
 *
 * @Run: https://stackoverflow.com/questions/23003867/android-passing-an-object-to-another-activity
 * => Done
 */

public class UserProfileView extends IRBaseFragment {

    @BindView(R.id.imvPlaceHolder)
    ImageView imvPlaceHolder;

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
        // loadUserFromStorage();
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

//    private void loadUserFromStorage() {
//        new AsyncTask<Void, Void, List<User>>() {
//            @Override
//            protected List<User> doInBackground(Void... params) {
//                return mAppDatabase.getUserDao().getUsers();
//            }
//
//            @Override
//            protected void onPostExecute(List<User> groupUsers) {
//                loadUserOnView(groupUsers);
//            }
//        }.execute();
//    }
//
//    private void loadUserOnView(List<User> groupUsers) {
//        if (groupUsers.size() > 0) {
//            mUser = groupUsers.get(0);
//            showUserOnView(mUser);
//        }
//    }
//
//    private void showUserOnView(User mUser) {
//        if (mUser != null) {
//            return;
//        }
//        imvPlaceHolder.setImageBitmap(Utils.convertToBitmap(mUser.getAvatar()));
//        tvName.setText(mUser.getName());
//        tvEmail.setText(mUser.getEmail());
//        tvDateOfTheDate.setText(mUser.getBirthday());
//        tvGender.setText((mUser.getMale() == 1) ? "Male" : "Female");
//    }

    @OnClick({R.id.btnEdit, R.id.btnShowAll})
    public void onClick(View view) {
        if (Utils.isDoubleClick()) {
            return;
        }
        switch (view.getId()) {
            case R.id.btnEdit:
                Intent intent = new Intent(mActivity, EditUserProfileView.class);
                startActivity(intent);
                break;
            case R.id.btnShowAll:
                break;
        }
    }
}
