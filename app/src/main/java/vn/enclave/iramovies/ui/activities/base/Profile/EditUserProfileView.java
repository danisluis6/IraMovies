package vn.enclave.iramovies.ui.activities.base.Profile;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import vn.enclave.iramovies.R;
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
 */

public class EditUserProfileView extends BaseView{

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

    private Snackbar mSnackbar;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_edit_profile;
    }

    @Override
    public void activityCreated(Bundle savedInstanceState) {
        initAtributes();
    }

    private void initAtributes() {
        edtName.setTypeface(OverrideFonts.getTypeFace(mContext, OverrideFonts.TYPE_FONT_NAME.HELVETICANEUE, OverrideFonts.TYPE_STYLE.REGULAR));
        edtDateOfBirth.setTypeface(OverrideFonts.getTypeFace(mContext, OverrideFonts.TYPE_FONT_NAME.HELVETICANEUE, OverrideFonts.TYPE_STYLE.LIGHT));
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
                break;
            case R.id.imvPlaceHolder:
                showDialog();
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case Constants.Permissions.CAMERA: {
                for (int permissionId : grantResults) {
                    if (permissionId != PackageManager.PERMISSION_GRANTED) {
                        Utils.Toast.showToast(this, getString(R.string.permission));
                        return;
                    }
                }
                takePhotoFromCamera();
                break;
            }
        }
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
        TextView textView = (TextView) layout.findViewById(android.support.design.R.id.snackbar_text);
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
//                if (Utils.checkPermissionReadExternalStorage(this)) {
//                    settingPermissionReadExternalStorage();
//                } else {
//                    takePhotoFromGallery();
//                }
            }
        });
        // Add the view to the Snackbar's layout
        layout.addView(snackBarLayout, 0);
        layout.setBackgroundColor(ContextCompat.getColor(mContext, android.R.color.white));
        mSnackbar.setDuration(BaseTransientBottomBar.LENGTH_INDEFINITE);
        mSnackbar.show();
    }

    private void takePhotoFromCamera() {
        // TODO
        Utils.Toast.showToast(mContext, "Hello World");

        // TODO

    }

    private void takePhotoFromGallery() {
        Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickPhoto, Constants.GALLERY_REQUEST);
    }

    private void loadPathFromStorage() {
        String path = ExecuteFileFromPicked.getInstance().getPath(mContext);
        if (path != null) {
            // TODO
        }
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
