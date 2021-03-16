package pro.watchnews.watchnewspro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import pro.watchnews.watchnewspro.adapters.FavAdapter;
import pro.watchnews.watchnewspro.helper.HttpUtils;
import pro.watchnews.watchnewspro.helper.Network;
import pro.watchnews.watchnewspro.helper.SQLiteHandler;
import pro.watchnews.watchnewspro.helper.ShowMessagesDialogsUtitly;
import pro.watchnews.watchnewspro.interfaces.FavInterface;
import pro.watchnews.watchnewspro.interfaces.HttpResponseCallback;
import pro.watchnews.watchnewspro.model.FavModel;
import pro.watchnews.watchnewspro.ui.main.PageViewModel;
import pro.watchnews.watchnewspro.utill.StorageUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static maes.tech.intentanim.CustomIntent.customType;

public class FavouritesActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{
   private ImageButton topbackbtn;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private PageViewModel pageViewModel;
    private RecyclerView recycleViewRadios;
    private ArrayList<FavModel> radioArrayList;
    private Context mContext;
 private  MenuItem menuItem;
    private int mColumnCount = 1;
    private String Uid;
    private SQLiteHandler db;
    private FavAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);
        mContext = getApplicationContext();
        radioArrayList = new ArrayList<>();
        recycleViewRadios =findViewById(R.id.recycler_view_fav);
        // recycleViewRadios.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));


        recycleViewRadios.setItemAnimator(new DefaultItemAnimator());
        if (mColumnCount <= 1) {
            recycleViewRadios.setLayoutManager(new LinearLayoutManager(mContext));
        } else {
            recycleViewRadios.setLayoutManager(new GridLayoutManager(mContext, mColumnCount));
        }
        recycleViewRadios.setHasFixedSize(true);
        recycleViewRadios.setItemAnimator(new DefaultItemAnimator());
        // recycleViewRadios.addItemDecoration(new SpacesItemDecoration(20));
        mSwipeRefreshLayout = findViewById(R.id.swipe_container_countries);
        mSwipeRefreshLayout.setOnRefreshListener(FavouritesActivity.this);
        db = new SQLiteHandler(this);
        Cursor cursor = db.alldata();
        if (cursor.getColumnCount() == 0) {

            Toast.makeText(mContext, "Data Not Found", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {

                Uid = cursor.getString(3);

            }
        }


        mSwipeRefreshLayout.post(new Runnable() {

            @Override
            public void run() {

                mSwipeRefreshLayout.setRefreshing(true);

                // Fetching data from server
                getNewsList();
            }
        });

        topbackbtn =findViewById(R.id.topbackbtn);
        topbackbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              finish();
            }
        });




        BottomNavigationView bottomnav=findViewById(R.id.bnavfav);


        bottomnav.setOnNavigationItemSelectedListener(bnavlistener) ;
        Menu menu = bottomnav.getMenu();
        menu.findItem(R.id.navfav).setChecked(true);
          menu.findItem(R.id.navfav).getIcon().setTint(getApplication().getResources().getColor(R.color.colorwhite));

    }



    private BottomNavigationView.OnNavigationItemSelectedListener bnavlistener=new
            BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment fragment;
                    switch (menuItem.getItemId()){
                        case R.id.navhome:
                            finish();
                            Intent i = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(i);
                            customType(FavouritesActivity.this,"fadein-to-fadeout");

                            break;
                        case R.id.navfav:
                            Toast.makeText(mContext, "You are already in Favourites", Toast.LENGTH_SHORT).show();
                            break;

                        case R.id.navsetting:
                            Intent setting = new Intent(getApplicationContext(), Settings.class);
                            startActivity(setting);
                            customType(FavouritesActivity.this,"fadein-to-fadeout");
                            finish();
                            break;

                    }


                    return false;
                }
            };

    @Override
    public void onBackPressed() {
        super.onBackPressed();
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
        //   requestParams.put("api_key", ApiClient.API_KEY);
//        requestParams.put("pass_key", "12345");
//        requestParams.put("case", "show_comments");

String Url="http://app.newslive.com/newslive/api/fav_get/"+Uid;
        HttpUtils.httpGetRequestWithParam(mContext, Url,
                requestParams, new HttpResponseCallback() {
                    @Override
                    public void onCompleteHttpResponse(String whichUrl,
                                                       String jsonResponse) {
                        ShowMessagesDialogsUtitly.hideProgressDialog();

                        if (jsonResponse != null) {

                            try {
                                JSONArray jsonArray = new JSONArray(jsonResponse);
                                // JSONObject responseObj = obj.getJSONObject("response");


                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = new JSONObject();
                                    jsonObject = jsonArray.getJSONObject(i);
                                    FavModel model = new Gson()
                                            .fromJson(jsonObject.toString(),
                                                    FavModel.class);
                                    radioArrayList.add(model);
                                }



                                mAdapter = new FavAdapter(mContext, radioArrayList, new FavInterface() {

                                    @Override
                                    public void onCompleteEntries(FavModel item, int pos) {

                                        StorageUtil storage = new StorageUtil(mContext);

                                        storage.storeAudioIndex(pos);

                                        Intent in = new Intent(getApplicationContext(), videoPlayer.class);
                                        in.putExtra("pos", pos);
                                        in.putExtra("url", item.getStream_url());
                                        in.putExtra("casturl", item.getDecoded_stream());
                                        in.putExtra("name", item.getChannel_name());
                                        in.putExtra("img", item.getCh_image());
                                        in.putExtra("channel_id", item.getChannel_id());
                                        in.putExtra("channel_desc", item.getChannel_desc());
                                        in.putExtra("category_name", item.getCategory_name());
                                        in.putExtra("country_name", item.getCountry_name());
                                        in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        mContext.startActivity(in);

                                        customType(FavouritesActivity.this,"fadein-to-fadeout");
                                    }
                                });
                                recycleViewRadios.setAdapter(mAdapter);




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
        radioArrayList.clear();
        getNewsList();
    }
    @Override
    public void finish() {
        customType(FavouritesActivity.this,"right-to-left");
        super.finish();
    }


}

