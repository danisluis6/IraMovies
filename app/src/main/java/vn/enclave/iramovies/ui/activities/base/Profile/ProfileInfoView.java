package vn.enclave.iramovies.ui.activities.base.Profile;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;

import butterknife.BindView;
import vn.enclave.iramovies.R;
import vn.enclave.iramovies.local.storage.entity.User;
import vn.enclave.iramovies.ui.activities.base.BaseView;
import vn.enclave.iramovies.utilities.Utils;

/**
 * Created by lorence on 10/11/2017.
 *
 * @Run: https://stackoverflow.com/questions/5056734/android-force-edittext-to-remove-focus
 * => Dismiss focus DONE
 *
 * @Run:
 */

public class ProfileInfoView extends BaseView{

    @BindView(R.id.imvPlaceHolder)
    public ImageView imvPlaceHolder;

    @BindView(R.id.edtName)
    public EditText edtName;

    @BindView(R.id.edtDateOfBirth)
    public EditText edtDateOfBirth;

    @BindView(R.id.edtEmail)
    public EditText edtEmail;

    @BindView(R.id.rdMale)
    public RadioButton rdMale;

    @BindView(R.id.rdFemale)
    public RadioButton rdFemale;

    @BindView(R.id.btnCancel)
    public Button btnCancel;

    @BindView(R.id.btnDone)
    public Button btnDone;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_edit_profile;
    }

    private User mUser;

    @Override
    public void activityCreated() {
        getInfoUserFromIntent();
    }


    public User getInfoUserFromIntent() {
        mUser = getIntent().getParcelableExtra("userObject");
        loadUserOnView(mUser);
        return mUser;
    }

    private void loadUserOnView(User user) {
        imvPlaceHolder.setImageBitmap(Utils.convertToBitmap(user.getAvatar()));
        edtName.setText(user.getName());
        edtDateOfBirth.setText(user.getBirthday());
        edtEmail.setText(user.getEmail());
        if (user.getMale() == 1) {
            rdMale.setChecked(true);
            rdFemale.setChecked(false);
        } else {
            rdMale.setChecked(false);
            rdFemale.setChecked(true);
        }
    }
}
