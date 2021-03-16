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
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import pro.watchnews.watchnewspro.R;
import pro.watchnews.watchnewspro.interfaces.Continentsinterface;
import pro.watchnews.watchnewspro.model.ContinentModel;

import java.util.ArrayList;

public class ContinentAdapter extends RecyclerView.Adapter<ContinentAdapter.ContinentViewHolder> implements Filterable {


    private Context context = null;
    ArrayList<ContinentModel> mAppointmentArray;
    private ArrayList<ContinentModel> mAppointmentSearchArray;
    private Continentsinterface mListInterface;
    private Context mContext;

    private String baseimgurl="https://app.newslive.com/newslive/public/storage/radio_images/";
    private Animation scale;
    public ContinentAdapter(Context context, ArrayList<ContinentModel> appoint_array, Continentsinterface listInterface) {
        this.context = context;
        this.mAppointmentArray = appoint_array;
        mAppointmentSearchArray = new ArrayList<>();
        this.mAppointmentSearchArray.addAll(appoint_array);
        mListInterface = listInterface;

    }

    @Override
    public ContinentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.continentgrid, parent, false);
        scale = AnimationUtils.loadAnimation(view.getContext(), R.anim.scaleanimation);

        return new ContinentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ContinentViewHolder holder, final int position) {


        holder.rq_list = mAppointmentArray.get(position);

        holder.continentname.setText(holder.rq_list.getContinent_name());
        String imgUrl=holder.rq_list.getContinent_image();
        imgUrl = baseimgurl+imgUrl;


        Glide.with(this.context).load(imgUrl)
                .thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.continentimg);


        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mListInterface.onCompletecntries(holder.rq_list,position);

            }
        });
        holder.mView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    holder.mView.startAnimation(scale);
                    holder.chgradient.setBackground(ContextCompat.getDrawable(context, R.drawable.gradienthover));
                } if(event.getAction() == MotionEvent.ACTION_UP){
                    holder.chgradient.setBackground(ContextCompat.getDrawable(context, R.drawable.continentgradient));
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

                    ArrayList<ContinentModel> filteredList = new ArrayList<>();

                    for (ContinentModel model : mAppointmentArray) {
                        String name = model.getContinent_name().toLowerCase();
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
                mAppointmentSearchArray = (ArrayList<ContinentModel>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ContinentViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView continentname;
        public ImageView continentimg=null;
        public FrameLayout chgradient;
        public ImageView pbtnc;
        //public final TextView status;

        public ContinentModel rq_list;

        public ContinentViewHolder(View view) {
            super(view);
            mView = view;
            continentname = (TextView) mView.findViewById(R.id.continentname);
            continentimg = (ImageView) mView.findViewById(R.id.continentimage);
            chgradient = (FrameLayout) mView.findViewById(R.id.cbg_gradient);
            pbtnc=   (ImageView) mView.findViewById(R.id.playbtng);
            // status = (TextView) mView.findViewById(R.id.appointy_status);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + continentname.getText() + "'";
        }
    }

}
