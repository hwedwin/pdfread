package test;

import com.pub.StringUtils;

public class Test1 {
	public static void main(String args[]) {
		String tesStr = "�ر�Լ���� 1.\"����zheng��ҽ���,���������ṩ���Ĺ�ͬ�����/���������.\" 2.\"���䳬��20��(��20��)�Ĵ���,�����˰����䴬�ӷ�,����25�괬��(��25��) �����˲���б�.\" 3.����������ʱ����Ͷ��������ʱ��Ϊ׼����2013��01��05��  12ʱ";
		String testStr = "ever body ,here, PICC�˱�������Ϣ������";
//		System.out.println(formatString4Auto1(tesStr, 10, " "));
		System.out.println(formatString4Auto1(testStr, 10, " "));
//		System.out.println("��".getBytes().length);
	}
	
	public static String autoCut(String values, final int lineSize, String formatWord){
		String enter = "\r\n";
		int strLen = 0;
		final int formatLen = formatWord.length();
		StringBuilder resultBuilder = new StringBuilder();
		for(int i = 0; i < values.length(); i++){
			char value = values.charAt(i);
			int formatIndex = values.indexOf(formatWord, i);
			//i==formatIndex��ʾ��ǰ�����ε��˷�����������ܴ�ӡ�¾ʹ�ӡ����ӡ���¾�ȥ������Ϊ���ײ���ӡ�����
			if(i == formatIndex ){
				if(strLen == 0){
					i += formatLen;
					continue;
				}else if(strLen + formatLen > lineSize){
					resultBuilder.append(enter);
					i += (formatLen - 1 > 0) ? (formatIndex -1) : 0;
					strLen = 0;
					continue;
				}else{
					resultBuilder.append(formatWord);
					i += (formatLen - 1 > 0) ? (formatIndex -1) : 0;
					strLen += formatLen;
					continue;
				}
			}
			//���ַ�Ϊ����ʱ
			else if(isChinese(value)){  
				if(strLen + 2 <= lineSize){
					resultBuilder.append(value);  //���ַ����ȼ��ϵ�ǰ����ΪС���г�ʱ
					strLen += 2;
				}else{
					resultBuilder.append(enter);  
					resultBuilder.append(value);
					strLen = 2;
				}
			}else{
				int nextIndex = getNextBlankIndex(values, i, formatWord);
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
	 * ���Զ����з���formatString4Auto1()�ڲ����ã�ͨ������Ӣ���ַ���ǰ����λ��begin�����Ҵ�Ӣ��
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
	 * @param formatWord Ӣ�ĵ��ʸ����
	 * @return  
	 */
	public static String formatString4Auto1(String value, final int lineSize,
			String formatWord) {
		value = formatString(value);
		StringBuilder sb = new StringBuilder();
		String entr = "\r\n";
		String[] valueArray = value.split(entr);
		
		for (String valueTmp : valueArray) {
			if (StringUtils.isNotBlank(sb.toString())) {
				sb.append(entr);
			}
			sb.append(autoCut(valueTmp, lineSize, formatWord));
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
