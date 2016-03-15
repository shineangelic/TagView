package cuneyt.example.model;

import java.util.ArrayList;
import java.util.Random;


/**
 * Created by Cuneyt on 21.8.2015.
 */
public class TagClass {

    String code;
    String name;
    String color;

    public TagClass() {

    }

    public TagClass(String sinif, String name) {
        this.code = sinif;
        this.name = name;
        this.color = getRandomColor();

    }

    public String getRandomColor() {
        ArrayList<String> colors = new ArrayList<>();
        colors.add("#ED7D99");
        colors.add("#00B0F0");
        colors.add("#FF00FF");
        colors.add("#D0CECE");
        colors.add("#00B050");
        colors.add("#9999FF");
        colors.add("#FF5FC6");
        colors.add("#FFC0FF");
        colors.add("#7F7F7F");
        colors.add("#48DDFF");

        return colors.get(new Random().nextInt(colors.size()));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSinif() {
        return code;
    }

    public void setSinif(String code) {
        this.code = code;
    }


}
