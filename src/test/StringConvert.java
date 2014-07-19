package test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * �ַ����Զ������㷨ʵ��
 * @author tianxingjian
 *
 */
public class StringConvert {
	public static void main(String args[]) {
		String testStr = "�ر�Լ���� 1.\"����zheng��ҽ���,���������ṩ���Ĺ�ͬ�����/���������.\" 2.\"���䳬��20��(��20��)�Ĵ���,�����˰����䴬�ӷ�,����25�괬��(��25��) �����˲���б�.\" 3.����������ʱ����Ͷ��������ʱ��Ϊ׼����2013��01��05��  12ʱ";
		String testStr1 = "every body here, PICC�˱�������Ϣ������! �ײ�KKK�������Ǹ���9999��";
		String testStr2 = "�ײ�KKK������";
		System.out.println(formatString4Auto(testStr, 20));
		System.out.println();
		System.out.println(formatString4Auto(testStr1, 20));
		System.out.println();
		System.out.println(formatString4Auto(testStr2, 6));
	}
	
	/**
	 * �����ַ����Զ����У����ҽ��Զ����е��ַ�����ΪĿ���ַ�������
	 * @param values ���뵥���ַ���ȷ��û�лس����лس����ܵ��³��ֿ��е����⣩
	 * @param lineSize ָ��Ŀ���ַ����п���Ӣ���ַ��������㣩
	 * @return
	 */
	public static String autoCut(String values, final int lineSize){
		String enter = "\r\n";
		int strLen = 0;
		
		StringBuilder resultBuilder = new StringBuilder();
		for(int i = 0; i < values.length(); i++){
			char value = values.charAt(i);
			/**
			 * �����������ַ�Ϊ�հ��ַ�ʱ������������򲻴�ӡ���������Ϊ��װ���¾�ֱ��
			 * ���һ�����з�
			 */
			if(Character.isWhitespace(value)){
				if(strLen == 0){
					
				}else if(strLen + 1 > lineSize){
					resultBuilder.append(enter);
					strLen = 0;
				}else{
					resultBuilder.append(value);
					strLen += 1;
				}
			}
			/**���ַ�Ϊ����ʱ,������ĺ���������Ƿ��źͿհ��ַ�,�������ַ��Ϳհ��ַ��ϲ�
			 *Ϊ���ɷָ���ַ�������Ҫô�����У�Ҫô�������У�
			 */
			else if(isChinese(value)){
				boolean joinSybol = false;
				int width = 1;
				String isSymbol = "";
				if(i + 1 < values.length()){
					isSymbol = values.charAt(i+1) + "";
					joinSybol = symbolMatcher(isSymbol);
				}
				if(joinSybol){
					width = 2;
					i++;
				}else{
					isSymbol = "";
				}
				if(strLen + 2 * width <= lineSize){
					resultBuilder.append(value + isSymbol);  //���ַ����ȼ��ϵ�ǰ����ΪС���г�ʱ
					strLen += 2 * width;
				}else{
					resultBuilder.append(enter);  
					resultBuilder.append(value + isSymbol);
					strLen = 2 * width;
				}
			}else{
//				int nextIndex = getNextBlankIndex(values, i, formatWord);
				int nextIndex = getNextSeparate(values, i);
				if(nextIndex == -1){
					String tempStr = values.substring(i);
					if(strLen + tempStr.length() > lineSize){
						resultBuilder.append(enter);
						resultBuilder.append(tempStr);
						break;
					}
				}else{
					String tempStr = values.substring(i, nextIndex);
					if(strLen + tempStr.length() > lineSize){
						resultBuilder.append(enter);
						resultBuilder.append(tempStr);
						strLen = nextIndex - i;
						i = nextIndex -1;
					}else{
						resultBuilder.append(tempStr);
						strLen += nextIndex - i;
						i = nextIndex -1;
					}
				}
			}
		}
		return resultBuilder.toString();
	}
	
