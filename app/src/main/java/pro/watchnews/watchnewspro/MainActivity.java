package pro.watchnews.watchnewspro;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import pro.watchnews.watchnewspro.helper.HttpUtils;
import pro.watchnews.watchnewspro.helper.SQLiteHandler;
import pro.watchnews.watchnewspro.helper.sessionManager;
import pro.watchnews.watchnewspro.interfaces.HttpResponseCallback;
import pro.watchnews.watchnewspro.ui.main.SectionsPagerAdapter;
import pro.watchnews.watchnewspro.utill.MyReciever;

import static maes.tech.intentanim.CustomIntent.customType;

public class MainActivity extends AppCompatActivity implements channelFragment.OnFragmentInteractionListener,
        categoriesFragment.OnFragmentInteractionListener,countriesFragment.OnFragmentInteractionListener,countries_list.OnFragmentInteractionListener, channels_co_ca.OnFragmentInteractionListener {


    private ProgressDialog pDialog;
    private ProgressDialog messaged;
    private sessionManager session;
    private SQLiteHandler db;
    private TextView fpassword;
    private String email , password;


  @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        BottomNavigationView bottomnav=findViewById(R.id.bnav);
        bottomnav.setOnNavigationItemSelectedListener(bnavlistener) ;
      /*db = new SQLiteHandler(getApplicationContext());
      session = new sessionManager(getApplicationContext());
      String URL_lOGIN ="https://www.watchnews.pro/wp-json/custom-plugin/login";
      pDialog.setMessage("Logging in ...");
//      showDialog();

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
                      pDialog.dismiss();
                      if (jsonResponse != null) {
                          session.setLogin(true);
                          try {
                              //JSONArray jsonArray = new JSONArray(jsonResponse);
                              JSONObject response = new JSONObject(jsonResponse);
                              JSONObject data = response.getJSONObject("data");
                              JSONObject allCaps = response.getJSONObject("allcaps");
                              if ( Boolean.getBoolean(allCaps.getString("subscriber")) == true){
                                  // Now store the user in SQLite
                                  String uid = data.getString("ID");
                                  String  user = data.getString("user_login");
                                  String name = data.getString("display_name");
                                  String email = data.getString("user_email");
                                  String created_at = data.getString("user_registered");

                                  // Inserting row in users table
                                  db.addUser(name, email, uid, created_at);

                                  // Launch main activity
                                  Intent intent = new Intent(MainActivity.this,
                                          MainActivity.class);
                                  intent.putExtra("EXTRA_SESSION_ID", uid);
                                  startActivity(intent);
                              }else{
                                  session.setLogin(false);
                                  db.deleteUsers();
                                  Intent toSubscription = new Intent(MainActivity.this , Subscription.class);
                                  startActivity(toSubscription);
                              }





                          } catch(JSONException e){
                              // JSON error
                              e.printStackTrace();
                              Toast.makeText(getApplicationContext(), "Wrong Username Or Password", Toast.LENGTH_LONG).show();
                          }

                      }
                      else {
                          // Error in login. Get the error message

                          Toast.makeText(getApplicationContext(),
                                  "Login failed", Toast.LENGTH_LONG).show();
                          pDialog.dismiss();

                      }

                  }
              });*/

    }
       private BottomNavigationView.OnNavigationItemSelectedListener bnavlistener=new
               BottomNavigationView.OnNavigationItemSelectedListener() {
                   @Override
                   public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                       Fragment fragment;
                       switch (menuItem.getItemId()){
                           case R.id.navhome:
                               Toast.makeText(MainActivity.this, "You are already in Home", Toast.LENGTH_SHORT).show();

                               break;
                           case R.id.navfav:

                               Intent fav = new Intent(getApplicationContext(), FavouritesActivity.class);
                               startActivity(fav);

                               customType(MainActivity.this,"fadein-to-fadeout");

                               break;

                           case R.id.navsetting:
                               Intent setting = new Intent(getApplicationContext(), Settings.class);
                               startActivity(setting);
                               customType(MainActivity.this,"fadein-to-fadeout");

                            break;

                       }


                       return false;
                   }
               };

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}