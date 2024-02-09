package fourier;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.Arrays;

import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JMenuItem;

/**
 * 二次元フーリエ変換のデータを持つクラス
 */
public class Fourier2dModel extends FourierModel {

	/**
	 * 以前のｘ座標を持つ
	 */
	private int previousX;
	/**
	 * 以前のｙ座標を持つ
	 */
	private int previousY;
	/**
	 * 元画像の配列データ
	 */
	protected double[][] sourceData;

	/**
	 * フーリエ変換後の実部
	 */
	protected double[][] realPart;

	/**
	 * フーリエ変換後の虚部
	 */
	protected double[][] imaginaryPart;

	/**
	 * パワースペクトルを持つ
	 */
	protected double[][] powerSpectrum;

	/**
	 * マウス操作で変更可能な実部
	 */
	protected double[][] interactiveRealPart;

	/**
	 * マウス操作で変更可能な虚部
	 */
	protected double[][] interactiveImarginaryPart;

	/**
	 * マウス操作で変更可能なパワースペクトル
	 */
	protected double[][] interactivePowerSpectrum;

	/**
	 * 逆変換を施した画像
	 */
	protected double[][] inverseData;

	/**
	 * 元画像のPaneModel
	 */
	protected FourierPaneModel sourceDataPaneModel = null;

	/**
	 * パワースペクトル画像のPaneModel
	 */
	protected FourierPaneModel powerSpectrumPaneModel = null;

	/**
	 * 操作可能なパワースペクトル画像のPaneModel
	 */
	protected FourierPaneModel interactivePowerSpectrumPaneModel = null;

	/**
	 * 逆変換画像のPaneModel
	 */

	protected FourierPaneModel interactiveDataPaneModel = null;
	/**
	 * カラー画像を格納する配列。
	 * 2次元配列を要素にもつ
	 */
	double[][][] yuvSourceData;

	/**
	 * デフォルトコンストラクタ
	 * @author Takakura
	 * @version 1.0
	 * @date 7/14
	 */
	public Fourier2dModel() {
		super();
		this.setSourceData(FourierModel.dataFourierGrayScale(), yuvSourceData);
	}

	/**
	 * 再描画を処理する
	 * @author Nakamura Hayami
	 * @version 1.2
	 * @date 7/15
	 * @param anActionEvent
	 */
	public void actionPerformed(ActionEvent anActionEvent) {
		if (yuvSourceData == null) {
			sourceDataPaneModel.picture(FourierModel.generateImageForData(sourceData));
			DiscreteFourier2dTransformation aTransformation = new DiscreteFourier2dTransformation(sourceData);
			powerSpectrumPaneModel.picture(FourierModel.generateImageForData(aTransformation.swap(powerSpectrum)));
			interactivePowerSpectrumPaneModel
					.picture(FourierModel.generateImageForData(aTransformation.swap(interactivePowerSpectrum)));
			computeInverseData();
			interactiveDataPaneModel.picture(FourierModel.generateImageForData(inverseData));
		} else {
			sourceDataPaneModel.picture(FourierModel.generateImageForData(yuvSourceData));
			DiscreteFourier2dTransformation aTransformation = new DiscreteFourier2dTransformation(yuvSourceData[0]);
			powerSpectrumPaneModel.picture(FourierModel.generateImageForData(aTransformation.swap(powerSpectrum)));
			interactivePowerSpectrumPaneModel
					.picture(FourierModel.generateImageForData(aTransformation.swap(interactivePowerSpectrum)));
			computeInverseData();
			if (FourierModel.isAllZero(inverseData) == true) {
				double[][][] yuvInverseMatrixes = new double[][][] { inverseData,
						inverseData, inverseData };
				interactiveDataPaneModel.picture(FourierModel.generateImageForData(yuvInverseMatrixes));
			} else {
				double[][][] yuvInverseMatrixes = new double[][][] { inverseData,
						yuvSourceData[1], yuvSourceData[2] };
				interactiveDataPaneModel.picture(FourierModel.generateImageForData(yuvInverseMatrixes));
			}

		}

		sourceDataPaneModel.changed();
		powerSpectrumPaneModel.changed();
		interactiveDataPaneModel.changed();
		interactivePowerSpectrumPaneModel.changed();
	}

