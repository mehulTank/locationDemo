package com.locationservice.myapplication.utility;

import android.Manifest;
import android.animation.Animator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.inputmethod.InputMethodManager;

import androidx.core.app.ActivityCompat;



import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


/**
 * @purpose commonly used functions
 * @purpose
 */
public class Utils {
    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_FILE = 2;
    public static final String INTENT_SERVICES = "INTENT_SERVICES";
    public static final String INTENT_OFFER_ID = "INTENT_OFFER_ID";
    public static final String INTENT_OFFER_TOTAL_AMT = "INTENT_OFFER_TOTAL_AMT";
    public static final String INTENT_STORE_DETAILS = "INTENT_STORE_DETAILS";
    public static final String INTENT_BOOKING_LIST_DETAIL = "INTENT_BOOKING_LIST_DETAIL";
    public static final String INTENT_STORE_SUB_INDEX = "INTENT_STORE_SUB_INDEX";
    public static final String INTENT_STORE_STUFF_SELECTED_ID = "INTENT_STORE_STUFF_SELECTED_ID";
    public static final String INTENT_FROM_SETTING = "INTENT_FROM_SETTING";
    public static final String INTENT_IMAGE_LIST = "INTENT_IMAGE_LIST";
    public static final String INTENT_SELECTED_GALLERY_IMAGE_INDEX = "INTENT_SELECTED_GALLERY_IMAGE_INDEX";
    public static final String VERSION_CHECK = "version_check";
    public static final String CITY_LIST = "city_list";
    public static final String CATEGORY_LIST = "list_categories";
    public static final String INTENT_STORE_ID = "INTENT_STORE_ID";
    public static final String INTENT_STORE = "INTENT_STORE";
    public static Fragment curFragment;

    /**
     * @param context
     * @param message
     * @return
     * @description use to check internet network connection if network
     * connection not available than alert for open network
     * settings
     */
    /*public static boolean isOnline(final Activity context, boolean message) {
        ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = mConnectivityManager.getActiveNetworkInfo();
        if (netInfo != null) {
            if (netInfo.isConnectedOrConnecting()) {
                return true;
            }
        }
        if (message) {
            final AlertDialog.Builder dialog = new AlertDialog.Builder(context);
            dialog.setTitle(context.getString(R.string.app_name_new));
            dialog.setCancelable(false);
            dialog.setMessage(context.getString(R.string.check_connection));
            dialog.setPositiveButton(context.getString(R.string.settings), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss();
                    context.startActivity(new Intent(android.provider.Settings.ACTION_SETTINGS));
                }
            });
            dialog.setNegativeButton(context.getString(android.R.string.cancel), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss();
                }
            });
            //dialog.show();

            return false;
        }
        return false;
    }*/

    /**
     * Method is used for checking network availability.
     *
     * @param context
     * @return isNetAvailable: boolean true for Internet availability, false otherwise
     */

    public static boolean isNetworkAvailable(Context context) {
        boolean isNetAvailable = false;
        if (context != null) {
            final ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

            if (mConnectivityManager != null) {
                boolean mobileNetwork = false;
                boolean wifiNetwork = false;

                boolean mobileNetworkConnecetd = false;
                boolean wifiNetworkConnecetd = false;

                final NetworkInfo mobileInfo = mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
                final NetworkInfo wifiInfo = mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

                if (mobileInfo != null) {
                    mobileNetwork = mobileInfo.isAvailable();
                }

                if (wifiInfo != null) {
                    wifiNetwork = wifiInfo.isAvailable();
                }

                if (wifiNetwork || mobileNetwork) {
                    if (mobileInfo != null)
                        mobileNetworkConnecetd = mobileInfo.isConnectedOrConnecting();
                    wifiNetworkConnecetd = wifiInfo.isConnectedOrConnecting();
                }

                isNetAvailable = (mobileNetworkConnecetd || wifiNetworkConnecetd);
            }
        }

        return isNetAvailable;
    }


