package it.angelic.tagviewlib;

import android.content.Context;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.util.TypedValue;

public class Utils {

	public static int dipToPx(Context c,float dipValue) {
		DisplayMetrics metrics = c.getResources().getDisplayMetrics();
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue, metrics);
	}

	public static double getColorLuminosity(int col) {
		return Color.red(col)*0.299 + Color.green(col)*0.587 + Color.blue(col)*0.114;
	}

}
