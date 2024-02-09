package fourier;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Arrays;
import pane.PaneController;
import utility.ImageUtility;

/**
 * 離散フーリエ変換の抽象モデル。
 */
public abstract class FourierModel extends mvc.Model
{
	/**
	 * 精度。
	 */
	public static final double accuracy = 1.0E-5d;

	/**
	 * 画像の幅。
	 */
	protected static final int imageWidth = 1024;

	/**
	 * 画像の高さ。
	 */
	protected static final int imageHeight = 300;

	/**
	 * ポップアップメニューの項目が選択されたときの処理をする抽象メソッド。
	 */
	public abstract void actionPerformed(ActionEvent anActionEvent);

	/**
	 * 指定された座標に対応するインタラクティブな実部と虚部を編集して計算を行う。
	 */
	public abstract void computeFromPoint(Point aPoint, boolean isAltDown);

	/**
	 * インタラクティブな実部と虚部から計算(元データの復元)を行う。
	 */
	public abstract void computeInverseData();

	/**
	 * 離散フーリエ2次元変換のための元データ(4×4の2次元配列)。
	 */
	public static double[][] data4x4()
	{
		double[][] sourceData = new double[][] { { 900, 901, 902, 903 }, { 910, 911, 912, 913 }, { 920, 921, 922, 923 }, { 930, 931, 932, 933 } };
		return sourceData;
	}

	/**
	 * 離散フーリエ1次元変換のための元データ(チャープ信号:低周波から高周波に周波数が連続的に変わる信号)。
	 */
	public static double[] dataChirpSignal()
	{
		double pi = Math.PI;
		int sourceSize = 1024;
		double[] sourceData = new double[sourceSize];
		for (int i = 0; i < sourceSize; i++)
		{
			double value = 12.0d * Math.sin((double)i * (double)i * pi / 2.0d / (double)sourceSize);
			sourceData[i] = value;
		}
		return sourceData;
	}

	/**
	 * 離散フーリエ2次元変換のための元データ(ジョゼフ・フーリエさんのカラー画像)。
	 */
	public static double[][][] dataFourierColor()
	{
		BufferedImage anImage = ImageUtility.readImage("SampleImages/JosephFourier2.jpg");
		double[][][] yuvMatrixes = ImageUtility.convertImageToYUVMatrixes(anImage);
		return yuvMatrixes;
	}

	/**
	 * 離散フーリエ2次元変換のための元データ(ジョゼフ・フーリエさんのグレースケール画像)。
	 */
	public static double[][] dataFourierGrayScale()
	{
		BufferedImage anImage = ImageUtility.readImage("SampleImages/JosephFourier1.jpg");
		double[][] luminanceMatrix = ImageUtility.convertImageToLuminanceMatrix(anImage);
		return luminanceMatrix;
	}

	/**
	 * 離散フーリエ1次元変換のための元データ(のこぎり波:波形の見た目が鋸の歯のような信号)。
	 */
	public static double[] dataSawtoothWave()
	{
		int sourceSize = 1024;
		double[] sourceData = new double[sourceSize];
		for (int i = 0; i < sourceSize; i++)
		{
			double value = 12.0d * ((double)(i % 50) / 25.0d - 1.0d);
			sourceData[i] = value;
		}
		return sourceData;
	}

	/**
	 * 離散フーリエ1次元変換のための元データ(矩形波:二つのレベルの間を規則的かつ瞬間的に変化する信号)。
	 */
	public static double[] dataSquareWave()
	{
		double pi = Math.PI;
		int sourceSize = 1024;
		double[] sourceData = new double[sourceSize];
		for (int i = 0; i < sourceSize; i++)
		{
			double cos = Math.cos(10.0d * 2.0d * pi * ((double)i / (double)sourceSize));
			double value = 12.0d * (cos >= 0.0d ? 1.0d : -1.0d);
			sourceData[i] = value;
		}
		return sourceData;
	}

