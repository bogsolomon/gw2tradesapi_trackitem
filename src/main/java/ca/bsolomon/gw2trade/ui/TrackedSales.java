package ca.bsolomon.gw2trade.ui;

import java.awt.Color;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

import javax.swing.JList;

import ca.bsolomon.gw2event.api.dao.TradeListing;
import ca.bsolomon.gw2trade.dao.TrackedListingChange;

import javax.swing.border.TitledBorder;
import javax.swing.JTable;
import javax.swing.JScrollPane;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.StandardXYToolTipGenerator;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.time.Minute;
import org.jfree.data.time.Second;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

public class TrackedSales extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JList<TrackedListingChange> list;
	private DefaultListModel<TrackedListingChange> model = new DefaultListModel<>();
	private JTable table;
	private OfferTableModel modelSellOffers = new OfferTableModel();
	private OfferTableModel modelBuyOffers = new OfferTableModel();
	private JScrollPane scrollPane;
	private JPanel panel_1;
	private JTable table_1;
	private JScrollPane scrollPane_1;
	private JPanel panel_2;
	private TimeSeries salesTimeSeries = new TimeSeries("Sale Prices");
	private TimeSeries volumeTimeSeries = new TimeSeries("Volume");

	public TrackedSales() {
		setLayout(new MigLayout("", "[grow][grow][grow]", "[grow][grow]"));

		list = new JList<TrackedListingChange>(model);
		add(list, "cell 0 0,grow");

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Sell Offers",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		add(panel, "cell 1 0,grow");
		panel.setLayout(new MigLayout("", "[grow]", "[grow]"));

		scrollPane = new JScrollPane();
		panel.add(scrollPane, "cell 0 0,grow");

		table = new JTable(modelSellOffers);
		scrollPane.setViewportView(table);

		panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Buy Offers",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		add(panel_1, "cell 2 0,grow");
		panel_1.setLayout(new MigLayout("", "[452px]", "[427px]"));

		scrollPane_1 = new JScrollPane();
		panel_1.add(scrollPane_1, "cell 0 0,alignx left,aligny top");

		table_1 = new JTable(modelBuyOffers);
		scrollPane_1.setViewportView(table_1);

		panel_2 = new ChartPanel(buildPriceVolumeChart(new TimeSeriesCollection(salesTimeSeries), new TimeSeriesCollection(volumeTimeSeries), "Sales"));
		add(panel_2, "cell 0 1 3 1,grow");
	}

	public void addChange(List<TrackedListingChange> changes,
			List<TradeListing> sellOffers, List<TradeListing> buyOffers) {
		for (TrackedListingChange change : changes) {
			model.add(0, change);
		}

		List<TradeListing> items = new ArrayList<>(sellOffers.subList(0, 20));

		// Collections.reverse(items);

		modelSellOffers.setOffers(items);
		modelSellOffers.fireTableDataChanged();

		salesTimeSeries.add(new Second(), items.get(0).getUnit_price());
		volumeTimeSeries.add(new Second(), items.get(0).getQuantity());
		
		items = new ArrayList<>(buyOffers.subList(0, 20));

		// Collections.reverse(items);

		modelBuyOffers.setOffers(items);
		modelBuyOffers.fireTableDataChanged();
	}

	private JFreeChart buildPriceVolumeChart(TimeSeriesCollection priceDataset,
			TimeSeriesCollection volumeDataset, String title) {
		JFreeChart chart = ChartFactory.createTimeSeriesChart(title, "Date",
				"Price", priceDataset, true, true, false);
		chart.setBackgroundPaint(Color.white);
		XYPlot plot = chart.getXYPlot();
		NumberAxis rangeAxis1 = (NumberAxis) plot.getRangeAxis();
		rangeAxis1.setLowerMargin(0.40); // to leave room for volume bars
		DecimalFormat format = new DecimalFormat("00.00");
		rangeAxis1.setNumberFormatOverride(format);

		XYItemRenderer renderer1 = plot.getRenderer();
		renderer1.setBaseToolTipGenerator(new StandardXYToolTipGenerator(
				StandardXYToolTipGenerator.DEFAULT_TOOL_TIP_FORMAT,
				new SimpleDateFormat("d-MMM-yyyy"), new DecimalFormat("0.00")));

		NumberAxis rangeAxis2 = new NumberAxis("Volume");
		rangeAxis2.setUpperMargin(1.00); // to leave room for price line
		plot.setRangeAxis(1, rangeAxis2);
		plot.setDataset(1, volumeDataset);
		plot.mapDatasetToRangeAxis(1, 1);
		XYBarRenderer renderer2 = new XYBarRenderer(0.20);
		renderer2.setBaseToolTipGenerator(new StandardXYToolTipGenerator(
				StandardXYToolTipGenerator.DEFAULT_TOOL_TIP_FORMAT,
				new SimpleDateFormat("d-MMM-yyyy"), new DecimalFormat(
						"0,000.00")));
		plot.setRenderer(1, renderer2);

		return chart;
	}

	public void clear() {
		salesTimeSeries.clear();
		volumeTimeSeries.clear();
		
		modelSellOffers.setOffers(new ArrayList<TradeListing>());
		modelSellOffers.fireTableDataChanged();
		
		modelBuyOffers.setOffers(new ArrayList<TradeListing>());
		modelBuyOffers.fireTableDataChanged();
	}
}
