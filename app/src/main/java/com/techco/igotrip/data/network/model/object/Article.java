package com.techco.igotrip.data.network.model.object;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Nhat on 12/24/17.
 */

public class Article implements Parcelable {
    public static final int WAITING = 1;
    public static final int APPROVED = 2;
    public static final int REJECT = 3;
    public static final int EXPIRED = 4;

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("contact_number")
    @Expose
    private String contactNumber;
    @SerializedName("contact_name")
    @Expose
    private String contactName;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("address_detail")
    @Expose
    private String addressDetail;
    @SerializedName("lat")
    @Expose
    private double lat;
    @SerializedName("lng")
    @Expose
    private double lng;
    @SerializedName("distance")
    @Expose
    private double distance;
    @SerializedName("type_name")
    @Expose
    private String articleType;
    @SerializedName("house_type_name")
    @Expose
    private String articleHouseType;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("province")
    @Expose
    private String province;
    @SerializedName("is_favorite")
    @Expose
    private boolean favorite;
    @SerializedName("is_my_trip")
    @Expose
    private boolean mytrip;
    @SerializedName("status")
    @Expose
    private int status;
    @SerializedName("favorite_count")
    @Expose
    private int favoriteCount;
    @SerializedName("index")
    @Expose
    private int index;
    @SerializedName("user_id")
    @Expose
    private int userId;

    protected Article(Parcel in) {
        id = in.readInt();
        title = in.readString();
        description = in.readString();
        contactNumber = in.readString();
        contactName = in.readString();
        createdAt = in.readString();
        updatedAt = in.readString();
        addressDetail = in.readString();
        lat = in.readDouble();
        lng = in.readDouble();
        distance = in.readDouble();
        articleType = in.readString();
        articleHouseType = in.readString();
        image = in.readString();
        province = in.readString();
        favorite = in.readByte() != 0;
        mytrip = in.readByte() != 0;
        status = in.readInt();
        favoriteCount = in.readInt();
        index = in.readInt();
        userId = in.readInt();
    }

    public static final Creator<Article> CREATOR = new Creator<Article>() {
        @Override
        public Article createFromParcel(Parcel in) {
            return new Article(in);
        }

        @Override
        public Article[] newArray(int size) {
            return new Article[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getAddressDetail() {
        return addressDetail;
    }

    public void setAddressDetail(String addressDetail) {
        this.addressDetail = addressDetail;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public String getArticleType() {
        return articleType;
    }

    public void setArticleType(String articleType) {
        this.articleType = articleType;
    }

    public String getArticleHouseType() {
        return articleHouseType;
    }

    public void setArticleHouseType(String articleHouseType) {
        this.articleHouseType = articleHouseType;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public boolean isMytrip() {
        return mytrip;
    }

    public void setMytrip(boolean mytrip) {
        this.mytrip = mytrip;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getFavoriteCount() {
        return favoriteCount;
    }

    public void setFavoriteCount(int favoriteCount) {
        this.favoriteCount = favoriteCount;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(title);
        parcel.writeString(description);
        parcel.writeString(contactNumber);
        parcel.writeString(contactName);
        parcel.writeString(createdAt);
        parcel.writeString(updatedAt);
        parcel.writeString(addressDetail);
        parcel.writeDouble(lat);
        parcel.writeDouble(lng);
        parcel.writeDouble(distance);
        parcel.writeString(articleType);
        parcel.writeString(articleHouseType);
        parcel.writeString(image);
        parcel.writeString(province);
        parcel.writeByte((byte) (favorite ? 1 : 0));
        parcel.writeByte((byte) (mytrip ? 1 : 0));
        parcel.writeInt(status);
        parcel.writeInt(favoriteCount);
        parcel.writeInt(index);
        parcel.writeInt(userId);
    }
}
