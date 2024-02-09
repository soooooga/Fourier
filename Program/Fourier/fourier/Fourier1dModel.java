package fourier;

import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.stream.IntStream;

import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JMenuItem;

/**
 * 表示されているパワースペクトルをマウスクリックなどで変更された後のパワースペクトルを表示させるための計算
 */

public class Fourier1dModel extends FourierModel {

	/**
	 * 以前の座標を持つ
	 */
	protected int firstX;
	/**
	 * mouseMovedで移動する前のx座標を保持するためのフィールド
	 */
	protected int previousX;
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
	 * マウス操作可能な画像を持つモデル
	 */
	protected FourierPaneModel interactivePowerSpectrumPaneModel = null;

	/**
	 * 双方向性データの枠組み
	 * 逆変換を施した画像を持つモデル？
	 */
	protected FourierPaneModel interactiveDataPaneModel = null;

	/**
	 * デフォルトコンストラクタ
	 * 
	 * @author Hayami
	 * @vesion 1.0
	 * @date 
	 */
	public Fourier1dModel() {
		super();
		this.setSourceData(FourierModel.dataSampleWave());
	}

	/**
	 * クリックなどのアクションが発生した時に処理する
	 * 
	 * @author Nakamura
	 * @version 1.1
	 * @date 7/10
	 * @param anActionEvent
	 */
	public void actionPerformed(ActionEvent anActionEvent) {
		DiscreteFourier1dTransformation aTransformation = new DiscreteFourier1dTransformation(sourceData);
		sourceDataPaneModel.picture(FourierModel.generateImageForData(sourceData));
		sourceDataPaneModel.changed();
		powerSpectrumPaneModel.picture(FourierModel.generateImageForSpectrum(aTransformation.swap(powerSpectrum)));
		powerSpectrumPaneModel.changed();
		interactivePowerSpectrumPaneModel.picture(FourierModel.generateImageForSpectrum(aTransformation.swap(interactivePowerSpectrum)));
		interactivePowerSpectrumPaneModel.changed();
		computeInverseData();
		interactiveDataPaneModel.picture(FourierModel.generateImageForData(inverseData));
		interactiveDataPaneModel.changed();
	}

	/**
	 * 数値を計算する
	 * 
	 * @author Nakamura
	 * @version 1.0
	 * @date 7/10
	 * @param aPoint
	 * @param isAltDown
	 */
	public void computeFromPoint(Point aPoint, boolean isAltDown) {
		int x = (int) aPoint.getX();
		if (previousX == -1 || Math.abs(intSwap(x,sourceData.length) - intSwap(previousX,sourceData.length)) > 20) {
			previousX = x;
		}
		x = intSwap(x,sourceData.length);
		previousX = intSwap(previousX,sourceData.length);
		if (isAltDown) {
			if (previousX > x) {
				IntStream.rangeClosed(x, previousX).forEach(i -> {
					interactiveRealPart[i] = 0.0;
					interactiveImaginaryPart[i] = 0.0;
					interactivePowerSpectrum[i] = 0.0;
				});
			} else if (previousX < x) {
				IntStream.rangeClosed(previousX, x).forEach(i -> {
					interactiveRealPart[i] = 0.0;
					interactiveImaginaryPart[i] = 0.0;
					interactivePowerSpectrum[i] = 0.0;
				});

			} else {
				interactiveRealPart[x] = 0.0;
				interactiveImaginaryPart[x] = 0.0;
				interactivePowerSpectrum[x] = 0.0;
				previousX = -1;
			}
		} else {
			if (previousX > x) {
				for (Integer i = x; i <= previousX; i++) {
					interactiveRealPart[i] = realPart[i];
					interactiveImaginaryPart[i] = imaginaryPart[i];
					interactivePowerSpectrum[i] = powerSpectrum[i];
				}
			} else if (previousX < x) {
				for (Integer i = previousX; i <= x; i++) {
					interactiveRealPart[i] = realPart[i];
					interactiveImaginaryPart[i] = imaginaryPart[i];
					interactivePowerSpectrum[i] = powerSpectrum[i];
				}
			} else {
				interactiveRealPart[x] = realPart[x];
				interactiveImaginaryPart[x] = imaginaryPart[x];
				interactivePowerSpectrum[x] = powerSpectrum[x];
				previousX = -1;
			}
		}
		return;
	}

	/**
	 * 逆数を計算する
	 * 
	 * @author Nakamura
	 * @version 1.0
	 * @date 7/10
	 */
	public void computeInverseData() {
		DiscreteFourier1dTransformation anInverseTransform = new DiscreteFourier1dTransformation(interactiveRealPart,interactiveImaginaryPart);
		anInverseTransform.inverseTransform();
		inverseData = anInverseTransform.inverseRealPart();
		return;
	}

