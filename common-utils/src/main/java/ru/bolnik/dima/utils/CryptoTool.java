package ru.bolnik.dima.utils;

import org.hashids.Hashids;

public class CryptoTool {

    private final Hashids hashids;

    public CryptoTool(String salt) {
        int minHashLen = 10;
        hashids = new Hashids(salt, minHashLen);
    }

    public String hashOf(Long value) {
        return hashids.encode(value);
    }

    public Long idOf(String value) {
        long[] res = hashids.decode(value);
        if (res != null && res.length > 0) {
            return res[0];
        }
        return null;
    }
}
