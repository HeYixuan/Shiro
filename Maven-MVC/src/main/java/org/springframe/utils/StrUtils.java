package org.springframe.utils;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.CodeSource;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.ProtectionDomain;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * @author Administrator
 * Modified By: JoanneYang
 * Modified Date: 2012-01-31
 */
@SuppressWarnings("unchecked")
public class StrUtils {
	private static Object initLock = new Object();

	private static MessageDigest digest = null;

	private static final String commonWords[] = { "a", "and", "as", "at", "be",
			"do", "i", "if", "in", "is", "it", "so", "the", "to" };

	private static Map commonWordsMap = null;

	private static Random randGen = null;

	private static char numbersAndLetters[] = null;

	private static String regex = "((http://)?([a-z]+[.])|(www.))\\w+[.]([a-z]{2,4})?[[.]([a-z]{2,4})]+((/[\\S&&[^,;\u4E00-\u9FA5]]+)+)?([.][a-z]{2,4}+|/?)";
	private static String A1 = " <a target=\"_bank\" href={0}>";
	private static String A2 = " </a>";

	public StrUtils() {
	}

	public static String toHref(String title) {
		StringBuffer sb = new StringBuffer(title);
		Pattern pat = Pattern.compile(regex);
		Matcher mat = pat.matcher(title);
		int index = 0;
		int index1 = 0;
		while (mat.find()) {
			String url = mat.group();
			if (url.indexOf("http://") != 0)
				url = "http://" + url;
			Object obj[] = { "'" + url + "'" };
			String a = MessageFormat.format(A1, obj);
			int l = a.length();
			index += index1;
			sb.insert(mat.start() + index, a);
			index += l;
			sb.insert((mat.end()) + index, A2);
			index1 = A2.length();
		}
		return sb.toString();
	}

	// 检测字符串是否存在中文字符
	public static boolean isChineseChar(String s) {
		boolean flag = false;
		if (s == null)
			return true;
		byte abyte0[] = s.getBytes();
		for (int i = 0; i < abyte0.length; i++) {
			if (abyte0[i] >= 0)
				continue;
			flag = true;
			break;
		}

		return flag;
	}

	// String[] to String
	public static String arrStrToStr(String[] s) {
		StringBuffer sb = new StringBuffer("");
		if (s == null) {
			return "";
		}
		for (int i = 0; i < s.length; i++) {
			if (i == 0)
				sb.append(s[i]);
			else
				sb.append(",");
			sb.append(s[i]);
		}
		return sb.toString();
	}

	// 日期合法判断
	public static final boolean checkDate(int y, int m, int d) {
		boolean result = true;
		if (y < 1900 || y > 2500 || m < 1 || m > 12)
			return false;
		if (((y % 4) == 0 && (y % 100) != 0) || ((y % 400) == 0)) {
			if ((m == 2) && (d > 29)) {
				result = false;
			}
			switch (m) {
			case 4:
				if (d > 30) {
					result = false;
					break;
				}
			case 6:
				if (d > 30) {
					result = false;
					break;
				}
			case 9:
				if (d > 30) {
					result = false;
					break;
				}
			case 11:
				if (d > 30) {
					result = false;
					break;
				}
			}
		} else {

			switch (m) {
			case 2:

				if (d > 28) {
					result = false;
					break;
				}
			case 4:
				if (d > 30) {
					result = false;
					break;
				}
			case 6:
				if (d > 30) {
					result = false;
					break;
				}
			case 9:
				if (d > 30) {
					result = false;
					break;
				}
			case 11:
				if (d > 30) {
					result = false;
					break;
				}
			}
		}
		return result;
	}

	// 把空字符串转换为empty
	public static final String nullToEmptyOfStr(String s) {
		return formatSqlStr(s);
	}

	// 把空字符串转换为empty
	public static final String toStr(String s) {
		if (s != null)
			return s.trim();
		else
			return "";
	}

	// 把空字符串转换为empty
	public static final String formatSqlStr(String s) {
		if (s != null) {
			s = s.trim();
			s = s.replaceAll("'", "‘");
			return s;
		} else
			return "";
	}

	// 把不为空字符串转换为其他字符串
	public static final String strToOtherStr(String s, String s1) {
		if (s == null || s.length() < 1)
			return "";
		else
			return s1;
	}

	// 把字符串转换为数字
	public static final int strToInt(String s, int i) {

		if (s == null)
			return i;
		if (!strIsDigital(s) || s.length() < 1)
			return i;
		try {
			return Integer.parseInt(s);
		} catch (Exception e) {
			return 0;
		}

	}

