package fourier;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import javax.swing.JPopupMenu;

/**
 * 表示されているパワースペクトルをマウスクリックなどで変更された後のパワースペクトルを表示させるための計算
 */

public class Fourier1dModel extends FourierModel {

	/**
	 * ソースデータを格納するdouble型の配列を保持するフィールド
	 */
	protected double[] sourceData;

	/**
	 * 実部を格納するdouble型の配列を保持するフィールド
	 */
	protected double[] realPart;

	/**
	 * 虚部を格納するdouble型の配列を保持するフィールド
	 */
	protected double[] imaginaryPart;

	/**
	 * パワースペクトルを格納するdouble型の配列を保持するフィールド
	 */
	protected double[] powerSpectrum;

	/**
	 * インタラクティブな実部を格納するdouble型の配列を保持するフィールド
	 */
	protected double[] interactiveRealPart;

	/**
	 * インタラクティブな虚部を格納するdouble型の配列を保持するフィールド
	 */
	protected double[] interactiveImaginaryPart;

	/**
	 * インタラクティブなパワースペクトルを格納するdouble型の配列を保持するフィールド
	 */
	protected double[] interactivePowerSpectrum;

	/**
	 * 逆データを格納するdouble型の配列をを保持するフィールド
	 */
	protected double[] inverseData;

	/**
	 * 元データの枠組み
	 */
	protected FourierPaneModel sourceDataPaneModel = null;

	/**
	 * パワースペクトルの枠組み
	 */
	protected FourierPaneModel powerSpectrumPaneModel = null;

	/**
	 * 双方向性パワースペクトルの枠組み
	 */
	protected FourierPaneModel interactivePowerSpectrumPaneModel = null;

	/**
	 * 双方向性データの枠組み
	 */
	protected FourierPaneModel interactiveDataPaneModel = null;

	/**
	 * デフォルトコンストラクタ
	 * 
	 * @author
	 * @vesion
	 * @date
	 */
	public Fourier1dModel() {
        super().FourierModel
	}

	/**
	 * クリックなどのアクションが発生した時に処理する
	 * 
	 * @author
	 * @version
	 * @date
	 * @param anActionEvent
	 */
	public void actionPerformed(ActionEvent anActionEvent) {

	}

	/**
	 * 数値を計算する
	 * 
	 * @author
	 * @version
	 * @date
	 * @param aPoint
	 * @param isAltDown
	 */
	public void computeFromPoint(Point aPoint, boolean isAltDown) {

	}

	/**
	 * 逆数を計算する
	 * 
	 * @author
	 * @version
	 * @date
	 */
	public void computeInverseData() {
        
	}

	/**
	 * 全てのスペクトルに対して実行
	 * 
	 * @author
	 * @version
	 * @date
	 */
	public void doAllSpectrum() {

	}


	/**
	 * パワースペクトル変更後のチャープ信号のスペクトルを計算
	 * 
	 * @author
	 * @version
	 * @date
	 */
	public void doChirpSignal() {
        
	}

	/**
	 * スペクトルをクリア
	 * 
	 * @author
	 * @version
	 * @date
	 */
	public void doClearSpectrum() {

	}

	/**
	 * パワースペクトル変更後のノコギリ波のスペクトルを計算
	 * 
	 * @author
	 * @version
	 * @date
	 */
	public void doSawtoothWave() {

	}

	/**
	 * パワースペクトル変更後の矩形波のスペクトルを計算
	 * 
	 * @author
	 * @version
	 * @date
	 */
	public void doSquareWave() {

	}

	/**
	 * パワースペクトル変更後のそれ以外の波のデータのスペクトルを計算
	 * 
	 * @author
	 * @version
	 * @date
	 */
	public void doSampleWave() {

	}

	/**
	 * パワースペクトル変更後の三角波のスペクトルを計算
	 * 
	 * @author
	 * @version
	 * @date
	 */
	public void doTriangleWave() {

	}

