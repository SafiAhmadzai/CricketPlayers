package app.com.besten.application;

import app.com.besten.receivers.ConnectivityReceiver;

public class Application extends android.app.Application {


    private static Application myAppInstance;


    @Override
    public void onCreate() {
        super.onCreate();
        myAppInstance=this;
    }

    public static Application getInstance(){

        return myAppInstance;
    }


    public void setConnectivityListener(ConnectivityReceiver.ConnectivityReceiverListerener listener){
      ConnectivityReceiver.ConnectivityReceiverListerener listerener =listener;
    }
}
