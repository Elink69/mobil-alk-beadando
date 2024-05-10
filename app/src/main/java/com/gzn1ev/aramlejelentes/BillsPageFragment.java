package com.gzn1ev.aramlejelentes;
import android.content.res.TypedArray;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class BillsPageFragment extends Fragment {

    private static final String TAG = BillsPageFragment.class.getName();
    private FirebaseUser user;
    private FirebaseFirestore firestore;
    private CollectionReference billsCollection;
    private BillsAdapter adapter;
    public BillsPageFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bills_page, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.billsRecycleView);
        firestore = FirebaseFirestore.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();

        billsCollection = firestore.collection("bills");
        Query query = billsCollection.orderBy("date", Query.Direction.DESCENDING).whereEqualTo("userId", user.getEmail());
        query.get().addOnSuccessListener(queryDocumentSnapshots -> {
                    if (queryDocumentSnapshots.isEmpty()){
                        Log.d(TAG, "query result is empty");
                        initializeData();
                    }else{
                        Log.d(TAG, "onCreateView: " + queryDocumentSnapshots.size());
                    }
                });

        FirestoreRecyclerOptions<Bill> options = new FirestoreRecyclerOptions.Builder<Bill>()
                .setQuery(query, Bill.class)
                .build();

        adapter = new BillsAdapter(options, view.getContext());
        recyclerView.setLayoutManager(new GridLayoutManager(container.getContext(),1));
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        adapter.stopListening();
    }

    private void initializeData(){
        String[] cardTitle = getResources().getStringArray(R.array.cardTitles);
        String[] billId = getResources().getStringArray(R.array.billIds);
        int[] billPrice = getResources().getIntArray(R.array.billPrices);
        String[] billDate = getResources().getStringArray(R.array.billDates);
        TypedArray billIsPaid = getResources().obtainTypedArray(R.array.billIsPaid);

        for (int i = 0; i < cardTitle.length; i++) {
            firestore.collection("bills").document()
                            .set(new Bill(billId[i],
                                cardTitle[i],
                                billPrice[i],
                                billDate[i],
                                billIsPaid.getBoolean(i, true),
                                user.getEmail()));
        }
        billIsPaid.recycle();
    }
}