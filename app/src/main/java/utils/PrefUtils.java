package utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class PrefUtils {


    public static final String user_Name = "__Name__";
    public static final String user_MobileNo = "__MobileNo__";
    public static final String user_Email = "__Email__";
    public static final String user_UserPhoto = "__UserPhoto__";
    public static final String user_UserPhoto1 = "__UserPhoto1__";

    public static final String user_profile = "__user_profile__";
    public static final String LoyaltyId = "__Loyalty_Id__";

    public static final String SimNumber = "__SimNumber__";
    public static final String UDID = "__UDID__";
    public static final String registrationId = "__registrationId__";

    public static final String IsLoggedIn = "IsLoggedIn";


    public void saveToPrefs(Context context, String key, String value) {


        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(context);
        final SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.commit();

    }

    public void logout(Context c) {
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(c);
        final SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.commit();
    }


    /**
     * Called to retrieve required value from shared preferences, identified by
     * given key. Default value will be returned of no value found or error
     * occurred.
     *
     * @param context      Context of caller activity
     * @param key          Key to find value against
     * @param defaultValue Value to return if no data found against given key
     * @return Return the value found against given key, default if not found or
     * any error occurs
     */
    public String getFromPrefs(Context context, String key, String defaultValue) {
        SharedPreferences sharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(context);
        try {
            return sharedPrefs.getString(key, defaultValue);
        } catch (Exception e) {
            e.printStackTrace();
            return defaultValue;
        }
    }
}
