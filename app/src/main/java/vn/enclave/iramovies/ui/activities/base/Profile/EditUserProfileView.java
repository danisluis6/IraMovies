package vn.enclave.iramovies.ui.activities.base.Profile;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;
import vn.enclave.iramovies.BuildConfig;
import vn.enclave.iramovies.R;
import vn.enclave.iramovies.local.storage.entity.User;
import vn.enclave.iramovies.ui.activities.base.BaseView;
import vn.enclave.iramovies.ui.views.SnackBarLayout;
import vn.enclave.iramovies.utilities.Constants;
import vn.enclave.iramovies.utilities.OverrideFonts;
import vn.enclave.iramovies.utilities.Utils;

/**
 * Created by lorence on 10/11/2017.
 *
 * @Run: https://stackoverflow.com/questions/5056734/android-force-edittext-to-remove-focus
 * => Dismiss focus DONE
 *
 * @Run: https://stackoverflow.com/questions/15005551/androidplacing-the-radio-buttons-horizontally
 * => Setting horizontal in Radio Group
 *
 * @Run: https://stackoverflow.com/questions/17120199/change-circle-color-of-radio-button-android
 * => Done
 *
 * @Run: Process image
 * @Run: https://stackoverflow.com/questions/25278821/how-to-round-an-image-with-glide-library
 * => Done
 *
 * @Run: https://stackoverflow.com/questions/6448856/android-camera-intent-how-to-get-full-sized-photo
 * => How to get full sized of photo.
 * @Run: https://stackoverflow.com/questions/38555301/android-taking-picture-with-fileprovider
 * => FileProvider
 *
 * @Run: https://stackoverflow.com/questions/14933330/datepicker-how-to-popup-datepicker-when-click-on-edittext
 * => DateDialogPicker
 *
 * @Run: https://stackoverflow.com/questions/11932805/cropping-circular-area-from-bitmap-in-android
 * => Crop Image
 */

public class EditUserProfileView extends BaseView implements IEditUserProfileView{

    @BindView(R.id.imvPlaceHolder)
    public ImageView imvPlaceHolder;

    @BindView(R.id.edtName)
    public EditText edtName;

    @BindView(R.id.tvDateOfBirth)
    public TextView tvDateOfBirth;

    @BindView(R.id.edtEmail)
    public EditText edtEmail;

    @BindView(R.id.rdMale)
    public RadioButton rdMale;

    @BindView(R.id.rdFemale)
    public RadioButton rdFemale;

    @BindView(R.id.radioGroup)
    public RadioGroup mRadioGroup;

    @BindView(R.id.btnCancel)
    public Button btnCancel;

    @BindView(R.id.btnDone)
    public Button btnDone;

    private Snackbar mSnackbar;
    private String mCurrentPhotoPath;
    private String mGender;
    private String mPathImage;
    private User mUser;

