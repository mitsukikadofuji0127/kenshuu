package jp.co.tafs.lesson;

public class GameHardware {

	private String HardwareName = "ニンテンドー3DS";
	private String HardwareMaker = "任天堂";
	private String InternetConnect = "有り";
	private String Releasedate = "2011/02/26";
	private int price = 14286;

	private boolean flag = false; //電源状態の初期値を設定
	private String activeSoft = null; //ソフトウェアの初期値を設定

	public void setPowerOn() { //電源を入れる
		if (!flag) {
			flag = true;
		}
	}

	public void setPowerOff() { //電源を切る
		if (flag) {
			flag = false;
		}
	}

	public boolean getPower() { //電源状態を確認
		return flag;

	}

	public void setSoftware(String name) { //ソフトウェアをセットする
		if (activeSoft == null) {
			activeSoft = name;
		}
	}

	public void setSoftware() { //ソフトウェアを取り出す
		if (activeSoft != null) {
			activeSoft = null;
		}

	}

	public String Softwarename() { //ソフトウェアが入ってるかの確認
		return activeSoft;
	}

	public void gameplay() { //ゲームで遊んでいるかどうか
		System.out.println("ゲームで遊んでいます");
	}
}
