package fourier;

/**
 * 連続フーリエ変換(continuous fourier tansformation)。ただし、未完成。
 */
public class ContinuousFourierTransformation extends FourierTransformation
{
	/**
	 * 連続フーリエ変換のインスタンスを作るコンストラクタ。
	 */
	public ContinuousFourierTransformation()
	{
		super();
	}

	/**
	 * 連続フーリエ変換を施す。
	 */
	public FourierTransformation transform()
	{
		return this;
	}
	
	/**
	 * 逆連続フーリエ変換を施す。
	 */
	public FourierTransformation inverseTransform()
	{
		return this;
	}
}
