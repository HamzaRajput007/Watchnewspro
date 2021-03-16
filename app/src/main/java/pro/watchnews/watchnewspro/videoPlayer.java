package pro.watchnews.watchnewspro;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;

import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;


import com.google.android.gms.cast.MediaInfo;
import com.google.android.gms.cast.MediaLoadRequestData;
import com.google.android.gms.cast.MediaMetadata;
import com.google.android.gms.cast.framework.CastButtonFactory;
import com.google.android.gms.cast.framework.CastContext;
import com.google.android.gms.cast.framework.CastSession;
import com.google.android.gms.cast.framework.SessionManagerListener;
import com.google.android.gms.cast.framework.media.RemoteMediaClient;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;

import com.mradzinski.caster.Caster;
import com.mradzinski.caster.MediaData;
import pro.watchnews.watchnewspro.adapters.releventAdapter;
import pro.watchnews.watchnewspro.helper.HttpUtils;
import pro.watchnews.watchnewspro.helper.SQLiteHandler;
import pro.watchnews.watchnewspro.helper.ShowMessagesDialogsUtitly;
import pro.watchnews.watchnewspro.helper.sessionManager;
import pro.watchnews.watchnewspro.interfaces.HttpResponseCallback;

import pro.watchnews.watchnewspro.interfaces.RemoteClient;
import pro.watchnews.watchnewspro.interfaces.releventInterface;
import pro.watchnews.watchnewspro.model.Model_Decrypted_Channed_Urls;
import pro.watchnews.watchnewspro.model.releventModel;
import pro.watchnews.watchnewspro.utill.StorageUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import tcking.github.com.giraffeplayer2.VideoInfo;
import tcking.github.com.giraffeplayer2.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import androidx.fragment.app.Fragment;
import androidx.mediarouter.app.MediaRouteButton;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import static android.text.TextUtils.isEmpty;
import static pro.watchnews.watchnewspro.R.drawable.*;
import static maes.tech.intentanim.CustomIntent.customType;

@RequiresApi(api = Build.VERSION_CODES.O)
public class videoPlayer extends AppCompatActivity {

    String channelTitle;
    private Activity mActivity;
    VideoView videoView;
    private SQLiteHandler db;
    private ToggleButton fav_btn;
    LinearLayout adContainer;
    String TAG = videoPlayer.class.getName();
    private int mColumnCount = 3;
    Boolean isPlayer = true;
    private ImageButton topbackbtn;
    private sessionManager session;
    private ProgressBar spinnerView;
    private TextView channelname;
    private RecyclerView recycleViewraleted;
    private ArrayList<releventModel> relatedarraylist;
    private Context mContext;
    private boolean flaoting;
    private LinearLayout playertopbar;
    private String channel_desc;
    private String urlchannel;
    private String country_name;
    private String category_name;
    private String Uid;
    private String uemail;
    private TextView channel_de;
    private TextView countryname;
    private TextView categoryname;
    private String channel_id;
    private MenuItem mediaRouteMenuItem;
    private Caster caster;
    public Boolean FAV = false;
    private releventAdapter mAdapter;
    private CastContext mCastContext;
    private CastSession mCastSession;
    private SessionManagerListener<CastSession> mSessionManagerListener;
    private PlaybackState mPlaybackState;
    private PlaybackLocation mLocation;
    private Timer mControllersTimer;
    private MediaInfo mSelectedMedia;
    private View mControllers;
    private final Handler mHandler = new Handler();
//    private String decryptedUrl = "https://1312990316.rsc.cdn77.org/V0qvErt6KnY96-LOCQeArg==,1613759299/1312990316/index.m3u8";

    private boolean mControllersVisible;
    private MenuItem mQueueMenuItem;
    private Retrofit retrofit;
    private RemoteClient remoteClient;
    private String decryptedUrl;
    private Button alternateStreamBtn, primaryStreamBtn;
    final String Urlnew = "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4";
    private LinearLayout primaryAlternateStreamLayout ;
    private String alternateUrl;
    public enum PlaybackLocation {
        LOCAL,
        REMOTE
    }

    /**
     * List of various states that we can be in
     */
    public enum PlaybackState {
        PLAYING, PAUSED, BUFFERING, IDLE
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);

