package com.example.topcricketplayer.activities;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.animation.Animation;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.example.topcricketplayer.R;
import com.example.topcricketplayer.adapters.LanguageAdapter;
import com.example.topcricketplayer.application.Application;
import com.example.topcricketplayer.models.Celebrity;
import com.example.topcricketplayer.models.Player;
import com.example.topcricketplayer.receivers.ConnectivityReceiver;
import com.example.topcricketplayer.utilities.SessionManager;

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

        db.collection("players").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()) {

                    List<Player> players = task.getResult().toObjects(Player.class);
                    Collections.sort(players);
                    Collections.reverse(players);

                    languageAdapter=new LanguageAdapter(players,MainActivity.this, new LanguageAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(Player item) {
                            Intent intent = new Intent(MainActivity.this, ActivityWeb.class);
                            intent.putExtra("rank", item.getRating());
                            intent.putExtra("name", item.getName());
                            intent.putExtra("content", item.getContent());
                            startActivity(intent);
                        }
                    });

                    rvDataLanguages.setLayoutManager(manager);
                    rvDataLanguages.setAdapter(languageAdapter);

                    for( int i = 0 ; i < players.size(); i++) {
                        Log.d(TAG, "onComplete: " + players.get(i).getName());
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
