package pro.watchnews.watchnewspro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import pro.watchnews.watchnewspro.helper.HttpUtils;
import pro.watchnews.watchnewspro.helper.Network;
import pro.watchnews.watchnewspro.helper.SQLiteHandler;
import pro.watchnews.watchnewspro.helper.ShowMessagesDialogsUtitly;
import pro.watchnews.watchnewspro.helper.sessionManager;
import pro.watchnews.watchnewspro.interfaces.HttpResponseCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static maes.tech.intentanim.CustomIntent.customType;

public class Settings extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private Context mContext;
    private String Uid;
    private ImageButton topbackbtn;
    private SQLiteHandler db;
    private String u_name;
    private String u_subscription;
    private String u_expiray;
    private String u_email;
    private sessionManager session;
    private TextView username;
    private TextView usersubs;
    private TextView userexpiray;
    private TextView useremail;
    private Button btnLogout;
    private Button btnaccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        mContext = getApplicationContext();
        db = new SQLiteHandler(getApplicationContext());

        // Session manager
        session = new sessionManager(getApplicationContext());
        mSwipeRefreshLayout = findViewById(R.id.swipe_container_countries);
        mSwipeRefreshLayout.setOnRefreshListener(Settings.this);
        db = new SQLiteHandler(this);
        Cursor cursor = db.alldata();
        if (cursor.getColumnCount() == 0) {

            Toast.makeText(mContext, "Data Not Found", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {

                Uid = cursor.getString(3);

            }
        }
        getNewsList();
         username=findViewById(R.id.u_name);
         usersubs=findViewById(R.id.u_subscription);
         userexpiray=findViewById(R.id.u_expiray);
         useremail=findViewById(R.id.u_email);
       btnaccount=findViewById(R.id.mngsubs);
        btnLogout = findViewById(R.id.btnlogout);

        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {

                mSwipeRefreshLayout.setRefreshing(true);

                // Fetching data from server
                getNewsList();
            }
        });

        btnaccount.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){

                    btnaccount.setBackground(ContextCompat.getDrawable(mContext, R.drawable.roundshape));
                    btnaccount.setTextColor(getApplication().getResources().getColor(R.color.colorwhite));
                } if(event.getAction() == MotionEvent.ACTION_UP){
                   btnaccount.setBackground(ContextCompat.getDrawable(mContext, R.drawable.roundshapemn));
                    btnaccount.setTextColor(getApplication().getResources().getColor(R.color.colorPrimary));
                }
                return false;
            }
        });

        btnLogout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){

                    btnLogout.setBackground(ContextCompat.getDrawable(mContext, R.drawable.roundshape));
                    btnLogout.setTextColor(getApplication().getResources().getColor(R.color.colorwhite));
                } if(event.getAction() == MotionEvent.ACTION_UP){
                    btnLogout.setBackground(ContextCompat.getDrawable(mContext, R.drawable.roundshapemn));
                    btnLogout.setTextColor(getApplication().getResources().getColor(R.color.colorPrimary));
                }
                return false;
            }
        });


        topbackbtn =findViewById(R.id.topbackbtn);
        topbackbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnaccount.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                account_open();
            }

        });



        btnLogout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                logoutUser();
            }
        });

        BottomNavigationView bottomnav=findViewById(R.id.bnavsetting);
        bottomnav.setOnNavigationItemSelectedListener(bnavlistener) ;
        Menu menu = bottomnav.getMenu();
        menu.findItem(R.id.navsetting).setChecked(true);
        menu.findItem(R.id.navsetting).getIcon().setTint(getApplication().getResources().getColor(R.color.colorwhite));

    }
    private BottomNavigationView.OnNavigationItemSelectedListener bnavlistener=new
            BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment fragment;
                    switch (menuItem.getItemId()){
                        case R.id.navhome:
                            Intent i = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(i);
                            customType(Settings.this,"fadein-to-fadeout");

                            break;
                        case R.id.navfav:
                            Intent fav = new Intent(getApplicationContext(), FavouritesActivity.class);
                            startActivity(fav);
                            customType(Settings.this,"fadein-to-fadeout");

                            break;

                        case R.id.navsetting:

                            Toast.makeText(mContext, "You are already in Settings", Toast.LENGTH_SHORT).show();

                            break;

                    }


                    return false;
                }
            };

    private void logoutUser() {
        session.setLogin(false);

        db.deleteUsers();

        // Launching the login activity
        Intent intent = new Intent(Settings.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }


    private void getNewsList() {


        if (!Network.haveNetworkConnection(mContext)) {
            Toast.makeText(mContext, getString(R.string.nointernet), Toast.LENGTH_LONG).show();
            mSwipeRefreshLayout.setRefreshing(false);
            return;
        }

        mSwipeRefreshLayout.setRefreshing(true);
        //ShowMessagesDialogsUtitly.showProgressDialog(mContext);
        Map<String, String> requestParams = new HashMap<String, String>();
         requestParams.put("MEMBERPRESS-API-KEY", "kLNnmZGrsR");
//        requestParams.put("pass_key", "12345");
//        requestParams.put("case", "show_comments");

        String Url="https://www.watchnews.pro/wp-json/mp/v1/members/"+Uid;
        HttpUtils.httpGetRequestWithParam(mContext, Url,
                requestParams, new HttpResponseCallback() {
                    @Override
                    public void onCompleteHttpResponse(String whichUrl,
                                                       String jsonResponse) {
                        ShowMessagesDialogsUtitly.hideProgressDialog();

                        if (jsonResponse != null) {

                            try {
                               JSONObject obj= new JSONObject(jsonResponse);
                                // JSONObject responseObj = obj.getJSONObject("response");


                                String fname= obj.getString("first_name");
                                String lname= obj.getString("last_name");
                                String email= obj.getString("email");
                                u_name=fname+" "+lname;
                                 useremail.setText(email);
                                username.setText(u_name);
                                JSONArray active_memberships= obj.getJSONArray("active_memberships");
                                for (int i = 0; i < active_memberships.length(); i++) {
                                    JSONObject jsonObject = new JSONObject();
                                    jsonObject = active_memberships.getJSONObject(i);

                                     u_subscription= jsonObject.getString("title");

                                         usersubs.setText(u_subscription);


                                     u_expiray= jsonObject.getString("expire_after");
                                     u_expiray = u_expiray+" "+"Days";
                                     userexpiray.setText(u_expiray);


                                }





                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            mSwipeRefreshLayout.setRefreshing(false);
                        } else {
                            mSwipeRefreshLayout.setRefreshing(false);
                            Toast.makeText(mContext, "Server issue, please try again later", Toast.LENGTH_LONG).show();
                        }
                    }

                });


    }


    @Override
    public void onRefresh() {

    }

    private void account_open(){
        Intent i = new Intent(this, Myaccount.class);
        startActivity(i);
    }
}
