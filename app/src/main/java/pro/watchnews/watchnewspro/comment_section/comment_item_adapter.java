package pro.watchnews.watchnewspro.comment_section;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import pro.watchnews.watchnewspro.R;

import java.util.ArrayList;

public class comment_item_adapter extends RecyclerView.Adapter<comment_item_adapter.ViewHolder> {

    Activity activity;
    ArrayList<comment_item_model> arraylist;
    LayoutInflater inflater;

    public comment_item_adapter(Activity activity, ArrayList<comment_item_model> arraylist) {
        this.activity = activity;
        this.arraylist = arraylist;
        this.inflater = activity.getLayoutInflater();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.comment_items,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        comment_item_model model=arraylist.get(position);
        holder.textView_name.setText(model.getTextView_name());
        holder.textView_comment.setText(model.getTextView_comment());
        holder.imageView_avatar.setImageResource(model.imageView_avatar);

    }

    @Override
    public int getItemCount() {
        return arraylist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textView_name,textView_comment;
        ImageView imageView_avatar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView_name=itemView.findViewById(R.id.textView_name);
            textView_comment=itemView.findViewById(R.id.textView_comment);
            imageView_avatar=itemView.findViewById(R.id.imageView_avatar);

        }
    }
}
