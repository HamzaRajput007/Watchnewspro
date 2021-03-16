package pro.watchnews.watchnewspro.interfaces;

import java.util.List;

import pro.watchnews.watchnewspro.model.CarouselItemModel;
import pro.watchnews.watchnewspro.model.Model_Decrypted_Channed_Urls;

import pro.watchnews.watchnewspro.model.SubscriptionsModels.ChannelsDescription;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface RemoteClient {

    @GET("msnbc.php")
    Call<Model_Decrypted_Channed_Urls> getMSNBC();

    @GET("fox.php")
    Call<Model_Decrypted_Channed_Urls> getFox();

    @GET("bbc.php")
    Call<Model_Decrypted_Channed_Urls> getBBC();

    @GET("cspan.php")
    Call<Model_Decrypted_Channed_Urls> getCSPAN();

    @GET("cbc.php")
    Call<Model_Decrypted_Channed_Urls> getCBC();

    @GET("cnbc.php")
    Call<Model_Decrypted_Channed_Urls> getCNBC();

    @GET("cnn.php")
    Call<Model_Decrypted_Channed_Urls> getCNN();

    @GET("foxbusiness.php")
    Call<Model_Decrypted_Channed_Urls> getFoxBusiness();

    @GET("hln.php")
    Call<Model_Decrypted_Channed_Urls> getHLN();

    @GET("oan.php")
    Call<Model_Decrypted_Channed_Urls> getOneAmerica();

    @GET("members")
    @Headers("MEMBERPRESS-API-KEY: kLNnmZGrsR")
    Call<List<ChannelsDescription>> checkSubscription(@Query("id") int id);

    @GET("memberships")
    @Headers("MEMBERPRESS-API-KEY: kLNnmZGrsR")
    Call<List<CarouselItemModel>> getSubscriptionPackages();

}
