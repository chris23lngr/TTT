package de.z1up.ttt.util;

public class MathUtils {

    public static boolean isPointValue(double d) {

        String s = new Double(d).toString();

        for(int i = 0; i < s.length(); i ++) {

            if(s.charAt(i) == '.') {

                String sub = s.substring(i+1);

                if(Integer.parseInt(sub) != 0) {
                    return true;
                }
            }
        }
        return false;
    }

    public static int getAfter(double d) {

        String s = new Double(d).toString();

        for (int i = 0; i < s.length(); i++) {

            if (s.charAt(i) == '.') {
                String sub = s.substring(i + 1);
                return Integer.parseInt(sub);
            }
        }
        return 0;
    }

}
