package com.airasia.utils;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by techjini on 14/7/18.
 */

public class CommonUtils {

    public static String fetchFileFromAssets(Context context, String fileName) {
        String json = null;
        if (context == null) {
            return json;
        }
        try {
            InputStream is = context.getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return json;
    }

    public static String getDateInUserFormat(Calendar calendar) {
        SimpleDateFormat sdf = new SimpleDateFormat(AppConstants.Formats.DISPLAY_DATE_FORMAT, Locale.ENGLISH);
        try {
            return sdf.format(calendar.getTime());
        } catch (Exception e) {
            return sdf.format(Calendar.getInstance().getTime());
        }
    }
}
