package com.gzn1ev.aramlejelentes;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import java.util.Locale;

public class BillsAdapter extends FirestoreRecyclerAdapter<Bill, BillsAdapter.BillsViewHolder> {

    private Context context;
    public BillsAdapter(@NonNull FirestoreRecyclerOptions<Bill> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull BillsViewHolder billsViewHolder, int i, @NonNull Bill bill) {
        billsViewHolder.cardTitle.setText(bill.getTitle());
        billsViewHolder.billId.setText(bill.getMId());
        billsViewHolder.billDate.setText(bill.getDate());
        billsViewHolder.billPrice.setText(String.format(Locale.getDefault(),"%d Ft", bill.getPrice()));
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.slide_in_row);
        billsViewHolder.itemView.startAnimation(animation);
    }

    @NonNull
    @Override
    public BillsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BillsViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_bills, parent, false));
    }

    public class BillsViewHolder extends RecyclerView.ViewHolder {

        TextView cardTitle;
        TextView billDate;
        TextView billId;
        TextView billPrice;
        public BillsViewHolder(@NonNull View itemView) {
            super(itemView);
            cardTitle = itemView.findViewById(R.id.cardTitle);
            billDate = itemView.findViewById(R.id.billDate);
            billId = itemView.findViewById(R.id.billId);
            billPrice = itemView.findViewById(R.id.billPrice);
            itemView.setOnClickListener(v -> {
                if (v instanceof CardView){
                    Intent intent = new Intent(context, BillDetails.class);
                    context.startActivity(intent);
                }
            });
        }
    }
}
