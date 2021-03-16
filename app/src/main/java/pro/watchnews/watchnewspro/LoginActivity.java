package pro.watchnews.watchnewspro;


import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import pro.watchnews.watchnewspro.helper.HttpUtils;
import pro.watchnews.watchnewspro.helper.ResetFormDialog;
import pro.watchnews.watchnewspro.helper.SQLiteHandler;
import pro.watchnews.watchnewspro.helper.sessionManager;
import pro.watchnews.watchnewspro.interfaces.HttpResponseCallback;
import pro.watchnews.watchnewspro.model.SubscriptionsModels.ChannelsDescription;
import pro.watchnews.watchnewspro.utill.MyReciever;
import pro.watchnews.watchnewspro.utill.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.icu.util.Calendar.*;
import static pro.watchnews.watchnewspro.R.drawable.ic_check_black_24dp;
import static pro.watchnews.watchnewspro.R.drawable.ic_warning_black_24dp;

public class LoginActivity extends AppCompatActivity implements ResetFormDialog.ResetFormDialogListener {

    private Button btnLogin;
    private TextView btnsignup;
    private Button btnLinkToRegister;
    private EditText inputEmail;
    private EditText inputPassword;
    private ProgressDialog pDialog;
    private ProgressDialog messaged;
    private sessionManager session;
    private SQLiteHandler db;
    private TextView fpassword;
    private AlarmManager alarmMgr;
    private PendingIntent alarmIntent;
    private ArrayList<ChannelsDescription> channelsDescriptions;

    private String status;
    String uid;
    private Context mContext;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        inputEmail = (EditText) findViewById(R.id.s_email);
        inputEmail.requestFocus();
        inputPassword = (EditText) findViewById(R.id.s_password);
        btnLogin = (Button) findViewById(R.id.login_btn);
        btnsignup = (TextView) findViewById(R.id.signuptext);
        alarmMgr = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, MyReciever.class);
        alarmIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
        channelsDescriptions = new ArrayList<>();

// Set the alarm to start at 10:31 AM
        Calendar calendar = getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(HOUR_OF_DAY, 11);
        calendar.set(MINUTE, 15);

