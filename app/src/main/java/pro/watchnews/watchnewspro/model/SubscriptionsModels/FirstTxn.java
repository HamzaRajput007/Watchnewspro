package pro.watchnews.watchnewspro.model.SubscriptionsModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FirstTxn {

    @SerializedName("membership")
    @Expose
    private String membership;
    @SerializedName("member")
    @Expose
    private String member;
    @SerializedName("coupon")
    @Expose
    private String coupon;
    @SerializedName("subscription")
    @Expose
    private String subscription;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("amount")
    @Expose
    private String amount;
    @SerializedName("total")
    @Expose
    private String total;
    @SerializedName("tax_amount")
    @Expose
    private String taxAmount;
    @SerializedName("tax_rate")
    @Expose
    private String taxRate;
    @SerializedName("tax_desc")
    @Expose
    private String taxDesc;
    @SerializedName("tax_class")
    @Expose
    private String taxClass;
    @SerializedName("trans_num")
    @Expose
    private String transNum;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("txn_type")
    @Expose
    private String txnType;
    @SerializedName("gateway")
    @Expose
    private String gateway;
    @SerializedName("prorated")
    @Expose
    private String prorated;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("expires_at")
    @Expose
    private String expiresAt;
    @SerializedName("corporate_account_id")
    @Expose
    private String corporateAccountId;
    @SerializedName("parent_transaction_id")
    @Expose
    private String parentTransactionId;
    @SerializedName("tax_compound")
    @Expose
    private String taxCompound;
    @SerializedName("tax_shipping")
    @Expose
    private String taxShipping;
    @SerializedName("response")
    @Expose
    private Object response;

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

    public String getCoupon() {
        return coupon;
    }

    public void setCoupon(String coupon) {
        this.coupon = coupon;
    }

    public String getSubscription() {
        return subscription;
    }

    public void setSubscription(String subscription) {
        this.subscription = subscription;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(String taxAmount) {
        this.taxAmount = taxAmount;
    }

    public String getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(String taxRate) {
        this.taxRate = taxRate;
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

    public String getTransNum() {
        return transNum;
    }

    public void setTransNum(String transNum) {
        this.transNum = transNum;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTxnType() {
        return txnType;
    }

    public void setTxnType(String txnType) {
        this.txnType = txnType;
    }

    public String getGateway() {
        return gateway;
    }

    public void setGateway(String gateway) {
        this.gateway = gateway;
    }

    public String getProrated() {
        return prorated;
    }

    public void setProrated(String prorated) {
        this.prorated = prorated;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(String expiresAt) {
        this.expiresAt = expiresAt;
    }

    public String getCorporateAccountId() {
        return corporateAccountId;
    }

    public void setCorporateAccountId(String corporateAccountId) {
        this.corporateAccountId = corporateAccountId;
    }

    public String getParentTransactionId() {
        return parentTransactionId;
    }

    public void setParentTransactionId(String parentTransactionId) {
        this.parentTransactionId = parentTransactionId;
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