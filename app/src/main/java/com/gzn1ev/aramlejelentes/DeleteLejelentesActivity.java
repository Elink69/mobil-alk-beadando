package com.gzn1ev.aramlejelentes;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class DeleteLejelentesActivity extends AppCompatActivity {
    private static final String TAG = "DeleteLejelentesActivity";

    private String userEmail;
    private DocumentReference documentReference;
    private DatePicker datePicker;
    private EditText fogyasztasEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_delete_lejelentes);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            userEmail = Objects.requireNonNull(extras.get("email")).toString();
        }

        fogyasztasEditText = findViewById(R.id.lejelentettFogyasztas);
        datePicker = findViewById(R.id.datePicker);

        Button deleteButton = findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(v -> checkAndDelete());
    }

    private void checkAndDelete(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
        Date selectedDate = new Date(datePicker.getYear()-1900, datePicker.getMonth(), datePicker.getDayOfMonth());
        String formattedDate = dateFormat.format(selectedDate);

        float fogyasztas = Float.parseFloat(fogyasztasEditText.getText().toString());
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        firestore.collection("lejelentesek")
                .whereEqualTo("userId", userEmail)
                .whereEqualTo("fogyasztas", fogyasztas)
                .whereEqualTo("date", formattedDate).get().addOnSuccessListener(queryDocumentSnapshots -> {
                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        document.getReference().delete().addOnSuccessListener(unused -> {
                            Toast.makeText(this, "Sikeres törlés", Toast.LENGTH_SHORT).show();
                            finish();
                        });
                    }
                }).addOnFailureListener(e -> {
                    Toast.makeText(this, "Nem található ilyen lejelentes", Toast.LENGTH_SHORT).show();
                });
    }
}