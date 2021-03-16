package pro.watchnews.watchnewspro.model.SubscriptionsModels;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChannelsDescription {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("nicename")
    @Expose
    private String nicename;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("registered_at")
    @Expose
    private String registeredAt;
    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("last_name")
    @Expose
    private String lastName;
    @SerializedName("display_name")
    @Expose
    private String displayName;
    @SerializedName("active_memberships")
    @Expose
    private List<Object> activeMemberships = null;
    @SerializedName("active_txn_count")
    @Expose
    private String activeTxnCount;
    @SerializedName("expired_txn_count")
    @Expose
    private String expiredTxnCount;
    @SerializedName("trial_txn_count")
    @Expose
    private String trialTxnCount;
    @SerializedName("sub_count")
    @Expose
    private Object subCount;
    @SerializedName("login_count")
    @Expose
    private String loginCount;
    @SerializedName("first_txn")
    @Expose
    private FirstTxn firstTxn;
    @SerializedName("latest_txn")
    @Expose
    private LatestTxn latestTxn;
    @SerializedName("address")
    @Expose
    private Address address;
    @SerializedName("profile")
    @Expose
    private Profile profile;
    @SerializedName("recent_transactions")
    @Expose
    private List<RecentTransaction> recentTransactions = null;
    @SerializedName("recent_subscriptions")
    @Expose
    private List<RecentSubscription> recentSubscriptions = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNicename() {
        return nicename;
    }

    public void setNicename(String nicename) {
        this.nicename = nicename;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRegisteredAt() {
        return registeredAt;
    }

    public void setRegisteredAt(String registeredAt) {
        this.registeredAt = registeredAt;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public List<Object> getActiveMemberships() {
        return activeMemberships;
    }

    public void setActiveMemberships(List<Object> activeMemberships) {
        this.activeMemberships = activeMemberships;
    }

    public String getActiveTxnCount() {
        return activeTxnCount;
    }

    public void setActiveTxnCount(String activeTxnCount) {
        this.activeTxnCount = activeTxnCount;
    }

    public String getExpiredTxnCount() {
        return expiredTxnCount;
    }

    public void setExpiredTxnCount(String expiredTxnCount) {
        this.expiredTxnCount = expiredTxnCount;
    }

    public String getTrialTxnCount() {
        return trialTxnCount;
    }

    public void setTrialTxnCount(String trialTxnCount) {
        this.trialTxnCount = trialTxnCount;
    }

    public Object getSubCount() {
        return subCount;
    }

    public void setSubCount(Object subCount) {
        this.subCount = subCount;
    }

    public String getLoginCount() {
        return loginCount;
    }

    public void setLoginCount(String loginCount) {
        this.loginCount = loginCount;
    }

    public FirstTxn getFirstTxn() {
        return firstTxn;
    }

    public void setFirstTxn(FirstTxn firstTxn) {
        this.firstTxn = firstTxn;
    }

    public LatestTxn getLatestTxn() {
        return latestTxn;
    }

    public void setLatestTxn(LatestTxn latestTxn) {
        this.latestTxn = latestTxn;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public List<RecentTransaction> getRecentTransactions() {
        return recentTransactions;
    }

    public void setRecentTransactions(List<RecentTransaction> recentTransactions) {
        this.recentTransactions = recentTransactions;
    }

    public List<RecentSubscription> getRecentSubscriptions() {
        return recentSubscriptions;
    }

    public void setRecentSubscriptions(List<RecentSubscription> recentSubscriptions) {
        this.recentSubscriptions = recentSubscriptions;
    }

}