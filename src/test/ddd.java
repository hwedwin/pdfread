package test;

import java.util.Properties;

public class ddd {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		   Properties props=System.getProperties(); //���ϵͳ���Լ�  
		   String osName = props.getProperty("os.name"); //����ϵͳ����  
		   String osArch = props.getProperty("os.arch"); //����ϵͳ����  
		   String osVersion = props.getProperty("os.version"); //����ϵͳ�汾  
		   System.out.println(osName);
		   System.out.println(osArch);
		   System.out.println(osVersion);
	}
	
	public static  void map(){
		   String line = "ad bb cc";
			
			String[] strData=line.split(" ");
			for(int i = 0; i < 3; i++){
				Com(strData,1,2);
				System.out.println(strData.length);
			}
		
		}
		
		//��n����������ѡm�������������
		public static void Com(String[]str, int n, int m){
			
			System.out.println(str[1] + ":" + n + m);
		}

}