	// 把字符串转换为数字
	public static final int toInt(String s, int i) {

		if (s == null)
			return i;
		if (!strIsDigital(s) || s.length() < 1)
			return i;
		try {
			return Integer.parseInt(s);
		} catch (Exception e) {
			return 0;
		}

	}

	public static final String escapeErrorChar(String s) {
		String s1 = null;
		s1 = s;
		if (s1 == null) {
			return s1;
		} else {
			s1 = replace(s1, "\\", "\\\\");
			s1 = replace(s1, "\"", "\\\"");
			return s1;
		}
	}

	// 检测字符串是否为数字串
	public static final boolean strIsDigital(String s) {
		boolean flag1 = true;
		if (s == null) {
			return false;
		}
		char ac[] = s.toCharArray();
		for (int i = 0; i < ac.length;) {
			if (!Character.isDigit(ac[i]))
				flag1 = false;
			break;
		}

		return flag1;
	}

	// 检测身份证
	public static final boolean checkIdCard(String s, String s1) {
		boolean flag = true;
		if (s.length() != 15 && s.length() != 18 || !isDate(s1))
			flag = false;
		else if (s.length() == 15) {
			if (!strIsDigital(s)) {
				flag = false;
			} else {
				String s6 = "19" + s.substring(6, 8);
				String s8 = s.substring(8, 10);
				String s10 = s.substring(10, 12);
				String s12 = s6 + "-" + s8 + "-" + s10;
				if (!s12.equals(s1))
					flag = false;
			}
		} else if (s.substring(17, 18).equals("X")
				|| s.substring(17, 18).equals("x")) {
			if (!strIsDigital(s.substring(0, 18)))
				flag = false;
		} else if (!strIsDigital(s)) {
			flag = false;
		} else {
			String s7 = s.substring(6, 10);
			String s9 = s.substring(10, 12);
			String s11 = s.substring(12, 14);
			String s13 = s7 + "-" + s9 + "-" + s11;
			if (!s13.equals(s1))
				flag = false;
		}
		return flag;
	}

	// 日期判断
	public static boolean isDate(String s) {
		boolean flag = false;
		DateFormat dateformat = DateFormat.getDateInstance();
		if (s == null) {
			flag = false;
		} else {
			try {
				java.util.Date date = dateformat.parse(s);
				if (date != null)
					flag = true;
			} catch (Exception exception) {
				flag = false;
			}
		}
		return flag;
	}

	public static String formatInputDate(String s) {
		if (!isDate(s)) {
			return "1900-1-1";
		}
		return s;
	}

	public static String isInputError(int i, int j) {
		if ((i & j) > 0)
			return "Error";
		else
			return "True";
	}

	// 把字符串转换为向量
	public static Vector splite(String s, String s1) {
		Vector vector = new Vector();
		int i;
		for (; s.length() >= 0; s = s.substring(i + s1.length(), s.length())) {
			i = s.indexOf(s1);
			if (i < 0) {
				vector.addElement(s);
				break;
			}
			String s2 = s.substring(0, i);
			vector.addElement(s2);
		}

		return vector;
	}

	// 把字符串转换为向量
	public static List splitToList(String s, String s1) {
		List list = new ArrayList();
		int i;
		for (; s.length() >= 0; s = s.substring(i + s1.length(), s.length())) {
			i = s.indexOf(s1);
			if (i < 0) {
				list.add(s);
				break;
			}
			String s2 = s.substring(0, i);
			list.add(s2);
		}

		return list;
	}

	// 字符串替换 s 搜索字符串 s1 要查找字符串 s2 要替换字符串
	public static final String replace(String s, String s1, String s2) {
		if (s == null)
			return null;
		int i = 0;
		if ((i = s.indexOf(s1, i)) >= 0) {
			char ac[] = s.toCharArray();
			char ac1[] = s2.toCharArray();
			int j = s1.length();
			StringBuffer stringbuffer = new StringBuffer(ac.length);
			stringbuffer.append(ac, 0, i).append(ac1);
			i += j;
			int k;
			for (k = i; (i = s.indexOf(s1, i)) > 0; k = i) {
				stringbuffer.append(ac, k, i - k).append(ac1);
				i += j;
			}

			stringbuffer.append(ac, k, ac.length - k);
			return stringbuffer.toString();
		} else {
			return s;
		}
	}