	/**
	 * Pointからinteractiveな配列を操作する
	 * @author Takakura,Nakamura
	 * @version 1.1
	 * @date 7/15
	 * @param aPoint
	 * @param isAltDown
	 */
	public void computeFromPoint(Point aPoint, boolean isAltDown) {
		int x = (int) aPoint.getX();
		int y = (int) aPoint.getY();

		int[] xySwap = int2dSwap(x, y, powerSpectrum[0].length);
		int swapX = xySwap[0];
		int swapY = xySwap[1];
		xySwap = int2dSwap(previousX, previousY, powerSpectrum[0].length);
		int swapPreviousX = xySwap[0];
		int swapPreviousY = xySwap[1];

		if (previousX == -1 || previousY == -1 || Math.abs(swapX -
				swapPreviousX) > 30 || Math.abs(swapY - swapPreviousY) > 30) {
			previousX = x;
			previousY = y;
		}

		if (isAltDown) {
			if (previousX >= x && previousY >= y) {
				x = x - 5;
				y = y - 5;
				previousX = previousX + 5;
				previousY = previousY + 5;

				for (int j = x; j <= previousX; j++) {
					for (int i = y; i <= previousY; i++) {
						xySwap = int2dSwap(j, i, powerSpectrum[0].length);
						interactiveRealPart[xySwap[1]][xySwap[0]] = 0.0;
						interactiveImarginaryPart[xySwap[1]][xySwap[0]] = 0.0;
						interactivePowerSpectrum[xySwap[1]][xySwap[0]] = 0.0;
					}
				}
			} else if (previousX >= x && previousY < y) {
				x = x - 5;
				y = y + 5;
				previousX = previousX + 5;
				previousY = previousY - 5;

				for (int j = x; j <= previousX; j++) {
					for (int i = previousY; i <= y; i++) {
						xySwap = int2dSwap(j, i, powerSpectrum[0].length);
						interactiveRealPart[xySwap[1]][xySwap[0]] = 0.0;
						interactiveImarginaryPart[xySwap[1]][xySwap[0]] = 0.0;
						interactivePowerSpectrum[xySwap[1]][xySwap[0]] = 0.0;
					}

				}
			} else if (previousX < x && previousY >= y) {
				x = x + 5;
				y = y - 5;
				previousX = previousX - 5;
				previousY = previousY + 5;

				for (int j = previousX; j <= x; j++) {
					for (int i = y; i <= previousY; i++) {
						xySwap = int2dSwap(j, i, powerSpectrum[0].length);
						interactiveRealPart[xySwap[1]][xySwap[0]] = 0.0;
						interactiveImarginaryPart[xySwap[1]][xySwap[0]] = 0.0;
						interactivePowerSpectrum[xySwap[1]][xySwap[0]] = 0.0;
					}
				}
			} else if (previousX < x && previousY < y) {
				x = x + 5;
				y = y + 5;
				previousX = previousX - 5;
				previousY = previousY - 5;

				for (int j = previousX; j <= x; j++) {
					for (int i = previousY; i <= y; i++) {
						xySwap = int2dSwap(j, i, powerSpectrum[0].length);
						interactiveRealPart[xySwap[1]][xySwap[0]] = 0.0;
						interactiveImarginaryPart[xySwap[1]][xySwap[0]] = 0.0;
						interactivePowerSpectrum[xySwap[1]][xySwap[0]] = 0.0;
					}
				}

			}

			else {
				interactiveRealPart[y][x] = 0.0;
				interactivePowerSpectrum[y][x] = 0.0;
				interactivePowerSpectrum[y][x] = 0.0;
				previousX = -1;
				previousY = -1;
			}
			return;
		} else {

			if (previousX >= x && previousY >= y) {
				x = x - 5;
				y = y - 5;
				previousX = previousX + 5;
				previousY = previousY + 5;

				for (int j = x; j <= previousX; j++) {
					for (int i = y; i <= previousY; i++) {
						xySwap = int2dSwap(j, i, powerSpectrum[0].length);
						interactiveRealPart[xySwap[1]][xySwap[0]] = realPart[xySwap[1]][xySwap[0]];
						interactiveImarginaryPart[xySwap[1]][xySwap[0]] = imaginaryPart[xySwap[1]][xySwap[0]];
						interactivePowerSpectrum[xySwap[1]][xySwap[0]] = powerSpectrum[xySwap[1]][xySwap[0]];
					}
				}
			} else if (previousX >= x && previousY < y) {
				x = x - 5;
				y = y + 5;
				previousX = previousX + 5;
				previousY = previousY - 5;

				for (int j = x; j <= previousX; j++) {
					for (int i = previousY; i <= y; i++) {
						xySwap = int2dSwap(j, i, powerSpectrum[0].length);
						interactiveRealPart[xySwap[1]][xySwap[0]] = realPart[xySwap[1]][xySwap[0]];
						interactiveImarginaryPart[xySwap[1]][xySwap[0]] = imaginaryPart[xySwap[1]][xySwap[0]];
						interactivePowerSpectrum[xySwap[1]][xySwap[0]] = powerSpectrum[xySwap[1]][xySwap[0]];
					}

				}
			} else if (previousX < x && previousY >= y) {
				x = x + 5;
				y = y - 5;
				previousX = previousX - 5;
				previousY = previousY + 5;

				for (int j = previousX; j <= x; j++) {
					for (int i = y; i <= previousY; i++) {
						xySwap = int2dSwap(j, i, powerSpectrum[0].length);
						interactiveRealPart[xySwap[1]][xySwap[0]] = realPart[xySwap[1]][xySwap[0]];
						interactiveImarginaryPart[xySwap[1]][xySwap[0]] = imaginaryPart[xySwap[1]][xySwap[0]];
						interactivePowerSpectrum[xySwap[1]][xySwap[0]] = powerSpectrum[xySwap[1]][xySwap[0]];
					}
				}
			} else if (previousX < x && previousY < y) {
				x = x + 5;
				y = y + 5;
				previousX = previousX - 5;
				previousY = previousY - 5;

				for (int j = previousX; j <= x; j++) {
					for (int i = previousY; i <= y; i++) {
						xySwap = int2dSwap(j, i, powerSpectrum[0].length);
						interactiveRealPart[xySwap[1]][xySwap[0]] = realPart[xySwap[1]][xySwap[0]];
						interactiveImarginaryPart[xySwap[1]][xySwap[0]] = imaginaryPart[xySwap[1]][xySwap[0]];
						interactivePowerSpectrum[xySwap[1]][xySwap[0]] = powerSpectrum[xySwap[1]][xySwap[0]];
					}
				}

			}

			else {
				interactiveRealPart[y][x] = realPart[y][x];
				interactivePowerSpectrum[y][x] = powerSpectrum[y][x];
				interactivePowerSpectrum[y][x] = powerSpectrum[y][x];
				previousX = -1;
				previousY = -1;
			}
			return;
		}
	}

