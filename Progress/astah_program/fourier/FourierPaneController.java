package fourier;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.Point;

import condition.Condition;
import condition.ValueHolder;
/**
 * 
 * 
 */
public abstract class FourierPaneController extends pane.PaneController {

	/**
	 * メニューポップアップを出すかどうか(真・偽)を保持するフィールド
	 */
	private boolean isMenuPopuping;

	/**
	 * フーリエ変換の窓のコントローラー
	 * 
	 * @author Takakura,Omori
	 * @version
	 * @date
	 */
	public FourierPaneController() {
		super();
		this.isMenuPopuping = null;
	}

	/**
	 * アクションイベントが起きることをモデルに通知する
	 *
	 * @author Takakura,Omori
	 * @version
	 * @date
	 * @param anActionEvent
	 */
	public abstract void actionPerformed(ActionEvent anActionEvent) ;

	/**
	 * マウスクリックした一をピクチャ座標にしてモデルに通知する。
	 * 
	 * @author Takakura,Omori
	 * @version
	 * @date
	 * @param aMouseEvent
	 */
	public void mouseClicked(MouseEvent aMouseEvent) {
		{
			try
			{
				ValueHolder<Point> aPoint = new ValueHolder<Point>(aMouseEvent.getPoint());
				PaneView aView = this.getView();
				aPoint.set(aView.convertViewPointToPicturePoint(aPoint.get()));
				new Condition(() -> aPoint.get() == null).ifTrue(() -> { throw new RuntimeException(); });
				aView.getModel().mouseClicked(aPoint.get(), aMouseEvent);
			}
			catch (RuntimeException anException) { return; }
			return;
		}
	}

	/**
	 * マウスドラッグした１をピクチャ座標にしてモデルに通知する。
	 * 
	 * 
	 * @author Tkakura,Omori
	 * @version
	 * @date
	 * @param aMouseEvent
	 */
	public void mouseDragged(MouseEvent aMouseEvent) {
        {
            try
            {
                ValueHolder<Point> aPoint = new ValueHolder<Point>(aMouseEvent.getPoint());
                PaneView aView = this.getView();
                aPoint.set(aView.convertViewPointToPicturePoint(aPoint.get()));
                new Condition(() -> aPoint.get() == null).ifTrue(() -> { throw new RuntimeException(); });
                aView.getModel().mouseDragged(aPoint.get(), aMouseEvent);
            }
            catch (RuntimeException anException) { return; }
            return;
        }
	}

	/**
	 * マウスが動いた位置をピクチャ座標にしてモデルに通知する
	 * 
	 * @author Takakura,Omori
	 * @version
	 * @date
	 * @param aMouseEvent
	 */
	public void mouseMoved(MouseEvent aMouseEvent) {
        {
            try
            {
                ValueHolder<Point> aPoint = new ValueHolder<Point>(aMouseEvent.getPoint());
                PaneView aView = this.getView();
                aPoint.set(aView.convertViewPointToPicturePoint(aPoint.get()));
                new Condition(() -> aPoint.get() == null).ifTrue(() -> { throw new RuntimeException(); });
                aView.getModel().mouseMoved(aPoint.get(), aMouseEvent);
            }
            catch (RuntimeException anException) { return; }
            return;
        }
	}

	/**
	 * マウスが押し続けられた位置をピクチャ座標にしてモデルに通知する
	 * 
	 * @author  Takakkura,Omori
	 * @version
	 * @date
	 * @param aMouseEvent
	 */
	public void mousePressed(MouseEvent aMouseEvent) {
        {
            try
            {
                ValueHolder<Point> aPoint = new ValueHolder<Point>(aMouseEvent.getPoint());
                PaneView aView = this.getView();
                aPoint.set(aView.convertViewPointToPicturePoint(aPoint.get()));
                new Condition(() -> aPoint.get() == null).ifTrue(() -> { throw new RuntimeException(); });
                aView.getModel().mousePressed(aPoint.get(), aMouseEvent);
            }
            catch (RuntimeException anException) { return; }
            return;
        }
	}

	/**
	 * マウスが押し続けられていたのが離された位置をピクチャ座標にしてモデルに通知する
	 * 
	 * @author Takakura,Omori
	 * @version
	 * @date
	 * @param aMouseEvent
	 */
	public void mouseReleased(MouseEvent aMouseEvent) {
        {
            try
            {
                ValueHolder<Point> aPoint = new ValueHolder<Point>(aMouseEvent.getPoint());
                PaneView aView = this.getView();
                aPoint.set(aView.convertViewPointToPicturePoint(aPoint.get()));
                new Condition(() -> aPoint.get() == null).ifTrue(() -> { throw new RuntimeException(); });
                aView.getModel().mouseReleased(aPoint.get(), aMouseEvent);
            }
            catch (RuntimeException anException) { return; }
            return;
        }

	}

	/**
	 * ポップアップメニューが出される場所をピクチャ座標にしてモデルに通知する
	 * 
	 * @author Takakura,Omori
	 * @version
	 * @date
	 * @param aMouseEvent
	 */
	public abstract void showPopupMenu(MouseEvent aMouseEvent) ;{
        {
            try
            {
                ValueHolder<Point> aPoint = new ValueHolder<Point>(aMouseEvent.getPoint());
                PaneView aView = this.getView();
                aPoint.set(aView.convertViewPointToPicturePoint(aPoint.get()));
                new Condition(() -> aPoint.get() == null).ifTrue(() -> { throw new RuntimeException(); });
                aView.getModel().showPopupMenu(aPoint.get(), aMouseEvent);
            }
            catch (RuntimeException anException) { return; }
            return;
        }

	}
	


        
}
