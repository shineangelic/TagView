package it.angelic.tagviewlib;

/**
 * Classic POJO for {@link SimpleTagView}'s data
 *
 * Created by shine@angelic.it on 14/03/2016.
 */
class SimpleTag {
    private boolean isDeletable;
    private String name;
    private int color;
    private int radius;

    public String getFontAwesomeCode() {
        return fontAwesomeCode;
    }

    public void setFontAwesomeCode(String fontAwesomeCode) {
        this.fontAwesomeCode = fontAwesomeCode;
    }

    private String fontAwesomeCode;

    public boolean isDeletable() {
        return isDeletable;
    }

    public void setDeletable(boolean isVisible) {
        this.isDeletable = isVisible;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }
}
