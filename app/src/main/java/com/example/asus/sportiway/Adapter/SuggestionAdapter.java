package com.example.asus.sportiway.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.asus.sportiway.Activity.EventDetailActivity;
import com.example.asus.sportiway.Model.ModelEvent;
import com.example.asus.sportiway.Preferences.SessionManager;
import com.example.asus.sportiway.R;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Asus on 06/09/2017.
 */

public class SuggestionAdapter extends RecyclerView.Adapter<SuggestionAdapter.ViewHolder>{

    private List<ModelEvent.EventBean> mEventses;
    private Context mContext;

    public SuggestionAdapter(Context context, List<ModelEvent.EventBean> eventses) {
        mEventses = eventses;
        mContext = context;
    }

    // Easy access to the context object in the recyclerview
    private Context getContext() {
        return mContext;
    }

    @Override
    public SuggestionAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.item_event, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(SuggestionAdapter.ViewHolder viewHolder, final int position) {
        // Get the data model based on position

        // Set item views based on your views and data model
        TextView txtNama = viewHolder.txtNama;
        txtNama.setText(mEventses.get(position).getName());
        TextView txtDate = viewHolder.txtDate;
        txtDate.setText(mEventses.get(position).getDate());
        TextView txtQuota = viewHolder.txtQuota;
        if(mEventses.get(position).getQuota() == 0){
            txtQuota.setText("Unlimited");
            txtQuota.setTextColor(getContext().getResources().getColor(R.color.red));
        } else {
            txtQuota.setText(Integer.toString(mEventses.get(position).getQuota()) + " Person");
        }
        TextView txtTime = viewHolder.txtTime;
        txtTime.setText(mEventses.get(position).getTime());
        TextView txtAddress = viewHolder.txtAddress;
        txtAddress.setText(mEventses.get(position).getAddress());
        TextView txtPrice = viewHolder.txtPrice;
        if(mEventses.get(position).getPrice() == 0){
            txtPrice.setText("Free");
            txtPrice.setTextColor(getContext().getResources().getColor(R.color.ijo));
        } else {
            txtPrice.setText(Integer.toString(mEventses.get(position).getPrice()));
        }

        CardView cvEvent = viewHolder.cvEvent;
        cvEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SessionManager sessionManager = new SessionManager(getContext());
                HashMap<String, String> user = sessionManager.getUserDetails();
                Intent intent = new Intent(getContext(), EventDetailActivity.class);
                intent.putExtra("eventId", mEventses.get(position).getEventId());
                intent.putExtra("eventRequestId", user.get(SessionManager.KEY_EVENT_ID));
                intent.putExtra("type", "merge");
                getContext().startActivity(intent);
            }
        });

        ImageView imgGender = viewHolder.imgGender;
        switch (mEventses.get(position).getGender()){
            case "Male":
                imgGender.setImageResource(R.drawable.ic_gender_male);
                break;
            case "Female":
                imgGender.setImageResource(R.drawable.ic_gender_female);
                break;
            default:
                imgGender.setImageResource(R.drawable.ic_gender_both);
                break;
        }

        ImageView imgType = viewHolder.imgType;
        switch (mEventses.get(position).getType()){
            case "Sepak Bola":
                imgType.setImageResource(R.drawable.bola);
                break;
            case "Jogging":
                imgType.setImageResource(R.drawable.sepatu);
                break;
            case "Bulu Tangkis":
                imgType.setImageResource(R.drawable.bultang);
                break;
            default:
                imgType.setImageResource(R.drawable.basket);
                break;
        }
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return mEventses.size();
    }



    public static class ViewHolder extends RecyclerView.ViewHolder {


        public TextView txtNama, txtDate, txtQuota, txtTime, txtAddress, txtPrice;
        public ImageView imgType, imgGender;
        CardView cvEvent;

        public ViewHolder(View itemView) {
            super(itemView);
            txtNama = (TextView) itemView.findViewById(R.id.txt_nama);
            txtAddress = (TextView) itemView.findViewById(R.id.txt_address);
            txtDate = (TextView) itemView.findViewById(R.id.txt_date);
            txtQuota = (TextView) itemView.findViewById(R.id.txt_quota);
            txtTime = (TextView) itemView.findViewById(R.id.txt_time);
            txtPrice = (TextView) itemView.findViewById(R.id.txt_price);
            imgType = (ImageView) itemView.findViewById(R.id.img_type);
            imgGender = (ImageView) itemView.findViewById(R.id.img_gender);
            cvEvent = (CardView) itemView.findViewById(R.id.cv_event);
        }
    }
}
