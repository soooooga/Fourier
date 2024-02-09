package fourier;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.awt.Point;
import java.io.File;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;
import utility.ImageUtility;

/**
 * 離散フーリエ2次元変換の例題プログラム。
 * オブザーバ・デザインパターン(MVC: Model-View-Controller)を用いた典型的(模範的)なプログラム。
 */
public class Example2d extends Object {
	/**
	 * 画像をファイルに書き出す際の番号。
	 */
	private static int fileNo = 50;

	/**
	 * ウィンドウの表示位置。
	 */
	private static Point displayPoint = new Point(330, 50);

	/**
	 * ウィンドウをずらして表示してゆく際の支距。
	 */
	private static Point offsetPoint = new Point(25, 25);

	/**
	 * 離散フーリエ2次元変換の例題プログラム群を実行する。
	 */
	public static void main(String[] arguments) {
		// 離散フーリエ2次元変換の例題プログラム(4×4の2次元配列)を実行する。
		Example2d.example1();

		// 離散フーリエ2次元変換の例題プログラム(ジョゼフ・フーリエさんのグレースケール画像)を実行する。
		Example2d.example2();

		// 離散フーリエ2次元変換の例題プログラム(ジョゼフ・フーリエさんのカラー画像)を実行する。
		Example2d.example3();

		return;
	}

	/**
	 * 離散フーリエ2次元変換の例題プログラム(4×4の2次元配列)。
	 */
	protected static void example1() {
		double[][] sourceData = FourierModel.data4x4();

		DiscreteFourier2dTransformation aTransformation = new DiscreteFourier2dTransformation(sourceData);
		double[][] realPart = aTransformation.realPart();
		double[][] imaginaryPart = aTransformation.imaginaryPart();
		double[][] powerSpectrum = aTransformation.normalizedLogarithmicPowerSpectrum();

		aTransformation = new DiscreteFourier2dTransformation(realPart, imaginaryPart);
		double[][] inverseRealPart = aTransformation.inverseRealPart();
		// double[][] inverseImaginaryPart = aTransformation.inverseImaginaryPart();

		ArrayList<double[][]> arrayList = new ArrayList<double[][]>(5);
		arrayList.add(sourceData);
		arrayList.add(realPart);
		arrayList.add(imaginaryPart);
		arrayList.add(powerSpectrum);
		arrayList.add(inverseRealPart);

		for (double[][] aMatrix : arrayList) {
			for (double[] anArray : aMatrix) {
				for (double aValue : anArray) {
					System.out.printf("%14.1f", aValue);
				}
				System.out.println();
			}
			System.out.println();
		}

		return;
	}

	/**
	 * 離散フーリエ2次元変換の例題プログラム(ジョゼフ・フーリエさんのグレースケール画像)。
	 */
	protected static void example2() {
		double[][] luminaceMatrix = FourierModel.dataFourierGrayScale();
		Example2d.perform(luminaceMatrix, null);
		return;
	}

	/**
	 * 離散フーリエ2次元変換の例題プログラム(ジョゼフ・フーリエさんのカラー画像)。
	 */
	protected static void example3() {
		double[][][] yuvMatrixes = FourierModel.dataFourierColor();
		Example2d.perform(yuvMatrixes[0], yuvMatrixes);
		return;
	}

