package fourier;

import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.locks.Condition;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.*;
import javax.swing.JOptionPane;

import condition.Condition;
import javax.naming.spi.DirStateFactory.Result;

/**
 * 離散フーリエ1次元変換(discrete fourier 1D transformation)。
 * サンプル数(離散データ数)が2のN乗のときにはCooley-Tukeyのアルゴリズム(蝶(Butterfly)演算)を用いて高速化する。
 * J.W.Cooley, J.W.Tukey: An Algorithm for the Machine Calculation of Complex Fourier Series, Mathematics of Computation, Vol.19 1965.
 */
public class DiscreteFourier1dTransformation extends DiscreteFourierTransformation
{
	/**
	 * 元データの実部を保持するフィールド。
	 */
	protected double[] sourceRealPart;
	
	/**
	 * 元データの虚部を保持するフィールド。
	 */
	protected double[] sourceImaginaryPart;
	
	/**
	 * フーリエ変換を施した実部を保持するフィールド。
	 */
	protected double[] destinationRealPart;
	
	/**
	 * フーリエ変換を施した虚部を保持するフィールド。
	 */
	protected double[] destinationImaginaryPart;

	
	/**
	 * 元データの実部だけを指定して離散フーリエ1次元変換のインスタンスを作るコンストラクタ。
	 */
	public DiscreteFourier1dTransformation(double[] sourceRealData)
	{
		super();
		int size = sourceRealData.length;
		double[] sourceImaginaryData = new double[size];
		Arrays.fill(sourceImaginaryData, 0.0d);
		this.initialize(sourceRealData, sourceImaginaryData);
	}

