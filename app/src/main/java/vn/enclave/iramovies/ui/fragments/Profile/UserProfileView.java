package vn.enclave.iramovies.ui.fragments.Profile;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import vn.enclave.iramovies.R;
import vn.enclave.iramovies.local.storage.entity.Reminder;
import vn.enclave.iramovies.local.storage.entity.User;
import vn.enclave.iramovies.ui.activities.base.Profile.EditUserProfileView;
import vn.enclave.iramovies.ui.fragments.IRBaseFragment;
import vn.enclave.iramovies.ui.fragments.Profile.adapter.ReminderAdapter;
import vn.enclave.iramovies.utilities.Constants;
import vn.enclave.iramovies.utilities.OverrideFonts;
import vn.enclave.iramovies.utilities.Utils;

import static android.app.Activity.RESULT_OK;

/**
 * Created by lorence on 08/11/2017.
 *
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

    @BindView(R.id.rcvReminders)
    RecyclerView rcvReminders;

    private User mUser;

    /**
     * Work with MVP
     */
    private UserProfilePresenter mUserProfilePresenter;
    private ReminderListInterface mReminderListInterface;
    private ReminderAdapter mReminderAdapter;

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
        initViews();
        loadUserFromStorage();
        loadReminderFromStorage();
    }

    private void initViews() {
        //Use this setting to improve performance if you know that changes in
        //the content do not change the layout size of the RecyclerView
        if (rcvReminders != null) {
            rcvReminders.setHasFixedSize(true);
        }
        if (mReminderAdapter == null) {
            mReminderAdapter = new ReminderAdapter(mActivity, new ArrayList<Reminder>());
        }
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mActivity);
        rcvReminders.setLayoutManager(mLayoutManager);
        rcvReminders.setAdapter(mReminderAdapter);
    }

    @Override
    protected void initAtributes(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.initAtributes(inflater, container, savedInstanceState);
        tvName.setTypeface(OverrideFonts.getTypeFace(mActivity, OverrideFonts.TYPE_FONT_NAME.HELVETICANEUE, OverrideFonts.TYPE_STYLE.REGULAR));
        tvDateOfTheDate.setTypeface(OverrideFonts.getTypeFace(mActivity, OverrideFonts.TYPE_FONT_NAME.HELVETICANEUE, OverrideFonts.TYPE_STYLE.LIGHT));
        tvEmail.setTypeface(OverrideFonts.getTypeFace(mActivity, OverrideFonts.TYPE_FONT_NAME.HELVETICANEUE, OverrideFonts.TYPE_STYLE.LIGHT));
        tvGender.setTypeface(OverrideFonts.getTypeFace(mActivity, OverrideFonts.TYPE_FONT_NAME.HELVETICANEUE, OverrideFonts.TYPE_STYLE.LIGHT));
        tvReminderList.setTypeface(OverrideFonts.getTypeFace(mActivity, OverrideFonts.TYPE_FONT_NAME.HELVETICANEUE, OverrideFonts.TYPE_STYLE.MEDIUM));
        tvCopyright.setTypeface(OverrideFonts.getTypeFace(mActivity, OverrideFonts.TYPE_FONT_NAME.HELVETICANEUE, OverrideFonts.TYPE_STYLE.LIGHT));
        btnEdit.setTypeface(OverrideFonts.getTypeFace(mActivity, OverrideFonts.TYPE_FONT_NAME.HELVETICANEUE, OverrideFonts.TYPE_STYLE.BLACK));
        btnShowAll.setTypeface(OverrideFonts.getTypeFace(mActivity, OverrideFonts.TYPE_FONT_NAME.HELVETICANEUE, OverrideFonts.TYPE_STYLE.BLACK));
    }

    private void loadUserFromStorage() {
        mUserProfilePresenter.getUser();
    }

    private void loadReminderFromStorage() {
        mUserProfilePresenter.getListReminder();
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
                mReminderListInterface.openReminderList(mReminderAdapter.getList());
                break;
        }
    }

    private void updateUserOnUI(User user) {
        tvName.setText(user.getName());
        tvEmail.setText(user.getEmail());
        tvGender.setText(Utils.convertIntToGender(mActivity, user.getMale()));
        tvDateOfTheDate.setText(user.getBirthday());
        if (user.getAvatar() != null) {
            Glide.with(mActivity)
                .load(user.getAvatar())
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
                    User user = data.getParcelableExtra(Constants.Parcelable.USER);
                    updateUserOnUI(user);
                    setUser(user);
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

    @Override
    public void onReminderSuccess(List<Reminder> reminders) {
        for (int index = 0; index < reminders.size(); index++) {
            if (getTime(reminders.get(index).getReminderDate()) < System.currentTimeMillis()) {
                mUserProfilePresenter.removeReminder(reminders.get(index).getId());
                reminders.remove(reminders.get(index));
            }
        }
        mReminderAdapter.setReminders(reminders);
    }

    public static long getTime(String releaseDate) {
        // 2017/12/22 09:18
        DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        Date startDate = null;
        try {
            startDate = df.parse(releaseDate);
            String newDateString = df.format(startDate);
            System.out.println(newDateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return startDate.getTime();
    }

    public User getUser() {
        return mUser;
    }

    public void setUser(User mUser) {
        this.mUser = mUser;
    }

    public void reload(Reminder reminder, boolean isUpdate) {
        if (isUpdate) {
            mReminderAdapter.updateReminder(reminder);
        } else {
            mReminderAdapter.addReminder(reminder);
        }
        onResume();
    }

    public void setReminderListInterface(ReminderListInterface reminderListInterface) {
        this.mReminderListInterface = reminderListInterface;
    }

    public void removeReminder(int id) {
        mUserProfilePresenter.removeReminder(id);
    }

    @Override
    public void onDeleteReminderSuccess(Integer id) {
        mReminderAdapter.removeReminder(id);
    }

    public interface ReminderListInterface {
        void openReminderList(ArrayList<Reminder> reminders);
    }
}
