package plot;

import java.util.List;

import org.knowm.xchart.QuickChart;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;

public class FunctionPlotter {
	
	private String title = "";
	private String xlabel = "x";
	private String ylabel = "y";
	private String legend = "y(x)";
	
	public FunctionPlotter() {  }
	
	public FunctionPlotter(String xlabel, String ylabel, String title, String legend) {
		this.xlabel = xlabel;
		this.ylabel = ylabel;
		this.title = title;
		this.legend = legend;
	}

	public void plot(List<Double> x, List<Double> y) {
	    XYChart chart = QuickChart.getChart(title, xlabel, ylabel, legend, x, y);
	    chart.setWidth(800);
	    SwingWrapper<XYChart> plotter = new SwingWrapper<>(chart);
	    plotter.displayChart();
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