	/**
	 * 離散フーリエ1次元変換のための元データ(いくつかの正弦波と余弦波の合成波)。
	 */
	public static double[] dataSampleWave()
	{
		double pi = Math.PI;
		int sourceSize = 1024;
		double[] sourceData = new double[sourceSize];
		for (int i = 0; i < sourceSize; i++)
		{
			double cos1 = 6.0d * Math.cos(12.0d * 2.0d * pi * (double)i / (double)sourceSize);
			double sin1 = 4.0d * Math.sin( 5.0d * 2.0d * pi * (double)i / (double)sourceSize);
			double cos2 = 3.0d * Math.cos(24.0d * 2.0d * pi * (double)i / (double)sourceSize);
			double sin2 = 2.0d * Math.sin(10.0d * 2.0d * pi * (double)i / (double)sourceSize);
			double value = cos1 + sin1 + cos2 + sin2;
			sourceData[i] = value;
		}
		return sourceData;
	}

	/**
	 * 離散フーリエ1次元変換のための元データ(三角波:波形の見た目が三角形のような信号)。
	 */
	public static double[] dataTriangleWave()
	{
		boolean flag = false;
		int sourceSize = 1024;
		double[] sourceData = new double[sourceSize];
		for (int i = 0; i < sourceSize; i++)
		{
			if (i % 50 == 0) { flag = flag ? false : true ; }
			double value = 12.0d * ((double)(i % 50) / 25.0d - 1.0d);
			if (flag) { value = 0.0d - value;}
			sourceData[i] = value;
		}
		return sourceData;
	}

	/**
	 * 1次元配列の中を指定された値で初期化する。
	 */
	public static void fill(double[] anArray, double aValue)
	{
		Arrays.fill(anArray, aValue);
		return;
	}

	/**
	 * マトリックス(2次元配列)の中を指定された値で初期化する。
	 */
	public static void fill(double[][] aMatrix, double aValue)
	{
		int width = aMatrix[0].length;
		int height = aMatrix.length;
		for (int j = 0; j < height; j++) {
			Arrays.fill(aMatrix[j], aValue);
		}
		return;
	}

	/**
	 * 離散フーリエ2次元変換のデータYをグレースケール画像に変換して応答する。
	 */
	public static BufferedImage generateImageForData(double[][] luminaceMatrix)
	{
		return ImageUtility.convertLuminanceMatrixToImage(luminaceMatrix);
	}

	/**
	 * 離散フーリエ2次元変換のデータYをUとVと共にカラー画像に変換して応答する。
	 */
	public static BufferedImage generateImageForData(double[][] yMatrix, double[][] uMatrix, double[][] vMatrix)
	{
		return ImageUtility.convertYUVMatrixesToImage(new double[][][] {yMatrix, uMatrix, vMatrix});
	}

	/**
	 * 離散フーリエ2次元変換のデータYをUとVと共にカラー画像に変換して応答する。
	 */
	public static BufferedImage generateImageForData(double[][][] yuvMatrixes)
	{
		return ImageUtility.convertYUVMatrixesToImage(yuvMatrixes);
	}

	/**
	 * 離散フーリエ1次元変換のデータを画像に変換して応答する。
	 */
	public static BufferedImage generateImageForData(double[] valueCollection)
	{
		double[] normalizedValues = FourierModel.normalize(valueCollection);
		int size = normalizedValues.length;
		int width = FourierModel.imageWidth;
		int height = FourierModel.imageHeight;
		int halfHeight = height / 2;
		BufferedImage anImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D aGraphics = anImage.createGraphics();
		aGraphics.setColor(Color.white);
		aGraphics.fillRect(0, 0, width, height);
		aGraphics.setColor(Color.gray);
		aGraphics.setStroke(new BasicStroke(1));
		aGraphics.drawLine(0, halfHeight, width, halfHeight);
		int[] xValues = new int[size];
		int[] yValues = new int[size];
		for (int index = 0; index < size; index++)
		{
			double value = normalizedValues[index];
			int x = (int)Math.round((double)index * ((double)width / (double)size));
			int y = (int)Math.round((0.0d - (value * 0.95d)) * (double)halfHeight) + halfHeight;
			xValues[index] = x;
			yValues[index] = y;
		}
		aGraphics.setColor(Color.gray);
		aGraphics.setStroke(new BasicStroke(2));
		aGraphics.drawPolyline(xValues, yValues, size);
		aGraphics.setColor(Color.black);
		for (int index = 0; index < size; index++)
		{
			int x = xValues[index];
			int y = yValues[index];
			Rectangle box = new Rectangle(x, y, 1, 1);
			box.grow(1, 1);
			aGraphics.fill(box);
		}
		return anImage;
	}

