package fourier;

import java.util.Arrays;

/**
 * 離散フーリエ2次元変換(discrete fourier 2D transformation)。
 */
public class DiscreteFourier2dTransformation extends DiscreteFourierTransformation
{
	/**
	 * 元データの実部を保持するフィールド。
	 */
	protected double[][] sourceRealPart;

	/**
	 * 元データの虚部を保持するフィールド。
	 */
	protected double[][] sourceImaginaryPart;

	/**
	 * フーリエ変換を施した実部を保持するフィールド。
	 */
	protected double[][] destinationRealPart;

	/**
	 * フーリエ変換を施した虚部を保持するフィールド。
	 */
	protected double[][] destinationImaginaryPart;

	/**
	 * 元データの実部だけを指定して離散フーリエ2次元変換のインスタンスを作るコンストラクタ。
	 */
	public DiscreteFourier2dTransformation(double[][] sourceRealData)
	{
		super();
		int width = sourceRealData[0].length;
		int height = sourceRealData.length;
		double[][] sourceImaginaryData = new double[height][width];
		for (int j = 0; j < height; j++)
		{
			Arrays.fill(sourceImaginaryData[j], 0.0d);
		}
		this.initialize(sourceRealData, sourceImaginaryData);
	}

	/**
	 * 元データの実部と虚部を指定して離散フーリエ2次元変換のインスタンスを作るコンストラクタ。
	 */
	public DiscreteFourier2dTransformation(double[][] sourceRealData, double[][] sourceImaginaryData)
	{
		super();
		this.initialize(sourceRealData, sourceImaginaryData);
	}

	/**
	 * 離散フーリエ変換の結果を水に流す。
	 */
	protected void flush()
	{
		destinationRealPart = null;
		destinationImaginaryPart = null;
		return;
	}

	/**
	 * 離散フーリエ変換(順変換)を施した虚部を応答する。
	 */
	public double[][] imaginaryPart()
	{
		if (destinationImaginaryPart == null) { this.transform(); }
		return destinationImaginaryPart;
	}

	/**
	 * 離散フーリエ変換を初期化する。
	 */
	protected void initialize()
	{
		sourceRealPart = null;
		sourceImaginaryPart = null;
		this.flush();
		return;
	}

	/**
	 * 離散フーリエ変換を初期化する。
	 */
	protected void initialize(double[][] sourceRealData, double[][] sourceImaginaryData)
	{
		sourceRealPart = sourceRealData;
		sourceImaginaryPart = sourceImaginaryData;
		this.flush();
		return;
	}

	/**
	 * 逆離散フーリエ変換(逆変換)を施した虚部を応答する。
	 */
	public double[][] inverseImaginaryPart()
	{
		if (destinationImaginaryPart == null) { this.inverseTransform(); }
		return destinationImaginaryPart;
	}

	/**
	 * 逆離散フーリエ変換(逆変換)を施した実部を応答する。
	 */
	public double[][] inverseRealPart()
	{
		if (destinationRealPart == null) { this.inverseTransform(); }
		return destinationRealPart;
	}

	/**
	 * 逆離散フーリエ変換(逆変換)を施す。
	 */
	public DiscreteFourier2dTransformation inverseTransform()
	{
		return this.transform(-1);
	}

	/**
	 * 行例(aMatrix)の対数を取って応答する。
	 */
	public double[][] logarithmicMatrix(double[][] aMatrix)
	{
		int width = aMatrix[0].length;
		int height = aMatrix.length;
		double[][] logarithmicMatrix = new double[height][width];
		for (int j = 0; j < height; j++)
		{
			for (int i = 0; i < width; i++)
			{
				double aValue = aMatrix[j][i];
				aValue = Math.log(aValue);
				logarithmicMatrix[j][i] = aValue;
			}
		}
		return logarithmicMatrix;
	}

	/**
	 * 離散フーリエ変換を施した結果の正規化対数パワースペクトルを応答する。
	 */
	public double[][] normalizedLogarithmicPowerSpectrum()
	{
		return normalizedMatrix(logarithmicMatrix(this.powerSpectrum()));
	}

	/**
	 * 離散フーリエ変換を施した結果の正規化対数スペクトルを応答する。
	 */
	public double[][] normalizedLogarithmicSpectrum()
	{
		return normalizedMatrix(logarithmicMatrix(this.spectrum()));
	}

	/**
	 * 行例(aMatrix)を正規化して応答する。
	 */
	public double[][] normalizedMatrix(double[][] aMatrix)
	{
		double maximum = 0.0d;
		int width = aMatrix[0].length;
		int height = aMatrix.length;
		for (int j = 0; j < height; j++)
		{
			for (int i = 0; i < width; i++)
			{
				maximum = Math.max(aMatrix[j][i], maximum);
			}
		}
		double[][] normalizedMatrix = new double[height][width];
		for (int j = 0; j < height; j++)
		{
			for (int i = 0; i < width; i++)
			{
				double aValue = aMatrix[j][i] / maximum;
				aValue = Math.max(aValue, 0.0d);
				aValue = Math.min(aValue, 1.0d);
				normalizedMatrix[j][i] = aValue;
			}
		}
		return normalizedMatrix;
	}

