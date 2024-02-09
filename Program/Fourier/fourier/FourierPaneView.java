package fourier;

/*
 * フーリエ変換の窓のビュー
 */
public class FourierPaneView extends pane.PaneView {

	/**
	 * 指定されたモデルから結果を受け取る
	 * （未実装）
	 * 
	 * @author
	 * @version
	 * @date 
	 * @param aModel
	 */
	 /* public FourierPaneView(FourierPaneModel aModel) {
		super();
	}  */

	/**
	 * 指定されたモデルとコントローラと自分（ビュー）とでMVCを構築するコンストラクタ。
	 * 
	 * @author 中村
	 * @version 1.0
	 * @date 2023/5/26
	 * @param aModel このビューのモデル
	 * @param aController このビューのコントローラ
	 */
	public FourierPaneView(FourierPaneModel aModel, FourierPaneController aController) {
		super(aModel,aController);

	}

	/**
	 * 再描画を行う
	 * 受け取った座標をもとにパワースペクトルを編集(未実装)
	 * @author
	 * @version
	 * @date
	 * @param aGraphics
	 */
	/*public void paintComponent(Graphics aGraphics)
	{
	}
	*/
}