	/**
	 * 離散フーリエ1次元変換の実部と虚部を画像に変換して応答する。
	 */
	public static BufferedImage generateImageForParts(double[] realPart, double[] imaginaryPart)
	{
		ArrayList<double[]> normalizedParts = FourierModel.normalize(realPart, imaginaryPart);
		int size = Math.min(realPart.length, imaginaryPart.length);
		int width = FourierModel.imageWidth;
		int height = FourierModel.imageHeight;
		int halfWidth = width / 2;
		int quarterHeight = height / 4;
		BufferedImage anImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D aGraphics = anImage.createGraphics();
		aGraphics.setColor(Color.white);
		aGraphics.fillRect(0, 0, width, height);
		for (int n = 0; n < normalizedParts.size(); n++)
		{
			int m = n * 2 + 1;
			int baseline = quarterHeight * m;
			int top = quarterHeight * (m - 1);
			int bottom = quarterHeight * (m + 1);
			aGraphics.setColor(Color.gray);
			aGraphics.setStroke(new BasicStroke(1));
			aGraphics.drawLine(0, baseline, width, baseline);
			aGraphics.drawLine(halfWidth, top, halfWidth, bottom);
			int[] xValues = new int[size];
			int[] yValues = new int[size];
			for (int index = 0; index < size; index++)
			{
				double[] aPart = normalizedParts.get(n);
				double value = aPart[index];
				int x = (int)Math.round((double)index * ((double)width / (double)size));
				int y = (int)Math.round((0.0d - (value * 0.95d)) * (double)quarterHeight) + baseline;
				xValues[index] = x;
				yValues[index] = y;
			}
			aGraphics.setColor(Color.black);
			aGraphics.setStroke(new BasicStroke(3));
			for (int index = 0; index < size; index++)
			{
				int x = xValues[index];
				int y = yValues[index];
				aGraphics.drawLine(x, y, x, baseline);
			}
		}
		return anImage;
	}

	/**
	 * 離散フーリエ1次元変換のスペクトルを画像に変換して応答する。
	 */
	public static BufferedImage generateImageForSpectrum(double[] aSpectrum)
	{
		double[] normalizedSpectrum = FourierModel.normalize(aSpectrum);
		int size = normalizedSpectrum.length;
		int width = FourierModel.imageWidth;
		int height = FourierModel.imageHeight;
		int halfWidth = width / 2;
		int baseHeight = (int)Math.round((double)height * 0.975d);
		BufferedImage anImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D aGraphics = anImage.createGraphics();
		aGraphics.setColor(Color.white);
		aGraphics.fillRect(0, 0, width, height);
		aGraphics.setColor(Color.gray);
		aGraphics.setStroke(new BasicStroke(1));
		aGraphics.drawLine(0, baseHeight, width, baseHeight);
		aGraphics.drawLine(halfWidth, 0, halfWidth, height);
		int[] xValues = new int[size];
		int[] yValues = new int[size];
		for (int index = 0; index < size; index++)
		{
			double value = normalizedSpectrum[index];
			int x = (int)Math.round((double)index * ((double)width / (double)size));
			int y = (int)Math.round((0.0d - (value * 0.95d)) * (double)height) + baseHeight;
			xValues[index] = x;
			yValues[index] = y;
		}
		aGraphics.setColor(Color.black);
		aGraphics.setStroke(new BasicStroke(3));
		for (int index = 0; index < size; index++)
		{
			int x = xValues[index];
			int y = yValues[index];
			aGraphics.drawLine(x, y, x, baseHeight);
		}
		return anImage;
	}

