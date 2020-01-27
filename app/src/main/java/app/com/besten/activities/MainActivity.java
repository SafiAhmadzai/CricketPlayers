package app.com.besten.activities;

import android.content.Intent;
import android.os.Bundle;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Toast;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import app.com.besten.R;
import app.com.besten.adapters.LanguageAdapter;
import app.com.besten.application.Application;
import app.com.besten.models.Celebrity;
import app.com.besten.models.Language;
import app.com.besten.network.VolleySingleton;
import app.com.besten.receivers.ConnectivityReceiver;
import app.com.besten.utilities.SessionManager;

public class MainActivity extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListerener {


    public static final  String TAG=MainActivity.class.getSimpleName();


    SessionManager sessionManager;
    Animation animation;

    RecyclerView rvDataLanguages;
    List<Celebrity> list;

    LanguageAdapter languageAdapter;
    LinearLayoutManager manager;
    String url="https://api.androidhive.info/json/contacts.json";

    FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        sessionManager=new SessionManager(MainActivity.this);

        list=new ArrayList<>();
        rvDataLanguages=findViewById(R.id.rvData);

        //to get data
        getLanguageData();


         manager=new LinearLayoutManager(MainActivity.this,LinearLayoutManager.VERTICAL,false);


    }

    private void getLanguageData() {

        FirebaseFirestore db = FirebaseFirestore.getInstance();
//                Toast.makeText(MainActivity.this, "Clicked", Toast.LENGTH_SHORT).show();

        db.collection("pls").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()) {

                    List<Language> languages = task.getResult().toObjects(Language.class);
                    Collections.sort(languages);

                    languageAdapter=new LanguageAdapter(languages,MainActivity.this, new LanguageAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(Language item) {
                            Intent intent = new Intent(MainActivity.this, ActivityWeb.class);
                            intent.putExtra("rank", item.getRank());
                            intent.putExtra("name", item.getName());
                            intent.putExtra("content", item.getContent());
                            startActivity(intent);
                        }
                    });

                    rvDataLanguages.setLayoutManager(manager);
                    rvDataLanguages.setAdapter(languageAdapter);

                    for( int i = 0 ; i < languages.size(); i++) {
                        Log.d(TAG, "onComplete: " + languages.get(i).getName());
                    }

                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "onFailure: BRO");
            }
        });




    }




    @Override
    protected void onResume() {
        super.onResume();
        Application.getInstance().setConnectivityListener(MainActivity.this);
    }

    @Override
    public void networkConnectionChanged(boolean isConnected) {

        Toast.makeText(this, ""+isConnected, Toast.LENGTH_SHORT).show();
    }
}
