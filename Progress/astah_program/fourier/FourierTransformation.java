package fourier;

/**
 * フーリエ変換の抽象クラス。
 * 連続フーリエ変換(continuous fourier tansformation)と離散フーリエ変換(discrete fourier transformation)を抽象する。
 */
public abstract class FourierTransformation extends Object
{
	/**
	 * フーリエ変換のインスタンスを作るコンストラクタ。
	 */
	public FourierTransformation()
	{
		super();
		this.initialize();
	}

	/**
	 * フーリエ変換を初期化する。
	 */
	protected void initialize()
	{
		return;
	}

	/**
	 * フーリエ変換を施すための抽象メソッド。
	 */
	public abstract FourierTransformation transform();

	/**
	 * 逆フーリエ変換を施すための抽象メソッド。
	 */
	public abstract FourierTransformation inverseTransform();
}
