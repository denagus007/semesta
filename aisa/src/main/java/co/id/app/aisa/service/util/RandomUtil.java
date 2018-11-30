package co.id.app.aisa.service.util;

import org.apache.commons.lang3.RandomStringUtils;

/**
 * Utility class for generating random Strings.
 */
public final class RandomUtil {

    private static final int DEF_COUNT = 20;

    private RandomUtil() {
    }

    /**
     * Generate a password.
     *
     * @return the generated password
     */
    public static String generatePassword() {
        return RandomStringUtils.randomAlphanumeric(DEF_COUNT);
    }

    /**
     * Generate an activation key.
     *
     * @return the generated activation key
     */
    public static String generateActivationKey() {
        return RandomStringUtils.randomNumeric(DEF_COUNT);
    }

    /**
     * Generate a reset key.
     *
     * @return the generated reset key
     */
    public static String generateResetKey() {
        return RandomStringUtils.randomNumeric(DEF_COUNT);
    }
    
    /**
     * 
     * @return generated phone number for new registered (if blank from caller)
     */
    public static String generatePhone() {
    	return RandomStringUtils.randomNumeric(12);
    }
    
    /**
     * 
     * @return generated email from new registered (if blank from caller)
     */
    public static String generateEmail() {
    	return RandomStringUtils.randomAlphabetic(4)+"@"+RandomStringUtils.randomAlphabetic(4)+".com";
    }
    
    /**
     * 
     * @return generated login / aka mem_no
     */
    public static String generateMemno() {
    	return RandomStringUtils.randomNumeric(10);
    }
}
