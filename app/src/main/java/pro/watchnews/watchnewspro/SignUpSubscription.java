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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.paypal.android.sdk.payments.PayPalAuthorization;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalFuturePaymentActivity;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;

import java.math.BigDecimal;
import java.util.concurrent.ExecutionException;

public class SignUpSubscription extends AppCompatActivity {

    LinearLayout backBtn;
    Button signUpButton;
    TextView subscribeText , priceText;
    private static final String TAG = "paymentExample";

    public static final String PAYPAL_KEY = "";

    private static final int REQUEST_CODE_PAYMENT = 1;
    private static final int REQUEST_CODE_FUTURE_PAYMENT = 2;
    private static final String CONFIG_ENVIRONMENT = PayPalConfiguration.ENVIRONMENT_SANDBOX;
    PayPalPayment thingsToBuy;
    Button order;
    PayPalConfiguration config ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_subscription);
        subscribeText = findViewById(R.id.subscribeTextId);
        priceText = findViewById(R.id.priceTextId);

        subscribeText.setText( "Subscribe " + getIntent().getStringExtra("Package") );
        priceText.setText("  For : $" + getIntent().getStringExtra("Price") + "/- Only");
        backBtn = findViewById(R.id.backBtnId);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        signUpButton = findViewById(R.id.signUp_btn_subscription);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                makePayment();
                Intent toPayment = new Intent(SignUpSubscription.this , Payment.class);
                toPayment.putExtra("Package" , getIntent().getStringExtra("Package"));
                toPayment.putExtra("Price" , getIntent().getStringExtra("Price"));
                startActivity(toPayment);
            }
        });
//        configurePayPal();

    }

    private void configurePayPal() {

        config = new PayPalConfiguration()
                .environment(CONFIG_ENVIRONMENT)
                .clientId(PAYPAL_KEY)
                .merchantName("Paypal Login")
                .merchantPrivacyPolicyUri(Uri.parse("https://www.example.com/privacy"))
                .merchantUserAgreementUri(Uri.parse("https://www.example.com/legal"));


    }

    private void makePayment() {

        try {
            Intent intent = new Intent(this , PayPalService.class);
            intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION , config);
            startService(intent);

            thingsToBuy = new PayPalPayment(new BigDecimal(String.valueOf("60.0")) ,"USD" , "Payment", PayPalPayment.PAYMENT_INTENT_SALE);
            Intent payment = new Intent(this , PaymentActivity.class);
            payment.putExtra(PaymentActivity.EXTRA_PAYMENT , thingsToBuy);
            payment.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION , config);
            startActivityForResult(payment , REQUEST_CODE_PAYMENT);
        }catch (Exception e){
            Toast.makeText(this, "Error " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        // TODO get the paypal key from shabbir bhai and apply that key in the project to handle the crash happening over here

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
    }
}