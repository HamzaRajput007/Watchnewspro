package pro.watchnews.watchnewspro;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingFlowParams;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.ConsumeParams;
import com.android.billingclient.api.ConsumeResponseListener;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.android.billingclient.api.SkuDetails;
import com.android.billingclient.api.SkuDetailsParams;
import com.android.billingclient.api.SkuDetailsResponseListener;
import com.google.android.gms.wallet.PaymentsClient;
import com.google.android.gms.wallet.Wallet;
import com.google.android.gms.wallet.WalletConstants;
import com.paypal.android.sdk.payments.PayPalAuthorization;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalFuturePaymentActivity;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;

import java.math.BigDecimal;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class Payment extends AppCompatActivity {

    /*private static final String TAG = "paymentExample";

    public static final String PAYPAL_KEY = "";

    private static final int REQUEST_CODE_PAYMENT = 1;
    private static final int REQUEST_CODE_FUTURE_PAYMENT = 2;
    private static final String CONFIG_ENVIRONMENT = PayPalConfiguration.ENVIRONMENT_SANDBOX;
    PayPalPayment thingsToBuy;
    Button order;
    PayPalConfiguration config ;
*/
    PaymentsClient paymentsClient;
    List<SkuDetails> skuDetails ;
    BillingClient billingClient;
    BillingFlowParams billingFlowParams;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        skuDetails = new ArrayList<>();
        PurchasesUpdatedListener purchasesUpdatedListener = new PurchasesUpdatedListener() {
            @Override
            public void onPurchasesUpdated(BillingResult billingResult, List<Purchase> purchases) {
                if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK
                        && purchases != null) {
                    for (Purchase purchase : purchases) {
                        handlePurchase(purchase);
                    }
                } else if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.USER_CANCELED) {
                    // Handle an error caused by a user cancelling the purchase flow.
                } else {
                    // Handle any other error codes.
                }            }
        };

        billingClient = BillingClient.newBuilder(this)
                .setListener(purchasesUpdatedListener)
                .enablePendingPurchases()
                .build();

        billingClient.startConnection(new BillingClientStateListener() {
            @Override
            public void onBillingSetupFinished(BillingResult billingResult) {
                if (billingResult.getResponseCode() ==  BillingClient.BillingResponseCode.OK) {
                    Log.d("TAG2", "onBillingSetupFinished: ");
                    // The BillingClient is ready. You can query purchases here.
                    List<String> skuList = new ArrayList<>();
                    skuList.add("premium_upgrade");
                    skuList.add("gas");
                    SkuDetailsParams.Builder params = SkuDetailsParams.newBuilder();
                    params.setSkusList(skuList).setType(BillingClient.SkuType.INAPP);
                    billingClient.querySkuDetailsAsync(params.build(),
                            new SkuDetailsResponseListener() {
                                @Override
                                public void onSkuDetailsResponse(BillingResult billingResult,
                                                                 List<SkuDetails> skuDetailsList) {
                        Log.d("BillCode", "onSkuDetailsResponse: " + billingResult.getResponseCode());


                                    try {
                                        for (SkuDetails skuDetail : skuDetailsList
                                             ) {
                                            skuDetails.add(skuDetail);
                                            billingFlowParams = BillingFlowParams.newBuilder()
                                                    .setSkuDetails(skuDetails.get(0))
                                                    .build();
                                            Toast.makeText(Payment.this, "NO Exception Occurred" + String.valueOf(skuDetails.size()), Toast.LENGTH_SHORT).show();
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        Log.d("ResultException", "onSkuDetailsResponse: " + e.getMessage());
                                    }
//                        skuDetails = skuDetailsList;

                                    // Process the result.
                                }
                            });
                        }
            }
            @Override
            public void onBillingServiceDisconnected() {
                Log.d("BillingDisconnected", "onBillingServiceDisconnected: ");
                // Try to restart the connection on the next request to
                // Google Play by calling the startConnection() method.
            }
        });





// Retrieve a value for "skuDetails" by calling querySkuDetailsAsync().

        int responseCode = billingClient.launchBillingFlow(this, billingFlowParams).getResponseCode();


// Handle the result.

//        Wallet.WalletOptions wa = new Wallet.WalletOptions().Builder().setEnvoirnment
//
//        Wallet.WalletOptions walletOptions = new Wallet.WalletOptions().environment(WalletConstants.ENVIRONMENT_TEST);

    /*
        order = findViewById(R.id.order);
        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makePayment();
            }
        });

        configurePayPal();*/
    }

    void handlePurchase(Purchase purchase) {
        // Purchase retrieved from BillingClient#queryPurchases or your PurchasesUpdatedListener.
//        Purchase mPurchase  = purchase;

        // Verify the purchase.
        // Ensure entitlement was not already granted for this purchaseToken.
        // Grant entitlement to the user.

        ConsumeParams consumeParams =
                ConsumeParams.newBuilder()
                        .setPurchaseToken(purchase.getPurchaseToken())
                        .build();

        ConsumeResponseListener listener = new ConsumeResponseListener() {
            @Override
            public void onConsumeResponse(BillingResult billingResult, String purchaseToken) {
                if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                    // Handle the success of the consume operation.
                }
            }
        };

        billingClient.consumeAsync(consumeParams, listener);
    }

   /* private void configurePayPal() {

        config = new PayPalConfiguration()
                .environment(CONFIG_ENVIRONMENT)
                .clientId(PAYPAL_KEY)
                .merchantName("Paypal Login")
                .merchantPrivacyPolicyUri(Uri.parse("https://www.paypalobjects.com/webstatic/ua/pdf/US/ints/privacy.pdf"))
                .merchantUserAgreementUri(Uri.parse("https://www.paypal.com/en/webapps/mpp/ua/legalhub-full"));


    }

    private void makePayment() {
        Intent intent = new Intent(this , PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION , config);
        startService(intent);

        thingsToBuy = new PayPalPayment(new BigDecimal(String.valueOf("60.0")) ,"USD" , "Payment", PayPalPayment.PAYMENT_INTENT_SALE);
        Intent payment = new Intent(this , PaymentActivity.class);
        payment.putExtra(PaymentActivity.EXTRA_PAYMENT , thingsToBuy);
        payment.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION , config);
        startActivityForResult(payment , REQUEST_CODE_PAYMENT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_PAYMENT){
            if(resultCode == Activity.RESULT_OK){
                PaymentConfirmation confirm = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                if (confirm != null){
                    try {
                        System.out.println(confirm.toJSONObject().toString(4));
                        System.out.println(confirm.getPayment().toJSONObject().toString(4));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED){
                Toast.makeText(this, "Payment has been cancelled", Toast.LENGTH_SHORT).show();
            }else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID){
                Toast.makeText(this, "Some error occurred", Toast.LENGTH_SHORT).show();
            }
        }else if (requestCode == REQUEST_CODE_FUTURE_PAYMENT){
            if (resultCode == Activity.RESULT_OK){
                PayPalAuthorization authorization = data.getParcelableExtra(PayPalFuturePaymentActivity.EXTRA_RESULT_AUTHORIZATION);
                if (authorization != null){
                    try {
                        Log.i("FuturePaymentExample", authorization.toJSONObject().toString(4));
                        String authorizationCode = authorization.getAuthorizationCode();
                        Log.i("FuturePaymentExample", authorizationCode);
                    }catch (Exception e){

                    }
                }
            }

        }
    }*/
}