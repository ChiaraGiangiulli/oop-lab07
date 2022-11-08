package it.unibo.nestedenum;

import java.util.Comparator;
import java.util.Objects;

/**
 * Implementation of {@link MonthSorter}.
 */
public final class MonthSorterNested implements MonthSorter {

    public enum Month{
        JANUARY(31),
        FEBRUARY(28),
        MARCH(31),
        APRIL(30),
        MAY(31),
        JUNE(30),
        JULY(31),
        AUGUST(31),
        SEPTEMBER(30),
        OCTOBER(31),
        NOVEMBER(30),
        DECEMBER(31);

        private int days; 
        
        Month(int n){
            this.days=n;
        }

        static Month fromString(String s){
            Objects.requireNonNull(s);
            s.toUpperCase();
            char character;
            Month match = null;
            try {
                return valueOf(s);
            } catch (IllegalArgumentException exc){
                for (final Month month: values()) {
                    character = month.toString().charAt(0);
                    if(s.startsWith(String.valueOf(character))){
                        if(match == null){
                            match = month;
                        }
                        else{
                            throw new IllegalArgumentException(
                                s + " is ambiguous: both " + match + " and " + month + " would be valid matches", exc);
                        }
                    }
                }
                if (match == null) {
                    throw new IllegalArgumentException("No matching months for " + s, exc);
                }
                return match;
            }
        }
    }

    private static class SortByDays implements Comparator<String> {
        @Override
        public int compare(String arg0, String arg1) {
            Month m0 = Month.fromString(arg0);
            Month m1 = Month.fromString(arg1);
            return Integer.compare(m0.days, m1.days);
        }
    }

    private static class SortByMonthOrder implements Comparator<String> {

        @Override
        public int compare(String arg0, String arg1) {
            return Month.fromString(arg0).compareTo(Month.fromString(arg1));
        }
    }

    @Override
    public Comparator<String> sortByDays() {
        return new SortByDays();
    }

    @Override
    public Comparator<String> sortByOrder() {
        return new SortByMonthOrder();
    }
}
