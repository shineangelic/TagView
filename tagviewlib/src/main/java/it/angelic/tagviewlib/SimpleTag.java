package it.angelic.tagviewlib;

/**
 * Created by shine@angelic.it on 14/03/2016.
 */
class SimpleTag {
    private boolean isVisible;
    private String name;
    private int color;
    private int radius;

    public boolean isVisible() {
        return isVisible;
    }

    public void setIsVisible(boolean isVisible) {
        this.isVisible = isVisible;
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