	/**
	 * 離散フーリエ1次元変換の実部と虚部を画像群(0:実部の画像,1:虚部の画像)に変換して応答する。
	 */
	public static ArrayList<BufferedImage> generateImageForTwoParts(double[] realPart, double[] imaginaryPart)
	{
		ArrayList<double[]> normalizedParts = FourierModel.normalize(realPart, imaginaryPart);
		int size = Math.min(realPart.length, imaginaryPart.length);
		int width = FourierModel.imageWidth;
		int height = FourierModel.imageHeight;
		int halfWidth = width / 2;
		int halfHeight = height / 2;
		ArrayList<BufferedImage> arrayList = new ArrayList<BufferedImage>(2);
		for (int n = 0; n < normalizedParts.size(); n++)
		{
			BufferedImage anImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			Graphics2D aGraphics = anImage.createGraphics();
			aGraphics.setColor(Color.white);
			aGraphics.fillRect(0, 0, width, height);
			aGraphics.setColor(Color.gray);
			aGraphics.setStroke(new BasicStroke(1));
			aGraphics.drawLine(0, halfHeight, width, halfHeight);
			aGraphics.drawLine(0, 0, 0, height);
			int[] xValues = new int[size];
			int[] yValues = new int[size];
			for (int index = 0; index < size; index++)
			{
				double[] aPart = normalizedParts.get(n);
				double value = aPart[index];
				int x = (int)Math.round((double)index * ((double)width / (double)size));
				int y = (int)Math.round((0.0d - (value * 0.95d)) * (double)halfHeight) + halfHeight;
				xValues[index] = x;
				yValues[index] = y;
			}
			aGraphics.setColor(Color.black);
			aGraphics.setStroke(new BasicStroke(3));
			for (int index = 0; index < size; index++)
			{
				int x = xValues[index];
				int y = yValues[index];
				aGraphics.drawLine(x, y, x, halfHeight);
			}
			arrayList.add(anImage);

		}
		return arrayList;
	}

	/**
	 * 行列の中がすべて零であるか否かを応答する。
	 */
	public static boolean isAllZero(double[][] aMatrix)
	{
		int width = aMatrix[0].length;
		int height = aMatrix.length;
		for (int j = 0; j < height; j++) {
			for (int i = 0; i < height; i++) {
				if (Math.abs(aMatrix[j][i]) > FourierModel.accuracy) { return false; }
			}
		}
		return true;
	}

	/**
	 * 行列の中がすべて零でないか否かを応答する。
	 */
	public static boolean isNotAllZero(double[][] aMatrix)
	{
		return !(FourierModel.isAllZero(aMatrix));
	}

	/**
	 * マウスクリックした位置をピクチャ座標として受け取り、それを処理する抽象メソッド。
	 */
	public abstract void mouseClicked(Point aPoint, MouseEvent aMouseEvent);

	/**
	 * マウスドラッグした位置をピクチャ座標として受け取り、それを処理する抽象メソッド。
	 */
	public abstract void mouseDragged(Point aPoint, MouseEvent aMouseEvent);

	/**
	 * 元データや逆変換データを正規化して応答する。
	 */
	public static double[] normalize(double[] valueCollection)
	{
		double maimumValue = 0.0d;
		int size = valueCollection.length;
		for (int index = 0; index < size; index++)
		{
			maimumValue = Math.max(Math.abs(valueCollection[index]), maimumValue);
		}
		if (maimumValue < FourierModel.accuracy) { maimumValue = 1.0d; }
		double[] normalizedValues = new double[size];
		for (int index = 0; index < size; index++)
		{
			normalizedValues[index] = valueCollection[index] / maimumValue;
		}
		return normalizedValues;
	}

	/**
	 * 実部と虚部を同時に正規化して応答する。
	 */
	public static ArrayList<double[]> normalize(double[] realPart, double[] imaginaryPart)
	{
		double maimumValue = 0.0d;
		int size = Math.min(realPart.length, imaginaryPart.length);
		for (int index = 0; index < size; index++)
		{
			maimumValue = Math.max(Math.abs(realPart[index]), maimumValue);
			maimumValue = Math.max(Math.abs(imaginaryPart[index]), maimumValue);
		}
		if (maimumValue < FourierModel.accuracy) { maimumValue = 1.0d; }
		double[] normalizedRealPart = new double[size];
		double[] normalizedImaginaryPart = new double[size];
		for (int index = 0; index < size; index++)
		{
			normalizedRealPart[index] = realPart[index] / maimumValue;
			normalizedImaginaryPart[index] = imaginaryPart[index] / maimumValue;
		}
		ArrayList<double[]> arrayList = new ArrayList<double[]>(2);
		arrayList.add(normalizedRealPart);
		arrayList.add(normalizedImaginaryPart);
		return arrayList;
	}

	/**
	 * 離散フーリエ変換のモデルのウィンドウを開く。
	 */
	public abstract void open();

	/**
	 * メニューをポップアップする抽象メソッド。
	 */
	public abstract void showPopupMenu(MouseEvent aMouseEvent, PaneController aController);
}
