package fourier;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;


import utility.ImageUtility;

/**
 * フーリエ変換の窓のモデル
 */

public class FourierPaneModel extends pane.PaneModel {

	/**
	 * 4つのウィンドウのどれかを判別するラベル
	 */
	private String label = "";

	/**
	 * 自身を作成したFourierModelを持つ
	 * Fourier1d or Fourier2d
	 */
	private FourierModel listener = null;

	/**
	 * デフォルトコンストラクタ
	 * @author Hayami
	 * @version 1.0
	 * @date 6/9
	 */
	public FourierPaneModel() {
		super();
		return;
	}

	/**
	 * Exampleからペインモデルを作るコンストラクタ
	 * 
	 * @author Nakamura
	 * @version 1.0
	 * @date 6/16
	 * @param aString ファイルパス
	 */
	public FourierPaneModel(String aString) {
		super();
		BufferedImage anImage = ImageUtility.readImage(aString);
		this.picture(anImage);
		return;
	}

	/**
	 * ファイル名からペインモデルを作るコンストラクタ
	 * 
	 * @author
	 * @version
	 * @date
	 * @param aString
	 * @param anlmage
	 * @param aString
	 */
	public FourierPaneModel(BufferedImage anImage, String aString) {
		super(aString);
		this.picture(anImage);
		return;
	}

	/**
	 * FourierModelから呼び出す時のコンストラクタ
	 * @author Hayami
	 * @version 1.0
	 * @date 6/9
	 * @param aString label
	 * @param anlmage 画像
	 * @param aModel FourierModel(1d or 2d)
	 */
	public FourierPaneModel(BufferedImage anImage, String aString, FourierModel aModel) {
		this.label = aString;
		this.picture(anImage);// anlmage を処理するなどの追加の初期化処理を行う
		this.listener = aModel; // フィールドに渡された FourierModel を設定する
		return;
	}

	/**
	 * クリックなどのアクションが発生した時に処理する（未実装）
	 * @author
	 * @version
	 * @date
	 * @param aString
	 * @param anActonEvent
	 */
	public void actionPerformed(ActionEvent anActonEvent){

	};

	/**
	 * 現在のユーザからインタラクションを受け付ける状態かどうかを返す。
	 * 
	 * @author Hayami
	 * @version 1.0
	 * @date 7/14
	 * @return boolean
	 */
	public boolean isInteractive() {
		if(this.label.equals("interactivePowerSpectrum")){
			return true;
		}
		return false;
	}

	/**
	 * 現在のユーザからインタラクションを受け付けない状態かどうかを返す。
	 * 
	 * @author Hayami
	 * @version 1.0
	 * @date 7/14
	 * @return 
	 */
	public boolean isNotInteractive() {
		if(this.label.equals("interactivePowerSpectrum")){
			return false;
		}
		return true;
	}

	/**
	 * ラベルを返す
	 * 
	 * @author Hayami
	 * @version 1.0
	 * @date 6/9
	 * @return label
	 */
	public String label() {
		return this.label;
	}

	/**
	 * マウスクリックの時の動作
	 * @author Nakamura
	 * @version 1.0
	 * @date 7/7
	 * @param aPoint
	 * @param aMouseEvent
	 */
	public void mouseClicked(Point aPoint, MouseEvent aMouseEvent) {
		FourierPaneController aController=new FourierPaneController();
		if(aMouseEvent.getButton()==MouseEvent.BUTTON1){
			this.listener.mouseClicked(aPoint, aMouseEvent);
		}
		if(aMouseEvent.getButton()==MouseEvent.BUTTON3){
			this.showPopupMenu(aMouseEvent, aController);
		}
		return;
	}

	/**
	 * マウスドラッグの時の動作
	 * @author Hayami
	 * @version 1.0
	 * @date 6/9
	 * @param aPoint
	 * @param aMouseEvent
	 */
	
	public void mouseDragged(Point aPoint, MouseEvent aMouseEvent) {
		if(this.isInteractive()){
			listener.mouseDragged(aPoint, aMouseEvent);
		}
		return;
	}

	/**
	 * ポップアップメニュー表示の動作
	 * 
	 * @author Hayami
	 * @version 1.0
	 * @date 66/9
	 * @param aMouseEvent
	 * @param aController
	 */
	public void showPopupMenu(MouseEvent aMouseEvent, FourierPaneController aController) {
		listener.showPopupMenu(aMouseEvent, aController);
	}
}