        retrofit = new Retrofit.Builder()
                .baseUrl("http://app.newslive.com/newslive/streams/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        remoteClient = retrofit.create(RemoteClient.class);
        mControllers = findViewById(R.id.controllers_video);
        urlchannel = getIntent().getStringExtra("url");
        channelTitle = getIntent().getStringExtra("name");
        channel_id = getIntent().getStringExtra("channel_id");
        videoView = (VideoView) findViewById(R.id.video_view);
        videoView.setVideoPath(getIntent().getStringExtra("url")).getPlayer().start();
        channel_desc = getIntent().getStringExtra("channel_desc");
        country_name = getIntent().getStringExtra("country_name");
        category_name = getIntent().getStringExtra("category_name");
        alternateUrl = getIntent().getStringExtra("alternate_url" );
        primaryAlternateStreamLayout = findViewById(R.id.primaryAlternateStreamLayoutId);
        if (channelTitle.equals("CNN") || channelTitle.equals("MSNBC") || channelTitle.equals("Fox News")){
            primaryAlternateStreamLayout.setVisibility(View.VISIBLE);
        }else {
            primaryAlternateStreamLayout.setVisibility(View.GONE);
        }



        alternateStreamBtn = findViewById(R.id.alternateStreamTextViewId);
        alternateStreamBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                videoView.setVideoPath(alternateUrl).getPlayer().start();
                alternateStreamBtn.setBackground(getResources().getDrawable(btn_background));
                alternateStreamBtn.setTextColor(getResources().getColor(R.color.colorwhite));
                primaryStreamBtn.setBackground(getResources().getDrawable(btn_bg_border));
                primaryStreamBtn.setTextColor(getResources().getColor(R.color.colorPrimary));
            }
        });
        primaryStreamBtn = findViewById(R.id.primaryStreamBtnId);
        primaryStreamBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                videoView.setVideoPath(getIntent().getStringExtra("url")).getPlayer().start();
                primaryStreamBtn.setBackground(getResources().getDrawable(btn_background));
                primaryStreamBtn.setTextColor(getResources().getColor(R.color.colorwhite));
                alternateStreamBtn.setBackground(getResources().getDrawable(btn_bg_border));
                alternateStreamBtn.setTextColor(getResources().getColor(R.color.colorPrimary));
            }
        });
        getDecryptedUrl(channel_id,channelTitle);

        MediaMetadata movieMetadata = new MediaMetadata(MediaMetadata.MEDIA_TYPE_MOVIE);

//        caster = Caster.create(this);
//        caster.addMiniController();
        MediaRouteButton mediaRouteButton = (MediaRouteButton) findViewById(R.id.media_button);
        CastButtonFactory.setUpMediaRouteButton(getApplicationContext(),mediaRouteButton);

        setupCastListener();
        mCastContext = CastContext.getSharedInstance(this);
        mCastSession = mCastContext.getSessionManager().getCurrentCastSession();

        if (!isEmpty(channel_id) || !isEmpty(channelTitle)){
            if (!channel_id.equals("14") || !channel_id.equals("3") || !channel_id.equals("4") || !channel_id.equals("12") || !channel_id.equals("11") || !channel_id.equals("6")
                    || !channel_id.equals("1") || !channel_id.equals("13") || !channel_id.equals("8") || !channel_id.equals("15")) {
                mSelectedMedia = new MediaInfo.Builder(urlchannel)
                        .setStreamType(MediaInfo.STREAM_TYPE_BUFFERED)
                        .setContentType("videos/x-mpegURL")
                        .setMetadata(movieMetadata)
                        .build();


            }
        }

        boolean shouldStartPlayback = true;
        int startPosition = 0;
//        mVideoView.setVideoURI(Uri.parse(mSelectedMedia.getContentId()));
//        Log.d(TAG, "Setting url of the VideoView to: " + mSelectedMedia.getContentId());
        if (shouldStartPlayback) {
            // this will be the case only if we are coming from the
            // CastControllerActivity by disconnecting from a device
            mPlaybackState = PlaybackState.PLAYING;
            updatePlaybackLocation(PlaybackLocation.LOCAL);
            if (startPosition > 0) {
                videoView.getPlayer().seekTo(startPosition);
            }
            videoView.getPlayer().start();
            startControllersTimer();
        } else {
            // we should load the video but pause it
            // and show the album art.
            if (mCastSession != null && mCastSession.isConnected()) {
                updatePlaybackLocation(PlaybackLocation.REMOTE);
            } else {
                updatePlaybackLocation(PlaybackLocation.LOCAL);
            }
            mPlaybackState = PlaybackState.IDLE;
        }