	public static final String replaceIgnoreCase(String s, String s1, String s2) {
		if (s == null)
			return null;
		String s3 = s.toLowerCase();
		String s4 = s1.toLowerCase();
		int i = 0;
		if ((i = s3.indexOf(s4, i)) >= 0) {
			char ac[] = s.toCharArray();
			char ac1[] = s2.toCharArray();
			int j = s1.length();
			StringBuffer stringbuffer = new StringBuffer(ac.length);
			stringbuffer.append(ac, 0, i).append(ac1);
			i += j;
			int k;
			for (k = i; (i = s3.indexOf(s4, i)) > 0; k = i) {
				stringbuffer.append(ac, k, i - k).append(ac1);
				i += j;
			}

			stringbuffer.append(ac, k, ac.length - k);
			return stringbuffer.toString();
		} else {
			return s;
		}
	}

	public static final String replace(String s, String s1, String s2, int ai[]) {
		if (s == null)
			return null;
		int i = 0;
		if ((i = s.indexOf(s1, i)) >= 0) {
			int j = 0;
			j++;
			char ac[] = s.toCharArray();
			char ac1[] = s2.toCharArray();
			int k = s1.length();
			StringBuffer stringbuffer = new StringBuffer(ac.length);
			stringbuffer.append(ac, 0, i).append(ac1);
			i += k;
			int l;
			for (l = i; (i = s.indexOf(s1, i)) > 0; l = i) {
				j++;
				stringbuffer.append(ac, l, i - l).append(ac1);
				i += k;
			}

			stringbuffer.append(ac, l, ac.length - l);
			ai[0] = j;
			return stringbuffer.toString();
		} else {
			return s;
		}
	}

	public static final String formatInputStr(String s) {
		String s1 = s;
		s1 = formatSqlStr(s1);
		s1 = escapeHTMLTags(s1);
		return s1;
	}

	public static final int formatInputStr(String s, int i) {
		String s1 = s;
		int p;
		s1 = formatSqlStr(s1);
		s1 = escapeHTMLTags(s1);
		try {
			p = Integer.parseInt(s1);
		} catch (Exception e) {
			p = i;
		}
		return p;
	}