	/**
	 * マウスクリックした位置をピクチャ座標として受け取る
	 * 
	 * @author 
	 * @version 
	 * @date 
	 * @param aPoint
	 * @param aMouseEvent
	 */
	public void mouseClicked(Point aPoint, MouseEvent aMouseEvent) {
		int x = aPoint.x;
		int y = aPoint.y;
		if((x != 0) || (y != 0)){
		}
		x = 0;
		y = 0;
	}

	/**
	 * マウスドラッグした位置をピクチャ座標として受け取る
	 * interactive 
	 * @author 
	 * @version 
	 * @date 
	 * @param aPoint
	 * @param aMouseEvent
	 */
	public void mouseDragged(Point aPoint, MouseEvent aMouseEvent) {
		double x = aPoint.getX();
		double y = aPoint.getY();
        interactivePowerSpectrum[x] = y;
		super.normalize(interactiveRealPart);
		this.actionPerformed(ActionEvent);
	}

	/**
	 * オープン(open)操作を実行するためのメソッド
	 * 
	 * @author
	 * @version
	 * @date
	 */
	public void open() {

	}

	/**
	 * ソースデータの配列を設定する
	 * 
	 * @author
	 * @version
	 * @date
	 * @param sourceDataArray
	 */
	public void setSourceData(double[] sourceDataArray) {

	}

	/**
	 * メニュー画面の表示
	 * 
	 * @author
	 * @version
	 * @date
	 * @param aMouseEvent
	 * @param aController
	 */
	public void showPopupMenu(MouseEvent aMouseEvent, FourierPaneController aController) {
		JPopupMenu popup = new JPopupMenu();
	
		JMenuItem ChirpSignalItem = new JMenuItem("Chirp Signal");
		ChirpSignalItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Chirp Signal selected");
				this.doChirpSignal();
			}
		});
	
		JMenuItem SampleWaveItem = new JMenuItem("Sample Wave");
		SampleWaveItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Sample Wave selected");
				this.doSampleWave();
			}
		});
	
		JMenuItem sawtoothWaveItem = new JMenuItem("Sawtooth Wave");
		sawtoothWaveItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Sawtooth Wave selected");
				this.dosawtoothWave();
			}
		});
	
		JMenuItem SquareWaveItem = new JMenuItem("Square Wave");
		SquareWaveItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Square Wave selected");
				this.doSquareWave();
			}
		});

    // "TriangleWave"メニューアイテムを作成します
    JMenuItem TriangleWaveItem = new JMenuItem("Triangle Wave");
    TriangleWaveItem.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("Triangle Wave selected");
            this.doTriangleWave();
        }
    });

	 // "AllSpectrum"メニューアイテムを作成します
	 JMenuItem AllSpectrumItem = new JMenuItem("All Spectrum");
	 AllSpectrumItem.addActionListener(new ActionListener() {
		 @Override
		 public void actionPerformed(ActionEvent e) {
			 System.out.println("AllSpectrum selected");
			 this.doAllSpectrum();
		 }
	 });

	  // "ClearSpectrum"メニューアイテムを作成します
	  JMenuItem ClearSpectrumItem = new JMenuItem("Clear Spectrum");
	  ClearSpectrumItem.addActionListener(new ActionListener() {
		  @Override
		  public void actionPerformed(ActionEvent e) {
			  System.out.println("ClearSpectrum selected");
			  this.doClearSpectrum();
		  }
	  });
 

   
	// メニューアイテムをポップアップメニューに追加します
    popup.add(ChirpSignalItem);
    popup.add(SampleWaveItem);
    popup.add(sawtoothWaveItem);
    popup.add(SquareWaveItem);
    popup.add(TriangleWaveItem);
	popup.add(AllSpectrumItem);
	popup.add(ClearSpectrumItem);

    // ポップアップメニューを表示します
    popup.show(aMouseEvent.getComponent(), aMouseEvent.getX(), aMouseEvent.getY());
}

		 

	}