	/**
	 * 現在のインタラクティブな配列から逆変換処理を行う
	 * @author Nakamura
	 * @version 1.0
	 * @date 7/8
	 */
	public void computeInverseData() {
		// マウス操作後呼び出される？
		DiscreteFourier2dTransformation anInverseTransform = new DiscreteFourier2dTransformation(interactiveRealPart, interactiveImarginaryPart);
		inverseData = anInverseTransform.inverseRealPart();
		return;
	}

	/**
	 * インタラクティブな配列をそれぞれの配列と同等にする
	 * @author Nakamura Hayami
	 * @version 1.2
	 * @date 7/17
	 */
	public void doAllSpectrum() {
		DiscreteFourier2dTransformation aTransform = new DiscreteFourier2dTransformation(sourceData);
		interactivePowerSpectrum = aTransform.normalizedLogarithmicPowerSpectrum();
		interactiveRealPart = aTransform.realPart();
		interactiveImarginaryPart = aTransform.imaginaryPart();
		computeInverseData();
		this.actionPerformed(null);
	}

	/**
	 * interactiveデータをリセットする
	 *
	 * @author Takakura Hayami
	 * @version 1.1
	 * @date 7/26
	 */
	public void doClearSpectrum() {
		this.setSourceData(sourceData, yuvSourceData);
		this.actionPerformed(null);
	}

