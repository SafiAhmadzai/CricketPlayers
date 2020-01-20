package app.com.tezz.activities;

import android.app.job.JobScheduler;
import android.os.Bundle;

import com.android.volley.ClientError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import app.com.tezz.R;
import app.com.tezz.adapters.CelebrityAdapter;
import app.com.tezz.application.Application;
import app.com.tezz.models.Celebrity;
import app.com.tezz.network.VolleySingleton;
import app.com.tezz.receivers.ConnectivityReceiver;
import app.com.tezz.utilities.SessionManager;

public class MainActivity extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListerener {


    public static final  String TAG=MainActivity.class.getSimpleName();


    SessionManager sessionManager;
    Animation animation;

    RecyclerView rvDataCelebrities;
    List<Celebrity> list;

    CelebrityAdapter celebrityAdapter;
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

        floatingActionButton=findViewById(R.id.fab);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Access a Cloud Firestore instance from your Activity
                FirebaseFirestore db = FirebaseFirestore.getInstance();


                // Create a new user with a first and last name
                Map<String, Object> user = new HashMap<>();
                user.put("first", "Rafaat");
                user.put("last", "Sultan");
                user.put("born", 1992);

// Add a new document with a generated ID
                db.collection("users")
                        .add(user)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Error adding document", e);
                            }
                        });

            }
        });

        list=new ArrayList<>();
        rvDataCelebrities=findViewById(R.id.rvData);

        //to get data
        getAllCelebrityData();


         manager=new LinearLayoutManager(MainActivity.this,LinearLayoutManager.VERTICAL,false);


    }

    private void getAllCelebrityData() {

        JsonArrayRequest request = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        if (response == null) {
                            Toast.makeText(getApplicationContext(), "Couldn't fetch the contacts! Pleas try again.", Toast.LENGTH_LONG).show();
                            return;
                        }

                        List<Celebrity> items = new Gson().fromJson(response.toString(), new TypeToken<List<Celebrity>>() {
                        }.getType());


                        Log.d("data",response.toString());

                        list.clear();
                        list.addAll(items);

                        Log.d("data",list.size()+"");

                        celebrityAdapter=new CelebrityAdapter(list,MainActivity.this);

                        rvDataCelebrities.setLayoutManager(manager);
                        rvDataCelebrities.setAdapter(celebrityAdapter);


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // error in getting json
                Toast.makeText(getApplicationContext(), "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        VolleySingleton.getInstance().getRequestQueue().add(request);

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