	/**
	 * 全てのスペクトルに対して実行
	 * @author Hayami
	 * @version 1.1
	 * @date 7/26
	 */
	public void doAllSpectrum() {
		DiscreteFourier1dTransformation aTransform = new DiscreteFourier1dTransformation(sourceData);
		interactivePowerSpectrum = aTransform.powerSpectrum();
		interactiveRealPart = aTransform.realPart();
		interactiveImaginaryPart = aTransform.imaginaryPart();
		computeInverseData();
		this.actionPerformed(null);
	}

	/**
	 * パワースペクトル変更後のチャープ信号のスペクトルを計算
	 * 
	 * @author Hayami
	 * @version 1.0
	 * @date 7/10
	 */
	public void doChirpSignal() {
		this.setSourceData(FourierModel.dataChirpSignal());
		this.actionPerformed(null);
	}

	/**
	 * スペクトルをクリア
	 * @author Hayami
	 * @version 1.0
	 * @date 7/10
	 */
	public void doClearSpectrum() {
		this.setSourceData(sourceData);
		this.actionPerformed(null);
	}

	/**
	 * パワースペクトル変更後のノコギリ波のスペクトルを計算
	 * 
	 * @author Hayami
	 * @version 1.0
	 * @date 7/10
	 */
	public void doSawtoothMove() {
		this.setSourceData(FourierModel.dataSawtoothWave());
		this.actionPerformed(null);
	}

	/**
	 * パワースペクトル変更後の矩形波のスペクトルを計算
	 * 
	 * @author Hayami
	 * @version 1.0
	 * @date 7/10
	 */
	public void doSquareWave() {
		this.setSourceData(FourierModel.dataSquareWave());
		this.actionPerformed(null);
	}

	/**
	 * パワースペクトル変更後のそれ以外の波のデータのスペクトルを計算
	 * 
	 * @author Hayami
	 * @version 1.0
	 * @date 7/10
	 */
	public void doSampleWave() {
		this.setSourceData(FourierModel.dataSampleWave());
		this.actionPerformed(null);
	}

	/**
	 * パワースペクトル変更後の三角波のスペクトルを計算
	 * 
	 * @author Hayami
	 * @version 1.0
	 * @date 7/10
	 */
	public void doTriangleWave() {
		this.setSourceData(FourierModel.dataTriangleWave());
		this.actionPerformed(null);
	}

	/**
	 * マウスクリックした位置をピクチャ座標として受け取る
	 * 
	 * @author Hayami
	 * @version 1.0
	 * @date 7/10
	 * @param aPoint
	 * @param aMouseEvent
	 */
	public void mouseClicked(Point aPoint, MouseEvent aMouseEvent) {
		previousX = -1;
		return;
	}

	/**
	 * マウスドラッグした位置をピクチャ座標として受け取る
	 * ver1.0 powerSpectrumを実装
	 * ver1.1 inverseTransformを実装
	 * ver1.2 消すモードを追加
	 * @author Nakamura
	 * @version 1.3
	 * @date 7/26
	 * @param aPoint
	 * @param aMouseEvent
	 */
	public void mouseDragged(Point aPoint, MouseEvent aMouseEvent) {
		computeFromPoint(aPoint, aMouseEvent.isAltDown());
		this.actionPerformed(null);
		previousX = (int) aPoint.getX();
		return;
	}

	/**
	 * オープン(open)操作を実行するためのメソッド
	 * 
	 * @author Hayami
	 * @version 1.0
	 * @date 7/7
	 */
	public void open() {
		GridLayout aLayout = new GridLayout(2, 2);
		JPanel aPanel = new JPanel(aLayout);
		DiscreteFourier1dTransformation aTransformation = new DiscreteFourier1dTransformation(sourceData);

		BufferedImage imageSourceData = FourierModel.generateImageForData(sourceData);
		this.sourceDataPaneModel = new FourierPaneModel(imageSourceData, "sourceData", this);
		FourierPaneView aView = new FourierPaneView(sourceDataPaneModel, new FourierPaneController());
	
		this.sourceDataPaneModel.addDependent(aView);
		aPanel.add(aView);

		BufferedImage imagePowerSpectrum = FourierModel.generateImageForSpectrum(aTransformation.swap(powerSpectrum));
		this.powerSpectrumPaneModel = new FourierPaneModel(imagePowerSpectrum, "powerSpectrum", this);
		aView = new FourierPaneView(powerSpectrumPaneModel, new FourierPaneController());
		this.powerSpectrumPaneModel.addDependent(aView);
		aPanel.add(aView);
		this.computeInverseData();
		BufferedImage imageInverseData = FourierModel.generateImageForData(inverseData);
		this.interactiveDataPaneModel = new FourierPaneModel(imageInverseData, "inverseData", this);
		aView = new FourierPaneView(interactiveDataPaneModel, new FourierPaneController());
		this.interactiveDataPaneModel.addDependent(aView);
		aPanel.add(aView);

		BufferedImage imageInteractivePowerSpectrum = FourierModel
				.generateImageForSpectrum(interactivePowerSpectrum);
		this.interactivePowerSpectrumPaneModel = new FourierPaneModel(imageInteractivePowerSpectrum,
				"interactivePowerSpectrum", this);
		aView = new FourierPaneView(interactivePowerSpectrumPaneModel, new FourierPaneController());
		this.interactivePowerSpectrumPaneModel.addDependent(aView);
		aPanel.add(aView);
		Example1d.open(aPanel);
	}

