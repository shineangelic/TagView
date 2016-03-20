package it.angelic.tagviewlib;

import android.content.res.Resources;

/**
 * Thrown when some awesome font symbol is not found
 *
 * Created by shine@angelic.it on 19/03/2016.
 */
public class FontNotFoundException extends Resources.NotFoundException {
    public FontNotFoundException(String s) {
        super(s);
    }
}