    /**
     * @param context
     * @param title
     * @param msg
     * @param strPositiveText
     * @param strNegativeText
     * @param isNagativeBtn
     * @param isFinish
     * @purpose dialog which show positive and optional negative button
     */
    public static void displayDialog(final Activity context, final String title, final String msg, final String strPositiveText, final String strNegativeText,
                                     final boolean isNagativeBtn, final boolean isFinish) {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setTitle(title);
        dialog.setCancelable(false);
        dialog.setMessage(msg);
        dialog.setPositiveButton(strPositiveText, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
                if (isFinish) {
                    context.getFragmentManager().popBackStack();
                }
            }
        });
        if (isNagativeBtn) {
            dialog.setNegativeButton(strNegativeText, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss();
                }
            });
        }
        dialog.show();
    }

    /**
     * Method is used for displaying dialog and finishing activity on dialog button click id isFinish is true
     *
     * @param title
     * @param msg
     * @param context
     * @param isFinish
     */
    public static void displayDialog(String title, String msg, final Context context, final boolean isFinish) {


        // final AlertDialog.Builder alertDialog = new
        // AlertDialog.Builder(context);
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog.setTitle(title);
        alertDialog.setCancelable(false);
        alertDialog.setMessage(msg);

        alertDialog.setNeutralButton(context.getString(android.R.string.ok), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                if (isFinish)
                    ((Activity) context).finish();
            }
        });
        final AlertDialog dialog = alertDialog.create();

        if (!((Activity) context).isFinishing()) {
            if (!dialog.isShowing()) {
                alertDialog.show();
            }
        }
    }


    public static void checkPermitionCameraGaller(Activity context) {
        ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE,}, 1);
    }

    public static void checkPermitionLocation(Activity context) {
        ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION,}, 1);
    }

    /**
     * @param mActivity
     * @param message
     * @param isCancelable
     * @return
     * @purpose show progress dialog
     */
    public static ProgressDialog showProgressDialog1(final Activity mActivity, final String message, boolean isCancelable) {
        final ProgressDialog mDialog = new ProgressDialog(mActivity);
        mDialog.show();
        mDialog.setCancelable(isCancelable);
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.setMessage(message);
        return mDialog;
    }

    public static final void dismissProgressDialog(ProgressDialog progressDialog) {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }


    public final static boolean isValidPassword(final String password) {
        // pattern = Pattern.compile(PASSWORD_PATTERN);
        // matcher = pattern.matcher(password);
        // return matcher.matches();
        if (password.length() < 6) {
            return false;
        } else {
            return true;
        }
    }


    /**
     * @param inputEmail
     * @return
     * @purpose validate email
     */
    public final static boolean isValidEmail(CharSequence inputEmail) {
        if (inputEmail == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(inputEmail).matches();
        }
    }

    /**
     * @param activity
     * @return
     * @purpose check the device has calling functionality or not
     */
    public final static boolean isCalling(Activity activity) {
        if (((TelephonyManager) activity.getSystemService(Context.TELEPHONY_SERVICE)).getPhoneType() == TelephonyManager.PHONE_TYPE_NONE) {
            // no phone
            return false;
        }
        return true;

    }

    /**
     * @param context
     * @return
     * @purpose get the device ID
     */
    public final static String getDeviceID(Context context) {
        return Secure.getString(context.getContentResolver(), Secure.ANDROID_ID);
    }


    /**
     * Hide KeyBoard Using CurrentFocus
     *
     * @return
     */
    public static void hideKeyboard(Context mContext) {
        InputMethodManager inputManager = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);

        View focus = ((Activity) mContext).getCurrentFocus();

        if (focus != null) {

            inputManager.hideSoftInputFromWindow(focus.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * Hide KeyBoard Using CurrentFocus when FragmentDialog
     *
     * @return
     */
    public static void hideKeyboardWithDialog(Context mContext) {
        InputMethodManager inputManager = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);

        View focus = ((Activity) mContext).getCurrentFocus();

        if (focus != null) {
            inputManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
        }

    }


    /**
     * @param activity
     * @purpose hide softkey board
     */
    public static void hideSoftKeyboardWhenNeeded(Activity activity) {
        final InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (inputMethodManager.isActive()) {
            if (activity.getCurrentFocus() != null) {
                inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

    /**
     * @param context
     * @param detail
     * @purpose for writing the log
     */
    public static void writeLog(final Context context, String detail) {
        if (true) {
            Log.i(context.getClass().getSimpleName(), detail);
        }
    }

    /**
     * @return isPresnet
     * @purpose check the sd card available or not
     */
    public final static Boolean checkSDCardAvalibility() {
        Boolean isSDPresent = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        return isSDPresent;
    }






    public static void hideProgressDialog(final Context mActivity, final ProgressDialog mDialog) {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }


    /**
     * @param mActivity
     * @param targetedFragment
     * @param shooterFragment
     * @purpose for call targeted fragment from current fragment
     */

    public static void addNextFragmentWithoutAnimation(Activity mActivity, Fragment targetedFragment, Fragment shooterFragment) {
        final FragmentTransaction transaction = mActivity.getFragmentManager().beginTransaction();

        // transaction.add(R.id.activity_menubar_containers, targetedFragment, targetedFragment.getClass().getSimpleName());
        //curFragment = targetedFragment;
        transaction.hide(shooterFragment);
        transaction.addToBackStack(targetedFragment.getClass().getSimpleName());
        transaction.commit();
    }





    // To reveal a previously invisible view using this effect:
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void show(final View view) {

        // get the center for the clipping circle
        int cx = (view.getLeft() + view.getRight()) / 2;
        int cy = (view.getTop() + view.getBottom()) / 2;

        // get the final radius for the clipping circle
        int finalRadius = Math.max(view.getWidth(), view.getHeight());

        // create the animator for this view (the start radius is zero)
        Animator anim = ViewAnimationUtils.createCircularReveal(view, cx, cy, 0, finalRadius);
        anim.setDuration(1000);

        // make the view visible and start the animation
        view.setVisibility(View.VISIBLE);
        anim.start();


    }


    public static boolean isGPSOn(final Context context) {

        final LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled = false;
        boolean network_enabled = false;
        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        try {
            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return gps_enabled || network_enabled;
    }

    public static boolean isMyServiceRunning(Class<?> serviceClass, Context ctx) {
        final ActivityManager manager = (ActivityManager) ctx.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    public static void setLanguage(Context mContext, String langCode) {

        Locale locale = new Locale(langCode);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.setLayoutDirection(locale);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            setSystemLocale(config, locale);
        } else {
            setSystemLocaleLegacy(config, locale);
        }
        mContext.getResources().updateConfiguration(config, mContext.getResources().getDisplayMetrics());
    }

    @SuppressWarnings("deprecation")
    public static void setSystemLocaleLegacy(Configuration config, Locale locale) {
        config.locale = locale;
    }

    @TargetApi(Build.VERSION_CODES.N)
    public static void setSystemLocale(Configuration config, Locale locale) {
        config.setLocale(locale);
    }

    public static int dp2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }


    public static String parseDateToddMMyyyy(String time) {
        String inputPattern = "yyyy-MM-dd";
        String outputPattern = "dd MMM yyyy";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = "";

        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }


}
