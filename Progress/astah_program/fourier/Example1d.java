package fourier;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.awt.Point;
import java.io.File;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;
import pane.PaneController;
import pane.PaneModel;
import pane.PaneView;
import utility.ImageUtility;

/**
 * 離散フーリエ1次元変換の例題プログラム。
 * オブザーバ・デザインパターン(MVC: Model-View-Controller)を用いた典型的(模範的)なプログラム。
 */
public class Example1d extends Object {
	/**
	 * 画像をファイルに書き出す際の番号。
	 */
	private static int fileNo = 0;

	/**
	 * ウィンドウの表示位置。
	 */
	private static Point displayPoint = new Point(30, 50);

	/**
	 * ウィンドウをずらして表示してゆく際の支距。
	 */
	private static Point offsetPoint = new Point(25, 25);

	/**
	 * 離散フーリエ1次元変換の例題プログラム群を実行する。
	 */
	public static void main(String[] arguments) {
		// 離散フーリエ1次元変換の例題プログラム(いくつかの正弦波と余弦波の合成波)を実行する。
		Example1d.example1();
		Example1d.example2();

		// 離散フーリエ1次元変換の例題プログラム(チャープ信号:低周波から高周波に周波数が連続的に変わる信号)を実行する。
		Example1d.example3();

		// 離散フーリエ1次元変換の例題プログラム(のこぎり波:波形の見た目が鋸の歯のような信号)を実行する。
		Example1d.example4();

		// 離散フーリエ1次元変換の例題プログラム(矩形波:二つのレベルの間を規則的かつ瞬間的に変化する信号)を実行する。
		Example1d.example5();

		// 離散フーリエ1次元変換の例題プログラム(三角波:波形の見た目が三角形のような信号)を実行する。
		Example1d.example6();

		return;
	}

	/**
	 * 離散フーリエ1次元変換の例題プログラム(いくつかの正弦波と余弦波の合成波)。
	 */
	protected static void example1() {
		GridLayout aLayout = new GridLayout(2, 2);
		JPanel aPanel = new JPanel(aLayout);

		double[] sourceData = FourierModel.dataSampleWave();
		DiscreteFourier1dTransformation aTransformation = new DiscreteFourier1dTransformation(sourceData);
		double[] realPart = aTransformation.realPart();
		double[] imaginaryPart = aTransformation.imaginaryPart();
		BufferedImage imageSourceData = FourierModel.generateImageForData(sourceData);
		Example1d.write(imageSourceData);
		PaneModel aModel = new PaneModel(imageSourceData);
		PaneView aView = new PaneView(aModel, new PaneController());
		aPanel.add(aView);

		double[] shortRealPart = new double[34];
		System.arraycopy(realPart, 0, shortRealPart, 0, shortRealPart.length);
		double[] shortImaginaryPart = new double[34];
		System.arraycopy(imaginaryPart, 0, shortImaginaryPart, 0, shortImaginaryPart.length);

		ArrayList<BufferedImage> arrayList = FourierModel.generateImageForTwoParts(shortRealPart, shortImaginaryPart);
		BufferedImage imageRealPart = arrayList.get(0);
		Example1d.write(imageRealPart);
		aModel = new PaneModel(imageRealPart);
		aView = new PaneView(aModel, new PaneController());
		aPanel.add(aView);

		aTransformation = new DiscreteFourier1dTransformation(realPart, imaginaryPart);
		double[] inverseRealPart = aTransformation.inverseRealPart();
		double[] inverseImaginaryPart = aTransformation.inverseImaginaryPart();
		BufferedImage imageInverseData = FourierModel.generateImageForData(inverseRealPart);
		Example1d.write(imageInverseData);
		aModel = new PaneModel(imageInverseData);
		aView = new PaneView(aModel, new PaneController());
		aPanel.add(aView);

		BufferedImage imageImaginaryPart = arrayList.get(1);
		Example1d.write(imageImaginaryPart);
		aModel = new PaneModel(imageImaginaryPart);
		aView = new PaneView(aModel, new PaneController());
		aPanel.add(aView);

		Example1d.open(aPanel);
		return;
	}

