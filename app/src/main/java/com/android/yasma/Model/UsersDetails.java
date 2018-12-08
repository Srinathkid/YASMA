package com.android.yasma.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class UsersDetails implements Parcelable {

    private Integer id;

    private String name;

    private String username;

    private String email;

    private Address address;

    private String phone;

    private String website;

    private Company company;

    protected UsersDetails(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        name = in.readString();
        username = in.readString();
        email = in.readString();
        address = in.readParcelable(Address.class.getClassLoader());
        phone = in.readString();
        website = in.readString();
        company = in.readParcelable(Company.class.getClassLoader());
    }

    public static final Creator<UsersDetails> CREATOR = new Creator<UsersDetails>() {
        @Override
        public UsersDetails createFromParcel(Parcel in) {
            return new UsersDetails(in);
        }

        @Override
        public UsersDetails[] newArray(int size) {
            return new UsersDetails[size];
        }
    };

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(id);
        }
        dest.writeString(name);
        dest.writeString(username);
        dest.writeString(email);
        dest.writeParcelable(address, flags);
        dest.writeString(phone);
        dest.writeString(website);
        dest.writeParcelable(company, flags);
    }
}
