package pro.watchnews.watchnewspro;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;


import com.google.gson.Gson;

import pro.watchnews.watchnewspro.adapters.ChannelAdapter;
import pro.watchnews.watchnewspro.helper.AppConstants;
import pro.watchnews.watchnewspro.helper.HttpUtils;
import pro.watchnews.watchnewspro.helper.Network;
import pro.watchnews.watchnewspro.helper.ShowMessagesDialogsUtitly;
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

import static maes.tech.intentanim.CustomIntent.customType;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link channelFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link channelFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class channelFragment extends Fragment implements  SwipeRefreshLayout.OnRefreshListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private  SwipeRefreshLayout mSwipeRefreshLayout;
    private PageViewModel pageViewModel;
    private RecyclerView recycleViewRadios;
    private ArrayList<ChannelModel> radioArrayList;
    private Context mContext;
    private int mColumnCount = 3;
    private ChannelAdapter mAdapter;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
private ImageButton errorbtn;
    private OnFragmentInteractionListener mListener;

    public channelFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment channelFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static channelFragment newInstance(String param1, String param2) {
        channelFragment fragment = new channelFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


        //adapter=new GridviewAdapterChannels(getApplicationContext(),listChannels);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_channel, container, false);
        mContext = getActivity();
        radioArrayList = new ArrayList<>();
        recycleViewRadios =root.findViewById(R.id.recycler_view);
       // recycleViewRadios.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
errorbtn= root.findViewById(R.id.errorbtn);
        recycleViewRadios.setItemAnimator(new DefaultItemAnimator());
        if (mColumnCount <= 1) {
            recycleViewRadios.setLayoutManager(new LinearLayoutManager(mContext));
        } else {
            recycleViewRadios.setLayoutManager(new GridLayoutManager(mContext, mColumnCount));
        }
        recycleViewRadios.setHasFixedSize(true);
        recycleViewRadios.setItemAnimator(new DefaultItemAnimator());
       // recycleViewRadios.addItemDecoration(new SpacesItemDecoration(20));
        mSwipeRefreshLayout = root.findViewById(R.id.swipe_container_countries);
        mSwipeRefreshLayout.setOnRefreshListener(this);



        mSwipeRefreshLayout.post(new Runnable() {

            @Override
            public void run() {

                mSwipeRefreshLayout.setRefreshing(true);

                // Fetching data from server
                getNewsList();
            }
        });

        return root;


    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onRefresh() {
     radioArrayList.clear();
getNewsList();

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


    private void getNewsList() {


        if (!Network.haveNetworkConnection(mContext)) {
            Toast.makeText(mContext, getString(R.string.nointernet), Toast.LENGTH_LONG).show();
            mSwipeRefreshLayout.setRefreshing(false);
            errorbtn.setVisibility(View.VISIBLE);
            errorbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent in = new Intent(getActivity(), MainActivity.class);
                    getActivity().startActivity(in);
                }
            });
            return;
        }

     mSwipeRefreshLayout.setRefreshing(true);
        //ShowMessagesDialogsUtitly.showProgressDialog(mContext);
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

                                        Intent in = new Intent(getActivity(), videoPlayer.class);
                                        in.putExtra("pos", pos);
                                        in.putExtra("url", item.getStream_url());
                                        in.putExtra("casturl", item.getDecoded_stream());
                                        in.putExtra("name", item.getChannel_name());
                                        in.putExtra("img", item.getCh_image());
                                        in.putExtra("alternate_url" , item.getAlternate_url());
                                        in.putExtra("channel_id", item.getChannel_id());
                                        in.putExtra("channel_desc", item.getChannel_desc());
                                        in.putExtra("country_name", item.getCountry_name());
                                        in.putExtra("category_name", item.getCategory_name());
                                        getActivity().startActivity(in);
                                        customType(getActivity(),"right-to-left");

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

}