	/**
	 * 離散フーリエ1次元変換の例題プログラム(いくつかの正弦波と余弦波の合成波)。
	 */
	protected static void example2() {
		double[] sourceData = FourierModel.dataSampleWave();
		Example1d.perform(sourceData);
		return;
	}

	/**
	 * 離散フーリエ1次元変換の例題プログラム(チャープ信号:低周波から高周波に周波数が連続的に変わる信号)。
	 */
	protected static void example3() {
		double[] sourceData = FourierModel.dataChirpSignal();
		Example1d.perform(sourceData);
		return;
	}

	/**
	 * 離散フーリエ1次元変換の例題プログラム(のこぎり波:波形の見た目が鋸の歯のような信号)。
	 */
	protected static void example4() {
		double[] sourceData = FourierModel.dataSawtoothWave();
		Example1d.perform(sourceData);
		return;
	}

	/**
	 * 離散フーリエ1次元変換の例題プログラム(矩形波:二つのレベルの間を規則的かつ瞬間的に変化する信号)。
	 */
	protected static void example5() {
		double[] sourceData = FourierModel.dataSquareWave();
		Example1d.perform(sourceData);
		return;
	}

	/**
	 * 離散フーリエ1次元変換の例題プログラム(三角波:波形の見た目が三角形のような信号)。
	 */
	protected static void example6() {
		double[] sourceData = FourierModel.dataTriangleWave();
		Example1d.perform(sourceData);
		return;
	}

	/**
	 * 元データ(sourceData)に離散フーリエ1次元変換を施して結果を表示する。
	 */
	protected static void perform(double[] sourceData) {
		GridLayout aLayout = new GridLayout(2, 2);
		JPanel aPanel = new JPanel(aLayout);
		DiscreteFourier1dTransformation aTransformation = new DiscreteFourier1dTransformation(sourceData);
		double[] realPart = aTransformation.realPart();
		double[] imaginaryPart = aTransformation.imaginaryPart();
		double[] powerSpectrum = aTransformation.powerSpectrum();
		BufferedImage imageSourceData = FourierModel.generateImageForData(sourceData);
		PaneModel aModel = new PaneModel(imageSourceData);
		PaneView aView = new PaneView(aModel, new PaneController());
		aPanel.add(aView);
		Example1d.write(imageSourceData);

		BufferedImage imageTwoParts = FourierModel.generateImageForParts(aTransformation.swap(realPart),
				aTransformation.swap(imaginaryPart));
		aModel = new PaneModel(imageTwoParts);
		aView = new PaneView(aModel, new PaneController());
		aPanel.add(aView);
		Example1d.write(imageTwoParts);

		aTransformation = new DiscreteFourier1dTransformation(realPart, imaginaryPart);
		double[] inverseRealPart = aTransformation.inverseRealPart();
		double[] inverseImaginaryPart = aTransformation.inverseImaginaryPart();
		BufferedImage imageInverseData = FourierModel.generateImageForData(inverseRealPart);
		aModel = new PaneModel(imageInverseData);
		aView = new PaneView(aModel, new PaneController());
		aPanel.add(aView);
		Example1d.write(imageInverseData);

		BufferedImage imagePowerSpectrum = FourierModel.generateImageForSpectrum(aTransformation.swap(powerSpectrum));
		aModel = new PaneModel(imagePowerSpectrum);
		aView = new PaneView(aModel, new PaneController());
		aPanel.add(aView);
		Example1d.write(imagePowerSpectrum);

		Example1d.open(aPanel);
		return;
	}

	/**
	 * レイアウトされたパネル(aPanel)を受け取り、それをウィンドウに乗せて開く。
	 */
	protected static void open(JPanel aPanel) {
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
	 * 画像(anImage)をファイルに書き出す。
	 */
	protected static void write(BufferedImage anImage) {
		File aDirectory = new File("ResultImages");
		if (aDirectory.exists() == false) {
			aDirectory.mkdir();
		}
		String aString = Integer.toString(fileNo++);
		while (aString.length() < 2) {
			aString = "0" + aString;
		}
		ImageUtility.writeImage(anImage, aDirectory.getName() + "/Fourier" + aString + ".jpg");
		return;
	}
}
