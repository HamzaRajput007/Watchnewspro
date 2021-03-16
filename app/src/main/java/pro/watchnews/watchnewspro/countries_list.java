package pro.watchnews.watchnewspro;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import pro.watchnews.watchnewspro.adapters.CountriesAdapter;
import pro.watchnews.watchnewspro.helper.HttpUtils;
import pro.watchnews.watchnewspro.helper.Network;
import pro.watchnews.watchnewspro.helper.ShowMessagesDialogsUtitly;
import pro.watchnews.watchnewspro.interfaces.Countriesinterface;
import pro.watchnews.watchnewspro.interfaces.HttpResponseCallback;
import pro.watchnews.watchnewspro.model.CountriesModel;
import pro.watchnews.watchnewspro.ui.main.PageViewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link countries_list.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link countries_list#newInstance} factory method to
 * create an instance of this fragment.
 */
public class countries_list extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private PageViewModel pageViewModel;
    private RecyclerView recycleViewCountries;
    private ArrayList<CountriesModel> countryArrayList;
    private Context mContext;
    private CountriesAdapter cntAdapter;
    private int mColumnCount = 1;
    private String continent_id;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;

    public countries_list() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment countries_list.
     */
    // TODO: Rename and change types and number of parameters
    public static countries_list newInstance(String param1, String param2) {
        countries_list fragment = new countries_list();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root=inflater.inflate(R.layout.fragment_countries_list, container, false);
        Bundle bundle =getArguments();
         continent_id=bundle.getString("continent_id");
        mContext = getActivity();
        countryArrayList = new ArrayList<>();
        recycleViewCountries =root.findViewById(R.id.recycler_view_countries);
        // recycleViewContinents.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));

        recycleViewCountries.setItemAnimator(new DefaultItemAnimator());
        if (mColumnCount <= 1) {
            recycleViewCountries.setLayoutManager(new LinearLayoutManager(mContext));
        } else {
            recycleViewCountries.setLayoutManager(new GridLayoutManager(mContext, mColumnCount));
        }
        recycleViewCountries.setHasFixedSize(true);
        recycleViewCountries.setItemAnimator(new DefaultItemAnimator());
        // recycleViewContinents.addItemDecoration(new SpacesItemDecoration(20));
        mSwipeRefreshLayout = root.findViewById(R.id.swipe_container_clist);
        mSwipeRefreshLayout.setOnRefreshListener(this);



        mSwipeRefreshLayout.post(new Runnable() {

            @Override
            public void run() {

                mSwipeRefreshLayout.setRefreshing(true);

                // Fetching data from server
                getCountriesList();
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



        public void onCompletecountries(CountriesModel item, int pos) {

        }



    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    @Override
    public void onRefresh() {
        countryArrayList.clear();
        getCountriesList();

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

    private void getCountriesList() {
        String countries_url="http://app.newslive.com/newslive/api/all_countries_continent/"+continent_id+"";

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


        HttpUtils.httpGetRequestWithParam(mContext,countries_url,
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
                                    CountriesModel model = new Gson()
                                            .fromJson(jsonObject.toString(),
                                                    CountriesModel.class);
                                    countryArrayList.add(model);
                                }



                                cntAdapter = new CountriesAdapter(mContext, countryArrayList, new Countriesinterface() {
                                    @Override
                                    public void onCompletecountries(CountriesModel item, int pos) {
                                        FragmentTransaction fr=getFragmentManager().beginTransaction();

                                        fr.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                                        fr.addToBackStack(null);
                                        channels_co_ca channels_co_ca=new channels_co_ca();
                                        fr.replace(R.id.maincontainer,channels_co_ca);
                                        fr.commit();
                                        Toast.makeText(mContext, "this is country", Toast.LENGTH_SHORT).show();

                                    }
                                });
                                recycleViewCountries.setAdapter(cntAdapter);




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