// setRepeating() lets you specify a precise custom interval--in this case,
// 1 day
        alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY, alarmIntent);
        // Progress dialog
        pDialog = new ProgressDialog(this);
        messaged = new ProgressDialog(this);

        // set new drawable
        pDialog.setCancelable(false);

        // SQLite database handler
        db = new SQLiteHandler(getApplicationContext());

        // Session manager
        session = new sessionManager(getApplicationContext());

        // Check if user is already logged in or not
        if (session.isLoggedIn()) {
            // User is already logged in. Take him to main activity
            Intent intent2 = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent2);
            finish();
        }

        fpassword = findViewById(R.id.fpassword);
        fpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });

        // Login button Click Event
        btnLogin.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                String email = inputEmail.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();

                // Check for empty data in the form
                if (!email.isEmpty() && !password.isEmpty()) {
                    // login user
                    checkLogin(email, password);
                } else {
                    // Prompt user to enter credentials
                    Toast.makeText(getApplicationContext(),
                            "Please enter the credentials!", Toast.LENGTH_LONG)
                            .show();
                }
            }

        });


        btnsignup.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                signup_activity();
            }

        });


    }

    public void openDialog() {
        ResetFormDialog exampleDialog = new ResetFormDialog();
        exampleDialog.show(getSupportFragmentManager(), "example dialog");
    }


    /**
     * function to verify login details in mysql db
     */
    private void checkLogin(final String email, final String password) {
        // Tag used to cancel the request

        String URL_lOGIN = "https://www.watchnews.pro/wp-json/custom-plugin/login";
        pDialog.setMessage("Logging in ...");
        showDialog();

        Map<String, String> requestParams = new HashMap<String, String>();
        //   requestParams.put("api_key", ApiClient.API_KEY);
//        requestParams.put("pass_key", "12345");
        requestParams.put("username", email);
        requestParams.put("password", password);


        HttpUtils.httpPostRequestWithParam(URL_lOGIN,
                requestParams, new HttpResponseCallback() {

                    @Override
                    public void onCompleteHttpResponse(String whichUrl,
                                                       String jsonResponse) {

                        if (jsonResponse != null) {
                            try {
                                //JSONArray jsonArray = new JSONArray(jsonResponse);
                                JSONObject response = new JSONObject(jsonResponse);
                                JSONObject data = response.getJSONObject("data");
                                JSONObject allCaps = response.getJSONObject("allcaps");
//                                if ( Boolean.getBoolean(allCaps.getString("subscriber")) == false){
                                // Now store the user in SQLite
                                uid = data.getString("ID");
                                String user = data.getString("user_login");
                                String name = data.getString("display_name");
                                String email = data.getString("user_email");
                                String created_at = data.getString("user_registered");

                                // Inserting row in users table
                                db.addUser(name, email, uid, created_at);
                                checkSubscription(Integer.valueOf(uid));
                                session.setLogin(true);
                                // Launch main activity
                                    /*Intent intent = new Intent(LoginActivity.this,
                                            MainActivity.class);
                                    intent.putExtra("EXTRA_SESSION_ID", uid);
                                    startActivity(intent);*/
//                                }else{
                                   /* AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                    final AlertDialog dialog = builder.create();
                                    dialog.setTitle("Subscription Expired");
                                    dialog.setMessage("Your current subscription has been expired. Kindly subscribe to one of our package.");
                                    dialog.setIcon(R.drawable.warning);
                                    dialog.setButton(Dialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            session.setLogin(false);
                                            db.deleteUsers();
                                            Intent toSubscription = new Intent(LoginActivity.this , Subscription.class);
                                            dialog.dismiss();
                                            startActivity(toSubscription);
                                        }
                                    });
                                    dialog.setButton(Dialog.BUTTON_NEGATIVE, "CANCEL", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            dialog.dismiss();
                                        }
                                    });
                                    dialog.show();*/

//                                }


                            } catch (JSONException e) {
                                // JSON error
                                e.printStackTrace();
                                pDialog.dismiss();
                                Toast.makeText(getApplicationContext(), "Wrong Username Or Password", Toast.LENGTH_LONG).show();
                            }

                        } else {
                            // Error in login. Get the error message

                            Toast.makeText(getApplicationContext(),
                                    "Login failed", Toast.LENGTH_LONG).show();
                            pDialog.dismiss();

                        }

                    }
                });
    }

    private void checkSubscription(int id) {
        RetrofitClient.connectTo("https://www.watchnews.pro/wp-json/mp/v1/");
        Call<List<ChannelsDescription>> call = RetrofitClient.getInstance().getApi().checkSubscription(id);
        call.enqueue(new Callback<List<ChannelsDescription>>() {
            @Override
            public void onResponse(Call<List<ChannelsDescription>> call, Response<List<ChannelsDescription>> response) {
                if (response.isSuccessful()) {
                    channelsDescriptions = (ArrayList<ChannelsDescription>) response.body();
                    status = channelsDescriptions.get(0).getRecentSubscriptions().get(0).getStatus();
                    switch (status) {
                        case "active":
                            goToMain();
                            break;

                        case "enabled":
                            goToMain();
                            break;

                        case "paused":
                            goToSubscription();
                            break;

                        case "cancelled":
                            goToSubscription();
                            break;

                        case "pending":
                            goToSubscription();
                            break;

                        case "stopped":
                            goToSubscription();
                            break;
                            
                        default:
                            Toast.makeText(LoginActivity.this, "Status : " + status, Toast.LENGTH_SHORT).show();

                    }

                } else {
                    Toast.makeText(LoginActivity.this, "Unsuccessful Response in checking subscription", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<ChannelsDescription>> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Failed Response in checking subscription", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void goToMain() {
        Intent toMain = new Intent(LoginActivity.this,
                MainActivity.class);
        toMain.putExtra("EXTRA_SESSION_ID", uid);
        pDialog.dismiss();
        startActivity(toMain);
        finish();
    }

    private void goToSubscription() {
        pDialog.dismiss();
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        final AlertDialog dialog = builder.create();
        dialog.setTitle("Subscription Expired");
        dialog.setMessage("Your current subscription has been expired. Kindly subscribe to one of our package.");
        dialog.setIcon(R.drawable.warning);
        dialog.setButton(Dialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                session.setLogin(false);
                db.deleteUsers();
                Intent toSubscription = new Intent(LoginActivity.this, Subscription.class);
                dialog.dismiss();
                pDialog.dismiss();
                startActivity(toSubscription);
            }
        });
        dialog.setButton(Dialog.BUTTON_NEGATIVE, "CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                pDialog.dismiss();
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    private void signup_activity() {
        Intent i = new Intent(this, signup.class);
        startActivity(i);
    }

    @Override
    public void applyTexts(String username) {

        String URL_lOGIN = "https://www.watchnews.pro/api/user/retrieve_password/?user_login=" + username;
        Map<String, String> requestParams = new HashMap<String, String>();
//        requestParams.put("uID", Uid);
        pDialog.setMessage("Sending Email ...");
        showDialog();
//        requestParams.put("cID", channel_id);


        HttpUtils.httpPostRequestWithParam("https://www.watchnews.pro/api/user/retrieve_password/?user_login=" + username,
                requestParams, new HttpResponseCallback() {
                    @Override
                    public void onCompleteHttpResponse(String whichUrl,
                                                       String jsonResponse) {
                        pDialog.dismiss();

                        if (jsonResponse != null) {

                            try {
                                //  JSONArray jsonArray = new JSONArray(jsonResponse);
                                JSONObject obj = new JSONObject(jsonResponse);
                                // String res = obj.getString("status");
                                String status = obj.getString("status");

                                if (status.equals("error")) {
                                    String error = obj.getString("error");
                                    messaged.setMessage(error);
                                    Drawable drawablemesg = getResources().getDrawable(ic_warning_black_24dp);
                                    messaged.setIndeterminateDrawable(drawablemesg);
                                    messaged.show();

                                } else {
                                    String msg = obj.getString("msg");
                                    Drawable drawablemesg = getResources().getDrawable(ic_check_black_24dp);
                                    messaged.setIndeterminateDrawable(drawablemesg);
                                    messaged.setMessage(msg);
                                    messaged.show();
                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        } else {
                            pDialog.dismiss();
                            Toast.makeText(mContext, "Server issue, please try again later", Toast.LENGTH_LONG).show();
                        }
                    }

                });


    }


}