    /**
     * Work with MVP
     */
    private EditUserProfilePresenter mEditUserProfilePresenter;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_edit_profile;
    }

    @Override
    public void activityCreated(Bundle savedInstanceState) {
        initAttributes();

        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = group.findViewById(checkedId);
                mGender = radioButton.getText().toString();
            }
        });
        getUserFromStorage();
        updateUserOnUI(getUser());
    }

    private void initAttributes() {

        mEditUserProfilePresenter = new EditUserProfilePresenter(this);
        mEditUserProfilePresenter.attachView(this);
        mPathImage = mCurrentPhotoPath = Constants.EMPTY_STRING;

        edtName.setTypeface(OverrideFonts.getTypeFace(mContext, OverrideFonts.TYPE_FONT_NAME.HELVETICANEUE, OverrideFonts.TYPE_STYLE.REGULAR));
        tvDateOfBirth.setTypeface(OverrideFonts.getTypeFace(mContext, OverrideFonts.TYPE_FONT_NAME.HELVETICANEUE, OverrideFonts.TYPE_STYLE.LIGHT));
        edtEmail.setTypeface(OverrideFonts.getTypeFace(mContext, OverrideFonts.TYPE_FONT_NAME.HELVETICANEUE, OverrideFonts.TYPE_STYLE.LIGHT));
        rdMale.setTypeface(OverrideFonts.getTypeFace(mContext, OverrideFonts.TYPE_FONT_NAME.HELVETICANEUE, OverrideFonts.TYPE_STYLE.BLACK));
        rdFemale.setTypeface(OverrideFonts.getTypeFace(mContext, OverrideFonts.TYPE_FONT_NAME.HELVETICANEUE, OverrideFonts.TYPE_STYLE.BLACK));
        btnDone.setTypeface(OverrideFonts.getTypeFace(mContext, OverrideFonts.TYPE_FONT_NAME.HELVETICANEUE, OverrideFonts.TYPE_STYLE.BLACK));
        btnCancel.setTypeface(OverrideFonts.getTypeFace(mContext, OverrideFonts.TYPE_FONT_NAME.HELVETICANEUE, OverrideFonts.TYPE_STYLE.BLACK));
    }

    @OnClick({R.id.btnCancel, R.id.btnDone, R.id.imvPlaceHolder})
    public void onClick(View view) {
        if (Utils.isDoubleClick()) {
            return;
        }
        switch (view.getId()) {
            case R.id.btnCancel:
                finish();
                break;
            case R.id.btnDone:
                saveUserInStorage();
                break;
            case R.id.imvPlaceHolder:
                showDialog();
                break;
        }
    }

    private void saveUserInStorage() {
        mEditUserProfilePresenter.addUser(getInfoUser());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case Constants.Permissions.CAMERA:
                {
                    for (int permissionId : grantResults) {
                        if (permissionId != PackageManager.PERMISSION_GRANTED) {
                            Utils.Toast.showToast(this, getString(R.string.permission));
                            return;
                        }
                    }
                    takePhotoFromCamera();
                    break;
                }
            case Constants.Permissions.STORAGE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    takePhotoFromGallery();
                } else {
                    Utils.Toast.showToast(this, getString(R.string.permission));
                    return;
                }
                break;
        }
    }

    @OnClick(R.id.mainLayout)
    public void closeSnackBar() {
        if (mSnackbar != null && mSnackbar.isShown()) {
            mSnackbar.dismiss();
        }
    }

    @OnClick(R.id.tvDateOfBirth)
    public void openDatePicker() {

        final Calendar myCalendar = Calendar.getInstance();

        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateDisplay(myCalendar);
            }

        };

        new DatePickerDialog(EditUserProfileView.this, date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void updateDisplay(Calendar myCalendar) {
        String myFormat = "yyyy/MM/dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        tvDateOfBirth.setText(sdf.format(myCalendar.getTime()));
    }

    public void showDialog() {
        if (Utils.isVisibleSoftKeyBoardAndroid(this)) {
            Utils.dimissKeyBoard(this);
        }
        if (getRootView() == null) {
            return;
        }
        if (mSnackbar != null && mSnackbar.isShown()) {
            mSnackbar.dismiss();
            return;
        }
        mSnackbar = Snackbar.make(getRootView(), Constants.DIALOG_CHOOSER, Snackbar.LENGTH_LONG);
        final Snackbar.SnackbarLayout layout = (Snackbar.SnackbarLayout) mSnackbar.getView();
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) layout.getLayoutParams();
        params.width = FrameLayout.LayoutParams.MATCH_PARENT;
        params.gravity = Gravity.CENTER | Gravity.BOTTOM;
        layout.setLayoutParams(params);
        TextView textView = layout.findViewById(android.support.design.R.id.snackbar_text);
        textView.setVisibility(View.INVISIBLE);

        SnackBarLayout snackBarLayout = new SnackBarLayout(this, null, getBinder(), mSnackbar);
        snackBarLayout.setSnackInterface(new SnackBarLayout.SnackInterface() {
            @Override
            public void openCamera() {
                if (Utils.checkPermissionCamera(EditUserProfileView.this)) {
                    takePhotoFromCamera();
                } else {
                    Utils.settingPermissionCamera(EditUserProfileView.this);
                }
            }

            @Override
            public void openGallery() {
                if (Utils.checkPermissionStorage(EditUserProfileView.this)) {
                    Utils.settingPermissionStorage(EditUserProfileView.this);
                } else {
                    takePhotoFromGallery();
                }
            }
        });
        // Add the view to the Snackbar's layout
        layout.addView(snackBarLayout, 0);
        layout.setBackgroundColor(ContextCompat.getColor(mContext, android.R.color.white));
        mSnackbar.setDuration(BaseTransientBottomBar.LENGTH_INDEFINITE);
        mSnackbar.show();
    }

    private void takePhotoFromCamera() {
        // To start the camera for this, add an extra to your Intent:
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // get output media file Uri

        File photoFile = null;
        try {
            photoFile = Utils.createImageFile();
            mCurrentPhotoPath = photoFile.getAbsolutePath();
        } catch (IOException ex) {
            // Error occurred while creating the File
        }

        // Continue only if the File was successfully created
        if (photoFile != null) {
            Uri photoURI = FileProvider.getUriForFile(mContext,
                    BuildConfig.APPLICATION_ID + ".fileprovider",
                    photoFile);
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
            startActivityForResult(cameraIntent, Constants.Activities_Result.CAMERA);
        }
    }

    private void takePhotoFromGallery() {
        Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickPhoto, Constants.Activities_Result.GALLERY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case Constants.Activities_Result.GALLERY:
                    if (data == null) {
                        return;
                    }
                    ExecuteFileFromPicked.getInstance().setContentUriFromIntent(data);
                    loadPathFromStorage(ExecuteFileFromPicked.getInstance().getPath(mContext));
                    break;
                case Constants.Activities_Result.CAMERA:
                    if (mCurrentPhotoPath != null) {
                        loadPathFromStorage(mCurrentPhotoPath);
                    }
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void loadPathFromStorage(String pathImage) {
        setPathImage(pathImage);
        if (pathImage != null) {
            Glide.with(mContext).load(pathImage).asBitmap().centerCrop().into(new BitmapImageViewTarget(imvPlaceHolder) {
                @Override
                protected void setResource(Bitmap resource) {
                    RoundedBitmapDrawable circularBitmapDrawable =
                            RoundedBitmapDrawableFactory.create(mContext.getResources(), resource);
                    circularBitmapDrawable.setCircular(true);
                    imvPlaceHolder.setImageDrawable(circularBitmapDrawable);
                }
            });
        }
    }

    @Override
    public void onSuccess(User user) {
        Intent intent = new Intent();
        intent.putExtra(Constants.Parcelable.USER, user);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    @Override
    public void onFailure(String message) {
        finish();
    }

    public User getInfoUser() {
        User mUser = new User();
        mUser.setName(edtName.getText().toString());
        mUser.setEmail(edtEmail.getText().toString());
        mUser.setBirthday(tvDateOfBirth.getText().toString());
        mUser.setMale(Utils.convertGenderToInt(mContext, mGender));
        mUser.setAvatar(TextUtils.equals(getPathImage(), Constants.EMPTY_STRING) ? null : Utils.convertPathToBlob(getPathImage()));
        return mUser;
    }

    private void updateUserOnUI(User user) {
        edtName.setText(user.getName());
        edtEmail.setText(user.getEmail());
        rdMale.setChecked(user.getMale() == 0);
        rdFemale.setChecked(user.getMale() == 1);
        tvDateOfBirth.setText(user.getBirthday());
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        if (user.getAvatar() != null) {
            Utils.convertToBitmap(user.getAvatar()).compress(Bitmap.CompressFormat.PNG, 100, stream);
            Glide.with(this)
                .load(stream.toByteArray())
                .asBitmap().centerCrop()
                .error(R.drawable.placeholder)
                .into(new BitmapImageViewTarget(imvPlaceHolder) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable circularBitmapDrawable =
                                RoundedBitmapDrawableFactory.create(getResources(), resource);
                        circularBitmapDrawable.setCircular(true);
                        imvPlaceHolder.setImageDrawable(circularBitmapDrawable);
                    }
            });
        }
    }

    public String getPathImage() {
        return mPathImage;
    }

    public void setPathImage(String mPathImage) {
        this.mPathImage = mPathImage;
    }

    public User getUserFromStorage() {
        User user = getIntent().getParcelableExtra(Constants.Parcelable.USER);
        setUser(user);
        return user;
    }

    public User getUser() {
        return mUser;
    }

    public void setUser(User mUser) {
        this.mUser = mUser;
    }

    private static class ExecuteFileFromPicked {

        private static ExecuteFileFromPicked sInstance;
        private Uri mUri;

        private ExecuteFileFromPicked() {
        }

        private static synchronized ExecuteFileFromPicked getInstance() {
            if (sInstance == null) {
                sInstance = new ExecuteFileFromPicked();
            }
            return sInstance;
        }

        private static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
            Cursor cursor = null;
            final String column = "_data";
            final String[] projection = {column};
            try {
                cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
                if (cursor != null && cursor.moveToFirst()) {
                    final int index = cursor.getColumnIndexOrThrow(column);
                    return cursor.getString(index);
                }
            } finally {
                if (cursor != null)
                    cursor.close();
            }
            return null;
        }

        private static boolean isExternalStorageDocument(Uri uri) {
            return "com.android.externalstorage.documents".equals(uri.getAuthority());
        }

        /**
         * @param uri The Uri to check.
         * @return Whether the Uri authority is DownloadsProvider.
         */
        private static boolean isDownloadsDocument(Uri uri) {
            return "com.android.providers.downloads.documents".equals(uri.getAuthority());
        }

        /**
         * @param uri The Uri to check.
         * @return Whether the Uri authority is MediaProvider.
         */
        private static boolean isMediaDocument(Uri uri) {
            return "com.android.providers.media.documents".equals(uri.getAuthority());
        }

        /**
         * @param uri The Uri to check.
         * @return Whether the Uri authority is Google Photos.
         */
        private static boolean isGooglePhotosUri(Uri uri) {
            return "com.google.android.apps.photos.content".equals(uri.getAuthority());
        }

        private void setContentUriFromIntent(Intent data) {
            if (data != null) {
                if (data.getData() != null) {
                    mUri = data.getData();
                }
            }
        }

        private String getPath(Context context) {
            if (mUri == null) {
                return null;
            }
            final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
            // DocumentProvider
            if (isKitKat && DocumentsContract.isDocumentUri(context, mUri)) {
                // ExternalStorageProvider
                if (isExternalStorageDocument(mUri)) {
                    final String docId = DocumentsContract.getDocumentId(mUri);
                    final String[] split = docId.split(":");
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
                // DownloadsProvider
                else if (isDownloadsDocument(mUri)) {
                    final String id = DocumentsContract.getDocumentId(mUri);
                    final Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
                    return getDataColumn(context, contentUri, null, null);
                }
                // MediaProvider
                else if (isMediaDocument(mUri)) {
                    final String docId = DocumentsContract.getDocumentId(mUri);
                    final String[] split = docId.split(":");
                    final String type = split[0];
                    Uri contentUri = null;
                    if ("image".equals(type)) {
                        contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                    } else if ("video".equals(type)) {
                        contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                    } else if ("audio".equals(type)) {
                        contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                    }
                    final String selection = "_id=?";
                    final String[] selectionArgs = new String[]{split[1]};
                    return getDataColumn(context, contentUri, selection, selectionArgs);
                }
            }
            // MediaStore (and general)
            else if ("content".equalsIgnoreCase(mUri.getScheme())) {
                // Return the remote address
                if (isGooglePhotosUri(mUri))
                    return mUri.getLastPathSegment();
                return getDataColumn(context, mUri, null, null);
            }
            // File
            else if ("file".equalsIgnoreCase(mUri.getScheme())) {
                return mUri.getPath();
            }
            return null;
        }
    }
}
