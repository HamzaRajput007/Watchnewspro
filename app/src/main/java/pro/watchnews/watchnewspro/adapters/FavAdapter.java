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
import pro.watchnews.watchnewspro.interfaces.FavInterface;
import pro.watchnews.watchnewspro.model.FavModel;

import java.util.ArrayList;

public class FavAdapter extends RecyclerView.Adapter<FavAdapter.ViewHolder> implements Filterable {


    private Context context = null;
    ArrayList<FavModel> mAppointmentArray;
    private ArrayList<FavModel> mAppointmentSearchArray;
    private FavInterface mListInterface;
    private Context mContext;

  private String baseimgurl="https://app.newslive.com/newslive/public/storage/radio_images/";
private Animation scale;
    public FavAdapter(Context context, ArrayList<FavModel> appoint_array, FavInterface listInterface) {
        this.context = context;
        this.mAppointmentArray = appoint_array;
        mAppointmentSearchArray = new ArrayList<>();
        this.mAppointmentSearchArray.addAll(appoint_array);
        mListInterface = listInterface;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.countrieslistviewlayout, parent, false);
        scale = AnimationUtils.loadAnimation(view.getContext(), R.anim.scaleanimation);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {


        holder.rq_list = mAppointmentArray.get(position);

        holder.textViewName.setText(holder.rq_list.getChannel_name());
       String imgUrl=holder.rq_list.getCh_image();
       imgUrl = baseimgurl+imgUrl;


        Glide.with(this.context).load(imgUrl)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .thumbnail(0.5f)
                .crossFade()
                .into(holder.channelimg);


        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               mListInterface.onCompleteEntries(holder.rq_list,position);
              //  Toast.makeText(context, "I am Channel", Toast.LENGTH_SHORT).show();
            }
        });
        holder.mView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){

                    holder.chgradient.setBackground(ContextCompat.getDrawable(context, R.drawable.gradienthover));
                } if(event.getAction() == MotionEvent.ACTION_UP){
                    holder.chgradient.setBackground(ContextCompat.getDrawable(context, R.drawable.pathgradient));
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

                    ArrayList<FavModel> filteredList = new ArrayList<>();

                    for (FavModel model : mAppointmentArray) {
                        String name = model.getChannel_name().toLowerCase();
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
                mAppointmentSearchArray = (ArrayList<FavModel>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView textViewName;
        public ImageView channelimg=null;
        public FrameLayout chgradient;
        public ImageView pbtnc;
        //public final TextView status;

        public FavModel rq_list;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            textViewName = (TextView) mView.findViewById(R.id.clist_name);
            channelimg = (ImageView) mView.findViewById(R.id.clist_img);
            chgradient = (FrameLayout) mView.findViewById(R.id.cbg_gradient);

            // status = (TextView) mView.findViewById(R.id.appointy_status);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + textViewName.getText() + "'";
        }
    }

}
