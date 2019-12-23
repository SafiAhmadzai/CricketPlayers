package app.com.tezz.activities;

import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import app.com.tezz.R;
import app.com.tezz.application.Application;
import app.com.tezz.receivers.ConnectivityReceiver;
import app.com.tezz.receivers.VolleySingleton;
import app.com.tezz.utilities.SessionManager;

public class MainActivity extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListerener {


    SessionManager sessionManager;
    TextView textView;

    Animation animation;

    VolleySingleton singleton;
    RequestQueue queue;

    Application myApp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        myApp = Application.getInstance();
        singleton = VolleySingleton.getInstance();
        queue = singleton.getRequestQueue();

        if (getIntent().getStringExtra("key")!=null){


            ///SHow tutorial
        }

        textView=findViewById(R.id.tvShowNetworkData);


        animation= AnimationUtils.loadAnimation(MainActivity.this,R.anim.fade_in);



        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://www.google.com";


        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        textView.setText("Response is: "+ response.substring(0,500));
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                textView.setText("That didn't work!");
            }
        });

        queue.add(stringRequest);



        sessionManager=new SessionManager(MainActivity.this);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //sessionManager.setHighScore(25);


                boolean  isConnected=ConnectivityReceiver.isConnected();


                Toast.makeText(MainActivity.this, ""+isConnected, Toast.LENGTH_SHORT).show();

            }
        });



        Log.d("data",sessionManager.getHighScore()+"");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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
