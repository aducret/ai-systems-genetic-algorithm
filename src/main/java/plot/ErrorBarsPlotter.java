package plot;

import java.util.List;

import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.XYSeries;
import org.knowm.xchart.XYSeries.XYSeriesRenderStyle;
import org.knowm.xchart.style.markers.SeriesMarkers;

public class ErrorBarsPlotter {

	private String title = "";
	private String xlabel = "x";
	private String ylabel = "y";
	private String legend = "y";
	
	public ErrorBarsPlotter() {  }
	
	public ErrorBarsPlotter(String xlabel, String ylabel, String title, String legend) {
		this.xlabel = xlabel;
		this.ylabel = ylabel;
		this.title = title;
		this.legend = legend;
	}
	
	public void plot(List<Double> x, List<Double> y, List<Double> errorBars) {
		// Create Chart
	    XYChart chart = new XYChartBuilder().width(800).height(600).title(title).xAxisTitle(xlabel).yAxisTitle(ylabel).build();

	    // Customize Chart
	    chart.getStyler().setDefaultSeriesRenderStyle(XYSeriesRenderStyle.Line);

	    // Series
	    XYSeries series = chart.addSeries(legend, x, y, errorBars);
	    series.setMarker(SeriesMarkers.NONE);
	    
	    new SwingWrapper<XYChart>(chart).displayChart();
	}
	
	public void setXLabel(String xlabel) {
		this.xlabel = xlabel;
	}

	public void setYLabel(String ylabel) {
		this.ylabel = ylabel;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public void setLegend(String legend) { 
		this.legend = legend;
	}
}