	/**
	 * 操作対象をカラー画像に変更する
	 *
	 * @author Takakura
	 * @version 1.0
	 * @date  7/14
	 */
	public void doFourierColor() {
		double[][][] yuvMatrixes = FourierModel.dataFourierColor();
		this.setSourceData(yuvMatrixes[0], yuvMatrixes);
		this.actionPerformed(null);
		return;
	}

	/**
	 * 操作対象をグレースケール画像に変更する
	 *
	 * @author Takakura,Nakamura
	 * @version 1.1
	 * @date 7/15
	 */
	public void doFourierGrayScale() {
		this.setSourceData(FourierModel.dataFourierGrayScale(), null);
		this.actionPerformed(null);
		return;
	}

	/**
	 * マウスクリックの時の動作を決定
	 *
	 * @author Takakura
	 * @version 1.0
	 * @date 7/14
	 * @param aPoint
	 * @param aMouseEvent
	 */
	public void mouseClicked(Point aPoint, MouseEvent aMouseEvent) {
		computeFromPoint(aPoint, aMouseEvent.isAltDown());
		this.actionPerformed(null);
		return;
	}

	/**
	 * マウスドラッグの時の動作を決定
	 *
	 * @author Takakura,Nakamura
	 * @version 1.1
	 * @date 7/14
	 * @param aPoint
	 * @param aMouseEvent
	 */
	public void mouseDragged(Point aPoint, MouseEvent aMouseEvent) {
		computeFromPoint(aPoint, aMouseEvent.isAltDown());
		// Alt(option)が押されると消すモード、逆は書くモード
		this.actionPerformed(null);
		previousX = (int) aPoint.getX();
		previousY = (int) aPoint.getY();
		return;
	}

	/**
	 * ウィンドウを開ける
	 *
	 * @author Takakura
	 * @version 1.0
	 * @date 7/14
	 */
	public void open() {
		GridLayout aLayout = new GridLayout(2, 2);
		JPanel aPanel = new JPanel(aLayout);
		Dimension aDimension = new Dimension();
		aDimension.width = sourceData[0].length;
		aDimension.height = sourceData[1].length;
		DiscreteFourier2dTransformation aTransformation = new DiscreteFourier2dTransformation(sourceData);
		BufferedImage imageSourceData = FourierModel.generateImageForData(sourceData);
		this.sourceDataPaneModel = new FourierPaneModel(imageSourceData, "sourceData", this);
		FourierPaneView aView = new FourierPaneView(sourceDataPaneModel, new FourierPaneController());
		this.sourceDataPaneModel.addDependent(aView);
		aPanel.add(aView);

		BufferedImage imagePowerSpectrum = FourierModel.generateImageForData(aTransformation.swap(powerSpectrum));
		this.powerSpectrumPaneModel = new FourierPaneModel(imagePowerSpectrum, "powerSpectrum", this);
		aView = new FourierPaneView(powerSpectrumPaneModel, new FourierPaneController());
		this.powerSpectrumPaneModel.addDependent(aView);
		aPanel.add(aView);

		BufferedImage imageInverseData = FourierModel.generateImageForData(inverseData);
		this.interactiveDataPaneModel = new FourierPaneModel(imageInverseData, "inverseData", this);
		aView = new FourierPaneView(interactiveDataPaneModel, new FourierPaneController());
		this.interactiveDataPaneModel.addDependent(aView);
		aPanel.add(aView);

		BufferedImage imageinteractivePowerSpectrum = FourierModel
				.generateImageForData(aTransformation.swap(interactivePowerSpectrum));
		this.interactivePowerSpectrumPaneModel = new FourierPaneModel(imageinteractivePowerSpectrum,
				"interactivePowerSpectrum", this);
		aView = new FourierPaneView(interactivePowerSpectrumPaneModel, new FourierPaneController());
		this.interactivePowerSpectrumPaneModel.addDependent(aView);
		aPanel.add(aView);

		Example2d.open(aPanel, aDimension);

	}

