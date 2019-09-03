package demo;

import java.util.Scanner;

public class Test1 {
	public static void main(String[] args) {
		/*
		 * 测试
		 * 鸡兔同笼 共35只 ，脚一共94只 ，问各多少只 int total = 35; int feet = 94;
		 * 
		 * int ji; int tu;
		 * 
		 * for( ji = 1;ji<=total;ji++) { tu = total-ji; if(tu*4+ji*2==feet) {
		 * System.out.println(tu+"=="+ji); break; } }
		 */

		// 判断100-1000中哪些数为水仙花数
		
		/*
		 * for (int i = 100; i <=1000; i++) { int baiwei = i/100; int shiwei = i%100/10;
		 * int gewei = i%100%10;
		 * if((Math.pow(baiwei,3)+Math.pow(shiwei,3)+Math.pow(gewei,3))==i) {
		 * System.out.println(i); } }
		 */
		
		//3000米长的绳子，每天减一半。问多少天这个绳子会小于5米？不考虑小数
		/*
		 * int day = 1; for(int i=3000;i>5;i = i/2) { //i = i/2; day++; }
		 * System.out.println(day);
		 */
		//有一对兔子，从出生第三个月起每个月都生一对兔子，小兔子长到第三个月后，每个月又生一对兔子，假如兔子都不死，问M个月时兔子的数量，M为键盘读入的正整数
		int m1 = 1;
		int m2 = 1;
		int temp;
		int month = 0;//总数
		Scanner input = new Scanner(System.in);
		System.out.println("请输入月份");
		month = input.nextInt();
		for(int i = 1;i<=month;i++) {
			if(i==1) {
				System.out.println("第"+i+"个月有"+m1+"对兔子");
				continue;
			} else if(i==2) {
				System.out.println("第"+i+"个月有"+m2+"对兔子");
				continue;
			} else {
				temp = m2;
				m2 = m1+m2;
				m1 = temp;
				System.out.println("第"+i+"个月有"+m2+"对兔子");
			} 
			
		}
	}

}
