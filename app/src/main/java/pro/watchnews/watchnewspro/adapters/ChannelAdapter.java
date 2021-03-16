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
import pro.watchnews.watchnewspro.interfaces.NewsListInterface;
import pro.watchnews.watchnewspro.model.ChannelModel;

import java.util.ArrayList;

public class ChannelAdapter extends RecyclerView.Adapter<ChannelAdapter.ViewHolder> implements Filterable {


    private Context context = null;
    ArrayList<ChannelModel> mAppointmentArray;
    private ArrayList<ChannelModel> mAppointmentSearchArray;
    private NewsListInterface mListInterface;
    private Context mContext;

  private String baseimgurl="https://app.newslive.com/newslive/public/storage/radio_images/";
private Animation scale;
    public ChannelAdapter(Context context, ArrayList<ChannelModel> appoint_array, NewsListInterface listInterface) {
        this.context = context;
        this.mAppointmentArray = appoint_array;
        mAppointmentSearchArray = new ArrayList<>();
        this.mAppointmentSearchArray.addAll(appoint_array);
        mListInterface = listInterface;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.gridviewlayout, parent, false);
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

                    ArrayList<ChannelModel> filteredList = new ArrayList<>();

                    for (ChannelModel model : mAppointmentArray) {
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
                mAppointmentSearchArray = (ArrayList<ChannelModel>) filterResults.values;
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

        public ChannelModel rq_list;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            textViewName = (TextView) mView.findViewById(R.id.channelname);
            channelimg = (ImageView) mView.findViewById(R.id.channelimage);
            chgradient = (FrameLayout) mView.findViewById(R.id.cbg_gradient);
            pbtnc=   (ImageView) mView.findViewById(R.id.playbtng);
            // status = (TextView) mView.findViewById(R.id.appointy_status);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + textViewName.getText() + "'";
        }
    }

}
