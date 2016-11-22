package mctemplates;

import com.sun.xml.internal.bind.v2.schemagen.Util;

/**
 *
 * @author swacisko
 */
public class LongestCommonSubstring {

    public static String getLongestCommonSubstring(String s1, String s2) {

        String res = "";

        for (int i = 0; i < s1.length(); i++) {
            for (int k = 0; k < s2.length(); k++) {
                if( s1.charAt(i) != s2.charAt(k) ) continue;
                
                int p = 0;
                String temp = "";

                while (i+p < s1.length() && k+p < s2.length()) {
                    if ( Util.equalsIgnoreCase( ""+s1.charAt(i + p), ""+s2.charAt(k + p) )) {
                        temp += s1.charAt(i + p);
                        p++;
                    } else {
                        break;
                    }
                }
                
                if( temp.length() > res.length() ){
                    res = temp;
                }
            }

        }
        
        return res;
    }

}
