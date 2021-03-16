package pro.watchnews.watchnewspro.model.SubscriptionsModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Address {

    @SerializedName("mepr-address-one")
    @Expose
    private String meprAddressOne;
    @SerializedName("mepr-address-two")
    @Expose
    private String meprAddressTwo;
    @SerializedName("mepr-address-city")
    @Expose
    private String meprAddressCity;
    @SerializedName("mepr-address-state")
    @Expose
    private String meprAddressState;
    @SerializedName("mepr-address-zip")
    @Expose
    private String meprAddressZip;
    @SerializedName("mepr-address-country")
    @Expose
    private String meprAddressCountry;

    public String getMeprAddressOne() {
        return meprAddressOne;
    }

    public void setMeprAddressOne(String meprAddressOne) {
        this.meprAddressOne = meprAddressOne;
    }

    public String getMeprAddressTwo() {
        return meprAddressTwo;
    }

    public void setMeprAddressTwo(String meprAddressTwo) {
        this.meprAddressTwo = meprAddressTwo;
    }

    public String getMeprAddressCity() {
        return meprAddressCity;
    }

    public void setMeprAddressCity(String meprAddressCity) {
        this.meprAddressCity = meprAddressCity;
    }

    public String getMeprAddressState() {
        return meprAddressState;
    }

    public void setMeprAddressState(String meprAddressState) {
        this.meprAddressState = meprAddressState;
    }

    public String getMeprAddressZip() {
        return meprAddressZip;
    }

    public void setMeprAddressZip(String meprAddressZip) {
        this.meprAddressZip = meprAddressZip;
    }

    public String getMeprAddressCountry() {
        return meprAddressCountry;
    }

    public void setMeprAddressCountry(String meprAddressCountry) {
        this.meprAddressCountry = meprAddressCountry;
    }

}