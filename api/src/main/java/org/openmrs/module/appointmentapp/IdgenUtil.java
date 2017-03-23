package org.openmrs.module.appointmentapp;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mstan on 23/03/2017.
 */
public class IdgenUtil {
    protected static Log log = LogFactory.getLog(IdgenUtil.class);

    public IdgenUtil() {
    }

    public static String convertToBase(long n, char[] baseCharacters, int padToLength) {
        StringBuilder base = new StringBuilder();

        for(long numInBase = (long)baseCharacters.length; n > 0L; n /= numInBase) {
            int index = (int)(n % numInBase);
            base.insert(0, baseCharacters[index]);
        }

        while(base.length() < padToLength) {
            base.insert(0, baseCharacters[0]);
        }

        return base.toString();
    }

    public static long convertFromBase(String s, char[] baseCharacters) {
        long ret = 0L;
        char[] inputChars = s.toCharArray();
        long multiplier = 1L;

        for(int i = inputChars.length - 1; i >= 0; --i) {
            int index = -1;

            for(int j = 0; j < baseCharacters.length; ++j) {
                if(baseCharacters[j] == inputChars[i]) {
                    index = j;
                }
            }

            if(index == -1) {
                throw new RuntimeException("Invalid character " + inputChars[i] + " found in " + s);
            }

            ret += multiplier * (long)index;
            multiplier *= (long)baseCharacters.length;
        }

        return ret;
    }

    public static List<String> getIdsFromStream(InputStream stream) {
        ArrayList contents = new ArrayList();
        BufferedReader r = null;

        try {
            r = new BufferedReader(new InputStreamReader(stream));

            for(String e = r.readLine(); e != null; e = r.readLine()) {
                contents.add(e.trim());
            }
        } catch (Exception var11) {
            throw new RuntimeException("Error retrieving IDs from stream", var11);
        } finally {
            if(r != null) {
                try {
                    r.close();
                } catch (Exception var10) {
                    log.warn("Error closing reader: ", var10);
                }
            }

        }

        return contents;
    }
}
