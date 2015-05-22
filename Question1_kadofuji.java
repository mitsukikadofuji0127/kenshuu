package src.main.java;

public class Question1 {

	public static void main(String[] args) {

	}

	/**
	 * 掛け算を実行するメソッドです。
	 * 
	 * @param num1 掛ける値1。
	 * @param num2 掛ける値2。
	 * @return 計算結果を返します。
	 */
	public int multiply(int num1, int num2) {
		int r = 0;
		r = num1 * num2;
		return r;
	}

	/**
	 * 引数に与えられた値の九九の段を計算するメソッドです。
	 * 
	 * @param timesNum 掛ける段の値。
	 * @return 計算結果を配列で返します。
	 */
	public String[] multiplication(int timesNum) {
		String[] calcString = new String[9];
		//int x = 0; //答えを入れる引数
		for (int n = 1; n < 10; n++) { //引数に与えられた値の九九の段を計算する
			calcString[n - 1] = Integer.toString(multiply(timesNum, n));
			//calcStringに計算した値をint型からString型に
			//	キャストして渡す
		}
		return calcString;
	}

	/**
	 *九九を揃えて表示するメソッドです。
	 * @return 
	 *
	 *
	 *
	 *
	 *
	 */
	public static void multiplicationTable() {
		String[] table;
		for (int i = 1; i < 10; i++) {
			table = multiplication(i);
			for (int j = 1; j < 10; j++) {
				System.out.print(i + "×" + j + "=" + table[j - 1] + "\t");
			}
			//	
			System.out.println();
		}

	}

}