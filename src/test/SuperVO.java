package test;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import weblogic.auddi.util.Logger;

/**
 * ���������������������Խ����е�PrpcItem��̳д��࣬�����Ϳ���ֱ��ͨ���ֶ�ֵ���и�ֵ��ȡֵ
 * getAttributes():ȡ�����ֶ�
 * setAttribute():ͨ���ֶ����������set����
 * getAttribute():ͨ���ֶ����������get����
 * @author tianxingjian
 *
 */
public class SuperVO {

	/**
	 * ȡ������������ֶ�
	 * @return
	 */
	public String[] getAttributes(){
		Class cla = this.getClass();
		Field [] fields = cla.getDeclaredFields();
		String []results = new String[fields.length];
		for(int i = 0; i < results.length; i++){
			results[i] = fields[i].getName();
		}
		return results;
	}
	
	/**
	 * ��fieldName�ֶε�set������ֵ
	 * @param fieldName
	 * @param o
	 */
	public void setAttribute(String fieldName, Object o){
		
		Class cla = this.getClass();
		try {
			Field field = cla.getDeclaredField(fieldName);
		} catch (Exception e1) {
			Logger.warning("�������ֶ�" + fieldName + "��ֵ���ɹ���");
		}
		
		StringBuffer sb = new StringBuffer();   
	    sb.append("set");   
	    sb.append(fieldName.substring(0, 1).toUpperCase());   
	    sb.append(fieldName.substring(1));   
		 
	    boolean isSuccess = false;
	    StringBuffer info = new StringBuffer();
		try {
			
			Method method = cla.getDeclaredMethod(sb.toString());
			try {
				method.invoke(this, o);//����Ҫ����ת����������ͨ�õ�����ת����û��apache�����������
				isSuccess = true;
			} catch (Exception e) {
				info.append(fieldName + "��ֵ���ɹ�:" + e.getMessage());
			}
			
		} catch (SecurityException e) {
			info.append(fieldName + "��ֵ���ɹ�:" + e.getMessage());
		} catch (NoSuchMethodException e) {  //��setAcc ����û�ҵ�ʱ������setacc����
			sb = new StringBuffer();
			sb.append("get");
			sb.append(fieldName);
			try {
				Method method = cla.getDeclaredMethod(sb.toString());
				try {
					method.invoke(this, o);  //����Ҫ����ת����������ͨ�õ�����ת����û��apache�����������
					isSuccess = true;
				} catch (Exception e2) {
					info.append(fieldName + "��ֵ���ɹ�:" + e.getMessage());
				}
			} catch (Exception e1) {
				info.append(fieldName + "��ֵ���ɹ�:" + e.getMessage());
			}
		}
		
		if(!isSuccess){
			Logger.warning(info.toString());
		}
	}
	
	/**
	 * ͨ��fieldName����get������get + ��fieldName���� get + ��FieldName���� is + ��fieldName��������ʽ
	 * û��������ת����ʵ�ʵ�����˷�����Ҫһ������ת�������о��ǽ�������ԭ���������Ͷ������Ӧ�Ķ���
	 * @param fieldName
	 * @return
	 */
	public Object getAttribute(String fieldName){
		Class cla = this.getClass();
		Object result = null;
		try {
			Field field = cla.getDeclaredField(fieldName);
		} catch (Exception e1) {
			Logger.warning("�������ֶ�" + fieldName + "��ֵ���ɹ���");
		}
		
		StringBuffer sb = new StringBuffer();   
	    sb.append("get");   
	    sb.append(fieldName.substring(0, 1).toUpperCase());   
	    sb.append(fieldName.substring(1));   
		 
	    boolean isSuccess = false;
	    StringBuffer info = new StringBuffer();
		try {
			Method method = cla.getDeclaredMethod(sb.toString());
			try {
				result = method.invoke(this);//����Ҫ����ת����������ͨ�õ�����ת����û��apache�����������
				isSuccess = true;
			} catch (Exception e) {
				info.append(fieldName + "��ֵ���ɹ�:" + e.getMessage());
			}
			
		} catch (SecurityException e) {
			info.append(fieldName + "��ֵ���ɹ�:" + e.getMessage());
		} catch (NoSuchMethodException e) {  //��setAcc ����û�ҵ�ʱ������setacc����
			sb = new StringBuffer();
			sb.append("get");
			sb.append(fieldName);
			try {
				Method method = cla.getDeclaredMethod(sb.toString());
				try {
					result = method.invoke(this);  //����Ҫ����ת����������ͨ�õ�����ת����û��apache�����������
					isSuccess = true;
				} catch (Exception e2) {
					info.append(fieldName + "��ֵ���ɹ�:" + e.getMessage());
				}
			} catch (SecurityException e1) {
				info.append(fieldName + "��ֵ���ɹ�:" + e.getMessage());
			} catch (NoSuchMethodException e1) {
				sb = new StringBuffer();
				sb.append("is"); //ֻ��boolean���ͲŻ�����
				sb.append(fieldName);
					
				try {
					Method method = cla.getDeclaredMethod(sb.toString());
					try {
						result = method.invoke(this);  //����Ҫ����ת����������ͨ�õ�����ת����û��apache�����������
						isSuccess = true;
					} catch (Exception e2) {
						info.append(fieldName + "��ֵ���ɹ�:" + e.getMessage());
					}
				}catch(Exception e3){
					info.append(fieldName + "��ֵ���ɹ�:" + e.getMessage());
				}
			}
				
			
		}
		
		if(!isSuccess){
			Logger.warning(info.toString());
		}
		
		return result;
	}
	
}
