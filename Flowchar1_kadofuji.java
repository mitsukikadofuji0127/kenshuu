package src.main.java;

public class Flowchar1 {

	public static void main(String[] args) {
		Fizzbass();

	}

	//Fizzbazzのメソッド
	public static void Fizzbass() {
		for (int i = 1; i < 101; i++) {
			if (i % 3 == 0) { //iが3で割り切れる数か検証
				if (i % 5 == 0) { //iが3で割り切れて、かつ5で割り切れたらFizzbassを出力
					System.out.println(i + " Fizzbass");
				}
				else { //iが3でしか割り切れなかったときにFizzを出力
					System.out.println(i + " Fizz");
				}
			}
			else { //iが3で割り切れなかったとき
				if (i % 5 == 0) { //iが5で割り切れたらbassを出力
					System.out.println(i + " bass");
				}
				else { //3でも5でも割り切れなかったとき、数字を出力
					System.out.println(i);
				}
			}
		}
	}

}