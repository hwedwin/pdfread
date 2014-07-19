package com.file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class FX
{
  public static void main(String[] args)
    throws Exception
  {
    String fileName = "";
    String fenxiFileName = "";
    String startDate = "";
    String endDate = "";
    String ms = "";
    boolean b = false;

    DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    Scanner sc = new Scanner(System.in);

    //E:/log/ManagedServer8202.out
    System.out.println("������Դ��־�ļ�ȫ·�������磺E:/log/testprint-uncategorized.log");
    fileName = sc.nextLine();

//    System.out.println("��������Ҫ��ȡ����ʼʱ�䣺���磺2012-11-26 00:30:00");
//    startDate = sc.nextLine();
//
//    while (!b) {
//      try {
//        b = true;
//        Date dateStart = df.parse(startDate);
//        if (startDate.length() != 19) {
//          b = false;
//          System.out.println("��Ҫ��ȡ����ʼʱ������������������롣�����磺2012-11-26 00:30:00");
//          startDate = sc.nextLine();
//        }
//      }
//      catch (Exception e) {
//        b = false;
//        System.out.println("��Ҫ��ȡ����ʼʱ������������������롣�����磺2012-11-26 00:30:00");
//        startDate = sc.nextLine();
//      }
//    }
//    b = false;
//    System.out.println("��������Ҫ��ȡ����ֹʱ�䣺���磺2012-11-26 18:00:00");
//    endDate = sc.nextLine();
//
//    while (!b) {
//      try {
//        b = true;
//        Date dateEnd = df.parse(endDate);
//        if (endDate.length() != 19) {
//          b = false;
//          System.out.println("��Ҫ��ȡ����ֹʱ������������������롣�����磺2012-11-26 00:30:00");
//          endDate = sc.nextLine();
//        }
//      }
//      catch (Exception e) {
//        b = false;
//        System.out.println("��Ҫ��ȡ����ֹʱ������������������롣�����磺2012-11-26 00:30:00");
//        endDate = sc.nextLine();
//      }
//    }

    System.out.println("�����밴�շ��ӻ��߰����룺M:���� S:��");
    ms = sc.nextLine();
    b = false;
    while (!b) {
      if ((!"MS".contains(ms)) || (ms.equals("")) || (ms == null)) {
        b = false;
        System.out.println("���밴�շ��ӻ��߰�����ʱ�������������롣��M:���� S:��");
        ms = sc.nextLine();
      } else {
        b = true;
      }
    }

    b = false;
    System.out.println("Ŀ¼fileName==" + fileName);
    System.out.println("ms==" + ms);
    Map<String, Integer> map = new LinkedHashMap<String, Integer>();
    FX logFenXi = new FX();
    try
    {
//      logFenXi.testMethod(fileName, fenxiFileName, startDate, endDate, 
//        ms);
    	File fileDir = new File(fileName);
    	if(fileDir.exists() && fileDir.isDirectory()){
    		File[] file = fileDir.listFiles();
    		for(int i = 0; i < file.length; i++){
    			if(file[i].getName().startsWith("ManagedServer")){
    				Map<String, Integer> tmpRsMap = logFenXi.testMethod(file[i].getAbsolutePath(), fenxiFileName, ms);
    				Set set = tmpRsMap.entrySet();
    				for (Iterator it = set.iterator(); it.hasNext();) {
    					Map.Entry entry = (Map.Entry) it.next();
    					String key = (String)entry.getKey();
    					if(map.get(key) != null && tmpRsMap.get(key) != null){
    						map.put(key, Integer.valueOf(tmpRsMap.get(key)) + Integer.valueOf(map.get(key)));
    					}else{
    						map.put(key, tmpRsMap.get(key));
    					}
    				}
    			}
    		}
    		logFenXi.writeMaptoFile(map);
    	}else{
    		System.out.println(fileName + "�����ڻ��߲���Ŀ¼��");
    	}
    }
    catch (RuntimeException e) {
      e.printStackTrace();
    }

    System.out.println("��־�ļ�����ͳ����ϣ�����");
  }

	public Map<String, Integer> testMethod(String fileName,
			String fenxiFileName, String ms) throws IOException, ParseException {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		InputStream is = new FileInputStream(fileName);

		StringBuffer buffer = new StringBuffer();

		BufferedReader reader = new BufferedReader(new InputStreamReader(is));

		String line = reader.readLine();

		Map map = new LinkedHashMap();

		int m = 0;

		if (ms.equals("M")) {
			m = 5;
		} else if (ms.equals("S")) {
			m = 8;
		}

		int n = 0;
		while (line != null) {
			System.out.println(line);

			if ((line.startsWith("17")) && (line.contains("��ӡϵͳ�߼�����ȫ������ʱʱ��"))) {
				if (map.get(line.substring(0, m)) == null) {
					map.put(line.substring(0, m), Integer.valueOf(1));
				} else {
					int k = ((Integer) map.get(line.substring(0, m)))
							.intValue();
					map.put(line.substring(0, m), Integer.valueOf(k + 1));
				}
			}

			line = reader.readLine();
			n++;
			System.out.println("���ڼ������������" + n);
		}
		is.close();

		reader.close();

		return map;
	}
  
	private void writeMaptoFile(Map map) throws IOException {
		Set set = map.entrySet();

		String fenxiFileName = "FX" + System.currentTimeMillis() + ".out";
		FileOutputStream fos = new FileOutputStream(fenxiFileName);
		OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
		BufferedWriter bw = new BufferedWriter(osw);
		// bw.write("ͳ����ֹʱ��" + startDate + "��" + endDate + "\r\n");
		bw.write("ͳ����־��ӡ���ø����� \r\n");
		for (Iterator it = set.iterator(); it.hasNext();) {
			Map.Entry entry = (Map.Entry) it.next();
			bw.write("ʱ�䣺" + entry.getKey() + "    ��ӡ�����ʽ��" + entry.getValue()
					+ "\r\n");
		}

		bw.close();

		osw.close();

		fos.close();
	}
}
