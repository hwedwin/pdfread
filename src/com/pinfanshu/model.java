package com.pinfanshu;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class model {
	final static float t = 0.01f;
	final static long n = 100000;
	
	/*
	 * 32λ����1����
	 */
	private final static long MASK_1 = 0x5555555555555555L;
    private final static long MASK_2 = 0x3333333333333333L;
    private final static long MASK_4 = 0x0F0F0F0F0F0F0F0FL;
    private final static long MASK_8 = 0x00FF00FF00FF00FFL;
    private final static long MASK_16 = 0x0000FFFF0000FFFFL;
    private final static long MASK_32 = 0x00000000FFFFFFFFL;
   
    public static int bitCount_Parallel(long n){
        n = (n & MASK_1) + ((n >>> 1) & MASK_1); 
        n = (n & MASK_2) + ((n >>> 2) & MASK_2); 
        n = (n & MASK_4) + ((n >>> 4) & MASK_4); 
        n = (n & MASK_8) + ((n >>> 8) & MASK_8); 
        n = (n & MASK_16) + ((n >>> 16) & MASK_16); 
        n = (n & MASK_32) + ((n >>> 32) & MASK_32); 
        return (int)n;
    }
    /*
	 * ���㵥����1��λ�� ,����1�ĸ���
	 */
	public static int BitCount(String str){
		String[] valueStr = str.split(" ");//���Ƚ��в��
		int bitCount=0;

		for(int i=0;i<valueStr.length;i++)
		{
			String[] values=valueStr[i].split(",");//����,�����
			for(int j=0;j<values.length;j++){
				
				if(values[j]=="0")
					bitCount+=0;
				else
					bitCount+=bitCount_Parallel(Long.parseLong(values[j]));
			}
		}

		return bitCount;
	}
	/*
	 * �������������1��λ�� ,����֧�����Լ���Ϻ����
	 */
	public static String[] BitCount(String str1,String str2){
		String[] resultData = new String[2];
		
		
		String[] fonStr = str1.split(" ");
		String[] backStr = str2.split(" ");
		String str="";
		int bitCount=0;
		if(str1.length()==0){
			str=str2;
			for(int i=0;i<backStr.length;i++){
				String[] values=backStr[i].split(",");
				for(String val:values){
					if(val=="0")
						bitCount+=0;
					else
						bitCount+=bitCount_Parallel(Long.parseLong(val));
				}
			}
		}
		else{
			for(int i=0;i<fonStr.length;i++){
				String[] valuesFon=fonStr[i].split(",");
				String[] valuesBack=backStr[i].split(",");
				for(int j=0;j<((valuesFon.length<valuesBack.length)?valuesFon.length:valuesBack.length);j++){
					if(valuesFon[j]=="0"||valuesBack[j]=="0"){
						str+="0"+",";
						bitCount+=0;
					}
					else{
						long temp = Long.parseLong(valuesFon[j])&Long.parseLong(valuesBack[j]);//��&����
						bitCount+=bitCount_Parallel(temp);
						str+=Long.toString(temp)+",";
					}
				}
				str=str.substring(0, str.length()-1);//ȥ����,��
				str+=" ";
			}
			
		}
		resultData[0]=str;
		resultData[1]=Integer.toString(bitCount);
		return resultData;
	}
	
	public static boolean Output(int cur_totalVar,String[] keyResult,String[] valueResult){
		String str = "";//�м����
		String strKeycom = "";//��¼���
		int resCount = 0;//���֧����
		int frequentNumber = (int) (t*n); //��map��֧����С��ָ��ֵ��ģʽֱ�ӹ���
		//int k;//��¼����������������ֱ�ӷ���false
		//�����������
		//if(cur_totalVar>=k)return false;
		if(cur_totalVar>=2){//���ǵ�ÿ���Ƶ����
			str = valueResult[0];//��������ÿ�ε���Outputʱ����һ��BitCount
			for (int i=0;i<cur_totalVar;i++)
			{
				String[] resultStr = BitCount(str, valueResult[i]);//������ϺͶ�Ӧ֧����
				resCount = Integer.parseInt(resultStr[1]);//��ȡ֧����
				
				if (resCount > frequentNumber) {
					strKeycom += keyResult[i] + "&";
					str = resultStr[0];
				}
				else
					return false;//���˵�������Ҫ������
			}
		}
		if(resCount != 0){
			System.out.println("keyCom="+strKeycom.subSequence(0, strKeycom.length()-1)+"	 resCount="+ +resCount);
			//dataStore.writeData(fileName,strKeycom.subSequence(0, strKeycom.length()-1)+ "  " +resCount+"\r\n");
		}
		return true;
	}
	/**
	 * @param cur_totalVar
	 * 			     ��ǰλ��
	 * @param nextVar
	 * 			     ��һλ��
	 * @param keyResult
	 * 			     �����������ϵ�key
	 * @param n
	 *            �������С
	 * @param keyMat
	 * 			     ������д��ļ��ж�ȡ��key      
	 * @param valueResult
	 *            �����������ϵ�key����Ӧ��value
	 * @param valueMat
	 *            ������д��ļ��ж�ȡ��value  
	 */
	public static void Com(int cur_totalVar,int nextVar,String[] keyResult,int n,String[] keyMat,String[] valueResult,String[] valueMat) {
		
		if(Output(cur_totalVar,keyResult,valueResult)){
			for (int i=nextVar;i<n;i++){
				keyResult[cur_totalVar]=keyMat[i];
				valueResult[cur_totalVar]=valueMat[i];
				Com(cur_totalVar+1,i+1,keyResult,n,keyMat,valueResult,valueMat);//�״�ĵط����ݹ��ʱ��ʹ��i+1����ͬ��λ�÷ֱ��Ÿ���λ��
			}
		}
		else
		{
			for (int i=nextVar;i<n;i++)
			{
				keyResult[cur_totalVar]=keyMat[i];//���¶�λ
				valueResult[cur_totalVar]=valueMat[i];
			}
		}
	}
	/*
	 * �����ҳ�Ƶ��ģʽ
	 */
	
	
	@SuppressWarnings("deprecation")
	public static void Miner() throws IOException{
		long startime=System.currentTimeMillis();
		List<String[]> valuesList=new ArrayList<String[]>();//���ڱ�����ļ��ж�ȡ������
	    final String fileName = "d:\\result\\part-r-00000";
		File file = new File(fileName);
		BufferedReader reader = null;
		try {
			System.out.println("����Ϊ��λ��ȡ�ļ����ݣ�һ�ζ�һ���У�");
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			// һ�ζ���һ�У�ֱ������nullΪ�ļ�����
			while ((tempString = reader.readLine()) != null) {
				// ��ʾ�к�
				String[] strSpilt = tempString.split("\t", 2);
				valuesList.add(strSpilt);// ��Ƶ������ӵ�valuesList��
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
		}
		
        //��List�е����ݶ��뵽String�����н��в���
        final String[] keyStr=new String[valuesList.size()];//����key���ݣ���������Com
        final String[] valuesStr=new String[valuesList.size()];//����values���ݣ���������Com
        int frequentNumber = (int) (t*n); //��map��֧����С��ָ��ֵ��ģʽֱ��ɾ��
        int tempPos=0;//��ʱ����
        for (int i = 0; i < valuesList.size(); i++) {    
        	
        	String[] lineStr = (String[]) valuesList.get(i);
        	
        	//���ú���������ֵ����1��λ���Ƿ����Ҫ�� 
			int resCount = BitCount(lineStr[1]);
			//���ݻ���
			if(resCount>frequentNumber){
				keyStr[tempPos]=lineStr[0];//����Key
				valuesStr[tempPos]=lineStr[1];//����values
				System.out.println("KeyCom="+keyStr[tempPos]+"	resCount="+resCount+"\r\n");
				tempPos++;
			}
    	}
        
        String[] keyMat = keyStr;
        String[] valueMat = valuesStr;
        String[] keyResult = new String[keyStr.length];
        String[] valueResult = new String[valuesStr.length];
        
        /*
        /*
         * ����Com������ϣ���������������ϼ�Ƶ����д���ļ�
         */
        //����Com����
        Com(0,0,keyResult,keyStr.length,keyMat,valueResult,valueMat);//������ϵ���
        System.out.println("Minerִ��ʱ��Ϊ��"+ (System.currentTimeMillis()-startime)/1000 + "��!");
	}
	public static void main(String[] arg) throws IOException{
		Miner();
	}
}
