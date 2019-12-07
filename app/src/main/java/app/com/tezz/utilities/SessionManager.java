package app.com.tezz.utilities;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {

    private static String NAME_PREFERENCE;
    int PRIVATE_MODE=0;

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context context;


    private static final String  IS_ONBOARDINGSHOWN="isONboardingshow";
    private  static final String HIFH_SCORE="highscore_user";

    public SessionManager(Context context) {
        this.context = context;

        pref=context.getSharedPreferences(NAME_PREFERENCE,PRIVATE_MODE);
        editor=pref.edit();

    }


    public void  setHighScore(int higNumber){

        editor.putInt(HIFH_SCORE,higNumber);
        editor.commit();
    }

    public int getHighScore(){

        return pref.getInt(HIFH_SCORE,0);
    }



    public void setIsOnboardingshown(boolean isFirstTime){

        editor.putBoolean(IS_ONBOARDINGSHOWN,isFirstTime);
        editor.commit();

    }




    public  boolean getIsOnboardingshown() {

        return pref.getBoolean(IS_ONBOARDINGSHOWN,true
        );
    }






}
