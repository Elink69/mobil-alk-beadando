package com.gzn1ev.aramlejelentes;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.*;

public class BillsAdapter extends RecyclerView.Adapter<BillsAdapter.BillsViewHolder> {
    private static final String TAG = "BillsAdapter";

    private Context context;
    private ArrayList<Bill> bills;
    private ArrayList<Bill> billsAll;
    private int lastPosition = -1;

    public BillsAdapter(Context context, ArrayList<Bill> bills) {
        this.context = context;
        this.bills = bills;
        this.billsAll = bills;
    }

    @NonNull
    @Override
    public BillsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BillsViewHolder(LayoutInflater.from(context).inflate(R.layout.list_bills, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BillsViewHolder holder, int position) {
        Bill currentBill = bills.get(position);

        holder.bindTo(currentBill);

        if (holder.getAbsoluteAdapterPosition() > lastPosition) {
               Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
               holder.itemView.startAnimation(animation);
               lastPosition = holder.getAbsoluteAdapterPosition();
        }
    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCount: " + bills.size());
        return bills.size();
    }

    public class BillsViewHolder extends RecyclerView.ViewHolder {
        private static final String TAG = "BillsAdapter";
        private TextView cardTitle;
        private TextView billDate;
        private TextView billId;
        private TextView billPrice;
        public BillsViewHolder(@NonNull View itemView) {
            super(itemView);

            cardTitle = itemView.findViewById(R.id.cardTitle);
            billDate = itemView.findViewById(R.id.billDate);
            billId = itemView.findViewById(R.id.billId);
            billPrice = itemView.findViewById(R.id.billPrice);
            itemView.setOnClickListener(v -> {
                if (v instanceof CardView){
                    Log.d(TAG, "BillsViewHolder: Clicked on a card");
                }
            });
        }

        public void bindTo(Bill currentBill) {
            cardTitle.setText(currentBill.getTitle());
            billDate.setText(currentBill.getDate());
            billId.setText(currentBill.getId());
            billPrice.setText(currentBill.getPrice());
        }
    }
}


