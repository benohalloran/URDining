package io.ohalloran.urdining.data;

/**
 * Created by Ben on 1/31/2015.
 */
public enum DiningHall {
    DANFORTH, DOUGLASS;

    public String titleCase() {
        char[] raw = toString().toCharArray();
        for (int i = 1; i < raw.length; i++)
            raw[i] -= 'A';
        return new String(raw);
    }

    public int getIndex() {
        switch (this) {
            case DANFORTH:
                return 0;
            case DOUGLASS:
                return 1;
        }
        throw new IllegalStateException(this + " is not not defined in getIndex");
    }

    public static DiningHall getEnum(String s) {
        return DiningHall.valueOf(s.toUpperCase());
    }

}
