package ixigo.example.apple.ixigohack.objects;

/**
 * Created by apple on 09/04/17.
 */

public class FirebaseDataObject {

    String image;
    String name;
    int startHour, starMin, endHour, endMin;

    public FirebaseDataObject(String image, String name, int startHour, int starMin, int endHour, int endMin) {
        this.image = image;
        this.name = name;
        this.startHour = startHour;
        this.starMin = starMin;
        this.endHour = endHour;
        this.endMin = endMin;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStartHour() {
        return startHour;
    }

    public void setStartHour(int startHour) {
        this.startHour = startHour;
    }

    public int getStarMin() {
        return starMin;
    }

    public void setStarMin(int starMin) {
        this.starMin = starMin;
    }

    public int getEndHour() {
        return endHour;
    }

    public void setEndHour(int endHour) {
        this.endHour = endHour;
    }

    public int getEndMin() {
        return endMin;
    }

    public void setEndMin(int endMin) {
        this.endMin = endMin;
    }
}
