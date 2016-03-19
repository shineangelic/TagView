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



    public static int dipToPx(Context c, float dipValue) {
        DisplayMetrics metrics = c.getResources().getDisplayMetrics();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue, metrics);
    }

    public static double getColorLuminosity(int col) {
        return Color.red(col) * 0.299 + Color.green(col) * 0.587 + Color.blue(col) * 0.114;
    }

    /**
     * Returns the Typeface from the given context with the given name typeface
     *
     * @param context  Context to get the assets from
     * @param typeface name of the ttf file
     * @return Typeface from the given context with the given name
     */
    public static Typeface getTypeface(Context context, String typeface) {
        if (mFont == null) {
            mFont = Typeface.createFromAsset(context.getAssets(), typeface);
        }
        return mFont;
    }
    public static ArrayList<String> getAwesomeCodes(Context ctx) {
        if (mAllIcons == null) {
            mAllIcons = new ArrayList();
            mAllIcons=new ArrayList<>(Arrays.asList(ctx.getResources().getStringArray(R.array.all_icons)));
        }
        return mAllIcons;
    }

    public static ArrayList<String> getAwesomeNames(Context ctx) {
        if (mAllIconNames == null) {
            mAllIconNames = new ArrayList();
            mAllIconNames=new ArrayList<>(Arrays.asList(ctx.getResources().getStringArray(R.array.all_icon_names)));
        }
        return mAllIconNames;
    }

}
