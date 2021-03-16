package pro.watchnews.watchnewspro.adapters;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingFlowParams;
import com.android.billingclient.api.SkuDetails;

import java.util.ArrayList;
import java.util.List;

import pro.watchnews.watchnewspro.R;
import pro.watchnews.watchnewspro.model.CarouselItemModel;

public class SubscriptionCarouselAdapter extends RecyclerView.Adapter<SubscriptionCarouselAdapter.SubscriptionViewHolder> {

    ArrayList<CarouselItemModel> carouselItemModels;
    Context mContext;
    OnSubscriptionClicked onSubscriptionClicked;

    AppCompatActivity appCompatActivity;
    List<SkuDetails> skuDetailsList;
    BillingClient billingClient;

    public SubscriptionCarouselAdapter(AppCompatActivity appCompatActivity, List<SkuDetails> skuDetailsList, BillingClient billingClient) {
        this.appCompatActivity = appCompatActivity;
        this.skuDetailsList = skuDetailsList;
        this.billingClient = billingClient;
    }

    public SubscriptionCarouselAdapter(ArrayList<CarouselItemModel> carouselItemModels, Context mContext, OnSubscriptionClicked onSubscriptionClicked) {
        this.carouselItemModels = carouselItemModels;
        this.mContext = mContext;
        this.onSubscriptionClicked = onSubscriptionClicked;
    }


    @NonNull
    @Override
    public SubscriptionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.carousel_item_layout, parent, false);
        SubscriptionViewHolder subscriptionViewHolder = new SubscriptionViewHolder(view, onSubscriptionClicked);
        return subscriptionViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SubscriptionViewHolder holder, int position) {

        holder.periodText.setText(skuDetailsList.get(position).getTitle());
//        holder.discountText.setText(carouselItemModels.get(position).getDiscountText());
        holder.priceText.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        holder.priceText.setText(skuDetailsList.get(position).getPrice());
        /*if (carouselItemModels.get(position).getNowText().equals("")){
            holder.nowText.setVisibility(View.GONE);
            holder.lineView.setVisibility(View.VISIBLE);
        }*/
//        holder.nowText.setText(carouselItemModels.get(position).getNowText());
        holder.chargesText.setText(skuDetailsList.get(position).getOriginalPrice());
//        holder.bottomPeriodText.setText(carouselItemModels.get(position).getBottomPeriodText());

    }

    @Override
    public int getItemCount() {
//        Toast.makeText(mContext, "List Size : " + skuDetailsList.size(), Toast.LENGTH_SHORT).show();
        return skuDetailsList.size();
    }

    public class SubscriptionViewHolder extends RecyclerView.ViewHolder implements View.OnTouchListener {
        TextView periodText, discountText, priceText, nowText, chargesText, bottomPeriodText;
        View lineView;
        Button subscribeBtn;
        OnSubscriptionClicked onSubscriptionClicked;

        public SubscriptionViewHolder(@NonNull View itemView, OnSubscriptionClicked onSubscriptionClicked) {
            super(itemView);
            periodText = itemView.findViewById(R.id.periondTextId);
//            discountText = itemView.findViewById(R.id.discountTextId);
            priceText = itemView.findViewById(R.id.priceText);
//            nowText = itemView.findViewById(R.id.nowTextId);
            chargesText = itemView.findViewById(R.id.chargestTextId);
//            bottomPeriodText = itemView.findViewById(R.id.forPeriodTextViewID);
            lineView = itemView.findViewById(R.id.lineSaprationViewId);
            subscribeBtn = itemView.findViewById(R.id.subscriptbNowBtnId);
            this.onSubscriptionClicked = onSubscriptionClicked;
            subscribeBtn.setOnTouchListener(this);

        }

        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN:
//                    subscribeBtn.setBackground(mContext.getResources().getDrawable(R.drawable.subscribe_btn_bg_white_2));
//                    subscribeBtn.setTextColor(mContext.getResources().getColor(R.color.white));
                    break;
                case MotionEvent.ACTION_UP:

                    Animation blink = AnimationUtils.loadAnimation(mContext , R.anim.blink);
                    subscribeBtn.startAnimation(blink);
                    BillingFlowParams billingFlowParams = BillingFlowParams.newBuilder()
                            .setSkuDetails(skuDetailsList.get(getAdapterPosition()))
                            .build();
                    int response = billingClient.launchBillingFlow(appCompatActivity, billingFlowParams)
                            .getResponseCode();
                    switch (response){
                        case BillingClient.BillingResponseCode.BILLING_UNAVAILABLE:
                            Toast.makeText(mContext, "BILLING_UNAVAILABLE", Toast.LENGTH_SHORT).show();
                            break;
                        case BillingClient.BillingResponseCode.DEVELOPER_ERROR:
                            Toast.makeText(mContext, "DEVELOPER_ERROR", Toast.LENGTH_SHORT).show();
                            break;

                        case BillingClient.BillingResponseCode.FEATURE_NOT_SUPPORTED:
                            Toast.makeText(mContext, "FEATURE_NOT_SUPPORTED", Toast.LENGTH_SHORT).show();
                            break;

                        case BillingClient.BillingResponseCode.ITEM_ALREADY_OWNED:
                            Toast.makeText(mContext, "ITEM_ALREADY_OWNED", Toast.LENGTH_SHORT).show();
                            break;

                        case BillingClient.BillingResponseCode.SERVICE_DISCONNECTED:
                            Toast.makeText(mContext, "SERVICE_DISCONNECTED", Toast.LENGTH_SHORT).show();
                            break;

                        case BillingClient.BillingResponseCode.SERVICE_TIMEOUT:
                            Toast.makeText(mContext, "SERVICE_TIMEOUT", Toast.LENGTH_SHORT).show();
                            break;

                        case BillingClient.BillingResponseCode.ITEM_UNAVAILABLE:
                            Toast.makeText(mContext, "ITEM_UNAVAILABLE", Toast.LENGTH_SHORT).show();
                            break;

                        default:
                            Toast.makeText(mContext, "DEFAULT_ERROR_CALLED", Toast.LENGTH_SHORT).show();

                    }

//                    subscribeBtn.setBackground(mContext.getResources().getDrawable(R.drawable.subscribe_btn_bg_2));
//                    subscribeBtn.setTextColor(mContext.getResources().getColor(R.color.white));
                    onSubscriptionClicked.onSubscribeClicked(getAdapterPosition());
                    break;
            }
            return false;
        }
    }

    public static interface OnSubscriptionClicked {
        public void onSubscribeClicked(int position);
    }

}
