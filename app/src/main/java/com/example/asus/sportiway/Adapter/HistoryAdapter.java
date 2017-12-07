package com.example.asus.sportiway.Adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.asus.sportiway.Model.History;
import com.example.asus.sportiway.R;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by Asus on 06/09/2017.
 */

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder>{
        private List<History> mHistories;
        private Context mContext;

        public HistoryAdapter(Context context, List<History> histories) {
            mHistories = histories;
            mContext = context;
        }

        // Easy access to the context object in the recyclerview
        private Context getContext() {
            return mContext;
        }

        @Override
        public HistoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);

            // Inflate the custom layout
            View contactView = inflater.inflate(R.layout.item_history, parent, false);

            // Return a new holder instance
            HistoryAdapter.ViewHolder viewHolder = new HistoryAdapter.ViewHolder(contactView);
            return viewHolder;
        }

        // Involves populating data into the item through holder
        @Override
        public void onBindViewHolder(HistoryAdapter.ViewHolder viewHolder, int position) {
            // Get the data model based on position
            History histories = mHistories.get(position);

            // Set item views based on your views and data model
            TextView txtNama = viewHolder.txtNama;
            txtNama.setText(histories.getName());
            TextView txtDate = viewHolder.txtDate;
            txtDate.setText(histories.getDate());
            TextView txtTime = viewHolder.txtTime;
            txtTime.setText(histories.getTime());
            TextView txtAddress = viewHolder.txtAddress;
            txtAddress.setText(histories.getAddress());
            TextView txtReputation = viewHolder.txtReputation;
            txtReputation.setText(histories.getReputation());
            TextView txtShare = viewHolder.txtShare;
            txtShare.setText(histories.getShared());

            ImageView imgType = viewHolder.imgType;
            switch (histories.getType()){
                case "Sepak Bola":
                    imgType.setImageResource(R.drawable.bola);
                    break;
                case "Jogging":
                    imgType.setImageResource(R.drawable.sepatu);
                    break;
                case "Bulu Tangkis":
                    imgType.setImageResource(R.drawable.bultang);
                    break;
            }
        }

        // Returns the total count of items in the list
        @Override
        public int getItemCount() {
            return mHistories.size();
        }

public static class ViewHolder extends RecyclerView.ViewHolder {


    public TextView txtNama, txtDate, txtTime, txtAddress, txtReputation, txtShare;
    public ImageView imgType;
    CardView cvHistory;

    public ViewHolder(View itemView) {
        super(itemView);
        txtNama = (TextView) itemView.findViewById(R.id.txt_nama);
        txtAddress = (TextView) itemView.findViewById(R.id.txt_address);
        txtDate = (TextView) itemView.findViewById(R.id.txt_date);
        txtTime = (TextView) itemView.findViewById(R.id.txt_time);
        txtReputation = (TextView) itemView.findViewById(R.id.txt_reputation);
        txtShare = (TextView) itemView.findViewById(R.id.txt_share);
        imgType = (ImageView) itemView.findViewById(R.id.img_type);
        cvHistory = (CardView) itemView.findViewById(R.id.cv_history);
    }
}
}
