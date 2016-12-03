/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package simulator.utils.modeltime;

/**
 *
 * @author Roman Vais
 */
public final class TimeStamp implements Comparable<TimeStamp>{

    public static final int SEC_IN_MIN = 60;
    public static final int SEC_IN_HOUR = 3600;
    public static final int SEC_IN_DAY = 86400;
    public static final int SEC_IN_MONTH = 2628000;
    public static final int SEC_IN_YEAR = 2628000 * 12; // 31536000

    private static final int[] DIVIDERS = {SEC_IN_YEAR, SEC_IN_MONTH, SEC_IN_DAY, SEC_IN_HOUR, SEC_IN_MIN, 1};
    public static final int YEAR = 0;
    public static final int MONTH = 1;
    public static final int DAY = 2;
    public static final int HOUR = 3;
    public static final int MIN = 4;
    public static final int SEC = 5;

    private int stamp;

    public TimeStamp() {
        this(null, 0, 0, 0, 0, 0, 0);
    }

    public TimeStamp(int year, int month, int day) {
        this(null, year, month, day, 0, 0, 0);
    }

    public TimeStamp(int year, int month, int day, int hour, int min) {
        this(null, year, month, day, hour, min, 0);
    }

    public TimeStamp(int year, int month, int day, int hour, int min, int sec) {
        this(null, year, month, day, hour, min, sec);
    }

    public TimeStamp(TimeStamp stamp) {
        this(stamp, 0, 0, 0, 0, 0, 0);
    }

    public TimeStamp(TimeStamp stamp, int inc) {
        this(stamp, 0, 0, 0, 0, 0, inc);
    }

    private TimeStamp(TimeStamp stamp, int... args) {
        this.stamp = 0;

        if (stamp != null) {
            this.stamp += stamp.getTimestamp();
        }

        for (int i = 0; i < DIVIDERS.length && i < args.length; i++) {
            this.stamp += DIVIDERS[i] * args[i];
        }
    }

    public int getTimestamp() {
        return this.stamp;
    }

    public String getDate(int referenceYear) {
        Integer d[] = new Integer[DIVIDERS.length + 1];

        for(int i = 0; i < d.length; i++) {
            d[i] = getDatePart(i);
        }

        d[0] += referenceYear;

        return String.format("%4d_%2d_%2d %2d:%2d:%2d", d[YEAR], d[MONTH], d[DAY], d[HOUR], d[MIN], d[SEC]);
    }

    public Integer getDatePart(int part) {
        int rest = this.stamp;
        int result = 0;

        for (int i = 0; i <= part; i++) {
            result = rest / DIVIDERS[i];
            rest = rest % DIVIDERS[i];
        }

        return result;
    }


    @Override
    public String toString() {
        return this.getDate(0);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof TimeStamp)) {
            return false;
        }

        return (this.compareTo(TimeStamp.class.cast(o))) == 0;
    }

    @Override
    public int hashCode() {
        return this.getTimestamp();
    }

    @Override
    public int compareTo(TimeStamp t) {
        return this.stamp - t.getTimestamp();
    }



}
