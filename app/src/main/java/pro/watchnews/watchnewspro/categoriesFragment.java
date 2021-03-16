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
import android.widget.Toast;

import com.google.gson.Gson;
import pro.watchnews.watchnewspro.adapters.CategoriesAdapter;
import pro.watchnews.watchnewspro.helper.HttpUtils;
import pro.watchnews.watchnewspro.helper.ShowMessagesDialogsUtitly;
import pro.watchnews.watchnewspro.interfaces.Categorysinterface;
import pro.watchnews.watchnewspro.interfaces.HttpResponseCallback;
import pro.watchnews.watchnewspro.model.CategoryModel;
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
 * {@link categoriesFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link categoriesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class categoriesFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private PageViewModel pageViewModel;
    private RecyclerView recycleViewcategorys;
    private ArrayList<CategoryModel> categoryArrayList;
    private Context mContext;
    private CategoriesAdapter catAdapter;
    private int mColumnCount = 3;
    private String categorys_url="http://app.newslive.com/newslive/api/all_categories";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public categoriesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment categoriesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static categoriesFragment newInstance(String param1, String param2) {
        categoriesFragment fragment = new categoriesFragment();
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
        View root=inflater.inflate(R.layout.fragment_categories, container, false);
        categoryArrayList = new ArrayList<>();
        recycleViewcategorys =root.findViewById(R.id.recycler_view_categories);
        // recycleViewcategorys.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));

        recycleViewcategorys.setItemAnimator(new DefaultItemAnimator());
        if (mColumnCount <= 1) {
            recycleViewcategorys.setLayoutManager(new LinearLayoutManager(mContext));
        } else {
            recycleViewcategorys.setLayoutManager(new GridLayoutManager(mContext, mColumnCount));
        }
        recycleViewcategorys.setHasFixedSize(true);
        recycleViewcategorys.setItemAnimator(new DefaultItemAnimator());
        // recycleViewcategorys.addItemDecoration(new SpacesItemDecoration(20));
        mSwipeRefreshLayout = root.findViewById(R.id.swipe_container_cat);
        mSwipeRefreshLayout.setOnRefreshListener(this);



        mSwipeRefreshLayout.post(new Runnable() {

            @Override
            public void run() {

                mSwipeRefreshLayout.setRefreshing(true);

                // Fetching data from server
                getcategorysList();
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
        categoryArrayList.clear();
        getcategorysList();

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
    private void getcategorysList() {


//        if (!Network.haveNetworkConnection(mContext)) {
//            Toast.makeText(mContext, getString(R.string.nointernet), Toast.LENGTH_LONG).show();
//            mSwipeRefreshLayout.setRefreshing(false);
//            return;
//        }

        mSwipeRefreshLayout.setRefreshing(true);
        //ShowMessagesDialogsUtitly.showProgressDialog(mContext);
        Map<String, String> requestParams = new HashMap<String, String>();
        //   requestParams.put("api_key", ApiClient.API_KEY);
//        requestParams.put("pass_key", "12345");
//        requestParams.put("case", "show_comments");


        HttpUtils.httpGetRequestWithParam(mContext,categorys_url,
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
                                    CategoryModel model = new Gson()
                                            .fromJson(jsonObject.toString(),
                                                    CategoryModel.class);
                                    categoryArrayList.add(model);
                                }



                                catAdapter = new CategoriesAdapter(mContext, categoryArrayList, new Categorysinterface() {
                                    @Override
                                    public void onCompletecntries(CategoryModel item, int pos) {



                                        Intent in = new Intent(getActivity(), countriesFragment.class);
                                        in.putExtra("pos", pos);
                                        in.putExtra("name", item.getCategory_name());
                                        in.putExtra("img", item.getCategory_image());
                                        getActivity().startActivity(in);
                                    }
                                });
                                recycleViewcategorys.setAdapter(catAdapter);




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
