package tech.moheng.chain.utils.utils;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * MOHENG unit conversion functions.
 */
public final class Convert {
    private Convert() { }

    public static BigDecimal fromSha(String number, Unit unit) {
        return fromSha(new BigDecimal(number), unit);
    }

    public static BigDecimal fromSha(BigDecimal number, Unit unit) {
        return number.divide(unit.getWeiFactor());
    }

    public static BigDecimal toSha(String number, Unit unit) {
        return toSha(new BigDecimal(number), unit);
    }

    public static BigDecimal toSha(BigDecimal number, Unit unit) {
        return number.multiply(unit.getWeiFactor());
    }
    
    public static BigDecimal hexToNumber(String hex) {
    	BigDecimal bigBalance = new BigDecimal(new BigInteger(hex.substring(2), 16));
        return bigBalance;
    }
    
    public static String intToHex(Integer number) {
        return "0x" + Integer.toHexString(number);
    }
    
    /**
	 * 字符串转换成为16进制(无需Unicode编码)
	 * 
	 * @param str
	 * @return
	 */
	public static String strToHexStr(String str) {
		char[] chars = "0123456789ABCDEF".toCharArray();
		StringBuilder sb = new StringBuilder("");
		byte[] bs = str.getBytes();
		int bit;
		for (int i = 0; i < bs.length; i++) {
			bit = (bs[i] & 0x0f0) >> 4;
			sb.append(chars[bit]);
			bit = bs[i] & 0x0f;
			sb.append(chars[bit]);
			// sb.append(' ');
		}
		return sb.toString().trim();
	}
	
	/**
	 * 16进制直接转换成为字符串(无需Unicode解码)
	 * @param hexStr
	 * @return
	 */
	public static String hexStrToStr(String hexStr) {
	    String str = "0123456789ABCDEF";
	    char[] hexs = hexStr.toCharArray();
	    byte[] bytes = new byte[hexStr.length() / 2];
	    int n;
	    for (int i = 0; i < bytes.length; i++) {
	        n = str.indexOf(hexs[2 * i]) * 16;
	        n += str.indexOf(hexs[2 * i + 1]);
	        bytes[i] = (byte) (n & 0xff);
	    }
	    return new String(bytes);
	}
    
    public enum Unit {
        WEI("wei", 0),
        KWEI("kwei", 3),
        MWEI("mwei", 6),
        GWEI("gwei", 9),
        SZABO("szabo", 12),
        FINNEY("finney", 15),
        MC("mc", 18),
        KMC("kmc", 21),
        MMC("mmc", 24),
        GMC("gmc", 27);

        private String name;
        private BigDecimal weiFactor;

        Unit(String name, int factor) {
            this.name = name;
            this.weiFactor = BigDecimal.TEN.pow(factor);
        }

        public BigDecimal getWeiFactor() {
            return weiFactor;
        }

        @Override
        public String toString() {
            return name;
        }

        public static Unit fromString(String name) {
            if (name != null) {
                for (Unit unit : Unit.values()) {
                    if (name.equalsIgnoreCase(unit.name)) {
                        return unit;
                    }
                }
            }
            return Unit.valueOf(name);
        }
    }
}