//        caster.setupMediaRouteButton(mediaRouteButton, true);
        mContext = getApplicationContext();
        relatedarraylist = new ArrayList<>();
        recycleViewraleted = findViewById(R.id.recyclerView_player);
        // recycleViewRadios.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
        channelname = findViewById(R.id.cp_name);
        countryname = findViewById(R.id.cp_category);
        categoryname = findViewById(R.id.cp_Views);

        fav_btn = findViewById(R.id.fav_btn);
        topbackbtn = findViewById(R.id.vtpb);
        topbackbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


//        playertopbar = findViewById(R.id.player_topbar);
        session = new sessionManager(getApplicationContext());
        if (mColumnCount <= 1) {
            recycleViewraleted.setLayoutManager(new LinearLayoutManager(mContext));
        } else {
            recycleViewraleted.setLayoutManager(new GridLayoutManager(mContext, mColumnCount));
        }
        recycleViewraleted.setHasFixedSize(true);
        recycleViewraleted.setItemAnimator(new DefaultItemAnimator());
        channelname.setText(channelTitle);
        mActivity = this;

        // videoView.getPlayer().setDisplayModel(GiraffePlayer.DISPLAY_FLOAT);
        //	hideSystemUI();

        countryname.setText(country_name);
        categoryname.setText(category_name);
        channel_de = findViewById(R.id.description_text);
        channel_de.setText(channel_desc);
        VideoInfo videoInfo = new VideoInfo().setShowTopBar(true);
        getNewsList();
        MediaData mediaData = new MediaData.Builder(Urlnew)
                .setStreamType(MediaData.STREAM_TYPE_BUFFERED)
                .setContentType("videos/mp4") // Or "videos/mp4"... or any supported content type
                .setMediaType(MediaData.MEDIA_TYPE_TV_SHOW)
                .setTitle(channelTitle)
                .setDescription(channel_desc)
                .setPlaybackRate(MediaData.PLAYBACK_RATE_NORMAL)
                .setAutoPlay(true)
                .build();

