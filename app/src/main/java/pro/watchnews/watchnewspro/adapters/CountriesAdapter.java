package pro.watchnews.watchnewspro.adapters;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import pro.watchnews.watchnewspro.R;
import pro.watchnews.watchnewspro.interfaces.Countriesinterface;
import pro.watchnews.watchnewspro.model.CountriesModel;

import java.util.ArrayList;

public class CountriesAdapter extends RecyclerView.Adapter<CountriesAdapter.CountriesViewHolder> implements Filterable {


    private Context context = null;
    ArrayList<CountriesModel> mAppointmentArray;
    private ArrayList<CountriesModel> mAppointmentSearchArray;
    private Countriesinterface mListInterface;
    private Context mContext;

    private String baseimgurl="https://app.newslive.com/newslive/public/storage/radio_images/";
    private Animation scale;
    public CountriesAdapter(Context context, ArrayList<CountriesModel> appoint_array, Countriesinterface listInterface) {
        this.context = context;
        this.mAppointmentArray = appoint_array;
        mAppointmentSearchArray = new ArrayList<>();
        this.mAppointmentSearchArray.addAll(appoint_array);
        mListInterface = listInterface;

    }

    @Override
    public CountriesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.countrieslistviewlayout, parent, false);
        scale = AnimationUtils.loadAnimation(view.getContext(), R.anim.scaleanimation);

        return new CountriesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final CountriesViewHolder holder, final int position) {


        holder.rq_list = mAppointmentArray.get(position);

        holder.countryname.setText(holder.rq_list.getCountries_name());
        String imgUrl=holder.rq_list.getCountries_image();
        imgUrl = baseimgurl+imgUrl;


        Glide.with(this.context).load(imgUrl)
                .thumbnail(0.1f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .into(holder.countryimg);


        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mListInterface.onCompletecountries(holder.rq_list,position);

            }
        });
        holder.mView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    holder.mView.startAnimation(scale);
                    holder.chgradient.setBackground(ContextCompat.getDrawable(context, R.color.colorAccent));

                } if(event.getAction() == MotionEvent.ACTION_UP){
                    holder.chgradient.setBackground(ContextCompat.getDrawable(context, R.color.colorwhite));
                }
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mAppointmentSearchArray.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String charString = charSequence.toString();

                if (charString.isEmpty()) {

                    mAppointmentSearchArray = mAppointmentArray;
                } else {

                    ArrayList<CountriesModel> filteredList = new ArrayList<>();

                    for (CountriesModel model : mAppointmentArray) {
                        String name = model.getCountries_name().toLowerCase();
                        if (name.contains(charString)) {
                            filteredList.add(model);
                        }
                    }

                    mAppointmentSearchArray = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mAppointmentSearchArray;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mAppointmentSearchArray = (ArrayList<CountriesModel>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class CountriesViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView countryname;
        public ImageView countryimg=null;
        public RelativeLayout chgradient;
        public ImageView pbtnc;
        //public final TextView status;

        public CountriesModel rq_list;

        public CountriesViewHolder(View view) {
            super(view);
            mView = view;
            countryname = (TextView) mView.findViewById(R.id.clist_name);
            countryimg = (ImageView) mView.findViewById(R.id.clist_img);
            chgradient = (RelativeLayout) mView.findViewById(R.id.clist);
            // status = (TextView) mView.findViewById(R.id.appointy_status);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + countryname.getText() + "'";
        }
    }

}
