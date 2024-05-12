package com.gzn1ev.aramlejelentes;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class BillDetails extends AppCompatActivity {

    private TextView detailsTitleTextView;
    private TextView billPriceTextView;
    private TextView billDateTextView;
    private TextView billPaidDateTextView;
    private ImageView checkIconImageView;
    private DocumentReference billReference;
    private Button payButton;
    private String billId;
    private String userEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_bill_details);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            billId = Objects.requireNonNull(extras.get("billId")).toString();
        }

        detailsTitleTextView = findViewById(R.id.detailsTitle);
        billPriceTextView = findViewById(R.id.billPriceTextView);
        billDateTextView = findViewById(R.id.billDateTextView);
        billPaidDateTextView = findViewById(R.id.billPaidDateTextView);
        checkIconImageView = findViewById(R.id.checkIcon);
        payButton = findViewById(R.id.payButton);
        payButton.setOnClickListener(v -> payBill());

        userEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail();

        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        CollectionReference billsCollection = firestore.collection("bills");

        billsCollection.whereEqualTo("userId", userEmail)
                .whereEqualTo("mid", billId)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        billReference = document.getReference();
                        Bill bill = document.toObject(Bill.class);
                        detailsTitleTextView.setText(bill.getTitle());
                        billPriceTextView.setText(String.format(Locale.GERMAN,"%,d Ft", bill.getPrice()));
                        billDateTextView.setText(bill.getDate());
                        if (bill.isPaid()) {
                            checkIconImageView.setVisibility(View.VISIBLE);
                            payButton.setVisibility(View.GONE);
                            billPaidDateTextView.setText(bill.getPaidDate());
                        }else{
                            checkIconImageView.setVisibility(View.GONE);
                            payButton.setVisibility(View.VISIBLE);
                            billPaidDateTextView.setText(R.string.awaitingPayment);
                        }
                    }
                });
    }

    private void payBill(){
        billReference.update("paid", true);
        String currentDate = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault()).format(new Date());
        billReference.update("paidDate", currentDate);
        checkIconImageView.setVisibility(View.VISIBLE);
        payButton.setVisibility(View.GONE);
        billPaidDateTextView.setText(currentDate);
    }
}