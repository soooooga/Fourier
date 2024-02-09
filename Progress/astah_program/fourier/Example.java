package fourier;

/**
 * フーリエ変換の例題プログラム。
 * オブザーバ・デザインパターン(MVC: Model-View-Controller)を用いた典型的(模範的)なプログラム。
 */
public class Example extends Object
{
	/**
	 * 離散フーリエ変換の例題プログラム群を実行する。
	 */
	public static void main(String[] arguments)
	{
		// 離散フーリエ1次元変換の例題群を実行する。
		Example1d.main(arguments);

		// 離散フーリエ2次元変換の例題群を実行する。
		Example2d.main(arguments);

		// 離散フーリエ1次元変換のモデルを開く。
		// (new Fourier1dModel()).open();

		// 離散フーリエ2次元変換のモデルを開く。
		// (new Fourier2dModel()).open();

		return;
	}
}
