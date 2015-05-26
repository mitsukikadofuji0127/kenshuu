package jp.co.tafs.lesson;

public class IamGamePlayer {
	public static void main(String[] args) {

		GameHardware game = new GameHardware();//インスタンスの生成
		game.getPower();//電源の確認
		game.setPowerOn();//電源を入れる

		game.Softwarename();//ソフトウェアが入っているかの確認
		game.setSoftware("スーパーマリオブラザーズ");//ソフトウェアを設定

		game.gameplay();//ゲームで遊ぶ

		System.out.println("ゲームで遊びました。");//感想
	}

}
