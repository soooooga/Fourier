package fourier;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;

/**
 * 
 */

public abstract class FourierPaneModel extends pane.PaneModel {

	/**
	 * 
	 */
	private String label = "";

	/**
	 * 
	 */
	private FourierModel listener = null;

	/**
	 * 
	 * 
	 * @author
	 * @version
	 * @date
	 */
	public FourierPaneModel() {
		super();
		this.label();
		this.listener();
		return;
	}

	/**
	 * 
	 * 
	 * @author
	 * @version
	 * @date
	 * @param aString
	 */
	public FourierPaneModel(String aString) {

	}

	/**
	 * * 
	 * 
	 * @author
	 * @version
	 * @date
	 * @param aString
	 * @param anlmage
	 * @param aString
	 */
	public FourierPaneModel(BufferedImage anlmage, String aString) {

	}

	/**
	 * 
	 * 
	 * @author
	 * @version
	 * @date
	 * @param aString
	 * @param anlmage
	 * @param aString
	 * @param aModel
	 */
	public FourierPaneModel(BufferedImage anlmage, String aString, FourierModel aModel) {

	}

	/**
	 *
	 * 
	 * @author
	 * @version
	 * @date
	 * @param aString
	 * @param anActonEvent
	 */
	public abstract void actionPerformed(ActionEvent anActonEvent);

	/**
	 * 現在のユーザからインタラクションを受け付ける状態かどうかを返す。
	 * 編集するウィンドウ用の配列 書いたり消したりする処理
	 * @author
	 * @version
	 * @date
	 * @return
	 */
	public boolean isInteractive() {
		return false;
	}

	/**
	 * 
	 * 
	 * @author
	 * @version
	 * @date
	 * @return
	 */
	public boolean isNotInteractive() {
		return false;
	}

	/**
	 * 
	 * 
	 * @author
	 * @version
	 * @date
	 * @return
	 */
	public String label() {
		return null;
	}

	/**
	 * 
	 * 
	 * @author Omori
	 * @version
	 * @date
	 * @param aPoint
	 * @param aMouseEvent
	 */
	public void mouseClicked(Point aPoint, MouseEvent aMouseEvent) {
	Fourier1dModel.mouseDragged(aPoint);
	}

	/**
	 * 
	 * 
	 * @author　Omori
	 * @version
	 * @date
	 * @param aPoint
	 * @param aMouseEvent
	 */
	public void moouseDragged(Point aPoint, MouseEvent aMouseEvent) {
    Fourier1dModel.mouseDragged(aPoint);
	}

	/**
	 * 
	 * 
	 * @author
	 * @version
	 * @date
	 * @param aMouseEvent
	 * @param aController
	 */
	public void showPopupMenu(MouseEvent aMouseEvent, FourierPaneController aController) {
      
	}

}
