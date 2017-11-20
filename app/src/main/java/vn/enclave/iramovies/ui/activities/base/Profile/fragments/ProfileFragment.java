package vn.enclave.iramovies.ui.activities.base.Profile.fragments;


import android.Manifest;
import android.annotation.TargetApi;
import android.arch.persistence.room.Room;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import vn.enclave.iramovies.R;
import vn.enclave.iramovies.local.storage.entity.User;
import vn.enclave.iramovies.ui.activities.base.Profile.PlaceHolderView;
import vn.enclave.iramovies.ui.activities.base.Profile.ProfileInfoView;
import vn.enclave.iramovies.ui.fragments.Base.IRBaseFragment;
import vn.enclave.iramovies.utilities.Constants;
import vn.enclave.iramovies.utilities.Utils;

import static android.app.Activity.RESULT_OK;

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

public class ProfileFragment extends IRBaseFragment {

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

    private User mUser;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public View getViewLayout(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home_profile, container, false);
    }

    @Override
    public void fragmentCreated() {
        loadUserFromStorage();
    }

    private void loadUserFromStorage() {
        new AsyncTask<Void, Void, List<User>>() {
            @Override
            protected List<User> doInBackground(Void... params) {
                return mAppDatabase.getUserDao().getUsers();
            }

            @Override
            protected void onPostExecute(List<User> groupUsers) {
                loadUserOnView(groupUsers);
            }
        }.execute();
    }

    private void loadUserOnView(List<User> groupUsers) {
        if (groupUsers.isEmpty()) {
            Utils.Toast.showToast(mActivity, getResources().getString(R.string.announce_update_info_account));
        } else {
            mUser = groupUsers.get(0);
            showUserOnView(mUser);
        }
    }

    private void showUserOnView(User mUser) {
        if (mUser != null) {
            return;
        }
        imvPlaceHolder.setImageBitmap(Utils.convertToBitmap(mUser.getAvatar()));
        tvName.setText(mUser.getName());
        tvEmail.setText(mUser.getEmail());
        tvDateOfTheDate.setText(mUser.getBirthday());
        tvGender.setText((mUser.getMale() == 1) ? "Male" : "Female");
    }

    @OnClick({R.id.imvPlaceHolder, R.id.btnEdit, R.id.btnShowAll})
    public void onClick(View view) {
        if (Utils.isDoubleClick()) {
            return;
        }
        switch (view.getId()) {
            case R.id.imvPlaceHolder:
                takePhotoFromGallery();
                break;
            case R.id.btnEdit:
                viewDetailProfile();
                break;
        }
    }

    private void takePhotoFromGallery() {
        Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickPhoto, Constants.GALLERY_REQUEST);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case Constants.GALLERY_REQUEST:
                    ExecuteFileFromPicked.getInstance().setContentUriFromIntent(data);
                case Constants.REVIEW_IMAGE:
                    if (ContextCompat.checkSelfPermission(mActivity, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(mActivity,
                                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                Constants.Permissions.ACCESS_EXTERNAL_STORAGE);
                    } else {
                        loadPathFromStorage();
                    }
                    break;
                case Constants.VIEW_IMAGE:
                    showImageAfterPicker(data);

            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void showImageAfterPicker(Intent data) {
        Bitmap bitmap = BitmapFactory.decodeFile(data.getStringExtra(Constants.IMAGE_PATH));
        imvPlaceHolder.setImageBitmap(bitmap);
        makeCircularImageView(bitmap);
    }

    private void makeCircularImageView(Bitmap bitmap) {
        RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(),bitmap);
        roundedBitmapDrawable.setCircular(true);
        imvPlaceHolder.setImageDrawable(roundedBitmapDrawable);
    }

    private void loadPathFromStorage() {
        String path = ExecuteFileFromPicked.getInstance().getPath(mActivity);
        if (path != null) {
            viewImageFromGallery(path);
        }
    }

    private void viewImageFromGallery(String pathImage) {
        Intent intent = new Intent(mActivity, PlaceHolderView.class);
        intent.putExtra(Constants.IMAGE_PATH, pathImage);
        startActivityForResult(intent, Constants.VIEW_IMAGE);
    }

    private void viewDetailProfile() {
        Intent intent = new Intent(mActivity, ProfileInfoView.class);
        intent.putExtra("userObject", mUser);
        startActivity(intent);
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case Constants.Permissions.ACCESS_EXTERNAL_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    takePhotoFromGallery();
                } else {
                    Toast.makeText(mActivity, "Permission denied!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
