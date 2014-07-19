package test;

import com.pub.StringUtils;

public class aa {
	/**
	 * �ַ����Զ����д���
	 * @param value  Ҫ���и�ʽ�����ַ���
	 * @param lineSize �涨ÿ�п�ȣ���Ӣ���ַ������
	 * @param formatWord Ӣ�ĵ���֮��ķָ���
	 * @return
	 */
	public static String formatString4Auto(String value, int lineSize,
			String formatWord) {
		value = formatString(value);
		StringBuilder sb = new StringBuilder();
		String entr = "\r\n";
		String[] valueArray = value.split(entr);
		String strTmp = "";
		String strTmpOld = "";
		for (String valueTmp : valueArray) {
			strTmp = "";
			if (StringUtils.isNotBlank(sb.toString())) {
				sb.append(entr);
			}
			String[] worldArray = valueTmp.split(formatWord);
			for (int i = 0; i < worldArray.length; i++) {
				if (worldArray[i].length() > lineSize) {
					sb.append(strTmp);
					strTmp = "";
					if (StringUtils.isNotBlank(sb.toString())
							&& !sb.toString().endsWith(entr)) {
						sb.append(entr);
					}
					sb.append(worldArray[i].substring(0, lineSize) + entr);
					worldArray[i] = worldArray[i].substring(lineSize);
					i--;
				} else {
					strTmpOld = strTmp;
					strTmp += worldArray[i];
					if (strTmp.length() == lineSize) {
						sb.append(strTmp + entr);
						if (!sb.toString().endsWith(formatWord + entr)) {
							strTmp = formatWord;
						} else {
							strTmp = "";
						}
					} else if (strTmp.length() < lineSize) {
						strTmp += formatWord;
					} else {
						sb.append(strTmpOld + entr);
						i--;
						strTmp = "";
					}
				}
			}
			if (StringUtils.isNotBlank(strTmp)) {
				sb.append(strTmp);
				strTmp = "";
			}
		}
		String strClausesCurrent = sb.toString();

		return strClausesCurrent;
	}

	/**
	 * �����ַ��滻����
	 * @param value
	 * @return
	 */
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
