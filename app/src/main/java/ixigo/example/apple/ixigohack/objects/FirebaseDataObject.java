package ixigo.example.apple.ixigohack.objects;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import ixigo.example.apple.ixigohack.objects.placePicker.PlacePickerResponse;

/**
 * Created by apple on 09/04/17.
 */

public class FirebaseDataObject implements Comparable<FirebaseDataObject>, Parcelable {

    String image;
    String name;
    Integer startHour, starMin, endHour, endMin;
    String firebaseKey;
    int dayPos;

    public FirebaseDataObject() {

    }

    public FirebaseDataObject(PlacePickerResponse.PlacesToVisit data, int positionFragment) {
        this.image = data.getKeyImageUrl();
        this.name = data.getName();
        this.startHour = data.getActivityStartHour();
        this.starMin = data.getActivityStartMinute();
        this.endHour = data.getActivityEndHour();
        this.endMin = data.getActivityEndMinute();
        this.dayPos = positionFragment;
    }

    public FirebaseDataObject(String image, String name, Integer startHour, Integer starMin, Integer endHour, Integer endMin, String firebaseKey, int dayPos) {
        this.image = image;
        this.name = name;
        this.startHour = startHour;
        this.starMin = starMin;
        this.endHour = endHour;
        this.endMin = endMin;
        this.firebaseKey = firebaseKey;
        this.dayPos = dayPos;
    }

    protected FirebaseDataObject(Parcel in) {
        image = in.readString();
        name = in.readString();
        firebaseKey = in.readString();
        startHour = (Integer) in.readValue(Integer.class.getClassLoader());
        starMin = (Integer) in.readValue(Integer.class.getClassLoader());
        endHour = (Integer) in.readValue(Integer.class.getClassLoader());
        endMin = (Integer) in.readValue(Integer.class.getClassLoader());
        dayPos = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(image);
        dest.writeString(name);
        dest.writeString(firebaseKey);
        dest.writeValue(startHour);
        dest.writeValue(starMin);
        dest.writeValue(endHour);
        dest.writeValue(endMin);
        dest.writeInt(dayPos);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<FirebaseDataObject> CREATOR = new Creator<FirebaseDataObject>() {
        @Override
        public FirebaseDataObject createFromParcel(Parcel in) {
            return new FirebaseDataObject(in);
        }

        @Override
        public FirebaseDataObject[] newArray(int size) {
            return new FirebaseDataObject[size];
        }
    };

    public String getDisplayedActivityTime() {
        return startHour + ":" + starMin + " to " + endHour + ":" + endMin;
    }

    public String getFirebaseKey() {
        return firebaseKey;
    }

    public void setFirebaseKey(String firebaseKey) {
        this.firebaseKey = firebaseKey;
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

    public Integer getStartHour() {
        return startHour;
    }

    public void setStartHour(Integer startHour) {
        this.startHour = startHour;
    }

    public int getDayPos() {
        return dayPos;
    }

    public void setDayPos(int dayPos) {
        this.dayPos = dayPos;
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

    @Override
    public int compareTo(@NonNull FirebaseDataObject o) {
        try {
            return Integer.compare(o.getStartHour(), getStartHour());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