	/**
	 * ���Զ����з���formatString4Auto()�ڲ����ã�ͨ������Ӣ���ַ���ǰ����λ��begin�����Ҵ�Ӣ��
	 * ���ʵĽ���λ�ã�����λ�ÿ����ǵ��ʸ������Ҳ������ĳ�������ַ��Ŀ�ʼ
	 * @param values  ������ַ���
	 * @param begin   �ַ�����ǰ����λ��
	 * @param formatWord  ���ʸ����
	 * @return
	 */
	private static int getNextBlankIndex(String values, final int begin, String formatWord){
		int index = values.indexOf(formatWord, begin);
		for(int i = begin + 1; i < values.length(); i++){
			char value = values.charAt(i);
			if(isChinese(value) || (index != -1 && index < i)){
				if(index > 0){
					return i < index ? i : index;
				}else{
					return i;
				}	
			}
		}
		return -1;
	}
	
	/**
	 * ���Զ����з���formatString4Auto()�ڲ����ã������ַ������ַ�����ǰ����λ�ã�
	 * �ҳ������һ�������Ż��߿հ��ַ���λ��
	 * @param values
	 * @param begin
	 * @return
	 */
	private static int getNextSeparate(String values, final int begin){
		int index = -1;
		String regex = "\\pP|\\s";
		String subString = values.substring(begin);
		String []sepStrings = subString.split(regex, 2);
		String destStr = sepStrings[0];

		if(begin + destStr.length() + 1 < values.length()){
			index = begin + destStr.length() + 1;
		}
		for(int i = begin + 1; i < values.length(); i++){
			char value = values.charAt(i);
			if(isChinese(value) || (index != -1 && index < i)){
				if(index > 0){
					return i < index ? i : index;
				}else{
					return i;
				}	
			}
		}
		return index;
	}

	/**
	 * �ж�һ���ַ��Ƿ�Ϊ������
	 * @param value
	 * @return
	 */
	private static boolean symbolMatcher(String value){
		Pattern pattern = Pattern.compile("\\pP");
		Matcher matcher = pattern.matcher(value);
		return matcher.matches();
	}
	/**
	 * �ж��ַ��Ƿ�Ϊ�����ַ�
	 * @param c
	 * @return
	 */
	private static boolean isChinese(char c) {
		Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
		if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
				|| ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
				|| ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
				|| ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS)
			return true;
		return false;
	}
	
	/**
	 * �ַ����Զ����еķ�����֧����Ӣ�Ļ���ַ������У������ַ������ΪӢ���ַ�����������
	 * ͨ������Ҫ������ַ�����ÿ�г��ȣ��ɴ�ӡӢ���ַ���������Ӣ�ĵ��ʼ��������Ѵ���
	 * @param value  Ҫ��ʽ�����ַ���
	 * @param lineSize  ÿ�пɴ�Ӣ���ַ�����
	 * @return  
	 */
	public static String formatString4Auto(String value, final int lineSize) {
		value = formatString(value);
		StringBuilder sb = new StringBuilder();
		String entr = "\r\n";
		String[] valueArray = value.split(entr);
		
		for (String valueTmp : valueArray) {
			if (StringUtils.isNotBlank(sb.toString())) {
				sb.append(entr);
			}
			sb.append(autoCut(valueTmp, lineSize));
		}
		
		return sb.toString();
	}
	
	public static String formatString(String value) {

		if (StringUtils.isNotBlank(value)) {
			value = value.replaceAll("<", "@@lt;");
			value = value.replaceAll(">", "@@gt;");
			value = value.replaceAll("&#039;", "\'");
			value = value.replaceAll("'", "@@apos;");
			// strEndorseText = strEndorseText.replaceAll("\"", "@@quot;");
			/**
			 * �������������ŵĽ���취 ��������<< ����>>,��ȷ���롶������
			 */

			value = value.replaceAll("@@apos;", "\'");
			value = value.replaceAll("@quot;", "\"");
			value = value.replaceAll("&quot;", "\"");
			value = value.replaceAll("@@lt;@@lt;", "��");
			value = value.replaceAll("@@gt;@@gt;", "��");
			// ����tab��
			value = value.replaceAll("\t", "  ");
			value = value.replaceAll("@@lt;", "<");
			value = value.replaceAll("@@gt;", ">");
			return value;
		} else {
			return "";
		}
	}
}

/**
 * �����ַ�����ֵ�жϵķ���
 * @author tianxingjian
 *
 */
class StringUtils {
	public static boolean isNotBlank(String str){
		if(null != str && !"".equals(str.trim())){
			return true;
		}
		return false;
	}
}