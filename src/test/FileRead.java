package test;



import java.io.IOException;
import java.util.BitSet;



public class FileRead {
	
	/*
	 * ��n����������ѡm�������������
	 */
	public static void Com(String[] strData,int n,int m){
		int[] number=new int[n];
		long count=0;
		boolean findfirst,end,swap;
		int i,j,k,l;
		end=false;
		System.out.println("n=" + n + ",m=" + m);
		//��ʼ��number
		for(int ii=0;ii<m;ii++){
			number[ii]=1;
		}
		//���ε��������Խ�Լʱ�� ,��һ������
		for(int ii=0;ii<n;ii++) {
			if(number[ii]!=0){
				//System.out.print((ii+1)+" ");
				System.out.print(strData[ii]+" ");
			}
		}
		System.out.println();
		/*
		 * ����������ʼֵ
		 */
		count=1; //����
		j=n; 
		k=0;
		if(n==m)return;
		while(!end) 
		{ 
			findfirst=false; 
			swap=false; //��־��λ 
			for(i=0;i<j;i++) { 
				if(!findfirst && number[i]!=0) { 
					k=i; //k��¼��ɨ�赽�ĵ�һ���� 
					findfirst=true; //���ñ�־	
				} 
				if(number[i]==1 && number[i+1]==0) //������ɨ���һ����10����� 
				{ 	
					number[i]=0; 
					number[i+1]=1; 
					swap=true; //���ý�����־ 
					for(l=0;l<i-k;l++) 
						number[l]=number[k+l]; 
					for(l=i-k;l<i;l++) 
						number[l]=0; //������֮ǰ�ġ�1��ȫ���ƶ�������� 
					if(k==i && i+1==n-m) //�����һ����1���Ѿ��ƶ�����m-n��λ�ã�˵���������һ������ˡ� 
						end=true;
				} 
				if(swap) //����һ�κ�Ͳ��ü����ҡ�10������� 
					break; 
			} 
			//���ε��������Խ�Լʱ�� 
			for(int ii=0;ii<n;ii++) {
				if(number[ii]!=0){
					//System.out.print((ii+1)+" ");
					System.out.print(strData[ii]+" ");
				}
			}
			System.out.println();
			count++; //���������������1  
		}
		System.out.println("count="+count);

	}
	
	
	public static void main(String[] args) throws IOException {
		
		String str="a b c d e f";
		String[]strSpilt=str.split(" ");
		int n=6;
		int m=3;
		Com(strSpilt,n,m);
		
//		String tempStr = "01111000";
//		BitSet bs = new BitSet(tempStr.length()); 
//		for(int i = 0; i < tempStr.length(); i++){
//			char temp = tempStr.charAt(i);
//			if('1' == temp){
//				System.out.println(i);
//				bs.set(i);
//			}
//		}
//		System.out.print(bs.toString());
//		System.out.println("wuqu");
	}
	

}
