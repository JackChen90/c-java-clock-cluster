package indi.jackie.clock.cluster.utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Md5Util {
    static Logger logger = LoggerFactory.getLogger(Md5Util.class);

    public Md5Util() {
    }

    public static String getMd5(String origin) {
        String pwd;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(origin.getBytes());
            pwd = (new BigInteger(1, md.digest())).toString(16);
        } catch (NoSuchAlgorithmException var3) {
            logger.error("=== md5 getMd5 error ===, origin:{}, error:{}", origin, var3.getMessage());
            pwd = null;
        }

        return pwd;
    }
}
