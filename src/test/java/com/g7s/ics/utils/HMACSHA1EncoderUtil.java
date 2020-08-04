package com.g7s.ics.utils;

import sun.misc.BASE64Encoder;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.SignatureException;

/**
 * @Author: zhangwenping
 * @Description:
 * @Date: Create in 16:59 2020-08-03
 */
public class HMACSHA1EncoderUtil {
    private static final String HMAC_SHA1_ALGORITHM = "HmacSHA1";
    /**
     * Computes RFC 2104-compliant HMAC signature.
     *
     * @param key
     *            The signing key.
     * @param data
     *            The data to be signed.
     *
     *
     * @return The Base64-encoded RFC 2104-compliant HMAC signature.
     * @throws SignatureException
     *             when signature generation fails
     */
    public static String calculateRFC2104HMAC(String key, String data) throws SignatureException {
        String result;
        try {
            // get an hmac_sha1 key from the raw key bytes
            SecretKeySpec signingKey = new SecretKeySpec(key.getBytes(), HMAC_SHA1_ALGORITHM);
            // get an hmac_sha1 Mac instance and initialize with the signing key
            Mac mac = Mac.getInstance(HMAC_SHA1_ALGORITHM);
            mac.init(signingKey);
            // compute the hmac on input data bytes
            byte[] rawHmac = mac.doFinal(data.getBytes("UTF-8"));
            // base64-encode the hmac
            BASE64Encoder base64Encoder = new BASE64Encoder();
            result = base64Encoder.encode(rawHmac);
        } catch (Exception e) {
            throw new SignatureException("Failed to generate HMAC : " + e.getMessage());
        }
        return result;
    }

}
