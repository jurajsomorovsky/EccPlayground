package de.rub.nds.eccjava.view.curve;

import de.rub.nds.eccjava.controller.AppController;
import de.rub.nds.eccjava.curve.Curve;
import de.rub.nds.eccjava.curve.DivisionException;
import de.rub.nds.eccjava.curve.Oracle;
import de.rub.nds.eccjava.curve.Point;
import java.awt.Color;
import java.math.BigInteger;
import java.util.List;
import org.apache.log4j.Logger;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.annotations.XYTextAnnotation;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.xy.XYDataItem;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 *
 * @author Juraj Somorovsky - juraj.somorovsky@rub.de
 * @version 0.1
 */
public class CurveGraphPanel extends ChartPanel {

    private static CurveGraphPanel INSTANCE = null;
    private static final AppController appController = new AppController();
    private static XYSeries eccSeries;
    private static final Logger LOG = Logger.getLogger(CurveGraphPanel.class);

    private CurveGraphPanel(JFreeChart chart) {
        super(chart);
    }

    public static CurveGraphPanel getInstance() {
        if (INSTANCE == null) {
            eccSeries = new XYSeries("ecc", false);
            XYSeriesCollection dataset = new XYSeriesCollection();
            dataset.addSeries(eccSeries);

            JFreeChart chart = ChartFactory.createScatterPlot(null, null, null, dataset, PlotOrientation.VERTICAL,
                    false, true, false);

            INSTANCE = new CurveGraphPanel(chart);

            XYItemRenderer renderer = chart.getXYPlot().getRenderer();
            renderer.setSeriesPaint(2, Color.BLACK);
        }
        return INSTANCE;
    }

    public void showAnnotations() {
        XYItemRenderer renderer = getChart().getXYPlot().getRenderer();
        renderer.removeAnnotations();

        for (int i = 0; i < eccSeries.getItemCount(); i++) {
            XYDataItem item = (XYDataItem) eccSeries.getDataItem(i);
            XYTextAnnotation annon = new XYTextAnnotation(new Integer(i).toString(), item.getX().longValue(), item
                    .getY().longValue());
            renderer.addAnnotation(annon);
        }
    }

    public void removeAnnotations() {
        XYItemRenderer renderer = getChart().getXYPlot().getRenderer();
        renderer.removeAnnotations();
    }

    public void updateSeries(Point point) {
        if (!point.isInfinity()) {
            eccSeries.add(point.getX(), point.getY());
        }
    }

    public void clearSeries() {
        eccSeries.clear();
    }
}
