package src.main.java;

public class Question2 {

	public static void main(String[] args) {
		// ソート対象となる配列です。
		int[] arr = { 5, 4, 2, 3, 8, 7, 1, 0, 6, 9 };

		// ソート前の値をコンソールに出力します。
		System.out.print("ソート前：");
		arrayPrintln(arr);
		//int a = arr[0];
		//System.out.println(a);
		//System.out.println(arr.length - 1);

		// ソートを実行します。
		quickSort(arr, 0, arr.length - 1);

		// ソート後の値をコンソールに出力します。
		System.out.print("ソート後：");
		arrayPrintln(arr);
	}

	/**
	 * クイックソートを行うメソッドです。
	 * ここに実装してください。
	 */
	public static void quickSort(int[] arr, int left, int right) {
		if (left <= right) {
			int top = left;//左端
			int tail = right;//右端
			int pivot = arr[(left + right) / 2];
			int save = 0;

			while (top <= tail) {
				while (arr[top] <= pivot) {
					top++;
				}
				while (arr[tail] > pivot) {
					tail--;
				}
				if (top <= tail) {
					save = arr[top];
					arr[top] = arr[tail];
					arr[tail] = save;
					top++;
					tail--;
				}
			}
			//再帰処理呼び出し
			quickSort(arr, left, tail - 1);
			quickSort(arr, top + 1, right);

		}
	}

	/**
	 * 配列の値を順に出力するメソッドです。
	 * 
	 * @param arr 出力対象の配列。
	 * @return 計算結果を配列で返します。
	 */
	public static void arrayPrintln(int[] arr) {

		// 配列の値を順に取り出し、コンソールに出力します。
		for (int i = 0; i < arr.length; i++) {
			System.out.println(arr[i] + " ");
		}
		System.out.println("");
	}

}