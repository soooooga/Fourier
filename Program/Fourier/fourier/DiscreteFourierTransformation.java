package fourier;

/**
 * 離散フーリエ変換(discrete fourier transformation)の抽象クラス。
 * 離散フーリエ1次元変換(discrete fourier 1D transformation)と離散フーリエ2次元変換(discrete fourier 2D transformation)を抽象する。
 */
public abstract class DiscreteFourierTransformation extends FourierTransformation
{
	/**
	 * 離散フーリエ変換を初期化する。
	 */
	protected void initialize()
	{
		super.initialize();
	}

	/**
	 * 離散フーリエ変換の結果を水に流す抽象メソッド。
	 */
	protected abstract void flush();

	/**
	 * 1次元配列(valueArray)を横半分のところでスワップした新たな1次元配列を応答する。
	 */
	public double[] swap(double[] valueArray)
	{
		int length = valueArray.length;
		double[] valueCollection = new double[length];
		System.arraycopy(valueArray, 0, valueCollection, 0, length);
		int halfLength = length / 2;
		double aValue;
		for (int i = 0; i < halfLength; i++)
		{
			int u = halfLength + i;
			aValue = valueCollection[i];
			valueCollection[i] = valueCollection[u];
			valueCollection[u] = aValue;
		}
		return valueCollection;
	}

	/**
	 * 2次元配列(valueMatrix)を縦横半分のところでスワップした新たな2次元配列を応答する。
	 */
	public double[][] swap(double[][] valueMatrix)
	{
		int width = valueMatrix[0].length;
		int height = valueMatrix.length;
		double[][] valueMap = new double[height][width];
		for (int j = 0; j < height; j++)
		{
			System.arraycopy(valueMatrix[j], 0, valueMap[j], 0, width);
		}
		int halfWidth = width / 2;
		int halfHeight = height / 2;
		double aValue;
		for (int j = 0; j < halfHeight; j++)
		{
			int v = halfHeight + j;
			for (int i = 0; i < halfWidth; i++)
			{
				int u = halfWidth + i;
				aValue = valueMap[j][i];
				valueMap[j][i] = valueMap[v][u];
				valueMap[v][u] = aValue;
				aValue = valueMap[v][i];
				valueMap[v][i] = valueMap[j][u];
				valueMap[j][u] = aValue;
			}
		}
		return valueMap;
	}
}
