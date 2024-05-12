package com.gzn1ev.aramlejelentes;

import static androidx.navigation.ui.NavigationUI.setupWithNavController;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.gzn1ev.aramlejelentes.R;

public class MainActivity extends AppCompatActivity{
    private static final String TAG = MainActivity.class.getName();
    private FirebaseAuth mAuth;
    private FirebaseUser user;

    private BottomNavigationView bottomNavigationView;
    private final MainPageFragment mainPageFragment = new MainPageFragment();
    private final BillsPageFragment billsPageFragment = new BillsPageFragment();
    private final ProfilePageFragment profilePageFragment = new ProfilePageFragment();

    private NotificationHandler notificationHandler;
    private CollectionReference billsCollection;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        notificationHandler = new NotificationHandler(this);

        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        billsCollection = firestore.collection("bills");

        bottomNavigationView = findViewById(R.id.bottomNav);

        NavHostFragment navHostFragment = (NavHostFragment)getSupportFragmentManager().findFragmentById(R.id.mainLayout);
        NavController navController = navHostFragment.getNavController();
        setupWithNavController(bottomNavigationView, navController);

        mAuth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null){
            Log.d(TAG, "Authentikált felhasználó");
        }else {
            Log.d(TAG, "Nem authentikált felhasználó");
            finish();
        }


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if(checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED){
                billsCollection.whereEqualTo("paid", false).whereEqualTo("userId", user.getEmail()).get().addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()){
                        notificationHandler.send("Még van befizetésre váró számlája!");
                    }
                });
            }
        }else{
            billsCollection.whereEqualTo("paid", false).whereEqualTo("userId", user.getEmail()).get().addOnSuccessListener(queryDocumentSnapshots -> {
                if (!queryDocumentSnapshots.isEmpty()){
                    notificationHandler.send("Még van befizetésre váró számlája!");
                }
            });
        }
    }
    private ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    Toast.makeText(this, "A hozzáférést engedélyezted, hasznos értesítéseket fogsz kapni", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "A hozzáférést elutasíttad, ha később engedélyezed hasznos értesítéseket kaphatsz", Toast.LENGTH_SHORT).show();
                }
            });
}