package app.com.tezz.activities;

import android.os.Bundle;

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
import app.com.tezz.utilities.SessionManager;

public class MainActivity extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListerener {


    SessionManager sessionManager;

    Animation animation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        if (getIntent().getStringExtra("key")!=null){


            ///SHow tutorial
        }



        animation= AnimationUtils.loadAnimation(MainActivity.this,R.anim.fade_in);




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
