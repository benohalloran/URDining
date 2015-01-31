package io.ohalloran.urdining.data;

/**
 * Created by Ben on 1/31/2015.
 */
public enum DiningHall {
    DANFORTH, DOUGLASS;

    public String titleCase() {
        String raw = toString();
        String tail = raw.substring(1).toLowerCase();
        return raw.charAt(0) + tail;
    }

    public int getIndex() {
        DiningHall[] halls = values();
        for (int i = 0; i < halls.length; i++)
            if (this == halls[i])
                return i;
        return -1; //will never reach here
    }

    public static DiningHall getEnum(String s) {
        return DiningHall.valueOf(s.toUpperCase());
    }

}