	/**
	 * ソースデータの配列を設定する
	 * 
	 * @author Takakura
	 * @version
	 * @date 7/21
	 * @param sourceDataArray
	 */
    public void setSourceData(double[] sourceDataArray) {
			this.sourceData = sourceDataArray;
      DiscreteFourier1dTransformation aTransformation = new DiscreteFourier1dTransformation(sourceData);
      this.realPart = aTransformation.realPart();
      this.imaginaryPart = aTransformation.imaginaryPart();
      this.powerSpectrum = aTransformation.powerSpectrum();
      FourierModel.fill(interactiveRealPart = new double[realPart.length],0.0);
      FourierModel.fill(interactiveImaginaryPart = new double[imaginaryPart.length],0.0);
      FourierModel.fill(interactivePowerSpectrum = new double[powerSpectrum.length],0.0);
      FourierModel.fill(inverseData = new double[sourceData.length],0.0);
      previousX = -1;
      return;
    }

	/**
	 * メニュー画面の表示
	 * @author Nakamura
	 * @version 1.0
	 * @date 7/7
	 * @param aMouseEvent
	 * @param aController
	 */
	public void showPopupMenu(MouseEvent aMouseEvent, FourierPaneController aController) {
		JPopupMenu popupMenu = new JPopupMenu();

		// 項目の作成と追加
		JMenuItem chirpSignalItem = new JMenuItem();
		Runnable chirpRunner = () -> {
			doChirpSignal();
		};
		chirpSignalItem.setAction(new MethodAction("chirp signal", chirpRunner));

		JMenuItem sampleWaveItem = new JMenuItem();
		Runnable sampleWaveRunner = () -> {
			doSampleWave();
		};
		sampleWaveItem.setAction(new MethodAction("sample wave", sampleWaveRunner));

		JMenuItem sawtoothWaveItem = new JMenuItem();
		Runnable sawtoothWaveRunner = () -> {
			doSawtoothMove();
		};
		sawtoothWaveItem.setAction(new MethodAction("sawtooth wave", sawtoothWaveRunner));

		JMenuItem squareWaveItem = new JMenuItem();
		Runnable squareWaveRunner = () -> {
			doSquareWave();
		};
		squareWaveItem.setAction(new MethodAction("square wave", squareWaveRunner));

		JMenuItem triangleWaveItem = new JMenuItem();
		Runnable triangleWaveRunner = () -> {
			doTriangleWave();
		};
		triangleWaveItem.setAction(new MethodAction("triangle wave", triangleWaveRunner));

		JMenuItem allSpectrumItem = new JMenuItem();
		Runnable allSpectrumRunner = () -> {
			doAllSpectrum();
		};
		allSpectrumItem.setAction(new MethodAction("all spectrum", allSpectrumRunner));

		JMenuItem clearSpectrumItem = new JMenuItem();
		Runnable clearSpectrumRunner = () -> {
			doClearSpectrum();
		};
		clearSpectrumItem.setAction(new MethodAction("clear spectrum", clearSpectrumRunner));

		popupMenu.add(chirpSignalItem);
		popupMenu.add(sampleWaveItem);
		popupMenu.add(sawtoothWaveItem);
		popupMenu.add(squareWaveItem);
		popupMenu.add(triangleWaveItem);

		popupMenu.addSeparator();

		popupMenu.add(allSpectrumItem);
		popupMenu.add(clearSpectrumItem);

		popupMenu.show((JComponent) aMouseEvent.getSource(), aMouseEvent.getX(), aMouseEvent.getY());

		// aPanel.setComponentPopupMenu(popupMenu);
	}

}

@SuppressWarnings("serial")
class MethodAction extends AbstractAction {
	public static final long serialVersionUID = 1L;
	public Runnable handleCallback = null;

	public MethodAction(String actionName, Runnable handleCallback) {
		super(actionName);
		this.handleCallback = handleCallback;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		this.handleCallback.run();
	}

}
