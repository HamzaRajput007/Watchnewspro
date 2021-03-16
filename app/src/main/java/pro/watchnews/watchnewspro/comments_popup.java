package pro.watchnews.watchnewspro;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import pro.watchnews.watchnewspro.comment_section.comment_item_adapter;
import pro.watchnews.watchnewspro.comment_section.comment_item_model;

import java.util.ArrayList;

public class comments_popup extends Fragment {
        ImageView imageView_back;
    RecyclerView recyclerView_comment;
    Animation comment_popup_animation;
    float x1, x2, y1,y2;

    RelativeLayout relativeLayout;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_comments_popup,container,false);
        recyclerView_comment=v.findViewById(R.id.recyclerView_comment);
        imageView_back=v.findViewById(R.id.imageView_back);
        relativeLayout=v.findViewById(R.id.relativeLayout);

        //load animation
        comment_popup_animation= AnimationUtils.loadAnimation(getActivity(),R.anim.comment_popup_animation);

        //passing animation
        relativeLayout.startAnimation(comment_popup_animation);

        imageView_back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "back is clicked", Toast.LENGTH_SHORT).show();
                getFragmentManager().beginTransaction().remove(comments_popup.this).commit();


            }
            });



        ArrayList<comment_item_model> arrayList=new ArrayList<>();

        comment_item_model model=new comment_item_model(R.drawable.ic_launcher_background,"Waleed Ahmad","Waoo! This is one big shot...");
        arrayList.add(model);


        recyclerView_comment.setLayoutManager(new LinearLayoutManager(getActivity()));

        comment_item_adapter adapter=new comment_item_adapter(getActivity(),arrayList);
        recyclerView_comment.setAdapter(adapter);

        return v;

    }
        public void dismiss(){
}


    public boolean onTouchEvent(MotionEvent touchEvent){
        switch(touchEvent.getAction()){
            case MotionEvent.ACTION_DOWN:
                x1 = touchEvent.getX();
                y1 = touchEvent.getY();
                getFragmentManager().beginTransaction().remove(comments_popup.this).commit();

                break;
        }
        return false;
    }
}
