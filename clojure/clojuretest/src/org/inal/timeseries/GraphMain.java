package org.inal.timeseries;

import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.DefaultXYDataset;

import com.google.common.collect.Lists;

public class GraphMain {

	private final JFreeChart chart;
	private final DefaultXYDataset dataset;

	public GraphMain() {
		dataset = new DefaultXYDataset();
		chart = ChartFactory.createXYLineChart("test", "x", "y", dataset,
				PlotOrientation.VERTICAL, true, true, false);

		chart.setAntiAlias(true);
		ChartPanel chartPanel = new ChartPanel(chart);
		new GraphWindow(chartPanel);
	}

	public void render(double[][] series, String graphId) {
		dataset.addSeries(graphId, series);
	}

	public void render(Graph<Double> graph, String graphId) {
		double[][] series = convertToSeriesArray(graph);
		dataset.addSeries(graphId, series);
	}

	private double[][] convertToSeriesArray(Graph<Double> graph) {
		List<Value<Double>> values = graph.getValues();
		double series[][] = new double[2][values.size()];
		for (int i = 0; i < values.size(); i++) {
			series[0][i] = values.get(i).getEquidistantDiscreteX();
			series[1][i] = values.get(i).getValue();
		}
		return series;
	}

	public void deleteGraph(String graphId) {
		dataset.removeSeries(graphId);
	}

	public static void main(String args[]) {
		GraphMain graphMain = new GraphMain();
		Graph<Double> graph = dummyGraph();
		graphMain.render(graph, "dummy");
	}

	private static Graph<Double> dummyGraph() {
		double[] input = new double[1000];
		for (int i = 0; i < 1000; i++) {
			input[i] = i * Math.random() - i * Math.random();

		}

		List<Value<Double>> values = values(input);
		Graph<Double> graph = new Graph<Double>(values, new ToString<Double>() {

			@Override
			public String toString(Double object) {
				return object.toString();
			}
		});
		return graph;
	}

	private static List<Value<Double>> values(double... values) {
		int x = 0;
		List<Value<Double>> result = Lists.newArrayList();
		for (double d : values) {
			result.add(new Value<Double>((double) x++, d));
		}
		return result;
	}
}