	/**
	 * 離散フーリエ変換を施した結果の正規化パワースペクトルを応答する。
	 */
	public double[][] normalizedPowerSpectrum()
	{
		return normalizedMatrix(this.powerSpectrum());
	}

	/**
	 * 離散フーリエ変換を施した結果の正規化スペクトルを応答する。
	 */
	public double[][] normalizedSpectrum()
	{
		return normalizedMatrix(this.spectrum());
	}

	/**
	 * 離散フーリエ変換を施した結果のパワースペクトルを応答する。
	 */
	public double[][] powerSpectrum()
	{
		double[][] realValues = this.realPart();
		double[][] imaginaryValues = this.imaginaryPart();
		int width = realValues[0].length;
		int height = realValues.length;
		double[][] powerSpectrum = new double[height][width];
		for (int j = 0; j < height; j++)
		{
			for (int i = 0; i < width; i++)
			{
				double realValue = realValues[j][i];
				double imaginaryValue = imaginaryValues[j][i];
				double aValue = (realValue * realValue) + (imaginaryValue * imaginaryValue);
				powerSpectrum[j][i] = aValue;
			}
		}
		return powerSpectrum;
	}

	/**
	 * 離散フーリエ変換(順変換)を施した実部を応答する。
	 */
	public double[][] realPart()
	{
		if (destinationRealPart == null) { this.transform(); }
		return destinationRealPart;
	}

	/**
	 * 離散フーリエ変換を施した結果のスペクトルを応答する。
	 */
	public double[][] spectrum()
	{
		return squareRootMatrix(this.powerSpectrum());
	}

	/**
	 * 行例(aMatrix)の平方根を取って応答する。
	 */
	public double[][] squareRootMatrix(double[][] aMatrix)
	{
		int width = aMatrix[0].length;
		int height = aMatrix.length;
		double[][] squareRootMatrix = new double[height][width];
		for (int j = 0; j < height; j++)
		{
			for (int i = 0; i < width; i++)
			{
				double aValue = aMatrix[j][i];
				aValue = Math.sqrt(aValue);
				squareRootMatrix[j][i] = aValue;
			}
		}
		return squareRootMatrix;
	}

	/**
	 * 離散フーリエ変換(順変換)を施す。
	 */
	public DiscreteFourier2dTransformation transform()
	{
		return this.transform(1);
	}

	/**
	 * 離散フーリエ変換を施す。flagが1の時は順変換。flagが-1の時は逆変換。
	 */
	public DiscreteFourier2dTransformation transform(int flag)
	{
		int width = sourceRealPart[0].length;
		int height = sourceRealPart.length;
		destinationRealPart = new double[height][width];
		destinationImaginaryPart = new double[height][width];
		for (int j = 0; j < height; j++)
		{
			double[] realPart = sourceRealPart[j];
			double[] imaginaryPart = sourceImaginaryPart[j];
			DiscreteFourier1dTransformation aTransformation = new DiscreteFourier1dTransformation(realPart, imaginaryPart);
			if (flag == -1)
			{
				realPart = aTransformation.inverseRealPart();
				imaginaryPart = aTransformation.inverseImaginaryPart();
			}
			else
			{
				realPart = aTransformation.realPart();
				imaginaryPart = aTransformation.imaginaryPart();
			}
			for (int i = 0; i < width; i++)
			{
				destinationRealPart[j][i] = realPart[i];
				destinationImaginaryPart[j][i] = imaginaryPart[i];
			}
		}
		for (int i = 0; i < width; i++)
		{
			double[] realPart = new double[height];
			double[] imaginaryPart = new double[height];
			for (int j = 0; j < height; j++)
			{
				realPart[j] = destinationRealPart[j][i];
				imaginaryPart[j] = destinationImaginaryPart[j][i];
			}
			DiscreteFourier1dTransformation aTransformation = new DiscreteFourier1dTransformation(realPart, imaginaryPart);
			if (flag == -1)
			{
				realPart = aTransformation.inverseRealPart();
				imaginaryPart = aTransformation.inverseImaginaryPart();
			}
			else
			{
				realPart = aTransformation.realPart();
				imaginaryPart = aTransformation.imaginaryPart();
			}
			for (int j = 0; j < height; j++)
			{
				destinationRealPart[j][i] = realPart[j];
				destinationImaginaryPart[j][i] = imaginaryPart[j];
			}
		}
		return this;
	}
}
