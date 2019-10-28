package vn.enclave.iramovies.local.storage.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import vn.enclave.iramovies.local.storage.DatabaseInfo;
import vn.enclave.iramovies.utilities.Constants;


/**
 *
 * Created by lorence on 10/11/2017.
 */

@Entity(tableName = DatabaseInfo.Tables.User)
public class User implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = DatabaseInfo.User.COLUMN_ID)
    private int id;

    @ColumnInfo(name = DatabaseInfo.User.COLUMN_NAME)
    private String name;

    @ColumnInfo(name = DatabaseInfo.User.COLUMN_BIRTHDAY)
    private String birthday;

    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    private byte[] avatar;

    @ColumnInfo(name = DatabaseInfo.User.COLUMN_EMAIL)
    private String email;

    @ColumnInfo(name = DatabaseInfo.User.COLUMN_GENDER)
    private int male;

    @Ignore
    public User() {
        name = email = Constants.EMPTY_STRING;
    }

    public User(String name, String birthday, byte[] avatar, String email, int male) {
        this.name = name;
        this.birthday = birthday;
        this.avatar = avatar;
        this.email = email;
        this.male = male;
    }

    protected User(Parcel in) {
        id = in.readInt();
        name = in.readString();
        birthday = in.readString();
        avatar = in.createByteArray();
        email = in.readString();
        male = in.readInt();
    }

    public static final Creator<vn.enclave.iramovies.local.storage.entity.User> CREATOR = new Creator<vn.enclave.iramovies.local.storage.entity.User>() {
        @Override
        public vn.enclave.iramovies.local.storage.entity.User createFromParcel(Parcel in) {
            return new vn.enclave.iramovies.local.storage.entity.User(in);
        }

        @Override
        public vn.enclave.iramovies.local.storage.entity.User[] newArray(int size) {
            return new vn.enclave.iramovies.local.storage.entity.User[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(birthday);
        dest.writeByteArray(avatar);
        dest.writeString(email);
        dest.writeInt(male);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public byte[] getAvatar() {
        return avatar;
    }

    public void setAvatar(byte[] avatar) {
        this.avatar = avatar;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getMale() {
        return male;
    }

    public void setMale(int male) {
        this.male = male;
    }
}
