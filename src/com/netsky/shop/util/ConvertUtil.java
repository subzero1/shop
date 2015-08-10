package com.netsky.shop.util;

public class ConvertUtil {
	/**
	 * ��Ŀ���Ӧת��ΪInteger <br>
	 * ���Ŀ��������ת����ת��������ת���߷���-1
	 * 
	 * @param arg
	 * @return Integer
	 */
	public static Integer toInteger(Object arg) {
		if (arg instanceof Integer) {
			return (Integer) arg;
		} else if (arg instanceof String) {
			String tmpStr = (String) arg;
			if (tmpStr != null && tmpStr.matches("^0|[+,-]?[1-9][0-9]{0,8}$")) {
				return new Integer(tmpStr);
			}
		} else if (arg instanceof Long) {
			Long tmpLong = (Long) arg;
			if (tmpLong < Integer.MAX_VALUE && tmpLong > Integer.MIN_VALUE) {
				return tmpLong.intValue();
			}
		}
		return new Integer(-1);
	}

	/**
	 * ��Ŀ���Ӧת��ΪInteger <br>
	 * ���Ŀ��������ת����ת��������ת���߷��� defaultValue
	 * 
	 * @param arg
	 * @param defaultValue
	 * @return Integer
	 */
	public static Integer toInteger(Object arg, Integer defaultValue) {
		if (arg instanceof Integer) {
			return (Integer) arg;
		} else if (arg instanceof String) {
			String tmpStr = (String) arg;
			if (tmpStr != null && tmpStr.matches("^0|[+,-]?[1-9][0-9]{0,8}$")) {
				return new Integer(tmpStr);
			}
		} else if (arg instanceof Long) {
			Long tmpLong = (Long) arg;
			if (tmpLong < Integer.MAX_VALUE && tmpLong > Integer.MIN_VALUE) {
				return tmpLong.intValue();
			}
		}
		return defaultValue;
	}

	/**
	 * ��Ŀ���Ӧת��ΪLong <br>
	 * ���Ŀ��������ת����ת��������ת���߷���-1
	 * 
	 * @param arg
	 * @return Long
	 */
	public static Long toLong(Object arg) {
		if (arg instanceof Long) {
			return (Long) arg;
		} else if (arg instanceof String) {
			String tmpStr = (String) arg;
			if (tmpStr != null && tmpStr.matches("^0|[+,-]?[1-9][0-9]{0,17}$")) {
				return new Long(tmpStr);
			}
		} else if (arg instanceof Integer) {
			Long tmpLong = (Long) arg;
			return tmpLong;
		}
		return new Long(-1);
	}

	/**
	 * ��Ŀ���Ӧת��ΪLong <br>
	 * ���Ŀ��������ת����ת��������ת���߷��� defaultValue
	 * 
	 * @param arg
	 * @param defaultValue
	 * @return Long
	 */
	public static Long toLong(Object arg, Long defaultValue) {
		if (arg instanceof Long) {
			return (Long) arg;
		} else if (arg instanceof String) {
			String tmpStr = (String) arg;
			if (tmpStr != null && tmpStr.matches("^0|[+,-]?[1-9][0-9]{0,17}$")) {
				return new Long(tmpStr);
			}
		} else if (arg instanceof Integer) {
			Long tmpLong = (Long) arg;
			return tmpLong;
		}
		return defaultValue;
	}

	/**
	 * ��Ŀ�����ת����String
	 * 
	 * @param arg
	 * @return String
	 */
	public static String toString(Object arg) {
		if (arg == null) {
			return "";
		} else if (arg instanceof String) {
			return (String) arg;
		} else {
			return arg.toString();
		}
	}

	/**
	 * ��Ŀ�����ת����String,���Ϊ�շ��ء�defaultValue
	 * 
	 * @param arg
	 * @param defaultValue
	 * @return String
	 */
	public static String toString(Object arg, String defaultValue) {
		if (arg == null) {
			return defaultValue;
		} else if (arg instanceof String) {
			return (String) arg;
		} else {
			return arg.toString();
		}
	}

	/**
	 * ��Ŀ���Ӧת��ΪLong <br>
	 * ���Ŀ��������ת����ת��������ת���߷���-1
	 * 
	 * @param arg
	 * @return Long
	 */
	public static Double toDouble(Object arg) {
		if (arg instanceof Double) {
			return (Double) arg;
		} else if (arg instanceof String) {
			String tmpStr = (String) arg;
			// if (tmpStr != null &&
			// tmpStr.matches("^0|[+,-]?[1-9][0-9]{0,17}(\\.)*\\d*$")) {
			if (tmpStr != null) {
				Double d = null;
				try {
					d = new Double(tmpStr);
				} catch (Exception e) {
					// TODO Auto-generated catch block
//					System.out.println("EXCEPTION IN CONVERUTIL.TODOUBLE(OBJECT)\n" + e);
					d = -1D;
				}
				return d;
			}
		} else if (arg instanceof Integer) {
			Double tmpDouble = new Double((Integer)arg);
			return tmpDouble;
		} else if (arg instanceof java.math.BigDecimal) {
			return ((java.math.BigDecimal) arg).doubleValue();
		} else if (arg instanceof java.math.BigInteger) {
			return ((java.math.BigInteger) arg).doubleValue();
		}
		return new Double(-1);
	}

	/**
	 * ��Ŀ���Ӧת��ΪLong <br>
	 * ���Ŀ��������ת����ת��������ת���߷��� defaultValue
	 * 
	 * @param arg
	 * @param defaultValue
	 * @return Long
	 */
	public static Double toDouble(Object arg, Double defaultValue) {
		if (arg instanceof Double) {
			return (Double) arg;
		} else if (arg instanceof String) {
			String tmpStr = (String) arg;
			// if (tmpStr != null &&
			// tmpStr.matches("^0|[+,-]?[1-9][0-9]{0,17}(\\.)*\\d*$")) {
			if (tmpStr != null) {
				Double d = defaultValue;
				try {
					d = new Double(tmpStr);
				} catch (Exception e) {
					// TODO Auto-generated catch block
//					System.out.println("EXCEPTION IN CONVERUTIL.TODOUBLE(OBJECT,DOUBLE)\n" + e);
					d = defaultValue;
				}
				return d;
			}
		} else if (arg instanceof Integer) {
			Double tmpDouble = new Double((Integer)arg);
			return tmpDouble;
		} else if (arg instanceof java.math.BigDecimal) {
			return ((java.math.BigDecimal) arg).doubleValue();
		}
		return defaultValue;
	}

	public static void main(String[] args) {
		System.out.println(ConvertUtil.toDouble("-1",0.01));
	}
}
