package com.gzn1ev.aramlejelentes;

import android.content.res.TypedArray;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class BillsPageFragment extends Fragment {

    private RecyclerView recyclerView;
    private ArrayList<Bill> itemList;
    private BillsAdapter adapter;
    private FirebaseUser user;

    public BillsPageFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bills_page, container, false);
        recyclerView = view.findViewById(R.id.billsRecycleView);
        recyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 1));

        itemList = new ArrayList<>();
        adapter = new BillsAdapter(this.getContext(), itemList);
        initializeData();

        recyclerView.setAdapter(adapter);

        return view;
    }

    private void initializeData(){
        String[] cardTitle = getResources().getStringArray(R.array.cardTitles);
        String[] billId = getResources().getStringArray(R.array.billIds);
        String[] billPrice = getResources().getStringArray(R.array.billPrices);
        String[] billDate = getResources().getStringArray(R.array.billDates);
        TypedArray billIsPaid = getResources().obtainTypedArray(R.array.billIsPaid);
        itemList.clear();

        user = FirebaseAuth.getInstance().getCurrentUser();

        for (int i = 0; i < cardTitle.length; i++) {
            itemList.add(new Bill(billId[i], cardTitle[i], billPrice[i], billDate[i], billIsPaid.getBoolean(i, true), user.getEmail()));
        }

        adapter.notifyDataSetChanged();
    }

}