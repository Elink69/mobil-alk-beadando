package com.gzn1ev.aramlejelentes;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

public class ProfilePageFragment extends Fragment {

    private TextView textViewName;
    private TextView textViewEmail;
    private TextView textViewMeter;
    private Button buttonDelete;

    public ProfilePageFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_page, container, false);
        Context context = view.getContext();
        textViewName = view.findViewById(R.id.textViewNamePlaceholder);
        textViewEmail = view.findViewById(R.id.textViewEmailPlaceholder);
        textViewMeter = view.findViewById(R.id.textViewMeterPlaceholder);
        buttonDelete = view.findViewById(R.id.buttonTorol);

        String userEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        buttonDelete.setOnClickListener(v -> {
            Intent intent = new Intent(context, DeleteLejelentesActivity.class);
            intent.putExtra("email", userEmail);
            context.startActivity(intent);
        });

        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        firestore.collection("userDetails")
                .whereEqualTo("email", userEmail).get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (QueryDocumentSnapshot document: queryDocumentSnapshots) {
                        UserDetails user = document.toObject(UserDetails.class);
                        textViewEmail.setText(userEmail);
                        textViewName.setText(user.getFullName());
                        textViewMeter.setText(user.getMeroOraId());
                    }
                });
        return view;
    }

}