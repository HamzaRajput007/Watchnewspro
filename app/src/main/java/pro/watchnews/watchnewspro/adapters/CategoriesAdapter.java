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
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import pro.watchnews.watchnewspro.R;
import pro.watchnews.watchnewspro.interfaces.Categorysinterface;
import pro.watchnews.watchnewspro.model.CarouselItemModel;
import pro.watchnews.watchnewspro.model.CategoryModel;

import java.util.ArrayList;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.CategoriesViewHolder> implements Filterable {


    private Context context;
    ArrayList<CategoryModel> mAppointmentArray;
    private ArrayList<CategoryModel> mAppointmentSearchArray;
    private Categorysinterface mListInterface;


    private String baseimgurl="https://app.newslive.com/newslive/public/storage/radio_images/";
    private Animation scale;
    public CategoriesAdapter(Context context, ArrayList<CategoryModel> appoint_array, Categorysinterface listInterface) {
        this.context = context;
        this.mAppointmentArray = appoint_array;
        mAppointmentSearchArray = new ArrayList<>();
        this.mAppointmentSearchArray.addAll(appoint_array);
        mListInterface = listInterface;

    }

    @Override
    public CategoriesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.categoriesgrid, parent, false);
        scale = AnimationUtils.loadAnimation(view.getContext(), R.anim.scaleanimation);
        context=view.getContext();
        return new CategoriesViewHolder(view);

    }

    @Override
    public void onBindViewHolder(final CategoriesViewHolder holder, final int position) {


        holder.rq_list = mAppointmentArray.get(position);

        holder.continentname.setText(holder.rq_list.getCategory_name());
        String imgUrl=holder.rq_list.getCategory_image();
        imgUrl = baseimgurl+imgUrl;


        Glide.with(this.context).load(imgUrl)
                .thumbnail(0.5f)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .crossFade()

                .into(holder.categoryimg);


        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //mListInterface.onCompleteEntries(holder.rq_list,position);
                Toast.makeText(context, "I am Category", Toast.LENGTH_SHORT).show();
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

                    ArrayList<CategoryModel> filteredList = new ArrayList<>();

                    for (CategoryModel model : mAppointmentArray) {
                        String name = model.getCategory_name().toLowerCase();
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
                mAppointmentSearchArray = (ArrayList<CategoryModel>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class CategoriesViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView continentname;
        public ImageView categoryimg=null;
        public FrameLayout chgradient;
        public ImageView pbtnc;

        //public final TextView status;
        public CategoryModel rq_list;

        public CategoriesViewHolder(View view) {
            super(view);
            mView = view;
            continentname = (TextView) mView.findViewById(R.id.categoryname);
            categoryimg = (ImageView) mView.findViewById(R.id.categoryimage);
            chgradient = (FrameLayout) mView.findViewById(R.id.cabg_gradient);
            pbtnc=   (ImageView) mView.findViewById(R.id.playbtng);
            // status = (TextView) mView.findViewById(R.id.appointy_status);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + continentname.getText() + "'";
        }
    }

}