	public static final String escapeHTMLTags(String s) {
		if (s == null || s.length() == 0)
			return s;
		StringBuffer stringbuffer = new StringBuffer(s.length());
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c == '<')
				stringbuffer.append("&lt;");
			else if (c == '>')
				stringbuffer.append("&gt;");
			else
				stringbuffer.append(c);
		}

		return stringbuffer.toString();
	}

	public static final synchronized String hash(String s) {
		if (digest == null)
			try {
				digest = MessageDigest.getInstance("MD5");
			} catch (NoSuchAlgorithmException nosuchalgorithmexception) {
				System.err
						.println("Failed to load the MD5 MessageDigest. Jive will be unable to function normally.");
				nosuchalgorithmexception.printStackTrace();
			}
		if (s != null) {
			digest.update(s.getBytes());
			return toHex(digest.digest());
		}
		return "";
	}

	public static final String toHex(byte abyte0[]) {
		StringBuffer stringbuffer = new StringBuffer(abyte0.length * 2);
		for (int i = 0; i < abyte0.length; i++) {
			if ((abyte0[i] & 0xff) < 16)
				stringbuffer.append("0");
			stringbuffer.append(Long.toString(abyte0[i] & 0xff, 16));
		}

		return stringbuffer.toString();
	}

	public static final String[] toLowerCaseWordArray(String s) {
		if (s == null || s.length() == 0)
			return new String[0];
		StringTokenizer stringtokenizer = new StringTokenizer(s, " ,\r\n.:/\\+");
		String as[] = new String[stringtokenizer.countTokens()];
		for (int i = 0; i < as.length; i++)
			as[i] = stringtokenizer.nextToken().toLowerCase();

		return as;
	}

	public static final String[] removeCommonWords(String as[]) {
		try {
			if (commonWordsMap.isEmpty())
				synchronized (initLock) {
					if (commonWordsMap == null) {
						commonWordsMap = new HashMap();
						for (int i = 0; i < commonWords.length; i++)
							commonWordsMap.put(commonWords[i], commonWords[i]);

					}
				}
			ArrayList arraylist = new ArrayList(as.length);
			for (int j = 0; j < as.length; j++)
				if (!commonWordsMap.containsKey(as[j]))
					arraylist.add(as[j]);

			return (String[]) arraylist.toArray(new String[arraylist.size()]);
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static final String randomString(int i) {
		if (i < 1)
			return null;
		// if (randGen == null)
		synchronized (initLock) {
			if (randGen == null) {
				randGen = new Random();
				numbersAndLetters = "0123456789abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ"
						.toCharArray();
			}
		}
		char ac[] = new char[i];
		for (int j = 0; j < ac.length; j++)
			ac[j] = numbersAndLetters[randGen.nextInt(71)];

		return new String(ac);
	}

	public static final String chopAtWord(String s, int i) {
		if (s == null)
			return s;
		char ac[] = s.toCharArray();
		int j = s.length();
		if (i < j)
			j = i;
		for (int k = 0; k < j - 1; k++) {
			if (ac[k] == '\r' && ac[k + 1] == '\n')
				return s.substring(0, k);
			if (ac[k] == '\n')
				return s.substring(0, k);
		}

		if (ac[j - 1] == '\n')
			return s.substring(0, j - 1);
		if (s.length() < i)
			return s;
		for (int l = i - 1; l > 0; l--)
			if (ac[l] == ' ')
				return s.substring(0, l).trim();

		return s.substring(0, i);
	}

	public static final String highlightWords(String s, String as[], String s1,
			String s2) {
		if (s == null || as == null || s1 == null || s2 == null)
			return null;
		for (int i = 0; i < as.length; i++) {
			String s3 = s.toLowerCase();
			char ac[] = s.toCharArray();
			String s4 = as[i].toLowerCase();
			int j = 0;
			if ((j = s3.indexOf(s4, j)) >= 0) {
				int k = s4.length();
				StringBuffer stringbuffer = new StringBuffer(ac.length);
				boolean flag = false;
				char c = ' ';
				if (j - 1 > 0) {
					c = ac[j - 1];
					if (!Character.isLetter(c))
						flag = true;
				}
				boolean flag1 = false;
				char c1 = ' ';
				if (j + k < ac.length) {
					c1 = ac[j + k];
					if (!Character.isLetter(c1))
						flag1 = true;
				}
				if (flag && flag1 || j == 0 && flag1) {
					stringbuffer.append(ac, 0, j);
					if (flag && c == ' ')
						stringbuffer.append(c);
					stringbuffer.append(s1);
					stringbuffer.append(ac, j, k).append(s2);
					if (flag1 && c1 == ' ')
						stringbuffer.append(c1);
				} else {
					stringbuffer.append(ac, 0, j);
					stringbuffer.append(ac, j, k);
				}
				j += k;
				int l;
				for (l = j; (j = s3.indexOf(s4, j)) > 0; l = j) {
					boolean flag2 = false;
					char c2 = ac[j - 1];
					if (!Character.isLetter(c2))
						flag2 = true;
					boolean flag3 = false;
					if (j + k < ac.length) {
						c1 = ac[j + k];
						if (!Character.isLetter(c1))
							flag3 = true;
					}
					if (flag2 && flag3 || j + k == ac.length) {
						stringbuffer.append(ac, l, j - l);
						if (flag2 && c2 == ' ')
							stringbuffer.append(c2);
						stringbuffer.append(s1);
						stringbuffer.append(ac, j, k).append(s2);
						if (flag3 && c1 == ' ')
							stringbuffer.append(c1);
					} else {
						stringbuffer.append(ac, l, j - l);
						stringbuffer.append(ac, j, k);
					}
					j += k;
				}

				stringbuffer.append(ac, l, ac.length - l);
				s = stringbuffer.toString();
			}
		}

		return s;
	}

	public static final String escapeForXML(String s) {
		if (s == null || s.length() == 0)
			return s;
		char ac[] = s.toCharArray();
		StringBuffer stringbuffer = new StringBuffer(ac.length);
		for (int i = 0; i < ac.length; i++) {
			char c = ac[i];
			if (c == '<')
				stringbuffer.append("&lt;");
			else if (c == '>')
				stringbuffer.append("&gt;");
			else if (c == '"')
				stringbuffer.append("&quot;");
			else if (c == '&')
				stringbuffer.append("&amp;");
			else
				stringbuffer.append(c);
		}

		return stringbuffer.toString();
	}

	public static final String unescapeFromXML(String s) {
		s = replace(s, "&lt;", "<");
		s = replace(s, "&gt;", ">");
		s = replace(s, "&amp;", "&");
		return replace(s, "&quot;", "\"");
	}

	public static final String formatTextArea(String s, int i) {
		if (s == null)
			return "";
		String s1 = s.trim();
		Vector vector = null;
		int j = i;
		s1 = s1 + "\n";
		vector = splite(s1, "\n");
		// boolean flag = false;
		// boolean flag1 = false;
		// String s2 = "";
		// String s4 = "";
		// String s6 = "";
		char ac[] = s1.toCharArray();
		StringBuffer stringbuffer = new StringBuffer(ac.length);
		for (int k = 0; k < vector.size() - 1; k++) {
			String s5 = vector.elementAt(k).toString();
			s5 = replace(s5, "\r", "");
			if (s5.length() > j) {
				String s7 = s5;
				for (int l = 0; l < s5.length() / j; l++) {
					String s3 = s7.substring(0, j) + "\n";
					stringbuffer.append(s3.toCharArray(), 0,
							s3.toCharArray().length);
					s7 = s7.substring(j, s7.length());
				}

				stringbuffer.append((s7 + "\n").toCharArray(), 0, (s7 + "\n")
						.toCharArray().length);
			} else {
				stringbuffer.append((s5 + "\n").toCharArray(), 0, (s5 + "\n")
						.toCharArray().length);
			}
		}

		return stringbuffer.toString().trim();
	}

	/**
	 * 判断单选框是否选中
	 * 
	 * @author haley
	 * @param radioValue -- 传入的值
	 * @param val -- 待比较的值
	 * @return 是否选中
	 * @date 2004-11-10
	 */
	public static String isChecked(String radioValue, String val) {
		try {
			if (radioValue.trim().equals(val.trim()))
				return "checked";
			else
				return "";
		} catch (Exception ex) {
			return null;
		}
	}

	/**
	 * 判断下拉列表选项框是否选中
	 * 
	 * @author haley
	 * @param optionValue -- 传入的值
	 * @param val --  待比较的值
	 * @return 是否选中
	 * @date 2004-11-10
	 */
	public static String isSelected(String optionValue, String val) {
		try {
			if (optionValue.trim().equals(val.trim()))
				return "selected";
			else
				return "";
		} catch (Exception ex) {
			return null;
		}
	}

	/**
	 * 判断下拉列表选项框是否选中
	 * 
	 * @author haley
	 * @param inStr1,inStr2 -- 传入的值
	 * @param outStr1,outStr2 -- 待返回的值
	 * @return inStr1与inStr2相等返回outStr1否则返回outStr2
	 * @date 2004-12-01
	 */
	public static String returnValue(String inStr1, String inStr2,
			String outStr1, String outStr2) {
		try {
			if (inStr1.indexOf(inStr2) != -1) {
				return outStr1;
			} else {
				return outStr2;
			}
		} catch (Exception ex) {
			return null;
		}
	}

	/**
	 * 判断此字符串是否为空、空字符串，或"null"
	 * 
	 * @author haley
	 * @param radioValue -- 传入的值
	 * @param val -- 待比较的值
	 * @return 是否选中
	 * @date 2004-11-10
	 */
	public static boolean isNull(String s) {
		return (s == null || s.equals("null") || s.equals("") || s.length() < 1) ? true
				: false;
	}

	/**
	 * 如果字符串str为空则转换为str1
	 * 
	 * @author haley
	 * @param radioValue --  传入的值
	 * @param val -- 待比较的值
	 * @return 是否选中
	 * @date 2004-11-10
	 */
	public static String getNullStr(String str, String str1) {
		if (isNull(str))
			return str1;
		else
			return formatSqlStr(str);
	}

	/**
	 * 转化用于TextArea显示的字串
	 * 
	 * @param str
	 *            String
	 * @return String Brief Memo: 备用方法替换显示的双引号,单引号,括号,空格
	 */
	public static String toTextAreaStr(String str) {
		if (str == null) {
			return "";
		}

		String szTmp = str;
		szTmp = replace(szTmp, "<", "&lt;");
		szTmp = replace(szTmp, ">", "&gt;");
		szTmp = replace(szTmp, "\"", "&quot;");
		szTmp = replace(szTmp, "\\", "&#92;"); // 有无必要呢.
		return szTmp;
		/*
		 * &#61; --> '=' &#34; --> '"' &#36; --> '$' &#37; --> '%' &#40; --> '('
		 * &#41; --> ')' &#47; --> '/'
		 * 
		 * &#60; --> ' <' &#61; --> '=' &#62; --> '>' &#63; --> '?' &#64; -->
		 * '@'
		 */
	}

	/**
	 * 转化用于保存Hidden里的类html文本数据
	 * 
	 * @param str
	 *            String
	 * @return String
	 */
	public static String toHtmlValue(String str) {
		if (str == null) {
			return "";
		}
		String szTmp = str;
		szTmp = replace(szTmp, "=", "&#61;");
		szTmp = replace(szTmp, "<", "&#60;");
		szTmp = replace(szTmp, ">", "&#62;");
		szTmp = replace(szTmp, "\"", "&#34;");
		szTmp = replace(szTmp, "\\", "&#92;");
		return szTmp;
	}

	/**
	 * 主要用于网页内容的显示(替换显示的双引号,尖括号,等号,换行符) 可根据需要修改[注意替抽象顺序,避免重复替换]
	 * 
	 * @param str String
	 * @return String
	 */
	public static String toHtmlSrc(String str) {
		if (str == null) {
			return "";
		}

		String szTmp = str;
		szTmp = szTmp.trim();
		szTmp = replace(szTmp, "<", "&lt;");
		szTmp = replace(szTmp, ">", "&gt;");
		szTmp = replace(szTmp, "\"", "&quot;");
		szTmp = replace(szTmp, "=", "&#61;");
		szTmp = replace(szTmp, "\n", "<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
		//szTmp = replace(szTmp, " ", "&nbsp;&nbsp;");
		return szTmp;
	}

	/**
	 * 主要用于网页内容的显示(替换显示的双引号,尖括号,等号,换行符) 可根据需要修改[注意替抽象顺序,避免重复替换] 
	 * 没有删除空格
	 * @param str
	 *            String
	 * @return String
	 */
	public static String toHtmlSrc2(String str) {
		if (str == null) {
			return "";
		}

		String szTmp = str;
		// szTmp = szTmp.trim();
		szTmp = replace(szTmp, "<", "&lt;");
		szTmp = replace(szTmp, ">", "&gt;");
		szTmp = replace(szTmp, "\"", "&quot;");
		szTmp = replace(szTmp, "=", "&#61;");
		szTmp = replace(szTmp, "\n", "<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
		//szTmp = replace(szTmp, " ", "&nbsp;&nbsp;");
		return szTmp;
	}

	/**
	 * 主要用于网页内容的显示(替换显示的双引号,尖括号,等号,换行符) 可根据需要修改[注意替抽象顺序,避免重复替换] 直接换行不加空格
	 * 
	 * @param str
	 *            String
	 * @return String
	 */
	public static String toHtmlSrc_br(String str) {
		if (str == null) {
			return "";
		}

		String szTmp = str;
		szTmp = szTmp.trim();
		szTmp = replace(szTmp, "<", "&lt;");
		szTmp = replace(szTmp, ">", "&gt;");
		szTmp = replace(szTmp, "\"", "&quot;");
		szTmp = replace(szTmp, "=", "&#61;");
		szTmp = replace(szTmp, "\n", "<br>");
		// szTmp = replace(szTmp, " ", "&nbsp;&nbsp;");
		return szTmp;
	}

	public static String toHtmlTableSrc(String str) {
		if (str == null) {
			return "";
		}

		String szTmp = str;
		szTmp = szTmp.trim();
		return szTmp;
	}

	/**
	 * 主要用于网页内容的显示(如果int值为0,赋空) 可根据需要修改[注意替抽象顺序,避免重复替换]
	 * 
	 * @param str
	 *            String
	 * @return String
	 */
	public static String toHtmlSrc(int value) {
		if (value == 0) {
			return "";
		} else
			return String.valueOf(value);
	}

	/**
	 * 主要用于网页内容的显示(替换显示的双引号,尖括号,等号)
	 * 
	 * @see toHtmlSrc [不替换换行符]
	 * @param str
	 *            String
	 * @return String
	 */

	public static String toHtmlStr(String str) {
		if (str == null) {
			return "";
		}

		String szTmp = str;
		szTmp = replace(szTmp, "<", "&lt;");
		szTmp = replace(szTmp, ">", "&gt;");
		szTmp = replace(szTmp, "\"", "&quot;");
		szTmp = replace(szTmp, "=", "&#61;");
		return szTmp;
	}

	public static String HtmltoStr(String str) {
		if (str == null) {
			return "";
		}

		String szTmp = str;
		// System.out.println(szTmp);
		szTmp = replace(szTmp, "<", "&lt;");
		szTmp = replace(szTmp, ">", "&gt;");
		szTmp = replace(szTmp, "\"", "&quot;");
		szTmp = replace(szTmp, "=", "&#61;");
		szTmp = replace(szTmp, "&nbsp;", "");
		szTmp = replace(szTmp, "\n", "");
		szTmp = replace(szTmp, "\r", "");
		// szTmp = replace(szTmp," ","");
		// szTmp = replace(szTmp," ","#");
		// System.out.print(szTmp);
		return szTmp;
	}

	/**
	 * 主要用于Javascript的赋值
	 * 
	 * @param str
	 *            String
	 * @return String
	 */
	public static String toScriptValue(String str) {
		if (str == null) {
			return "";
		}

		String szTmp = str;
		szTmp = replace(szTmp, "\\", "\\\\");
		szTmp = replace(szTmp, "\"", "\\\"");
		return szTmp;
	}

	/**
	 * 转化用于保存到数据库的字串,并去掉前后
	 * 
	 * @param str
	 *            String
	 * @return String
	 */
	public static String toInsStr(String str) {
		if (str == null || str.trim().equals("")) {
			return "";
		}
		return replace(str.trim(), "'", "''");
	}

	/**
	 * 转化用于保存到数据库的字串(对较短数据处理)
	 * 
	 * @param str
	 *            String
	 * @return String
	 */
	public static String toInsSqlStr(String str) {
		StringBuffer sb = new StringBuffer("");
		if (str == null || str.trim().equals("")) {
			return "";
		}
		String szStr = str.trim();
		for (int i = 0; i < szStr.length(); i++) {
			if (szStr.substring(i, i + 1).equals("'"))
				sb.append("''");
			else
				sb.append(szStr.substring(i, i + 1));
		}

		return sb.toString();
	}

	/**
	 * 格式化复选框生成字串
	 * 
	 * @param checkboxID
	 * @return
	 */
	public static String getCheckbox(String[] checkboxID) {
		String[] ss;
		ss = checkboxID;
		String temp = "";
		StringBuffer sb = new StringBuffer("");
		try {
			for (int i = 0; i < ss.length; i++) {
				sb.append(ss[i]);
				sb.append(",");
			}
			if (temp.length() > 0)
				temp = sb.toString().substring(0, temp.length() - 1);
			return temp;
		} catch (NullPointerException e) {
			return "";
		}
	}

	/**
	 * 取指定长度的字符串 一个中文按两个字符计算
	 * 
	 * @param s
	 * @return
	 */
	public static String getChinessStr(String s, int len) {

		if (len >= s.getBytes().length) {
			return s;
		}
		int nums = 0;
		len -= 3;

		StringBuffer sb = new StringBuffer();
		char[] temp;
		temp = s.toCharArray();
		String str = "";

		for (int i = 0; i < temp.length; i++) {
			str = String.valueOf(temp[i]);
			if (str.getBytes().length > 1)
				nums += 2;
			else
				nums++;
			sb.append(str);
			if (nums >= len)
				break;
		}

		return sb.toString();
	}

	public static String toLimitStr(String src, int len) {
		String szTmp = toStr(src);
		int iLen = szTmp.length();
		int iMax = 1;
		if (len > 1)
			iMax = len;
		if (iLen == 0)
			return "";
		else if (iLen > iMax)
			szTmp = szTmp.substring(0, iMax);
		return szTmp;
	}

	public static String toLimit(String src, int len) {
		if (src == null)
			return "";
		String szTmp = toStr(src);
		int iLen = szTmp.length();
		int iMax = 1;
		if (len > 1)
			iMax = len;
		if (iLen == 0)
			return "";
		else if (iLen > iMax)
			szTmp = szTmp.substring(0, iMax);
		return szTmp;
	}

	/**
	 * 
	 * @param src
	 *            源数组
	 * @param des
	 *            目标数组
	 * @param desTo
	 *            目标数组的结束位置
	 * @return
	 */
	public static String[] arrayCopy(String[] src, String[] des, int desTo) {
		for (int i = 0; i < desTo; i++) {
			if (src.length > i)
				des[i] = src[i];
			else
				des[i] = "";
		}
		return des;
	}

	/**
	 * 格式化生成SQL语句串输入参数
	 * 
	 * @param s
	 * @return
	 */
	public static final String formatSQL(String s) {
		if (s != null) {
			s = s.trim();
			s = s.replaceAll("'", "‘");
			return s;
		} else {
			return "";
		}
	}

	public static final java.util.Date toDate(String s) {

		java.util.Date date = new java.util.Date();

		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-DD");
		try {
			date = sf.parse(s);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return date;
	}
	

	/**
	 * 获取一个类的class文件所在的绝对路径. 只要是在本程序中可以被加载的类,都可以定位到它的class文件的绝对路径
	 * 
	 * @param cls
	 *            一个对象的Class属性
	 * @return 这个类的class文件位置的绝对路径,如果没有这个类的定义,则返回null
	 */
	public static String getPathFromClass(Class cls) throws IOException {
		String path = null;
		if (cls == null) {
			throw new NullPointerException();
		}
		URL url = getClassLocationURL(cls);
		if (url != null) {
			path = url.getPath();
			if ("jar".equalsIgnoreCase(url.getProtocol())) {
				try {
					path = new URL(path).getPath();
				} catch (MalformedURLException e) {
					e.printStackTrace();
				}
				int location = path.indexOf("!/");
				if (location != -1) {
					path = path.substring(0, location);
				}
			}
			File file = new File(path);
			path = file.getCanonicalPath();
		}
		return path;
	}

	/**
	 * 这个方法可以通过与某个类的class文件的相对路径来获取文件或目录的绝对路径
	 * 通过这个方法，我们可以根据我们程序自身的类文件的位置来定位某个相对路径。
	 * 比如：某个txt文件相对于程序的Test类文件的路径是../../resource/test.txt，
	 * 那么使用本方法Path.getFullPathRelateClass("../../resource/test.txt",Test.class)
	 * 得到的结果是txt文件的在系统中的绝对路径
	 * 
	 * @param relatedPath
	 *            相对路径
	 * @param cls
	 *            用来定位的类
	 * @return 相对路径所对应的绝对路径
	 * @throws IOException
	 *             因为本方法将查询文件系统，所以可能抛出IO异常
	 */
	public static String getFullPathRelateClass(String relatedPath, Class cls)
			throws IOException {
		String path = null;
		if (relatedPath == null) {
			throw new NullPointerException();
		}
		String clsPath = getPathFromClass(cls);
		File clsFile = new File(clsPath);
		String tempPath = clsFile.getParent() + File.separator + relatedPath;
		File file = new File(tempPath);
		path = file.getCanonicalPath();
		return path;
	}

	private static URL getClassLocationURL(final Class cls) {
		if (cls == null)
			throw new IllegalArgumentException("null input: cls");
		URL result = null;
		final String clsAsResource = cls.getName().replace('.', '/').concat(
				".class");
		final ProtectionDomain pd = cls.getProtectionDomain();

		if (pd != null) {
			final CodeSource cs = pd.getCodeSource();
			if (cs != null)
				result = cs.getLocation();

			if (result != null) {
				if ("file".equals(result.getProtocol())) {
					try {
						if (result.toExternalForm().endsWith(".jar")
								|| result.toExternalForm().endsWith(".zip"))
							result = new URL("jar:".concat(
									result.toExternalForm()).concat("!/")
									.concat(clsAsResource));
						else if (new File(result.getFile()).isDirectory())
							result = new URL(result, clsAsResource);
					} catch (MalformedURLException ignore) {
						ignore.printStackTrace();
					}
				}
			}
		}

		if (result == null) {
			final ClassLoader clsLoader = cls.getClassLoader();
			result = clsLoader != null ? clsLoader.getResource(clsAsResource)
					: ClassLoader.getSystemResource(clsAsResource);
		}
		return result;
	}

	/**
	 * 截取英文字符
	 */
	public static String splitStr(String s) {
		char[] cs = s.toCharArray();
		int max = 46;
		int count = 0;
		int last = cs.length;
		for (int i = 0; i < cs.length; i++) {
			if (cs[i] > 255)
				count += 2;
			else
				count++;
			if (count > max) {
				last = i + 1;
				break;
			}
		}
		if (count <= max)
			return s;
		max -= 3;
		for (int i = last - 1; i >= 0; i--) {
			if (cs[i] > 255)
				count -= 2;
			else
				count--;
			if (count <= max) {
				return s.substring(0, i) + "...";
			}
		}
		return "...";
	}

	public static final java.sql.Date toSqlDate(String s) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date timeDate = null;
		try {
			timeDate = dateFormat.parse(s);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		java.sql.Date dateTime = new java.sql.Date(timeDate.getTime());// sql类型
		return dateTime;
	}

	// 把字符串转换为数字
	public static final long toLong(String s, long i) {
		if (s == null)
			return i;
		if (!strIsDigital(s) || s.length() < 1)
			return i;
		try {
			return Long.parseLong(s);
		} catch (Exception e) {
			return 0L;
		}
	}
	
	/**
	 * 是不是Email
	 * @param input 需要验证的字符串
	 * @return 是 true; 否 false
	 */
	public static boolean isEmail(String input){
	  final String regex = "[a-zA-Z0-9][a-zA-Z0-9_\\.\\-]*[a-zA-Z0-9]@([a-zA-Z0-9_\\-]+[\\.]){1,}[a-zA-Z0-9_\\-]+";
		if(null != input){
			return Pattern.matches(regex,input);
		}
		else{
			return false;
		}
	}
	public static void main(String args[]) {

		StrUtils.toDate("2006-05-01");
		System.out.println(StrUtils.toDate("2006-05-01"));
	}

}