package test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexTest {
	public static void main(String []args){
		String tesStr = "�ر� Լ���� 1.\"����zheng��ҽ���,���������ṩ���Ĺ�ͬ�����/���������.\" 2.\"���䳬��20��(��20��)�Ĵ���,�����˰����䴬�ӷ�,����25�괬��(��25��) �����˲���б�.\" 3.����������ʱ����Ͷ��������ʱ��Ϊ׼����2013��01��05��  12ʱ";
		String []strs = tesStr.split("\\p{Punct}");
		String testStr = "aabb";
//		String []strs = tesStr.split("\\p{Punct}");
//		String []strs = tesStr.split("\\pP|\\s", 3);
		for(String str : strs){
			System.out.println(str);
		}
		System.out.println(Character.isWhitespace('\t'));
//		Pattern pattern = Pattern.compile("\\pP|\\s");
//		Matcher matcher = pattern.matcher(",");
//		System.out.println(matcher.matches());

	}
}
