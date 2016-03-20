package cuneyt.example.model;

import java.util.ArrayList;
import java.util.Random;


/**
 * Inner support POJO to keep TAG's data
 *
 * Created by Cuneyt on 21.8.2015.
 */
public class TagClass {

    private String code;
    private String name;
    private String color;

    public TagClass() {

    }

    public TagClass(String sinif, String name) {
        this.code = sinif;
        this.name = name;
        this.color = getRandomColor();

    }

    public String getRandomColor() {
        ArrayList<String> colors = new ArrayList<>();
        colors.add("#997D99");
        colors.add("#00B0F0");
        colors.add("#aa66cc");//Holo purple
        colors.add("#330033");
        colors.add("#BB5050");
        colors.add("#9999FF");
        colors.add("#99cc66");//colore verde gatta
        colors.add("#FFC0FF");
        colors.add("#444444");
        colors.add("#FFCC81");

        //aa66cc


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
