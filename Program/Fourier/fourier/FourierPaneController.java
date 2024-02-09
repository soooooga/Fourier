package fourier;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.lang.ModuleLayer.Controller;
import java.awt.Point;

import pane.PaneView;
import condition.Condition;
import condition.ValueHolder;

/**
 * フーリエ変換の窓のコントローラー
 */
public class FourierPaneController extends pane.PaneController {

	/**
	 * メニューポップアップを出すかどうか(真・偽)を保持するフィールド
	 */
	// private boolean isMenuPopuping = true;

	/**
	 * フーリエ変換の窓のコントローラー
	 * 
	 * @author Takakura,Omori
	 * @version 1.0
	 * @date 6/9
	 */
	public FourierPaneController() {
		super();
	}

	/**
	 * アクションイベントが起きることをモデルに通知する
	 * 
	 * @author Takakura,Omori
	 * @version 1.0
	 * @date 6/9
	 * @param anActionEvent
	 */
	public void actionPerformed(ActionEvent anActionEvent) {

	};

	/**
	 * マウスクリックした位置をピクチャ座標にしてモデルに通知する。
	 * 
	 * @author Takakura,Omori
	 * @version 1.0
	 * @date 6/9
	 * @param aMouseEvent
	 */
	@Override
	public void mouseClicked(MouseEvent aMouseEvent) {
		super.mouseClicked(aMouseEvent);
	}

	/**
	 * マウスドラッグをピクチャ座標にしてモデルに通知する。
	 * 
	 * @author Tkakura,Omori
	 * @version 1.0
	 * @date 6/9
	 * @param aMouseEvent
	 */
	@Override
	public void mouseDragged(MouseEvent aMouseEvent) {
		super.mouseDragged(aMouseEvent);
		return;
	}

	/**
	 * マウスが動いた位置をピクチャ座標にしてモデルに通知する
	 * 
	 * @author Takakura,Omori
	 * @version 1.0
	 * @date 6/9
	 * @param aMouseEvent
	 */
	public void mouseMoved(MouseEvent aMouseEvent) {
		super.mouseMoved(aMouseEvent);
	}

	/**
	 * マウスが押し続けられた位置をピクチャ座標にしてモデルに通知する
	 * 
	 * @author Takakkura,Omori
	 * @version 1.0
	 * @date 6/9
	 * @param aMouseEvent
	 */

	public void mousePressed(MouseEvent aMouseEvent) {
		super.mousePressed(aMouseEvent);
		return;
	}

	/**
	 * マウスが押し続けられていたのが離された位置をピクチャ座標にしてモデルに通知する
	 * 
	 * @author Takakura,Omori
	 * @version 1.0
	 * @date 6/9
	 * @param aMouseEvent
	 */
	/*
	public void mouseReleased(MouseEvent aMouseEvent) {
		{
			try {
				ValueHolder<Point> aPoint = new ValueHolder<Point>(aMouseEvent.getPoint());
				PaneView aView = this.getView();
				aPoint.set(aView.convertViewPointToPicturePoint(aPoint.get()));
				new Condition(() -> aPoint.get() == null).ifTrue(() -> {
					throw new RuntimeException();
				});
				aView.getModel().mouseReleased(aPoint.get(), aMouseEvent);
			} catch (RuntimeException anException) {
				return;
			}
			return;
		}

	*/

	/**
	 * ポップアップメニューが出される場所をピクチャ座標にしてモデルに通知する
	 * 
	 * @author Takakura,Omori
	 * @version 1.0
	 * @date 7/9
	 * @param aMouseEvent
	 */
	public void showPopupMenu(MouseEvent aMouseEvent) {

	}
}
