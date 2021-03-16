package pro.watchnews.watchnewspro.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import pro.watchnews.watchnewspro.R;
import pro.watchnews.watchnewspro.adapters.ChannelAdapter;
import pro.watchnews.watchnewspro.channelFragment;
import pro.watchnews.watchnewspro.helper.AppConstants;
import pro.watchnews.watchnewspro.helper.HttpUtils;
import pro.watchnews.watchnewspro.helper.Network;
import pro.watchnews.watchnewspro.helper.ShowMessagesDialogsUtitly;
import pro.watchnews.watchnewspro.helper.SpacesItemDecoration;
import pro.watchnews.watchnewspro.interfaces.HttpResponseCallback;
import pro.watchnews.watchnewspro.interfaces.NewsListInterface;
import pro.watchnews.watchnewspro.model.ChannelModel;
import pro.watchnews.watchnewspro.ui.main.PageViewModel;
import pro.watchnews.watchnewspro.utill.StorageUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ChannelsMain extends Fragment {

    private PageViewModel pageViewModel;
    private RecyclerView recycleViewRadios;
    private ArrayList<ChannelModel> radioArrayList;
    private Context mContext;
    private int mColumnCount = 1;
    private ChannelAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_channel, container, false);
        mContext = getActivity();
        radioArrayList = new ArrayList<>();
        recycleViewRadios = root.findViewById(R.id.recycler_view);
        recycleViewRadios.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));

        recycleViewRadios.setItemAnimator(new DefaultItemAnimator());
        if (mColumnCount <= 1) {
            recycleViewRadios.setLayoutManager(new LinearLayoutManager(mContext));
        } else {
            recycleViewRadios.setLayoutManager(new GridLayoutManager(mContext, mColumnCount));
        }
        recycleViewRadios.setHasFixedSize(true);
        recycleViewRadios.setItemAnimator(new DefaultItemAnimator());
        recycleViewRadios.addItemDecoration(new SpacesItemDecoration(20));

        getNewsList();
        return root;

    }
    private void getNewsList() {
        if (!Network.haveNetworkConnection(mContext)) {
            Toast.makeText(mContext, getString(R.string.nointernet), Toast.LENGTH_LONG).show();
            return;
        }


        ShowMessagesDialogsUtitly.showProgressDialog(mContext);
        Map<String, String> requestParams = new HashMap<String, String>();
        //   requestParams.put("api_key", ApiClient.API_KEY);
//        requestParams.put("pass_key", "12345");
//        requestParams.put("case", "show_comments");


        HttpUtils.httpGetRequestWithParam(mContext, AppConstants.BASE_URL,
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
                                    ChannelModel model = new Gson()
                                            .fromJson(jsonObject.toString(),
                                                    ChannelModel.class);
                                    radioArrayList.add(model);
                                }



                                mAdapter = new ChannelAdapter(mContext, radioArrayList, new NewsListInterface() {
                                    @Override
                                    public void onCompleteEntries(ChannelModel item, int pos) {

                                        StorageUtil storage = new StorageUtil(mContext);
                                        storage.storeAudio(radioArrayList);
                                        storage.storeAudioIndex(pos);

                                        Intent in = new Intent(getActivity(), channelFragment.class);
                                        in.putExtra("pos", pos);
                                        in.putExtra("url", item.getStream_url());
                                        in.putExtra("name", item.getChannel_name());
                                        getActivity().startActivity(in);
                                    }
                                });
                                recycleViewRadios.setAdapter(mAdapter);


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        } else {

                            Toast.makeText(mContext, "Server issue, please try again later", Toast.LENGTH_LONG).show();
                        }
                    }

                });


    }
}