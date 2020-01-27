package app.com.besten.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import app.com.besten.application.Application;

public class ConnectivityReceiver extends BroadcastReceiver {

    public static ConnectivityReceiverListerener connectivityReceiverListerener;



    @Override
    public void onReceive(Context context, Intent intent) {

        ConnectivityManager connectivityManager= (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info=connectivityManager.getActiveNetworkInfo();


        boolean isConnected=info.isConnectedOrConnecting();

        if (connectivityReceiverListerener!=null){
            connectivityReceiverListerener.networkConnectionChanged(isConnected);
        }
    }


    public static boolean isConnected(){

        ConnectivityManager connectivityManager= (ConnectivityManager) Application.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info=connectivityManager.getActiveNetworkInfo();

        boolean isConnected= info.isConnectedOrConnecting();

        if (connectivityReceiverListerener!=null){
            connectivityReceiverListerener.networkConnectionChanged(isConnected);
        }

        return isConnected;

    }


    public interface ConnectivityReceiverListerener{
        void networkConnectionChanged(boolean isConnected);
    }
}
