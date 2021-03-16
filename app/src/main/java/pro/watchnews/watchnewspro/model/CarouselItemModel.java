package pro.watchnews.watchnewspro.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CarouselItemModel {

    String periodText;
    String discountText;
    String ammountText;
    String nowText;
    String priceText;
    String bottomPeriodText;

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("content")
    @Expose
    private String content;
    @SerializedName("excerpt")
    @Expose
    private String excerpt;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("author")
    @Expose
    private Author author;
    @SerializedName("date_gmt")
    @Expose
    private String dateGmt;
    @SerializedName("modified")
    @Expose
    private String modified;
    @SerializedName("modified_gmt")
    @Expose
    private String modifiedGmt;
    @SerializedName("group")
    @Expose
    private String group;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("period")
    @Expose
    private String period;
    @SerializedName("period_type")
    @Expose
    private String periodType;
    @SerializedName("signup_button_text")
    @Expose
    private String signupButtonText;
    @SerializedName("limit_cycles")
    @Expose
    private Boolean limitCycles;
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
    @SerializedName("trial")
    @Expose
    private Boolean trial;
    @SerializedName("trial_days")
    @Expose
    private String trialDays;
    @SerializedName("trial_amount")
    @Expose
    private String trialAmount;
    @SerializedName("trial_once")
    @Expose
    private String trialOnce;
    @SerializedName("group_order")
    @Expose
    private String groupOrder;
    @SerializedName("is_highlighted")
    @Expose
    private Boolean isHighlighted;
    @SerializedName("plan_code")
    @Expose
    private String planCode;
    @SerializedName("pricing_title")
    @Expose
    private String pricingTitle;
    @SerializedName("pricing_show_price")
    @Expose
    private Boolean pricingShowPrice;
    @SerializedName("pricing_display")
    @Expose
    private String pricingDisplay;
    @SerializedName("custom_price")
    @Expose
    private String customPrice;
    @SerializedName("pricing_heading_txt")
    @Expose
    private String pricingHeadingTxt;
    @SerializedName("pricing_footer_txt")
    @Expose
    private String pricingFooterTxt;
    @SerializedName("pricing_button_txt")
    @Expose
    private String pricingButtonTxt;
    @SerializedName("pricing_button_position")
    @Expose
    private String pricingButtonPosition;
    @SerializedName("pricing_benefits")
    @Expose
    private List<String> pricingBenefits = null;
    @SerializedName("register_price_action")
    @Expose
    private String registerPriceAction;
    @SerializedName("register_price")
    @Expose
    private String registerPrice;
    @SerializedName("thank_you_page_enabled")
    @Expose
    private Boolean thankYouPageEnabled;
    @SerializedName("thank_you_page_type")
    @Expose
    private String thankYouPageType;
    @SerializedName("thank_you_message")
    @Expose
    private String thankYouMessage;
    @SerializedName("thank_you_page_id")
    @Expose
    private String thankYouPageId;
    @SerializedName("custom_login_urls_enabled")
    @Expose
    private Boolean customLoginUrlsEnabled;
    @SerializedName("custom_login_urls_default")
    @Expose
    private String customLoginUrlsDefault;
    @SerializedName("custom_login_urls")
    @Expose
    private List<Object> customLoginUrls = null;
    @SerializedName("expire_type")
    @Expose
    private String expireType;
    @SerializedName("expire_after")
    @Expose
    private String expireAfter;
    @SerializedName("expire_unit")
    @Expose
    private String expireUnit;
    @SerializedName("expire_fixed")
    @Expose
    private String expireFixed;
    @SerializedName("tax_exempt")
    @Expose
    private Boolean taxExempt;
    @SerializedName("tax_class")
    @Expose
    private String taxClass;
    @SerializedName("allow_renewal")
    @Expose
    private String allowRenewal;
    @SerializedName("access_url")
    @Expose
    private String accessUrl;
    @SerializedName("disable_address_fields")
    @Expose
    private Boolean disableAddressFields;
    @SerializedName("simultaneous_subscriptions")
    @Expose
    private Boolean simultaneousSubscriptions;
    @SerializedName("use_custom_template")
    @Expose
    private Boolean useCustomTemplate;
    @SerializedName("custom_template")
    @Expose
    private String customTemplate;
    @SerializedName("customize_payment_methods")
    @Expose
    private Boolean customizePaymentMethods;
    @SerializedName("custom_payment_methods")
    @Expose
    private List<Object> customPaymentMethods = null;
    @SerializedName("customize_profile_fields")
    @Expose
    private Boolean customizeProfileFields;
    @SerializedName("custom_profile_fields")
    @Expose
    private List<Object> customProfileFields = null;
    @SerializedName("cannot_purchase_message")
    @Expose
    private String cannotPurchaseMessage;

    public CarouselItemModel(String periodText, String discountText, String ammountText, String nowText, String priceText, String bottomPeriodText) {
        this.periodText = periodText;
        this.discountText = discountText;
        this.ammountText = ammountText;
        this.nowText = nowText;
        this.priceText = priceText;
        this.bottomPeriodText = bottomPeriodText;
    }

    public String getPeriodText() {
        return periodText;
    }

    public void setPeriodText(String periodText) {
        this.periodText = periodText;
    }

    public String getDiscountText() {
        return discountText;
    }

    public void setDiscountText(String discountText) {
        this.discountText = discountText;
    }

    public String getAmmountText() {
        return ammountText;
    }

    public void setAmmountText(String ammountText) {
        this.ammountText = ammountText;
    }

    public String getNowText() {
        return nowText;
    }

    public void setNowText(String nowText) {
        this.nowText = nowText;
    }

    public String getPriceText() {
        return priceText;
    }

    public void setPriceText(String priceText) {
        this.priceText = priceText;
    }

    public String getBottomPeriodText() {
        return bottomPeriodText;
    }

    public void setBottomPeriodText(String bottomPeriodText) {
        this.bottomPeriodText = bottomPeriodText;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getExcerpt() {
        return excerpt;
    }

    public void setExcerpt(String excerpt) {
        this.excerpt = excerpt;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public String getDateGmt() {
        return dateGmt;
    }

    public void setDateGmt(String dateGmt) {
        this.dateGmt = dateGmt;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }

    public String getModifiedGmt() {
        return modifiedGmt;
    }

    public void setModifiedGmt(String modifiedGmt) {
        this.modifiedGmt = modifiedGmt;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
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

    public String getSignupButtonText() {
        return signupButtonText;
    }

    public void setSignupButtonText(String signupButtonText) {
        this.signupButtonText = signupButtonText;
    }

    public Boolean getLimitCycles() {
        return limitCycles;
    }

    public void setLimitCycles(Boolean limitCycles) {
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

    public Boolean getTrial() {
        return trial;
    }

    public void setTrial(Boolean trial) {
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

    public String getTrialOnce() {
        return trialOnce;
    }

    public void setTrialOnce(String trialOnce) {
        this.trialOnce = trialOnce;
    }

    public String getGroupOrder() {
        return groupOrder;
    }

    public void setGroupOrder(String groupOrder) {
        this.groupOrder = groupOrder;
    }

    public Boolean getIsHighlighted() {
        return isHighlighted;
    }

    public void setIsHighlighted(Boolean isHighlighted) {
        this.isHighlighted = isHighlighted;
    }

    public String getPlanCode() {
        return planCode;
    }

    public void setPlanCode(String planCode) {
        this.planCode = planCode;
    }

    public String getPricingTitle() {
        return pricingTitle;
    }

    public void setPricingTitle(String pricingTitle) {
        this.pricingTitle = pricingTitle;
    }

    public Boolean getPricingShowPrice() {
        return pricingShowPrice;
    }

    public void setPricingShowPrice(Boolean pricingShowPrice) {
        this.pricingShowPrice = pricingShowPrice;
    }

    public String getPricingDisplay() {
        return pricingDisplay;
    }

    public void setPricingDisplay(String pricingDisplay) {
        this.pricingDisplay = pricingDisplay;
    }

    public String getCustomPrice() {
        return customPrice;
    }

    public void setCustomPrice(String customPrice) {
        this.customPrice = customPrice;
    }

    public String getPricingHeadingTxt() {
        return pricingHeadingTxt;
    }

    public void setPricingHeadingTxt(String pricingHeadingTxt) {
        this.pricingHeadingTxt = pricingHeadingTxt;
    }

    public String getPricingFooterTxt() {
        return pricingFooterTxt;
    }

    public void setPricingFooterTxt(String pricingFooterTxt) {
        this.pricingFooterTxt = pricingFooterTxt;
    }

    public String getPricingButtonTxt() {
        return pricingButtonTxt;
    }

    public void setPricingButtonTxt(String pricingButtonTxt) {
        this.pricingButtonTxt = pricingButtonTxt;
    }

    public String getPricingButtonPosition() {
        return pricingButtonPosition;
    }

    public void setPricingButtonPosition(String pricingButtonPosition) {
        this.pricingButtonPosition = pricingButtonPosition;
    }

    public List<String> getPricingBenefits() {
        return pricingBenefits;
    }

    public void setPricingBenefits(List<String> pricingBenefits) {
        this.pricingBenefits = pricingBenefits;
    }

    public String getRegisterPriceAction() {
        return registerPriceAction;
    }

    public void setRegisterPriceAction(String registerPriceAction) {
        this.registerPriceAction = registerPriceAction;
    }

    public String getRegisterPrice() {
        return registerPrice;
    }

    public void setRegisterPrice(String registerPrice) {
        this.registerPrice = registerPrice;
    }

    public Boolean getThankYouPageEnabled() {
        return thankYouPageEnabled;
    }

    public void setThankYouPageEnabled(Boolean thankYouPageEnabled) {
        this.thankYouPageEnabled = thankYouPageEnabled;
    }

    public String getThankYouPageType() {
        return thankYouPageType;
    }

    public void setThankYouPageType(String thankYouPageType) {
        this.thankYouPageType = thankYouPageType;
    }

    public String getThankYouMessage() {
        return thankYouMessage;
    }

    public void setThankYouMessage(String thankYouMessage) {
        this.thankYouMessage = thankYouMessage;
    }

    public String getThankYouPageId() {
        return thankYouPageId;
    }

    public void setThankYouPageId(String thankYouPageId) {
        this.thankYouPageId = thankYouPageId;
    }

    public Boolean getCustomLoginUrlsEnabled() {
        return customLoginUrlsEnabled;
    }

    public void setCustomLoginUrlsEnabled(Boolean customLoginUrlsEnabled) {
        this.customLoginUrlsEnabled = customLoginUrlsEnabled;
    }

    public String getCustomLoginUrlsDefault() {
        return customLoginUrlsDefault;
    }

    public void setCustomLoginUrlsDefault(String customLoginUrlsDefault) {
        this.customLoginUrlsDefault = customLoginUrlsDefault;
    }

    public List<Object> getCustomLoginUrls() {
        return customLoginUrls;
    }

    public void setCustomLoginUrls(List<Object> customLoginUrls) {
        this.customLoginUrls = customLoginUrls;
    }

    public String getExpireType() {
        return expireType;
    }

    public void setExpireType(String expireType) {
        this.expireType = expireType;
    }

    public String getExpireAfter() {
        return expireAfter;
    }

    public void setExpireAfter(String expireAfter) {
        this.expireAfter = expireAfter;
    }

    public String getExpireUnit() {
        return expireUnit;
    }

    public void setExpireUnit(String expireUnit) {
        this.expireUnit = expireUnit;
    }

    public String getExpireFixed() {
        return expireFixed;
    }

    public void setExpireFixed(String expireFixed) {
        this.expireFixed = expireFixed;
    }

    public Boolean getTaxExempt() {
        return taxExempt;
    }

    public void setTaxExempt(Boolean taxExempt) {
        this.taxExempt = taxExempt;
    }

    public String getTaxClass() {
        return taxClass;
    }

    public void setTaxClass(String taxClass) {
        this.taxClass = taxClass;
    }

    public String getAllowRenewal() {
        return allowRenewal;
    }

    public void setAllowRenewal(String allowRenewal) {
        this.allowRenewal = allowRenewal;
    }

    public String getAccessUrl() {
        return accessUrl;
    }

    public void setAccessUrl(String accessUrl) {
        this.accessUrl = accessUrl;
    }

    public Boolean getDisableAddressFields() {
        return disableAddressFields;
    }

    public void setDisableAddressFields(Boolean disableAddressFields) {
        this.disableAddressFields = disableAddressFields;
    }

    public Boolean getSimultaneousSubscriptions() {
        return simultaneousSubscriptions;
    }

    public void setSimultaneousSubscriptions(Boolean simultaneousSubscriptions) {
        this.simultaneousSubscriptions = simultaneousSubscriptions;
    }

    public Boolean getUseCustomTemplate() {
        return useCustomTemplate;
    }

    public void setUseCustomTemplate(Boolean useCustomTemplate) {
        this.useCustomTemplate = useCustomTemplate;
    }

    public String getCustomTemplate() {
        return customTemplate;
    }

    public void setCustomTemplate(String customTemplate) {
        this.customTemplate = customTemplate;
    }

    public Boolean getCustomizePaymentMethods() {
        return customizePaymentMethods;
    }

    public void setCustomizePaymentMethods(Boolean customizePaymentMethods) {
        this.customizePaymentMethods = customizePaymentMethods;
    }

    public List<Object> getCustomPaymentMethods() {
        return customPaymentMethods;
    }

    public void setCustomPaymentMethods(List<Object> customPaymentMethods) {
        this.customPaymentMethods = customPaymentMethods;
    }

    public Boolean getCustomizeProfileFields() {
        return customizeProfileFields;
    }

    public void setCustomizeProfileFields(Boolean customizeProfileFields) {
        this.customizeProfileFields = customizeProfileFields;
    }

    public List<Object> getCustomProfileFields() {
        return customProfileFields;
    }

    public void setCustomProfileFields(List<Object> customProfileFields) {
        this.customProfileFields = customProfileFields;
    }

    public String getCannotPurchaseMessage() {
        return cannotPurchaseMessage;
    }

    public void setCannotPurchaseMessage(String cannotPurchaseMessage) {
        this.cannotPurchaseMessage = cannotPurchaseMessage;
    }

}
