package it.angelic.tagviewlib;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.DisplayMetrics;
import android.util.TypedValue;

import java.util.ArrayList;
import java.util.Arrays;

public class SimpleTagViewUtils {
    private static ArrayList mAllIcons;
    private static ArrayList mAllIconNames;

    private static Typeface mFont;


    static int dipToPx(Context c, float dipValue) {
        DisplayMetrics metrics = c.getResources().getDisplayMetrics();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue, metrics);
    }

    static double getWeigthedColorLuminosity(int col) {
        return Color.red(col) * 0.299 + Color.green(col) * 0.587 + Color.blue(col) * 0.114;
    }

    /**
     * Returns the font-awesome Typeface from the given context.
     * Use this typeface with entities returned by {@link #getAwesomeCodes(Context)}
     *
     * @param context  Context to get the assets from 
     * @return Typeface from the given context with the given name
     */
    public static Typeface getAwesomeTypeface(Context context ) throws FontNotFoundException {
        if (mFont == null) {
            try {
                mFont = Typeface.createFromAsset(context.getAssets(), Constants.FONT);
            } catch (Exception e) {
                throw new FontNotFoundException(e.getMessage());
            }
        }
        return mFont;
    }

    /**
     * Used to retrieve Entities used to map font-awesome symbols
     * as mapped on font-awesome 4.5.0 cheatsheet
     *
     * @param ctx Context to get the symbols from
     * @return  ArrayList of all font-awesome unicodes
     */
    public static ArrayList<String> getAwesomeCodes(Context ctx) {
        if (mAllIcons == null) {
            mAllIcons = new ArrayList();
            mAllIcons = new ArrayList<>(Arrays.asList(ctx.getResources().getStringArray(R.array.all_icons)));
        }
        return mAllIcons;
    }
    /**
     * Used to retrieve Entities'position used when mapping
     * font-awesome codes.
     *
     * @param ctx
     * @return ArrayList of all font-awesome icons
     */
    public static ArrayList<String> getAwesomeNames(Context ctx) {
        if (mAllIconNames == null) {
            mAllIconNames = new ArrayList();
            mAllIconNames = new ArrayList<>(Arrays.asList(ctx.getResources().getStringArray(R.array.all_icon_names)));
        }
        return mAllIconNames;
    }

}
