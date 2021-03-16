package pro.watchnews.watchnewspro.model.SubscriptionsModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RecentSubscription {

    @SerializedName("coupon")
    @Expose
    private String coupon;
    @SerializedName("membership")
    @Expose
    private String membership;
    @SerializedName("member")
    @Expose
    private String member;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("subscr_id")
    @Expose
    private String subscrId;
    @SerializedName("gateway")
    @Expose
    private String gateway;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("period")
    @Expose
    private String period;
    @SerializedName("period_type")
    @Expose
    private String periodType;
    @SerializedName("limit_cycles")
    @Expose
    private String limitCycles;
    @SerializedName("limit_cycles_num")
    @Expose
    private String limitCyclesNum;
    @SerializedName("limit_cycles_action")
    @Expose
    private String limitCyclesAction;
    @SerializedName("limit_cycles_expires_after")
    @Expose
    private String limitCyclesExpiresAfter;
    @SerializedName("limit_cycles_expires_type")
    @Expose
    private String limitCyclesExpiresType;
    @SerializedName("prorated_trial")
    @Expose
    private String proratedTrial;
    @SerializedName("trial")
    @Expose
    private String trial;
    @SerializedName("trial_days")
    @Expose
    private String trialDays;
    @SerializedName("trial_amount")
    @Expose
    private String trialAmount;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("total")
    @Expose
    private String total;
    @SerializedName("tax_rate")
    @Expose
    private String taxRate;
    @SerializedName("tax_amount")
    @Expose
    private String taxAmount;
    @SerializedName("tax_desc")
    @Expose
    private String taxDesc;
    @SerializedName("tax_class")
    @Expose
    private String taxClass;
    @SerializedName("cc_last4")
    @Expose
    private String ccLast4;
    @SerializedName("cc_exp_month")
    @Expose
    private String ccExpMonth;
    @SerializedName("cc_exp_year")
    @Expose
    private String ccExpYear;
    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("tax_compound")
    @Expose
    private String taxCompound;
    @SerializedName("tax_shipping")
    @Expose
    private String taxShipping;
    @SerializedName("response")
    @Expose
    private Object response;

    public String getCoupon() {
        return coupon;
    }

    public void setCoupon(String coupon) {
        this.coupon = coupon;
    }

    public String getMembership() {
        return membership;
    }

    public void setMembership(String membership) {
        this.membership = membership;
    }

    public String getMember() {
        return member;
    }

    public void setMember(String member) {
        this.member = member;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSubscrId() {
        return subscrId;
    }

    public void setSubscrId(String subscrId) {
        this.subscrId = subscrId;
    }

    public String getGateway() {
        return gateway;
    }

    public void setGateway(String gateway) {
        this.gateway = gateway;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getPeriodType() {
        return periodType;
    }

    public void setPeriodType(String periodType) {
        this.periodType = periodType;
    }

    public String getLimitCycles() {
        return limitCycles;
    }

    public void setLimitCycles(String limitCycles) {
        this.limitCycles = limitCycles;
    }

    public String getLimitCyclesNum() {
        return limitCyclesNum;
    }

    public void setLimitCyclesNum(String limitCyclesNum) {
        this.limitCyclesNum = limitCyclesNum;
    }

    public String getLimitCyclesAction() {
        return limitCyclesAction;
    }

    public void setLimitCyclesAction(String limitCyclesAction) {
        this.limitCyclesAction = limitCyclesAction;
    }

    public String getLimitCyclesExpiresAfter() {
        return limitCyclesExpiresAfter;
    }

    public void setLimitCyclesExpiresAfter(String limitCyclesExpiresAfter) {
        this.limitCyclesExpiresAfter = limitCyclesExpiresAfter;
    }

    public String getLimitCyclesExpiresType() {
        return limitCyclesExpiresType;
    }

    public void setLimitCyclesExpiresType(String limitCyclesExpiresType) {
        this.limitCyclesExpiresType = limitCyclesExpiresType;
    }

    public String getProratedTrial() {
        return proratedTrial;
    }

    public void setProratedTrial(String proratedTrial) {
        this.proratedTrial = proratedTrial;
    }

    public String getTrial() {
        return trial;
    }

    public void setTrial(String trial) {
        this.trial = trial;
    }

    public String getTrialDays() {
        return trialDays;
    }

    public void setTrialDays(String trialDays) {
        this.trialDays = trialDays;
    }

    public String getTrialAmount() {
        return trialAmount;
    }

    public void setTrialAmount(String trialAmount) {
        this.trialAmount = trialAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(String taxRate) {
        this.taxRate = taxRate;
    }

    public String getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(String taxAmount) {
        this.taxAmount = taxAmount;
    }

    public String getTaxDesc() {
        return taxDesc;
    }

    public void setTaxDesc(String taxDesc) {
        this.taxDesc = taxDesc;
    }

    public String getTaxClass() {
        return taxClass;
    }

    public void setTaxClass(String taxClass) {
        this.taxClass = taxClass;
    }

    public String getCcLast4() {
        return ccLast4;
    }

    public void setCcLast4(String ccLast4) {
        this.ccLast4 = ccLast4;
    }

    public String getCcExpMonth() {
        return ccExpMonth;
    }

    public void setCcExpMonth(String ccExpMonth) {
        this.ccExpMonth = ccExpMonth;
    }

    public String getCcExpYear() {
        return ccExpYear;
    }

    public void setCcExpYear(String ccExpYear) {
        this.ccExpYear = ccExpYear;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTaxCompound() {
        return taxCompound;
    }

    public void setTaxCompound(String taxCompound) {
        this.taxCompound = taxCompound;
    }

    public String getTaxShipping() {
        return taxShipping;
    }

    public void setTaxShipping(String taxShipping) {
        this.taxShipping = taxShipping;
    }

    public Object getResponse() {
        return response;
    }

    public void setResponse(Object response) {
        this.response = response;
    }

}