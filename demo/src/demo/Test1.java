package demo;

import java.util.Scanner;

public class Test1 {
	public static void main(String[] args) {
		/*
		 * ����
		 * ����ͬ�� ��35ֻ ����һ��94ֻ ���ʸ�����ֻ int total = 35; int feet = 94;
		 * 
		 * int ji; int tu;
		 * 
		 * for( ji = 1;ji<=total;ji++) { tu = total-ji; if(tu*4+ji*2==feet) {
		 * System.out.println(tu+"=="+ji); break; } }
		 */

		// �ж�100-1000����Щ��Ϊˮ�ɻ���
		
		/*
		 * for (int i = 100; i <=1000; i++) { int baiwei = i/100; int shiwei = i%100/10;
		 * int gewei = i%100%10;
		 * if((Math.pow(baiwei,3)+Math.pow(shiwei,3)+Math.pow(gewei,3))==i) {
		 * System.out.println(i); } }
		 */
		
		//3000�׳������ӣ�ÿ���һ�롣�ʶ�����������ӻ�С��5�ף�������С��
		/*
		 * int day = 1; for(int i=3000;i>5;i = i/2) { //i = i/2; day++; }
		 * System.out.println(day);
		 */
		//��һ�����ӣ��ӳ�������������ÿ���¶���һ�����ӣ�С���ӳ����������º�ÿ��������һ�����ӣ��������Ӷ���������M����ʱ���ӵ�������MΪ���̶����������
		int m1 = 1;
		int m2 = 1;
		int temp;
		int month = 0;//����
		Scanner input = new Scanner(System.in);
		System.out.println("�������·�");
		month = input.nextInt();
		for(int i = 1;i<=month;i++) {
			if(i==1) {
				System.out.println("��"+i+"������"+m1+"������");
				continue;
			} else if(i==2) {
				System.out.println("��"+i+"������"+m2+"������");
				continue;
			} else {
				temp = m2;
				m2 = m1+m2;
				m1 = temp;
				System.out.println("��"+i+"������"+m2+"������");
			} 
			
		}
	}

}
