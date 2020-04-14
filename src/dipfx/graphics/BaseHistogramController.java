package dipfx.graphics;

import dipfx.common.LogUtil;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.image.Image;

import java.util.logging.Level;
import java.util.logging.Logger;

abstract public class BaseHistogramController {
    protected Logger logger;

    @FXML
    private BarChart<String, Number> srcBarChart;

    @FXML
    private BarChart<String, Number> dstBarChart;

    @FXML
    private BarChart<String, Number> targetBarChart;

    private Image source;
    private Image destination;
    private Image target;

    public BaseHistogramController() {
        this.logger = LogUtil.getLogger(super.getClass().getName());
    }

    public abstract int[] getHistogramFor(Image image);

    public void initialize() {
        logger.info("initializing histogram charts...");
        this.buildCharts();
    }

    public Image getSource() {
        return source;
    }

    public void setSource(Image source) {
        this.source = source;
    }

    public Image getDestination() {
        return destination;
    }

    public void setDestination(Image destination) {
        this.destination = destination;
    }

    public Image getTarget() {
        return target;
    }

    public void setTarget(Image target) {
        this.target = target;
    }

    public BarChart<String, Number> getSrcBarChart() {
        return srcBarChart;
    }

    public BarChart<String, Number> getDstBarChart() {
        return dstBarChart;
    }

    public BarChart<String, Number> getTargetBarChart() {
        return targetBarChart;
    }

    protected void plotChartData(BarChart<String, Number> chart, int[] data) {
        XYChart.Series<String, Number> chartSeries = new XYChart.Series<>();
        for (int i = 0; i < data.length ; i++) {
            logger.log(Level.FINEST, "chart data value {0} at {1}", new Object[]{data[i], i});
            chartSeries.getData().add(new XYChart.Data<>(String.valueOf(i), data[i]));
        }

        //noinspection unchecked
        chart.getData().addAll(chartSeries);
    }

    protected void buildCharts() {
        this.maybePlotImageHistogram(getSrcBarChart(), getSource());
        this.maybePlotImageHistogram(getDstBarChart(), getDestination());
        this.maybePlotImageHistogram(getTargetBarChart(), getTarget());
    }

    protected void maybePlotImageHistogram(BarChart<String, Number> chart, Image image) {
        if (image == null) {
            logger.log(Level.WARNING, "image is empty: {0}", chart);
            return;
        }

        int[] histogram = this.getHistogramFor(image);
        this.plotChartData(chart, histogram);
    }
}
