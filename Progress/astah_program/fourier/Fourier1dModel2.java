package fourier;
import java.awt.Point;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;

import pane.PaneController;
import pane.PaneModel;
import pane.PaneView;
import utility.ImageUtility;

import javax.swing.*;
import java.awt.event.*;

/**
 * 表示されているパワースペクトルをマウスクリックなどで変更された後のパワースペクトルを表示させるための計算
 */

public abstract class Fourier1dModel2 extends FourierModel {

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
	public Fourier1dModel2() {
        super.FourierModel();
	}

	/**
	 * ポップアップメニューの項目が選択されたときの処理をする抽象メソッド。
	 * 
	 * @author
	 * @version
	 * @date
	 * @param anActionEvent
	 */
	public abstract void actionPerformed(ActionEvent anActionEvent);

	/**
	 * 指定された座標に対応するインタラクティブな実部と虚部を編集して計算を行う。
	 * 
	 * @author
	 * @version
	 * @date
	 * @param aPoint
	 * @param isAltDown
	 */
	public void computeFromPoint(Point aPoint, boolean isAltDown) {
		realPart = aPoint.getX();
		imaginaryPart = aPoint.getY();
		if(isAltDown){
			
		}
	}

	/**
	 * インタラクティブな実部と虚部から計算(元データの復元)を行う。
	 * 
	 * @author
	 * @version
	 * @date
	 */
	public void computeInverseData() {
        
	}

	/**
	 * スペクトル黒くする
	 * 
	 * @author
	 * @version
	 * @date
	 */
	public void doAllSpectrum() {
		Arrays.fill(sourceData,0.0);
		return;
	}


	/**
	 * チャープ信号のソースデータ持ってくる。
	 * 
	 * @author
	 * @version
	 * @date
	 */
	public void doChirpSignal() {
        sourceData = super.dataChirpSignal();
		return;
	}

	/**
	 * スペクトルをクリア
	 * 
	 * @author
	 * @version
	 * @date
	 */
	public void doClearSpectrum() {
		Arrays.fill(sourceData,1.0);
		return;
	}

	/**
	 * パワースペクトル変更後のノコギリ波のスペクトルを計算
	 * 
	 * @author
	 * @version
	 * @date
	 */
	public void doSawtoothMove() {
		sourceData = super.dataSawtoothWave();
		return;
	}

	/**
	 * パワースペクトル変更後の矩形波のスペクトルを計算
	 * 
	 * @author
	 * @version
	 * @date
	 */
	public void doSquareWave() {
		sourceData = super.dataSquareWave();
		return;

	}

	/**
	 * パワースペクトル変更後のそれ以外の波のデータのスペクトルを計算
	 * 
	 * @author
	 * @version
	 * @date
	 */
	public void doSampleWave() {
		sourceData = super.dataSampleWave();
		return;
	}

	/**
	 * パワースペクトル変更後の三角波のスペクトルを計算
	 * 
	 * @author
	 * @version
	 * @date
	 */
	public void doTriangleWave() {
		sourceData = super.dataTriangleWave();
		return;

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
	 * 
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
		GridLayout aLayout = new GridLayout(2, 2);
		JPanel aPanel = new JPanel(aLayout);
		DiscreteFourier1dTransformation aTransformation = new DiscreteFourier1dTransformation(sourceData);
		double[] realPart = aTransformation.realPart();
		double[] imaginaryPart = aTransformation.imaginaryPart();
		double[] powerSpectrum = aTransformation.powerSpectrum();
		BufferedImage imageSourceData = FourierModel.generateImageForData(sourceData);
		FourierPaneModel sourceDataPaneModel = new FourierPaneModel(imageSourceData);
		PaneView aView = new PaneView(sourceDataPaneModel , new PaneController());
		aPanel.add(aView);
		Example1d.write(imageSourceData);

		BufferedImage imageTwoParts = FourierModel.generateImageForParts(aTransformation.swap(realPart),
			aTransformation.swap(imaginaryPart));
		FourierPaneModel powerSpectrumPaneModel = new FourierPaneModel(imageTwoParts);
		aView = new PaneView(powerSpectrumPaneModel, new PaneController());
		aPanel.add(aView);
		Example1d.write(imageTwoParts);

		aTransformation = new DiscreteFourier1dTransformation(interactiveRealPart, interactiveImaginaryPart);
		double[] inverseRealPart = aTransformation.inverseRealPart();
		double[] inverseImaginaryPart = aTransformation.inverseImaginaryPart();
		BufferedImage imageInverseData = FourierModel.generateImageForData(interactiveRealPart);
		FourierPaneModel interactiveDataPaneModel = new FourierPaneModel(imageInverseData);
		aView = new PaneView(interactiveDataPaneModel, new PaneController());
		aPanel.add(aView);
		Example1d.write(imageInverseData);

		imageTwoParts = FourierModel.generateImageForParts(aTransformation.swap(interactiveRealPart),
			aTransformation.swap(interactiveImaginaryPart));
		FourierPaneModel interactivePowerSpectrumPaneModel = new FourierPaneModel(imageTwoParts);
		aView = new PaneView(interactivePowerSpectrumPaneModel, new PaneController());
		aPanel.add(aView);
		Example1d.write(imageTwoParts);


		JFrame aWindow = new JFrame("Fourier Example (1D)");
		aWindow.getContentPane().add(aPanel);
		aWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		aWindow.addNotify();
		int titleBarHeight = aWindow.getInsets().top;
		aWindow.setMinimumSize(new Dimension(400, 200 + titleBarHeight));
		aWindow.setResizable(true);
		aWindow.setSize(800, 400 + titleBarHeight);
		aWindow.setLocation(displayPoint.x, displayPoint.y);
		aWindow.setVisible(true);
		aWindow.toFront();
		displayPoint.translate(offsetPoint.x, offsetPoint.y);
		return;

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
