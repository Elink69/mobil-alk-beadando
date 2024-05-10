package com.gzn1ev.aramlejelentes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class RegisterActivity extends AppCompatActivity {

    private static final String LOG_TAG = RegisterActivity.class.getName();

    private EditText nameEditText;
    private EditText emailEditText;
    private EditText passwordEditText;
    private EditText passwordAgainEditText;
    private EditText meroOraIdEditText;
    private FirebaseAuth mAuth;
    private FirebaseFirestore firestore;
    private CollectionReference userDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.register), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        nameEditText = findViewById(R.id.editTextName);
        emailEditText = findViewById(R.id.editTextEmail);
        passwordEditText = findViewById(R.id.editTextPassword);
        passwordAgainEditText = findViewById(R.id.editTextPasswordAgain);
        meroOraIdEditText = findViewById(R.id.editTextMeroOra);
        mAuth = FirebaseAuth.getInstance();

        firestore = FirebaseFirestore.getInstance();
        userDetails = firestore.collection("userDetails");
    }

    public void register(View view) {
        String name = nameEditText.getText().toString();
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        String passwordAgain = passwordAgainEditText.getText().toString();
        String meroOraId = meroOraIdEditText.getText().toString();
        String currentDate = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault()).format(new Date());

        if(!password.equals(passwordAgain)){
            Toast.makeText(this, "A két jelszó nem egyezik", Toast.LENGTH_LONG).show();
            return;
        }
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, task -> {
            if(task.isSuccessful()){
                userDetails.add(new UserDetails(name, email, currentDate, meroOraId));
                startApp();
            }
            else{
                Toast.makeText(this, "Sikertelen regisztráció", Toast.LENGTH_LONG).show();
                finish();
            }
        });
    }

    public void cancel(View view) {
        finish();
    }

    public void startApp() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}