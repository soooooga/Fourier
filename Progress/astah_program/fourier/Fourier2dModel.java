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
import javax.swing.JComponent;
import javax.swing.JMenuItem;
/**
 *
 */
public class Fourier2dModel extends FourierModel {

    /**
     *
     */
    protected double[][] sourceData;

    /**
     *
     */
    protected double[][] realPart;

    /**
     *
     */
    protected double[][] imaginaryPart;

    /**
     *
     */
    protected double[][] powerSpectrum;

    /**
     *
     */
    protected double[][] interactiveRealPart;

    /**
     *
     */
    protected double[][] interactiveImarginaryPart;

    /**
     *
     */
    protected double[][] interactivePowerSpectrum;

    /**
     *
     */
    protected double[][] inverseData;

    /**
     *
     */
    protected FourierPaneModel sourceDataPaneModel = null;

    /**
     *
     */
    protected FourierPaneModel powerSpectrumPaneModel = null;

    /**
     *
     */
    protected FourierPaneModel interactivePowerSpectrumPaneModel = null;

    /**
     *
     */

    protected FourierPaneModel interactiveDataPaneModel = null;
    /**
     *
     */
    double[][][] yuvSourceData;

    /**
     *
     *
     * @author Takakura
     * @version
     * @date
     */
    public Fourier2dModel() {
        super();
        this.setSourceData(FourierModel.dataFourierGrayScale(), yuvSourceData);
        
    }

    /**
     *
     *
     * @author Takakura
     * @version
     * @date
     * @param anActionEvent
     */
    public void actionPerformed(ActionEvent anActionEvent){
        
    }

    /**
     *
     *
     * @author Takakura
     * @version
     * @date
     * @param aPoint
     * @param isAltDown
     */
    public void computeFromPoint(Point aPoint, boolean isAltDown) {
 
    }

    /**
     *
     *
     * @author
     * @version
     * @date
     */
    public void computeInverseData() {
        //マウス操作後呼び出される？
        //復元画像の処理(逆変換)を行う？
    }

    /**
     *
     *
     * @author
     * @version
     * @date
     */
    public void doAllSpectrum() {
       
    }
    
    /**
     *
     *
     * @author Takakura
     * @version
     * @date
     */
    public void doClearSpectrum() {
    
    }

    

//    /**
//     *
//     *
//     * @author Takakura
//     * @version
//     * @date
//     */
//    public void doClearSpectrum(double[] anArrray, double aValue, double[][] aMatrix, double values) {
//        boolean flag = super.isAllZero(sourceData);
//        if(flag == true){
//            sourceData.super.fill();
//        }
//        return;
//    }

    /**
     *
     *
     * @author Takakura
     * @version
     * @date
     */
    public void doFourierColor() {
        this.setSourceData(null, Fourier2dModel.dataFourierColor());
        return;
    }

    /**
     *
     *
     * @author Takakura
     * @version
     * @date
     */
    public void doFourierGrayScale() {
        this.setSourceData(FourierModel.dataFourierGrayScale(), yuvSourceData);
        return;

    }

    /**
     *
     *
     * @author Takakura
     * @version
     * @date
     * @param aPoint
     * @param aMouseEvent
     */
    public void mouseClicked(Point aPoint, MouseEvent aMouseEvent) {
        int x = aPoint.x;
        int y = aPoint.y;
        if ((x != 0) || (y != 0)) {
        }
        x = 0;
        y = 0;
        System.out.printf("x = %d, y = %d", x, y);
        return;
    }

    /**
     *
     *
     * @author Takakura
     * @version
     * @date
     * @param aPoint
     * @param aMouseEvent
     */
    public void mouseDragged(Point aPoint, MouseEvent aMouseEvent) {
        System.out.println(aPoint);
        int x = (int) aPoint.getX();
        int y = (int) aPoint.getY();
        double value = this.powerSpectrum[x][y];
        interactivePowerSpectrum[x][y] = value;
        BufferedImage anImage = FourierModel.generateImageForData(interactivePowerSpectrum);
        this.interactivePowerSpectrumPaneModel.picture(anImage);
        this.interactivePowerSpectrumPaneModel.changed();
        System.out.println("yes");
    }

    /**
     *
     *
     * @author Takakura
     * @version
     * @date
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

        BufferedImage imageInverseData = FourierModel.generateImageForData(interactiveRealPart);
        this.interactiveDataPaneModel = new FourierPaneModel(imageInverseData, "inversedate", this);
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
     *
     *
     * @author Takakura
     * @version
     * @date
     * @param sourceDataMatrix
     * @param yuvMatrixes
     */
    public void setSourceData(double[][] sourceDataMatrix, double[][][] yuvMatrixes) {
        //Modelを元にprotectの配列を設定する？
        this.sourceData = sourceDataMatrix;
        DiscreteFourier2dTransformation aTransformation = new DiscreteFourier2dTransformation(sourceData);
        this.realPart = aTransformation.realPart();
        this.imaginaryPart = aTransformation.imaginaryPart();
        this.powerSpectrum = aTransformation.powerSpectrum();
        this.interactiveRealPart = new double[1024];
        Arrays.fill(interactiveRealPart, 0.0);
        this.interactiveImaginaryPart = new double[1024];
        Arrays.fill(interactiveImaginaryPart, 0.0);
        this.interactivePowerSpectrum = new double[1024];
        Arrays.fill(interactivePowerSpectrum, 0.0);
        this.inverseData = new double[1024];
        Arrays.fill(inverseData, 0.0);
        return;
    }

    /**
     *
     *
     * @author Takakura
     * @version
     * @date
     * @param aMouseEvent
     * @param aController
     */
    public void showPopupMenu(MouseEvent aMouseEvent, FourierPaneController aController) {
        JPopupMenu popup = new JPopupMenu();
        JMenuItem AllSpectrumItem = new JMenuItem("All Spectrum");
        JMenuItem ClearSpectrumItem = new JMenuItem("Clear Spectrum");
        JMenuItem FourierColorItem = new JMenuItem("Fourier Color");
        JMenuItem FourierGrayScaleItem = new JMenuItem("Fourier Gray Scale");
        
        popupMenu.add(AllSpectrum);
        popupMenu.add(ClearSpectrumItem);
        popupMenu.add(sawtoothWaveItem);
        popupMenu.add(FourierColorItem);
        popupMenu.add(FourierGrayScaleItem);
        
        popupMenu.addSeparator();


        popupMenu.show((JComponent) aMouseEvent.getSource(), aMouseEvent.getX(), aMouseEvent.getY());
        }
    
        public void myAction(){
            return;
        }
    
    }