	/**
	 * 元データ(sourceData)に離散フーリエ2次元変換を施して結果を表示する。
	 */
	protected static void perform(double[][] sourceData, double[][][] yuvMatrixes) {
		GridLayout aLayout = new GridLayout(2, 2);
		JPanel aPanel = new JPanel(aLayout);
		Dimension aDimension = new Dimension();
		aDimension.width = sourceData[0].length;
		aDimension.height = sourceData[1].length;
		BufferedImage imageSourceData = null;
		if (yuvMatrixes == null) {
			imageSourceData = ImageUtility.convertLuminanceMatrixToImage(sourceData);
		} else {
			imageSourceData = ImageUtility.convertYUVMatrixesToImage(yuvMatrixes);
		}
		String fileName = Example2d.write(imageSourceData);
		FourierPaneModel aModel = new FourierPaneModel(imageSourceData, fileName);
		FourierPaneView aView = new FourierPaneView(aModel, new FourierPaneController());
		aPanel.add(aView);

		DiscreteFourier2dTransformation aTransformation = new DiscreteFourier2dTransformation(sourceData);
		double[][] realPart = aTransformation.realPart();
		double[][] imaginaryPart = aTransformation.imaginaryPart();
		double[][] powerSpectrum = aTransformation.normalizedLogarithmicPowerSpectrum();
		double[][] aSpectrum = aTransformation.normalizedLogarithmicSpectrum();

		powerSpectrum = aTransformation.swap(powerSpectrum);
		BufferedImage imagePowerSpectrum = ImageUtility.convertLuminanceMatrixToImage(powerSpectrum);
		fileName = Example2d.write(imagePowerSpectrum);
		aModel = new FourierPaneModel(imagePowerSpectrum, fileName);
		aView = new FourierPaneView(aModel, new FourierPaneController());
		aPanel.add(aView);

		aTransformation = new DiscreteFourier2dTransformation(realPart, imaginaryPart);
		realPart = aTransformation.inverseRealPart();
		imaginaryPart = aTransformation.inverseImaginaryPart();
		double[][] inverseData = realPart;

		BufferedImage imageInverseData = null;
		if (yuvMatrixes == null) {
			imageInverseData = ImageUtility.convertLuminanceMatrixToImage(inverseData);
		} else {
			double[][][] yuvInverseMatrixes = new double[][][] { inverseData, yuvMatrixes[1], yuvMatrixes[2] };
			imageInverseData = ImageUtility.convertYUVMatrixesToImage(yuvInverseMatrixes);
		}
		fileName = Example2d.write(imageInverseData);
		aModel = new FourierPaneModel(imageInverseData, fileName);
		aView = new FourierPaneView(aModel, new FourierPaneController());
		aPanel.add(aView);

		aSpectrum = aTransformation.swap(aSpectrum);
		BufferedImage imageSpectrum = ImageUtility.convertLuminanceMatrixToImage(aSpectrum);
		fileName = Example2d.write(imageSpectrum);
		aModel = new FourierPaneModel(imageSpectrum, fileName);
		aView = new FourierPaneView(aModel, new FourierPaneController());
		aPanel.add(aView);

		Example2d.open(aPanel, aDimension);
		return;
	}

	/**
	 * レイアウトされたパネル(aPanel)と大きさ(aDimension)を受け取り、それをウィンドウに乗せて開く。
	 */
	protected static void open(JPanel aPanel, Dimension aDimension) {
		int width = aDimension.width;
		int height = aDimension.height;
		// double ratio = (double) height / (double) width;
		width = Math.min(Math.max(width, 150), 350);
		height = Math.min(Math.max(height, 150), 350);

		JFrame aWindow = new JFrame("Fourier Example (2D)");
		aWindow.getContentPane().add(aPanel);
		aWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		aWindow.addNotify();
		int titleBarHeight = aWindow.getInsets().top;
		aWindow.setMinimumSize(new Dimension(width, height + titleBarHeight));
		aWindow.setResizable(true);
		aWindow.setSize(width * 2, height * 2 + titleBarHeight);
		aWindow.setLocation(displayPoint.x, displayPoint.y);
		aWindow.setVisible(true);
		aWindow.toFront();
		displayPoint = new Point(displayPoint.x + offsetPoint.x, displayPoint.y + offsetPoint.y);
		return;
	}

	/**
	 * 画像(anImage)をファイルに書き出す。
	 */
	protected static String write(BufferedImage anImage) {
		File aDirectory = new File("ResultImages");
		if (aDirectory.exists() == false) {
			aDirectory.mkdir();
		}
		String aString = Integer.toString(fileNo++);
		while (aString.length() < 2) {
			aString = "0" + aString;
		}
		ImageUtility.writeImage(anImage, aDirectory.getName() + "/Fourier" + aString + ".jpg");
		aString = aDirectory.getName() + "/Fourier" + aString + ".jpg";
		return aString;
	}
}