	/**
	 * 配列の設定を行う
	 *
	 * @author Takakura,Hayami,Nakamura
	 * @version 1.2
	 * @date 7/26
	 * @param sourceDataMatrix
	 * @param yuvMatrixes
	 */
	public void setSourceData(double[][] sourceDataMatrix, double[][][] yuvMatrixes) {
		this.sourceData = sourceDataMatrix;
		this.yuvSourceData = yuvMatrixes;
		DiscreteFourier2dTransformation aTransformation = new DiscreteFourier2dTransformation(sourceData);
		this.realPart = aTransformation.realPart();
		this.imaginaryPart = aTransformation.imaginaryPart();
		this.powerSpectrum = aTransformation.normalizedLogarithmicPowerSpectrum();

		FourierModel.fill(interactiveRealPart = Arrays.stream(realPart).map(double[]::clone).toArray(double[][]::new), 0.0);
		FourierModel.fill(interactiveImarginaryPart = Arrays.stream(imaginaryPart).map(double[]::clone).toArray(double[][]::new), 0.0);
		FourierModel.fill(interactivePowerSpectrum = Arrays.stream(powerSpectrum).map(double[]::clone).toArray(double[][]::new), 0.0);
		FourierModel.fill(inverseData = Arrays.stream(sourceDataMatrix).map(double[]::clone).toArray(double[][]::new), 0.0);
		previousX = -1;
		previousY = -1;
		return;
	}

	/**
	 * ポップアップメニューの設定
	 *
	 * @author Takakura
	 * @version
	 * @date
	 * @param aMouseEvent
	 * @param aController
	 */
	public void showPopupMenu(MouseEvent aMouseEvent, FourierPaneController aController) {
		JPopupMenu popupMenu = new JPopupMenu();
		// 項目の作成と追加
		JMenuItem allSpectrumItem = new JMenuItem();
		Runnable allSpectrumRunner = () -> {
			doAllSpectrum();
		};
		allSpectrumItem.setAction(new Method2dAction("all Spectrum", allSpectrumRunner));

		JMenuItem clearSpectrumItem = new JMenuItem();
		Runnable clearSpectrumRunner = () -> {
			doClearSpectrum();
		};
		clearSpectrumItem.setAction(new Method2dAction("clear Spectrum", clearSpectrumRunner));

		JMenuItem fourierColorItem = new JMenuItem();
		Runnable fourierColorRunner = () -> {
			doFourierColor();
		};
		fourierColorItem.setAction(new Method2dAction("fourier Color", fourierColorRunner));

		JMenuItem fourierGrayScaleItem = new JMenuItem();
		Runnable fourierGrayScaleRunner = () -> {
			doFourierGrayScale();
		};
		fourierGrayScaleItem.setAction(new Method2dAction("fourier Gray Scale", fourierGrayScaleRunner));

		popupMenu.add(fourierColorItem);
		popupMenu.add(fourierGrayScaleItem);

		popupMenu.addSeparator();

		popupMenu.add(allSpectrumItem);
		popupMenu.add(clearSpectrumItem);

		popupMenu.show((JComponent) aMouseEvent.getSource(), aMouseEvent.getX(), aMouseEvent.getY());
	}

	/**
	 * パワースペクトル操作のための座標変換メソッド
	 * @author Nakamura
	 * @version 1.1
	 * @date 7/15
	 * @param swapX
	 * @param swapY
	 * @param length
	 * @return
	 */
	public int[] int2dSwap(int swapX, int swapY, int length) {
		if (swapX < 0)
			swapX = 0;
		if (swapY < 0)
			swapY = 0;
		if (length < swapX)
			swapX = length;
		if (length < swapY)
			swapY = length;
		int halfLength = length / 2;
		int aValue[] = new int[2];
		if (swapX < halfLength && swapY < halfLength) {

			aValue[0] = swapX + halfLength;
			aValue[1] = swapY + halfLength;
		} else if (swapX < halfLength && swapY >= halfLength) {
			aValue[0] = swapX + halfLength;
			aValue[1] = swapY - halfLength;
		} else if (swapX >= halfLength && swapY < halfLength) {
			aValue[0] = swapX - halfLength;
			aValue[1] = swapY + halfLength;
		} else {
			aValue[0] = swapX - halfLength;
			aValue[1] = swapY - halfLength;
		}
		return aValue;
	}

}

@SuppressWarnings("serial")
class Method2dAction extends AbstractAction {
	private static final long serialVersionUID = 1L;
	private Runnable handleCallback = null;

	public Method2dAction(String actionName, Runnable handleCallback) {
		super(actionName);
		this.handleCallback = handleCallback;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		this.handleCallback.run();
	}

}