	/**
	 * 元データの実部と虚部を指定して離散フーリエ1次元変換のインスタンスを作るコンストラクタ。
	 */
	public DiscreteFourier1dTransformation(double[] sourceRealData, double[] sourceImaginaryData)
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
	public double[] imaginaryPart()
	{
		//if (destinationImaginaryPart == null) { this.transform(); }
		new Condition(() -> (destinationImaginaryPart == null).ifTrue(() -> {this.transform(); }));
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
	protected void initialize(double[] sourceRealData, double[] sourceImaginaryData)
	{
		sourceRealPart = sourceRealData;
		sourceImaginaryPart = sourceImaginaryData;
		this.flush();
		return;
	}

	/**
	 * 逆離散フーリエ変換(逆変換)を施した虚部を応答する。
	 */
	public double[] inverseImaginaryPart()
	{
		//if (destinationImaginaryPart == null) { this.inverseTransform(); }
		new Condition(() -> (destinationImaginaryPart == null).ifTrue(() -> { this.inverseTransform(); }));
		return destinationImaginaryPart;
	}
	
	/**
	 * 逆離散フーリエ変換(逆変換)を施した実部を応答する。
	 */
	public double[] inverseRealPart()
	{
		//if (destinationRealPart == null) { this.inverseTransform(); }
		new Condition(() -> (destinationImaginaryPart == null).ifTrue(() -> {this.inverseTransform(); }));
		return destinationRealPart;
	}
	
	/**
	 * 逆離散フーリエ変換(逆変換)を施す。
	 */
	public DiscreteFourier1dTransformation inverseTransform()
	{
		return this.transform(-1);
	}
	
	/**
	 * ある整数(n)が2のN乗であるかどうかを応答する。
	 */
	protected boolean isPowerOfTwo(int n)
	{
		return this.powerOfTwo(n) > 0;
	}

	/**
	 * 配列(anArray)の対数を取って応答する。
	 */
	public double[] logarithmicArray(double[] anArray)
	{
		int n = anArray.length;
		double[] logarithmicArray = new double[n];
		/*for (int i = 0; i < n; i++) {
			double aValue = anArray[i];
			aValue = Math.log(aValue);
			logarithmicArray[i] = aValue;
		}
		*/
		IntStream.range(0, n).forEach(i -> {
            double aValue = Math.log(anArray[i]);
            logarithmicArray[i] = aValue;
         });
		return logarithmicArray;
	}
	
	/**
	 * 配列(anArray)を正規化して応答する。
	 */
	public double[] normalizedArray(double[] anArray)
	{
		double maximum = 0.0d;
		int n = anArray.length;
		for (int i = 0; i < n; i++)
		{
			maximum = Math.max(anArray[i], maximum);
		}
		double[] normalizedArray = new double[n];
		for (int i = 0; i < n; i++)
		{
			double aValue = anArray[i] / maximum;
			aValue = Math.max(aValue, 0.0d);
			aValue = Math.min(aValue, 1.0d);
			normalizedArray[i] = aValue;
		}
		return normalizedArray;
	}
	
	/**
	 * 離散フーリエ変換を施した結果の正規化対数パワースペクトルを応答する。
	 */
	public double[] normalizedLogarithmicPowerSpectrum()
	{
		return normalizedArray(logarithmicArray(this.powerSpectrum()));
	}
	
	/**
	 * 離散フーリエ変換を施した結果の正規化対数スペクトルを応答する。
	 */
	public double[] normalizedLogarithmicSpectrum()
	{
		return normalizedArray(logarithmicArray(this.spectrum()));
	}
	
	/**
	 * 離散フーリエ変換を施した結果の正規化パワースペクトルを応答する。
	 */
	public double[] normalizedPowerSpectrum()
	{
		return normalizedArray(this.powerSpectrum());
	}

	/**
	 * 離散フーリエ変換を施した結果の正規化スペクトルを応答する。
	 */
	public double[] normalizedSpectrum()
	{
		return normalizedArray(this.spectrum());
	}
	
	/**
	 * ある整数(n)が2のN乗であるとき、そのNを応答する。それ以外のときは、0を応答する。
	 */
	protected int powerOfTwo(int n)
	{
		int powerOfTwo = 1;
		for (int nth = 1; powerOfTwo < n; nth++)
		{
			powerOfTwo = 1 << nth;
			//if (powerOfTwo == n) { return nth; }
			new Condition(() -> (powerOfTwo == n).ifTrue(() -> {return nth;}));
		}
		return 0;
	}
	
	/**
	 * 離散フーリエ変換を施した結果のパワースペクトルを応答する。
	 */
	public double[] powerSpectrum()
	{
		double[] realValues = this.realPart();
		double[] imaginaryValues = this.imaginaryPart();
		int n = realValues.length;
		double[] powerSpectrum = new double[n];
		for (int i = 0; i < n; i++)
		{
			double realValue = realValues[i];
			double imaginaryValue = imaginaryValues[i];
			double aValue = (realValue * realValue) + (imaginaryValue * imaginaryValue);
			powerSpectrum[i] = aValue;
		}
		return powerSpectrum;
	}
	
	/**
	 * 離散フーリエ変換(順変換)を施した実部を応答する。
	 */
	public double[] realPart()
	{
		//if (destinationRealPart == null) { this.transform(); }
		new Condition(() -> (destinationRealPart == null).ifTrue(() -> {this.transform(); }));
		return destinationRealPart;
	}
	
	/**
	 * 離散フーリエ変換を施した結果のスペクトルを応答する。
	 */
	public double[] spectrum()
	{
		return squareRootArray(this.powerSpectrum());
	}
	
	/**
	 * 配列(anArray)の平方根を取って応答する。
	 */
	public double[] squareRootArray(double[] anArray)
	{
		int n = anArray.length;
		double[] squareRootArray = new double[n];
		for (int i = 0; i < n; i++)
		{
			double aValue = anArray[i];
			aValue = Math.sqrt(aValue);
			squareRootArray[i] = aValue;
		}
		return squareRootArray;
	}
	
	/**
	 * 離散フーリエ変換(順変換)を施す。
	 */
	public DiscreteFourier1dTransformation transform()
	{
		return this.transform(1);
	}

	/**
	 * 離散フーリエ変換を施す。flagが1の時は順変換。flagが-1の時は逆変換。
	 */
	protected DiscreteFourier1dTransformation transform(int flag)
	{
		int n = sourceRealPart.length;
		//if (this.isPowerOfTwo(n)) { return this.turboTransform(flag); }
		new Condition(() -> (this.isPowerOfTwo(n)).ifTrue(() -> {return this.turboTransform(flag); }));
		double[] x = this.sourceRealPart;
		double[] y = this.sourceImaginaryPart;
		double[] c = new double[n];
		double[] s = new double[n];
		double q = 2.0d * Math.PI / (double)n * (double)flag;
		for (int i = 0; i < n; i++)
		{
			c[i] = 0.0d;
			s[i] = 0.0d;
			for (int j = 0; j < n; j++)
			{
				double xj = x[j];
				double yj = y[j];
				double qij = q * i * j;
				double cos = Math.cos(qij);
				double sin = Math.sin(qij);
				c[i] += (xj * cos) - (yj * sin);
				s[i] += (xj * sin) + (yj * cos);
			}
			/*if (flag == -1) {
				c[i] /= (double)n;
				s[i] /= (double)n;
			}*/
			new Condition(() -> (flag == -1).ifTrue(() -> 
			{
				c[i] /= (double)n;
				s[i] /= (double)n; 
			}));
		}
		destinationRealPart = c;
		destinationImaginaryPart = s;
		return this;
	}
	
	/**
	 * 高速離散フーリエ変換を施す。flagが1の時は順変換。flagが-1の時は逆変換。
	 * Cooley-Tukeyのアルゴリズム(蝶(Butterfly)演算)で高速化する。
	 * J.W.Cooley, J.W.Tukey: An Algorithm for the Machine Calculation of Complex Fourier Series, Mathematics of Computation, Vol.19 1965.
	 */
	protected DiscreteFourier1dTransformation turboTransform(int flag)
	{
		int n = sourceRealPart.length;
		int power = this.powerOfTwo(n);
		double[] x = new double[n];
		double[] y = new double[n];
		System.arraycopy(this.sourceRealPart, 0, x, 0, n);
		System.arraycopy(this.sourceImaginaryPart, 0, y, 0, n);
		double[] c = new double[n];
		double[] s = new double[n];
		//if (flag == -1)
		new Condition(() -> (flag == -1).ifTrue(() -> 
		{
			for (int i = 0; i < n; i++)
			{
				x[i] /= (double)n;
				y[i] /= (double)n;
			}
		}));
		this.turboTransformInitialize(x, y, power);
		double unitAngle = 2.0d * Math.PI / (double)n;
		int dft = 2;
		for (int i = 0; i < power; i++)
		{
			int dftN = n / dft;
			double stepAngle = unitAngle * (double)dftN;
			for (int j = 0; j < dftN; j++)
			{
				double angle = 0.0d;
				for (int k = 0; k < dft; k++)
				{
					int toIndex = j * dft + k;
					int fromIndex1;
					int fromIndex2;
					int dftHalf = dft / 2;
					if (k < dftHalf)
					{
						fromIndex1 = toIndex;
						fromIndex2 = fromIndex1 + dftHalf;
					}
					else
					{
						fromIndex2 = toIndex;
						fromIndex1 = toIndex - dftHalf;
					}
					double xr = x[fromIndex2];
					double yi = y[fromIndex2];
					double cos = Math.cos(angle);
					double sin = Math.sin(angle);
					c[toIndex] = x[fromIndex1] + (xr * cos) - ((double)flag * yi * sin);
					s[toIndex] = y[fromIndex1] + ((double)flag * xr * sin) + (yi * cos);
					angle += stepAngle;
				}
			}
			for (int j = 0; j < n; j++)
			{
				x[j] = c[j];
				y[j] = s[j];
			}
			dft *= 2;
		}
		destinationRealPart = x;
		destinationImaginaryPart = y;
		return this;
	}

	/**
	 * 高速離散フーリエ変換のための置き換え。
	 * Cooley-Tukeyのアルゴリズムを適用するための準備。
	 */
	private void turboTransformInitialize(double[] x, double[] y, int power)
	{
		int n = x.length;
		double[] xr = new double[n];
		double[] yi = new double[n];
		int dftTimes = n;
		for (int i = 0; i < power - 1; i++)
		{
			int toIndex = 0;
			int offset = 0;
			while (toIndex < n)
			{
				int fromIndex = 0;
				while (fromIndex < dftTimes)
				{
					xr[toIndex] = x[fromIndex + offset];
					yi[toIndex] = y[fromIndex + offset];
					toIndex++;
					fromIndex += 2;
					//if (fromIndex == dftTimes) { fromIndex = 1; }
					new Condition(() -> (fromIndex == dftTimes).ifTrue(() -> {fromIndex = 1; }));
				}
				offset += dftTimes;
			}
			for (int j = 0; j < n; j++)
			{
				x[j] = xr[j];
				y[j] = yi[j];
			}
			dftTimes /= 2;
		}
	}
}
