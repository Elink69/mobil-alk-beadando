package com.gzn1ev.aramlejelentes;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import com.github.anastr.speedviewlib.TubeSpeedometer;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainPageFragment extends Fragment {
    private static final String TAG = MainPageFragment.class.getName();
    private FirebaseFirestore firestore;
    private CollectionReference lejelentesekCollection;
    private TubeSpeedometer gauge;
    private View view;
    private Button submitButton;
    private String userEmail;

    public MainPageFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main_page, container, false);
        gauge = view.findViewById(R.id.tubeSpeedometer);

        userEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail();

        submitButton = view.findViewById(R.id.submitMeter);
        submitButton.setOnClickListener(v -> bejelentes());

        firestore = FirebaseFirestore.getInstance();
        lejelentesekCollection = firestore.collection("lejelentesek");

        setGauge();
        return view;
    }

    public void setGauge(){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy", Locale.getDefault());
        String currentDate = formatter.format(new Date());

        lejelentesekCollection.whereGreaterThanOrEqualTo("date", currentDate).whereEqualTo("userId", userEmail).get().addOnSuccessListener(queryDocumentSnapshots -> {
            float usage = 0f;
            for (QueryDocumentSnapshot doc : queryDocumentSnapshots){
                usage += doc.toObject(Lejelentes.class).getFogyasztas();
            }
            if(usage > gauge.getMaxSpeed()){
                gauge.setMaxSpeed(usage*1.5f);
            }
            gauge.speedTo(usage);
        });
    }

    public void bejelentes(){
        EditText kWh = view.findViewById(R.id.editTextOraBejelentes);
        float kWhValue = Float.parseFloat(kWh.getText().toString());
        String date = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault()).format(new Date());
        lejelentesekCollection.document()
                .set(new Lejelentes(date, kWhValue, userEmail)).addOnSuccessListener(unused -> {
                    setGauge();
                    kWh.setText("");
                });
    }
}