//        if (caster.isConnected()) {
//            caster.getPlayer().loadMediaAndPlay(mediaData);
//        }
        db = new SQLiteHandler(this);
        Cursor cursor = db.alldata();
        if (cursor.getColumnCount() == 0) {

            Toast.makeText(mActivity, "Data Not Found", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {

                Uid = cursor.getString(3);
                uemail = cursor.getString(2);
            }
        }
        checkfav();


        fav_btn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    fav_btn.setBackgroundResource(R.drawable.redheart);
                    favinsert();
                } else {
                    fav_btn.setBackgroundResource(heartbtn);
                    deletefav();
                }
            }
        });

        BottomNavigationView bottomnav = findViewById(R.id.bnavVideo);
        bottomnav.setOnNavigationItemSelectedListener(bnavlistener);

    }

    private void getDecryptedUrl(String channel_id,String channelTitle) {

        String url = null;

        if (channel_id.equals("14") && channelTitle.equals("MSNBC")){
            url = getMSNBC();
        }
        if (channel_id.equals("3") && channelTitle.equals("Fox News")){
            url = getFox();
        }
        if (channel_id.equals("4") && channelTitle.equals("BBC News")){
            url = getBBC();
        }
        if (channel_id.equals("12") && channelTitle.equals("C-SPAN")){
            url = getCSPAN();
        }
        if (channel_id.equals("11") && channelTitle.equals("CBC")){
            url = getCBC();
        }
        if (channel_id.equals("6") && channelTitle.equals("CNBC News")){
            url = getCNBC();
        }
        if (channel_id.equals("1") && channelTitle.equals("CNN")){
            url = getCNN();
        }
        if (channel_id.equals("13") && channelTitle.equals("Fox Business")){
            url = getFoxBusiness();
        }
        if (channel_id.equals("8") && channelTitle.equals("HLN")){
            url = getHLN();
        }
        if (channel_id.equals("15") && channelTitle.equals("One America")){
            url = getOneAmerica();
        }

//        return url;
    }

    private String getOneAmerica() {
        Call<Model_Decrypted_Channed_Urls> call = remoteClient.getOneAmerica();
        call.enqueue(new Callback<Model_Decrypted_Channed_Urls>() {
            @Override
            public void onResponse(Call<Model_Decrypted_Channed_Urls> call, Response<Model_Decrypted_Channed_Urls> response) {
                MediaMetadata movieMetadata = new MediaMetadata(MediaMetadata.MEDIA_TYPE_MOVIE);
                decryptedUrl = response.body().getUrl();
                mSelectedMedia = new MediaInfo.Builder(decryptedUrl)
                        .setStreamType(MediaInfo.STREAM_TYPE_BUFFERED)
                        .setContentType("application/x-mpegURL")
                        .setMetadata(movieMetadata)
                        .build();
            }

            @Override
            public void onFailure(Call<Model_Decrypted_Channed_Urls> call, Throwable t) {
                Toast.makeText(videoPlayer.this, "Server Error", Toast.LENGTH_SHORT).show();
            }
        });

        return decryptedUrl;
    }

    private String getHLN() {
        Call<Model_Decrypted_Channed_Urls> call = remoteClient.getHLN();
        call.enqueue(new Callback<Model_Decrypted_Channed_Urls>() {
            @Override
            public void onResponse(Call<Model_Decrypted_Channed_Urls> call, Response<Model_Decrypted_Channed_Urls> response) {
                MediaMetadata movieMetadata = new MediaMetadata(MediaMetadata.MEDIA_TYPE_MOVIE);
                decryptedUrl = response.body().getUrl();
                mSelectedMedia = new MediaInfo.Builder(decryptedUrl)
                        .setStreamType(MediaInfo.STREAM_TYPE_BUFFERED)
                        .setContentType("application/x-mpegURL")
                        .setMetadata(movieMetadata)
                        .build();
            }

            @Override
            public void onFailure(Call<Model_Decrypted_Channed_Urls> call, Throwable t) {
                Toast.makeText(videoPlayer.this, "Server Error", Toast.LENGTH_SHORT).show();
            }
        });

        return decryptedUrl;
    }

    private String getFoxBusiness() {
        Call<Model_Decrypted_Channed_Urls> call = remoteClient.getFoxBusiness();
        call.enqueue(new Callback<Model_Decrypted_Channed_Urls>() {
            @Override
            public void onResponse(Call<Model_Decrypted_Channed_Urls> call, Response<Model_Decrypted_Channed_Urls> response) {
                MediaMetadata movieMetadata = new MediaMetadata(MediaMetadata.MEDIA_TYPE_MOVIE);
                decryptedUrl = response.body().getUrl();
                mSelectedMedia = new MediaInfo.Builder(decryptedUrl)
                        .setStreamType(MediaInfo.STREAM_TYPE_BUFFERED)
                        .setContentType("application/x-mpegURL")
                        .setMetadata(movieMetadata)
                        .build();
            }

            @Override
            public void onFailure(Call<Model_Decrypted_Channed_Urls> call, Throwable t) {
                Toast.makeText(videoPlayer.this, "Server Error", Toast.LENGTH_SHORT).show();
            }
        });

        return decryptedUrl;
    }

    private String getCNN() {
        Call<Model_Decrypted_Channed_Urls> call = remoteClient.getCNN();
        call.enqueue(new Callback<Model_Decrypted_Channed_Urls>() {
            @Override
            public void onResponse(Call<Model_Decrypted_Channed_Urls> call, Response<Model_Decrypted_Channed_Urls> response) {
                MediaMetadata movieMetadata = new MediaMetadata(MediaMetadata.MEDIA_TYPE_MOVIE);
                decryptedUrl = response.body().getUrl();
                mSelectedMedia = new MediaInfo.Builder(decryptedUrl)
                        .setStreamType(MediaInfo.STREAM_TYPE_BUFFERED)
                        .setContentType("application/x-mpegURL")
                        .setMetadata(movieMetadata)
                        .build();
            }

            @Override
            public void onFailure(Call<Model_Decrypted_Channed_Urls> call, Throwable t) {
                Toast.makeText(videoPlayer.this, "Server Error", Toast.LENGTH_SHORT).show();
            }
        });

        return decryptedUrl;
    }

    private String getCNBC() {
        Call<Model_Decrypted_Channed_Urls> call = remoteClient.getCNBC();
        call.enqueue(new Callback<Model_Decrypted_Channed_Urls>() {
            @Override
            public void onResponse(Call<Model_Decrypted_Channed_Urls> call, Response<Model_Decrypted_Channed_Urls> response) {
                MediaMetadata movieMetadata = new MediaMetadata(MediaMetadata.MEDIA_TYPE_MOVIE);
                decryptedUrl = response.body().getUrl();
                mSelectedMedia = new MediaInfo.Builder(decryptedUrl)
                        .setStreamType(MediaInfo.STREAM_TYPE_BUFFERED)
                        .setContentType("application/x-mpegURL")
                        .setMetadata(movieMetadata)
                        .build();
            }

            @Override
            public void onFailure(Call<Model_Decrypted_Channed_Urls> call, Throwable t) {
                Toast.makeText(videoPlayer.this, "Server Error", Toast.LENGTH_SHORT).show();
            }
        });

        return decryptedUrl;
    }

    private String getCBC() {
        Call<Model_Decrypted_Channed_Urls> call = remoteClient.getCBC();
        call.enqueue(new Callback<Model_Decrypted_Channed_Urls>() {
            @Override
            public void onResponse(Call<Model_Decrypted_Channed_Urls> call, Response<Model_Decrypted_Channed_Urls> response) {
                MediaMetadata movieMetadata = new MediaMetadata(MediaMetadata.MEDIA_TYPE_MOVIE);
                decryptedUrl = response.body().getUrl();
                mSelectedMedia = new MediaInfo.Builder(decryptedUrl)
                        .setStreamType(MediaInfo.STREAM_TYPE_BUFFERED)
                        .setContentType("application/x-mpegURL")
                        .setMetadata(movieMetadata)
                        .build();
            }

            @Override
            public void onFailure(Call<Model_Decrypted_Channed_Urls> call, Throwable t) {
                Toast.makeText(videoPlayer.this, "Server Error", Toast.LENGTH_SHORT).show();
            }
        });

        return decryptedUrl;
    }

    private String getCSPAN() {
        Call<Model_Decrypted_Channed_Urls> call = remoteClient.getCSPAN();
        call.enqueue(new Callback<Model_Decrypted_Channed_Urls>() {
            @Override
            public void onResponse(Call<Model_Decrypted_Channed_Urls> call, Response<Model_Decrypted_Channed_Urls> response) {
                MediaMetadata movieMetadata = new MediaMetadata(MediaMetadata.MEDIA_TYPE_MOVIE);
                decryptedUrl = response.body().getUrl();
                mSelectedMedia = new MediaInfo.Builder(decryptedUrl)
                        .setStreamType(MediaInfo.STREAM_TYPE_BUFFERED)
                        .setContentType("application/x-mpegURL")
                        .setMetadata(movieMetadata)
                        .build();
            }

            @Override
            public void onFailure(Call<Model_Decrypted_Channed_Urls> call, Throwable t) {
                Toast.makeText(videoPlayer.this, "Server Error", Toast.LENGTH_SHORT).show();
            }
        });

        return decryptedUrl;
    }

    private String getBBC() {
        Call<Model_Decrypted_Channed_Urls> call = remoteClient.getBBC();
        call.enqueue(new Callback<Model_Decrypted_Channed_Urls>() {
            @Override
            public void onResponse(Call<Model_Decrypted_Channed_Urls> call, Response<Model_Decrypted_Channed_Urls> response) {
                MediaMetadata movieMetadata = new MediaMetadata(MediaMetadata.MEDIA_TYPE_MOVIE);
                decryptedUrl = response.body().getUrl();
                mSelectedMedia = new MediaInfo.Builder(decryptedUrl)
                        .setStreamType(MediaInfo.STREAM_TYPE_BUFFERED)
                        .setContentType("application/x-mpegURL")
                        .setMetadata(movieMetadata)
                        .build();
            }

            @Override
            public void onFailure(Call<Model_Decrypted_Channed_Urls> call, Throwable t) {
                Toast.makeText(videoPlayer.this, "Server Error", Toast.LENGTH_SHORT).show();
            }
        });

        return decryptedUrl;
    }

    private String getFox() {
        Call<Model_Decrypted_Channed_Urls> call = remoteClient.getFox();
        call.enqueue(new Callback<Model_Decrypted_Channed_Urls>() {
            @Override
            public void onResponse(Call<Model_Decrypted_Channed_Urls> call, Response<Model_Decrypted_Channed_Urls> response) {
                MediaMetadata movieMetadata = new MediaMetadata(MediaMetadata.MEDIA_TYPE_MOVIE);
                decryptedUrl = response.body().getUrl();
                mSelectedMedia = new MediaInfo.Builder(decryptedUrl)
                        .setStreamType(MediaInfo.STREAM_TYPE_BUFFERED)
                        .setContentType("application/x-mpegURL")
                        .setMetadata(movieMetadata)
                        .build();
            }

            @Override
            public void onFailure(Call<Model_Decrypted_Channed_Urls> call, Throwable t) {
                Toast.makeText(videoPlayer.this, "Server Error", Toast.LENGTH_SHORT).show();
            }
        });

        return decryptedUrl;
    }

    private String getMSNBC() {

        Call<Model_Decrypted_Channed_Urls> call = remoteClient.getMSNBC();
        call.enqueue(new Callback<Model_Decrypted_Channed_Urls>() {
            @Override
            public void onResponse(Call<Model_Decrypted_Channed_Urls> call, Response<Model_Decrypted_Channed_Urls> response) {
                MediaMetadata movieMetadata = new MediaMetadata(MediaMetadata.MEDIA_TYPE_MOVIE);
                decryptedUrl = response.body().getUrl();
                mSelectedMedia = new MediaInfo.Builder(decryptedUrl)
                        .setStreamType(MediaInfo.STREAM_TYPE_BUFFERED)
                        .setContentType("application/x-mpegURL")
                        .setMetadata(movieMetadata)
                        .build();
            }

            @Override
            public void onFailure(Call<Model_Decrypted_Channed_Urls> call, Throwable t) {
                Toast.makeText(videoPlayer.this, "Error getting Decrypted Url", Toast.LENGTH_SHORT).show();
            }
        });

        return decryptedUrl;
    }

    private void setupCastListener() {
        mSessionManagerListener = new SessionManagerListener<CastSession>() {

            @Override
            public void onSessionEnded(CastSession session, int error) {
                onApplicationDisconnected();
            }

            @Override
            public void onSessionResumed(CastSession session, boolean wasSuspended) {
                onApplicationConnected(session);
            }

            @Override
            public void onSessionResumeFailed(CastSession session, int error) {
                onApplicationDisconnected();
            }

            @Override
            public void onSessionStarted(CastSession session, String sessionId) {
                onApplicationConnected(session);
            }

            @Override
            public void onSessionStartFailed(CastSession session, int error) {
                onApplicationDisconnected();
            }

            @Override
            public void onSessionStarting(CastSession session) {
            }

            @Override
            public void onSessionEnding(CastSession session) {
            }

            @Override
            public void onSessionResuming(CastSession session, String sessionId) {
            }

            @Override
            public void onSessionSuspended(CastSession session, int reason) {
            }

            private void onApplicationConnected(CastSession castSession) {
                mCastSession = castSession;
                if (null != mSelectedMedia) {

                    if (mPlaybackState == PlaybackState.PLAYING) {
                        videoView.getPlayer().pause();
                        loadRemoteMedia(true);
                        return;
                    } else {
                        mPlaybackState = PlaybackState.IDLE;
                        updatePlaybackLocation(PlaybackLocation.REMOTE);
                    }
                }
                invalidateOptionsMenu();
            }

            private void onApplicationDisconnected() {
                updatePlaybackLocation(PlaybackLocation.LOCAL);
                mPlaybackState = PlaybackState.PLAYING;
                mLocation = PlaybackLocation.LOCAL;
                videoView.setVideoPath(urlchannel).getPlayer().start();
                invalidateOptionsMenu();
            }
        };
    }

    private void loadRemoteMedia(boolean autoPlay) {
        if (mCastSession == null) {
            return;
        }
        final RemoteMediaClient remoteMediaClient = mCastSession.getRemoteMediaClient();
        if (remoteMediaClient == null) {
            return;
        }
        remoteMediaClient.registerCallback(new RemoteMediaClient.Callback() {
            @Override
            public void onStatusUpdated() {
                remoteMediaClient.unregisterCallback(this);
            }
        });
        remoteMediaClient.load(new MediaLoadRequestData.Builder()
                .setMediaInfo(mSelectedMedia)
                .setAutoplay(autoPlay)
                .build());
    }

    private void updatePlaybackLocation(PlaybackLocation location) {
        mLocation = location;
        if (location == PlaybackLocation.LOCAL) {
            if (mPlaybackState == PlaybackState.PLAYING
                    || mPlaybackState == PlaybackState.BUFFERING) {
                startControllersTimer();
            } else {
                stopControllersTimer();
            }
        } else {
            stopControllersTimer();
            updateControllersVisibility(false);
        }
    }

    private void stopControllersTimer() {
        if (mControllersTimer != null) {
            mControllersTimer.cancel();
        }
    }

    private void startControllersTimer() {
        if (mControllersTimer != null) {
            mControllersTimer.cancel();
        }
        if (mLocation == PlaybackLocation.REMOTE) {
            return;
        }
        mControllersTimer = new Timer();
        mControllersTimer.schedule(new HideControllersTask(), 5000);
    }

    // should be called from the main thread
    private void updateControllersVisibility(boolean show) {
        if (show) {
//            getSupportActionBar().show();
            mControllers.setVisibility(View.VISIBLE);
        } else {
            if (!Utils.isOrientationPortrait(this)) {
                getSupportActionBar().hide();
            }
            mControllers.setVisibility(View.INVISIBLE);
        }
    }

    private class HideControllersTask extends TimerTask {

        @Override
        public void run() {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    updateControllersVisibility(false);
                    mControllersVisible = false;
                }
            });

        }
    }


    private BottomNavigationView.OnNavigationItemSelectedListener bnavlistener = new
            BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment fragment;
                    switch (menuItem.getItemId()) {
                        case R.id.navhome:
                            Intent i = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(i);
                            customType(videoPlayer.this, "fadein-to-fadeout");
                            finish();
                            break;
                        case R.id.navfav:
                            Intent fav = new Intent(getApplicationContext(), FavouritesActivity.class);
                            startActivity(fav);
                            customType(videoPlayer.this, "fadein-to-fadeout");
                            finish();
                            break;

                        case R.id.navsetting:
                            Intent setting = new Intent(getApplicationContext(), Settings.class);
                            startActivity(setting);
                            customType(videoPlayer.this, "fadein-to-fadeout");
                     finish();
                            break;

                    }


                    return false;
                }
            };


    public void fav_btn_validation() {

    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }


    private void getNewsList() {

        //ShowMessagesDialogsUtitly.showProgressDialog(mContext);
        Map<String, String> requestParams = new HashMap<String, String>();
        //   requestParams.put("api_key", ApiClient.API_KEY);
//        requestParams.put("pass_key", "12345");
//        requestParams.put("case", "show_comments");
        String url = "http://app.newslive.com/newslive/api/rel_channels";

        HttpUtils.httpGetRequestWithParam(mContext, url,
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
                                    releventModel model = new Gson()
                                            .fromJson(jsonObject.toString(),
                                                    releventModel.class);
                                    relatedarraylist.add(model);
                                }


                                mAdapter = new releventAdapter(mContext, relatedarraylist, new releventInterface() {
                                    @Override
                                    public void onCompleteEntries(releventModel item, int pos) {

                                        StorageUtil storage = new StorageUtil(mContext);


                                        Intent in = new Intent(mContext, videoPlayer.class);
                                        in.putExtra("pos", pos);
                                        in.putExtra("url", item.getStream_url());
                                        in.putExtra("name", item.getChannel_name());
                                        in.putExtra("img", item.getCh_image());
                                        in.putExtra("channel_id", item.getChannel_id());
                                        in.putExtra("channel_desc", item.getChannel_desc());
                                        in.putExtra("category_name", item.getCategory_name());
                                        in.putExtra("country_name", item.getCountry_name());
                                        in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        mContext.startActivity(in);
                                        customType(videoPlayer.this, "left-to-right");

                                    }
                                });
                                recycleViewraleted.setAdapter(mAdapter);


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        } else {

                            Toast.makeText(mContext, "Server issue, please try again later", Toast.LENGTH_LONG).show();
                        }
                    }

                });


    }

    private void favinsert() {


        // ShowMessagesDialogsUtitly.showProgressDialog(mContext);
        Map<String, String> requestParams = new HashMap<String, String>();
        requestParams.put("uID", Uid);
        requestParams.put("uemail", uemail);
        requestParams.put("cID", channel_id);


        HttpUtils.httpPostRequestWithParam("http://app.newslive.com/newslive/api/fav_insert",
                requestParams, new HttpResponseCallback() {
                    @Override
                    public void onCompleteHttpResponse(String whichUrl,
                                                       String jsonResponse) {
                        ShowMessagesDialogsUtitly.hideProgressDialog();

                        if (jsonResponse != null) {

                            try {
                                //  JSONArray jsonArray = new JSONArray(jsonResponse);
                                JSONObject obj = new JSONObject(jsonResponse);
                                String res = obj.getString("msg");
                                String stre = obj.getString("status");

                                if (stre.equals("1")) {
                                    Toast.makeText(mContext, res, Toast.LENGTH_SHORT).show();
                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        } else {

                            Toast.makeText(mContext, "Server issue, please try again later", Toast.LENGTH_LONG).show();
                        }
                    }

                });


    }

    private void checkfav() {
        // ShowMessagesDialogsUtitly.showProgressDialog(mContext);
        Map<String, String> requestParams = new HashMap<String, String>();
        requestParams.put("uID", Uid);

        requestParams.put("cID", channel_id);


        HttpUtils.httpPostRequestWithParam("http://app.newslive.com/newslive/api/fav_check",
                requestParams, new HttpResponseCallback() {
                    @Override
                    public void onCompleteHttpResponse(String whichUrl,
                                                       String jsonResponse) {
                        ShowMessagesDialogsUtitly.hideProgressDialog();

                        if (jsonResponse != null) {

                            try {
                                //  JSONArray jsonArray = new JSONArray(jsonResponse);
                                JSONObject obj = new JSONObject(jsonResponse);
                                String res = obj.getString("msg");
                                String stre = obj.getString("status");

                                if (stre.equals("2")) {
                                    fav_btn.setBackgroundResource(R.drawable.redheart);

                                } else {
                                    fav_btn.setBackgroundResource(heartbtn);

                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        } else {

                            Toast.makeText(mContext, "Server issue, please try again later", Toast.LENGTH_LONG).show();
                        }
                    }

                });


    }

    private void deletefav() {
        // ShowMessagesDialogsUtitly.showProgressDialog(mContext);
        Map<String, String> requestParams = new HashMap<String, String>();
        requestParams.put("uID", Uid);

        requestParams.put("cID", channel_id);


        HttpUtils.httpPostRequestWithParam("http://app.newslive.com/newslive/api/fav_delete",
                requestParams, new HttpResponseCallback() {
                    @Override
                    public void onCompleteHttpResponse(String whichUrl,
                                                       String jsonResponse) {
                        ShowMessagesDialogsUtitly.hideProgressDialog();

                        if (jsonResponse != null) {

                            try {
                                //  JSONArray jsonArray = new JSONArray(jsonResponse);
                                JSONObject obj = new JSONObject(jsonResponse);
                                String res = obj.getString("msg");
                                String stre = obj.getString("status");

                                if (stre.equals("3")) {
                                    fav_btn.setBackgroundResource(heartbtn);
                                    Toast.makeText(mActivity, res, Toast.LENGTH_SHORT).show();

                                } else {
                                    fav_btn.setBackgroundResource(redheart);

                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        } else {

                            Toast.makeText(mContext, "Server issue, please try again later", Toast.LENGTH_LONG).show();
                        }
                    }

                });


    }


    @Override
    public void finish() {
        customType(videoPlayer.this, "right-to-left");
        super.finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() was called");
        if (mLocation == videoPlayer.PlaybackLocation.LOCAL) {
            videoView.getPlayer().pause();
            mPlaybackState = PlaybackState.PLAYING;
        }
        mCastContext.getSessionManager().removeSessionManagerListener(
                mSessionManagerListener, CastSession.class);
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy() is called");
        stopControllersTimer();
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        Log.d(TAG, "onResume() was called");
        mCastContext.getSessionManager().addSessionManagerListener(
                mSessionManagerListener, CastSession.class);
        if (mCastSession != null && mCastSession.isConnected()) {
            updatePlaybackLocation(videoPlayer.PlaybackLocation.REMOTE);
        } else {
            updatePlaybackLocation(videoPlayer.PlaybackLocation.LOCAL);
        }
        if (mQueueMenuItem != null) {
            mQueueMenuItem.setVisible(
                    (mCastSession != null) && mCastSession.isConnected());
        }
        super.onResume();
    }
}
