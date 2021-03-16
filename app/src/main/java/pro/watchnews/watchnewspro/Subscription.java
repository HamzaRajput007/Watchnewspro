package pro.watchnews.watchnewspro;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.billingclient.api.AcknowledgePurchaseParams;
import com.android.billingclient.api.AcknowledgePurchaseResponseListener;
import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.ConsumeParams;
import com.android.billingclient.api.ConsumeResponseListener;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.android.billingclient.api.SkuDetails;
import com.android.billingclient.api.SkuDetailsParams;
import com.android.billingclient.api.SkuDetailsResponseListener;
import com.azoft.carousellayoutmanager.CarouselLayoutManager;
import com.azoft.carousellayoutmanager.CarouselZoomPostLayoutListener;
import com.azoft.carousellayoutmanager.CenterScrollListener;
import com.azoft.carousellayoutmanager.ItemTransformation;
//import com.jama.carouselview.CarouselView;

import org.json.JSONException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import pro.watchnews.watchnewspro.adapters.SubscriptionCarouselAdapter;
import pro.watchnews.watchnewspro.model.BillingClientSetup;
import pro.watchnews.watchnewspro.model.CarouselItemModel;
import pro.watchnews.watchnewspro.utill.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Subscription extends AppCompatActivity implements SubscriptionCarouselAdapter.OnSubscriptionClicked, PurchasesUpdatedListener {

    // TODO Subscribe button should load the selected item of RecyclerView [DONE]
    // TODO or Add the Subscribe button into the item layout of the Carousel RecyclerView Item To do this thing [DONE]
    //TODO resume video tutorial from 1:10:00 and implement remaining feature for Billing API

    BillingClient billingClient;
    AcknowledgePurchaseResponseListener acknowledgePurchaseResponseListener;

    WebView web;
    String networkUrl;
    ImageView imageViewLoading;
    Document document = null;
    private ProgressDialog pDialog;
    LinearLayout backBtnLayout;
    TextView txtPremium;
//    Button subscribeBtn;

    //    CarouselView carouselView;
    int[] images = {R.drawable.abcnews, R.drawable.watchnewslogo, R.drawable.globeicon};
    RecyclerView recyclerView;

    ArrayList<CarouselItemModel> carouselItemModels;
    SubscriptionCarouselAdapter subscriptionCarouselAdapter;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:
                    if (web.canGoBack()) {
                        web.goBack();
                        new MyAsynTask().execute();
                    } else {
                        finish();
                    }
                    return true;
                case KeyEvent.ACTION_DOWN:

            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscribtion);
        recyclerView = findViewById(R.id.recyclerViewCarouselId);
        backBtnLayout = findViewById(R.id.backBtnId);
        txtPremium = findViewById(R.id.textView2);

         setupBillingClient();

      /*  subscribeBtn = findViewById(R.id.subscriptbNowBtnId);
        subscribeBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        subscribeBtn.setBackground(getResources().getDrawable(R.drawable.subscribe_btn_bg_white));
                        subscribeBtn.setTextColor(getResources().getColor(R.color.colorPrimary));
                        break;

                    case MotionEvent.ACTION_UP:
                        subscribeBtn.setBackground(getResources().getDrawable(R.drawable.subscribe_btn_bg));
                        subscribeBtn.setTextColor(getResources().getColor(R.color.white));
                        Intent toRegister = new Intent(getApplicationContext() , SignUpSubscription.class);
                        startActivity(toRegister);
                        break;

                }
                return false;
            }
        });*/
      /* subscribeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toRegister = new Intent(getApplicationContext() , SignUpSubscription.class);
                startActivity(toRegister);
            }
        });*/

        backBtnLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        final CarouselLayoutManager carouselLayoutManager = new CarouselLayoutManager(CarouselLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(carouselLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addOnScrollListener(new CenterScrollListener());
        /*        recyclerView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View view, int i, int i1, int i2, int i3) {
                view.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.carousel_item_light_bg));
            }
        });*/
        carouselLayoutManager.setPostLayoutListener(new MyCarouselLayoutListener());

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading Subscription Packages ");
        showDialog();
        carouselItemModels = new ArrayList<>();
        RetrofitClient.connectTo("https://www.watchnews.pro/wp-json/mp/v1/");
        Call<List<CarouselItemModel>> call = RetrofitClient.getInstance().getApi().getSubscriptionPackages();
        call.enqueue(new Callback<List<CarouselItemModel>>() {
            @Override
            public void onResponse(Call<List<CarouselItemModel>> call, Response<List<CarouselItemModel>> response) {
                if (response.isSuccessful()) {
                    carouselItemModels = (ArrayList<CarouselItemModel>) response.body();
                    for (int i = 0; i < carouselItemModels.size(); i++) {
                        if (i == 0) {
                            carouselItemModels.get(i).setAmmountText("$84");
                        } else if (i == 1) {
                            carouselItemModels.get(i).setAmmountText("$42");
                        } else if (i == 2) {
                            carouselItemModels.get(i).setAmmountText("");

                        } else {
                            carouselItemModels.get(i).setAmmountText("");
                        }

                    }
/*
                    subscriptionCarouselAdapter = new SubscriptionCarouselAdapter(carouselItemModels, getApplicationContext(), Subscription.this);

                    recyclerView.setAdapter(subscriptionCarouselAdapter);*/
                    hideDialog();
                } else {
                    hideDialog();
                    Toast.makeText(Subscription.this, "Subscription Packages Not Loaded Successfully !", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<CarouselItemModel>> call, Throwable t) {
                hideDialog();
                Toast.makeText(Subscription.this, "Failed to Load Subscription Packages", Toast.LENGTH_SHORT).show();
            }
        });

        if (billingClient.isReady()){
            SkuDetailsParams params = SkuDetailsParams.newBuilder()
                    .setSkusList(Arrays.asList("yearly" , "six_months" , "monthly"))
                    .setType(BillingClient.SkuType.SUBS)
                    .build();
            billingClient.querySkuDetailsAsync(params, new SkuDetailsResponseListener() {
                @Override
                public void onSkuDetailsResponse(@NonNull BillingResult billingResult, @Nullable List<SkuDetails> list) {
                 if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                    if (list != null){
                        loadProductToRecyclerView(list);

                    }else{
                        try {
                            SkuDetails skuDetailsYearly = new SkuDetails("yearly");
                            SkuDetails skuDetailsSixMonths = new SkuDetails("six_months");
                            SkuDetails skuDetailsMonthly = new SkuDetails("monthly");
                            list.add(skuDetailsMonthly);
                            list.add(skuDetailsYearly);
                            list.add(skuDetailsSixMonths);
                            Log.d("list_size", "onSkuDetailsResponse: " + list.size());
                            loadProductToRecyclerView(list);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                 }
                 else
                     Toast.makeText(Subscription.this, "Error code: "+ billingResult.getResponseCode(), Toast.LENGTH_SHORT).show();
                }
            });
        }

/*

        carouselItemModels.add(new CarouselItemModel("Monthly" , "" , "" , "" , "$6.99" , "Per Month"));
        carouselItemModels.add(new CarouselItemModel("6 Months" , "15% Off" , "$42" , "NOW" , "$35" , "For 6 Months"));
        carouselItemModels.add(new CarouselItemModel("Yearly" , "30% Off" , "$84" , "" , "$60" , "Yearly"));
*/

//        layoutsCarousel = new ArrayList<>();
//        layoutsCarousel.add((Layout) this.getResources().getLayout(R.layout.monthly_package_fragment));
//        layoutsCarousel.add((Layout) this.getResources().getLayout(R.layout.six_month_package));
//        layoutsCarousel.add((Layout) this.getResources().getLayout(R.layout.yearly_package));
          /* carouselView = findViewById(R.id.carouselViewId);
        carouselView.setIndicatorAnimationType(IndicatorAnimationType.THIN_WORM);
        carouselView.setCarouselOffset(OffsetType.CENTER);
        carouselView.enableSnapping(true);
        carouselView.setCarouselScrollListener(new CarouselScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState, int position) {

            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                Log.d("onScrolled", " to position X = : " + dx + " and Y = ");
            }
        });
        carouselView.setCarouselViewListener(new CarouselViewListener() {
            @Override
            public void onBindView(View view, int position) {
                ImageView imageView = findViewById(R.id.imageViewCarouselID);
                imageView.setImageResource(images[position ]);
            }
        });
        carouselView.show();

        carouselView.setScaleOnScroll(true);

   /*     web = findViewById(R.id.subscriptionWebViewId);
        imageViewLoading = findViewById(R.id.imageViewLoading);
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Loading ...");
        showDialog();
        networkUrl = "https://www.watchnews.pro/subscription";

        WebSettings webSettings = web.getSettings();
        webSettings.setJavaScriptEnabled(true);

        web.getSettings().setLoadsImagesAutomatically(true);
        web.setWebViewClient(new myWebClient());
        web.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);

        web.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // chromium, enable hardware acceleration
            web.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        } else {
            // older android version, disable hardware acceleration
            web.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
        new MyAsynTask().execute();*/
    }

    private void loadProductToRecyclerView(List<SkuDetails> list) {
        SubscriptionCarouselAdapter subscriptionCarouselAdapter = new SubscriptionCarouselAdapter(Subscription.this , list , billingClient);
        recyclerView.setAdapter(subscriptionCarouselAdapter);
        recyclerView.setHasFixedSize(true);
    }

    private void setupBillingClient() {
       acknowledgePurchaseResponseListener = new AcknowledgePurchaseResponseListener() {
           @Override
           public void onAcknowledgePurchaseResponse(@NonNull BillingResult billingResult) {
               if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK){
                   Toast.makeText(Subscription.this, "Consume OK!", Toast.LENGTH_SHORT).show();
                   //Query
                   List<Purchase> purchases = billingClient.queryPurchases(BillingClient.SkuType.SUBS)
                           .getPurchasesList();
                   for(Purchase purchase : purchases){
                       handleItemAlreadyPurchased(purchase);
                   }
               }
           }
       };
        billingClient = BillingClientSetup.getInstance(this , this);
        billingClient.startConnection(new BillingClientStateListener() {
            @Override
            public void onBillingSetupFinished(@NonNull BillingResult billingResult) {
                if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK){
                    Toast.makeText(Subscription.this, "Success to connect billing", Toast.LENGTH_SHORT).show();
                    //Query
                    List<Purchase> purchases = billingClient.queryPurchases(BillingClient.SkuType.SUBS)
                            .getPurchasesList();
                    if (purchases.size() > 0){
                        recyclerView.setVisibility(View.GONE);
                        for (Purchase purchase : purchases){
                            handleItemAlreadyPurchased(purchase);
                        }
                    }else{
                        txtPremium.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                        loadAllSubscribePackage();
                    }
                }
                else
                    Toast.makeText(Subscription.this, "Error Code: " +
                            billingResult.getResponseCode() , Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onBillingServiceDisconnected() {
                Toast.makeText(Subscription.this, "You are disconnected form Billing Service", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadAllSubscribePackage() {
        if (billingClient.isReady()){
            SkuDetailsParams params = SkuDetailsParams.newBuilder()
                    .setSkusList(Arrays.asList("yearly" , "monthly" , "six_months"))
                    .setType(BillingClient.SkuType.SUBS)
                    .build();
            billingClient.querySkuDetailsAsync(params, new SkuDetailsResponseListener() {
                @Override
                public void onSkuDetailsResponse(@NonNull BillingResult billingResult, @Nullable List<SkuDetails> list) {
                    if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK){
                        SubscriptionCarouselAdapter adapter = new SubscriptionCarouselAdapter(Subscription.this , list , billingClient);
                        recyclerView.setAdapter(adapter);
                    }
                    else {
                        Toast.makeText(Subscription.this, "Error: " + billingResult.getResponseCode(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        else {
            Toast.makeText(this, "Billing Client not ready!", Toast.LENGTH_SHORT).show();
        }
    }

    private void handleItemAlreadyPurchased(Purchase purchase) {
       if (purchase.getPurchaseState() == Purchase.PurchaseState.PURCHASED){
           if (!purchase.isAcknowledged()){
               AcknowledgePurchaseParams acknowledgePurchaseParams = AcknowledgePurchaseParams.newBuilder()
                       .setPurchaseToken(purchase.getPurchaseToken())
                       .build();
               billingClient.acknowledgePurchase(acknowledgePurchaseParams ,acknowledgePurchaseResponseListener );

           }else
               txtPremium.setText("YOu are Premium !");
        }
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    @Override
    public void onSubscribeClicked(int position) {
        Intent toSignUpSubscription = new Intent(Subscription.this, SignUpSubscription.class);
        toSignUpSubscription.putExtra("Package", carouselItemModels.get(position).getPricingTitle());
        toSignUpSubscription.putExtra("Price", carouselItemModels.get(position).getPrice());
        startActivity(toSignUpSubscription);

    }

    @Override
    public void onPurchasesUpdated(@NonNull BillingResult billingResult, @Nullable List<Purchase> list) {
        if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK
        && list != null)
            for (Purchase purchase : list){
                handleItemAlreadyPurchased(purchase);
            }
        else if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.USER_CANCELED)
            Toast.makeText(this, "User has been cancelled", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, "Error: "+billingResult.getResponseCode(), Toast.LENGTH_SHORT).show();

    }

    public class myWebClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

//            String urlTest = url;
//            Toast.makeText(Subscription.this, "", Toast.LENGTH_SHORT).show();
//            if (Uri.parse(url).getHost().equals("watchnews.pro")) {

            networkUrl = url;
//                 topFrame.setVisibility(View.GONE);
//                 ViewGroup.LayoutParams layoutParams = web.getLayoutParams();
//                 layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
//                 layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
//                 web.setLayoutParams(layoutParams);
//                 LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT , LinearLayout.LayoutParams.MATCH_PARENT);
//                 web.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT , ViewGroup.LayoutParams.MATCH_PARENT));
//                 imageViewLoading.setVisibility(View.VISIBLE);
            new MyAsynTask().execute();
            return false;
//            } else {
//                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
//                startActivity(intent);
//                return true;
//            }

        }
    }

    private class MyAsynTask extends AsyncTask<Void, Void, Document> {

        @Override
        protected Document doInBackground(Void... voids) {
            removeViews();
            return document;
        }

        private void removeViews() {
            try {
                document = Jsoup.connect(networkUrl).get();
             /*   document.getElementsByClass("td-header-menu-wrap-full").remove();
                document.getElementsByClass("td-footer-wrapper").remove();
                document.getElementsByClass("td-footer-container").remove();
                document.getElementsByClass("td-footer-template-3").remove();
                document.getElementsByClass("td-sub-footer-container").remove();
                document.getElementsByClass("comment-form").remove();
                document.getElementsByClass("td-crumb-container").remove();
                document.getElementsByClass("td-category").remove();
//                document.getElementsByClass("entry-title").remove();
                document.getElementsByClass("td-post-featured-image").remove();
//                document.getElementsByClass("td-post-sharing-visible").remove();
                document.getElementsByClass("comments").remove();
                document.getElementsByClass("td-post-sharing").remove();
                document.getElementsByClass("td-ps-bg").remove();
                document.getElementsByClass("td-ps-notext").remove();
                document.getElementsByClass("td-post-sharing-style1").remove();*/

//                    document.getElementById("aw0").remove();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected void onPostExecute(final Document document) {
            super.onPostExecute(document);
            web.loadDataWithBaseURL(networkUrl, document.toString(), "text/html", "utf-8", "");
            web.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
            imageViewLoading.setVisibility(View.GONE);
            pDialog.dismiss();
            //after adding below these two lines webview able to load the images and videos
//            web.loadUrl(networkUrl);

           /* web.setWebViewClient(new WebViewClient(){
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                    view.loadUrl(networkUrl);
                    return super.shouldOverrideUrlLoading(view, request);
                }

                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    imageViewLoading.setVisibility(View.VISIBLE);
                    Log.d("url",url);
                    networkUrl=url;
                    captureLinkClick=true;
                    new MyAsynTask().execute();
                    return true;
                }

            });*/

           /*web.setWebViewClient(new WebViewClient()
           {
               @Override
               public boolean shouldOverrideUrlLoading(WebView view, String url) {


               }
           });*/
        }
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    private class MyCarouselLayoutListener extends CarouselZoomPostLayoutListener {
        @Override
        public ItemTransformation transformChild(@NonNull View child, float itemPositionToCenterDiff, int orientation) {
            final float scale = (float) (2 * (2 * -StrictMath.atan(Math.abs(itemPositionToCenterDiff) + 1.0) / Math.PI + 1));

            // because scaling will make view smaller in its center, then we should move this item to the top or bottom to make it visible
            final float translateY;
            final float translateX;
            if (CarouselLayoutManager.VERTICAL == orientation) {
                final float translateYGeneral = child.getMeasuredHeight() * (1 - scale) / 2f;
                translateY = Math.signum(itemPositionToCenterDiff) * translateYGeneral;
                translateX = 0;
//                child.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.carousel_item_light_bg));
            } else {
                final float translateXGeneral = child.getMeasuredWidth() * (1 - scale) / 2f;
                translateX = Math.signum(itemPositionToCenterDiff) * translateXGeneral;
                translateY = 0;
//                child.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.carousel_item_light_bg));
//                child.get
                Log.d("ItemID : ", String.valueOf(child.getId()));
                Log.d("TranslateX : ", String.valueOf(translateX));
                Log.d("TranslateY : ", String.valueOf(translateY));
                Log.d("translateXGeneral : ", String.valueOf(translateXGeneral));
                Log.d("TAG", "============================================================");
            }
            return new ItemTransformation(scale, scale, translateX, translateY);
        }
    }
}