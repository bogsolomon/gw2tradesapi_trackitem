package ca.bsolomon.gw2trade.ui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.ScrollPaneConstants;

import net.miginfocom.swing.MigLayout;

import javax.swing.JList;

import ca.bsolomon.gw2event.api.dao.TradeListing;
import ca.bsolomon.gw2trade.dao.TrackedListingChange;
import ca.bsolomon.gw2trade.util.MultipleGroupingDecimalFormat;

import javax.swing.border.TitledBorder;
import javax.swing.table.TableModel;
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
	private JScrollPane scrollPane_2;

	public TrackedSales() {
		setLayout(new MigLayout("", "[475px][grow][grow]", "[grow][grow]"));
		
		scrollPane_2 = new JScrollPane();
		add(scrollPane_2, "cell 0 0,grow");

		list = new JList<TrackedListingChange>(model);
		scrollPane_2.setViewportView(list);

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Sell Offers",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		add(panel, "cell 1 0,grow");
		panel.setLayout(new MigLayout("", "[grow]", "[grow]"));

		table = new JTable(modelSellOffers);
		scrollPane = createPagingScrollPaneForTable(table);
		panel.add(scrollPane, "cell 0 0,grow");
		
		scrollPane.setViewportView(table);

		panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Buy Offers",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		add(panel_1, "cell 2 0,grow");
		panel_1.setLayout(new MigLayout("", "[452px]", "[427px]"));

		table_1 = new JTable(modelBuyOffers);
		scrollPane_1 = createPagingScrollPaneForTable(table_1);
		panel_1.add(scrollPane_1, "cell 0 0,alignx left,aligny top");

		scrollPane_1.setViewportView(table_1);

		panel_2 = new ChartPanel(buildPriceVolumeChart(new TimeSeriesCollection(salesTimeSeries), new TimeSeriesCollection(volumeTimeSeries), "Sales"));
		add(panel_2, "cell 0 1 3 1,grow");
	}

	public void addChange(List<TrackedListingChange> changes,
			List<TradeListing> sellOffers, List<TradeListing> buyOffers) {
		if (changes != null) {
			for (TrackedListingChange change : changes) {
				model.add(0, change);
			}
		}

		if(sellOffers.size() != 0) {
			modelSellOffers.setOffers(sellOffers);
			modelSellOffers.fireTableDataChanged();
	
			salesTimeSeries.add(new Second(), sellOffers.get(0).getUnit_price());
			volumeTimeSeries.add(new Second(), sellOffers.get(0).getQuantity());
			
			 if (modelSellOffers.getPageCount() <= 1 || modelSellOffers.getPageOffset() == 0) {
				 scrollPane.getCorner(ScrollPaneConstants.UPPER_RIGHT_CORNER).setEnabled(false);
			 } else {
				 scrollPane.getCorner(ScrollPaneConstants.UPPER_RIGHT_CORNER).setEnabled(true);
			 }
			 
			 if (modelSellOffers.getPageCount() <= 1) {
				 scrollPane.getCorner(ScrollPaneConstants.LOWER_RIGHT_CORNER).setEnabled(false);
			 } else if(modelSellOffers.getPageOffset() != modelSellOffers.getPageCount()-1){
				 scrollPane.getCorner(ScrollPaneConstants.LOWER_RIGHT_CORNER).setEnabled(true);
			 }
		}
		
		if(buyOffers.size() != 0) {
			// Collections.reverse(items);
	
			modelBuyOffers.setOffers(buyOffers);
			modelBuyOffers.fireTableDataChanged();
			
			if (modelBuyOffers.getPageCount() <= 1) {
				 scrollPane_1.getCorner(ScrollPaneConstants.UPPER_RIGHT_CORNER).setEnabled(false);
			 } else {
				 scrollPane_1.getCorner(ScrollPaneConstants.UPPER_RIGHT_CORNER).setEnabled(true);
			 }
		}
	}

	private JFreeChart buildPriceVolumeChart(TimeSeriesCollection priceDataset,
			TimeSeriesCollection volumeDataset, String title) {
		JFreeChart chart = ChartFactory.createTimeSeriesChart(title, "Date",
				"Price", priceDataset, true, true, false);
		chart.setBackgroundPaint(Color.white);
		XYPlot plot = chart.getXYPlot();
		NumberAxis rangeAxis1 = (NumberAxis) plot.getRangeAxis();
		rangeAxis1.setLowerMargin(0.40); // to leave room for volume bars
		DecimalFormatSymbols unusualSymbols = new DecimalFormatSymbols();
		unusualSymbols.setGroupingSeparator('.');
		unusualSymbols.setDecimalSeparator(',');
		NumberFormat format = new MultipleGroupingDecimalFormat();
		rangeAxis1.setNumberFormatOverride(format);

		XYItemRenderer renderer1 = plot.getRenderer();
		renderer1.setBaseToolTipGenerator(new StandardXYToolTipGenerator(
				StandardXYToolTipGenerator.DEFAULT_TOOL_TIP_FORMAT,
				new SimpleDateFormat("d-MMM-yyyy"), format));

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
	
	// We provide our own version of a scrollpane that includes
	  // the page up and page down buttons by default.
	  public static JScrollPane createPagingScrollPaneForTable(JTable jt) {
	    JScrollPane jsp = new JScrollPane(jt);
	    TableModel tmodel = jt.getModel();

	    // Don't choke if this is called on a regular table . . .
	    if (!(tmodel instanceof OfferTableModel)) {
	      return jsp;
	    }

	    // Okay, go ahead and build the real scrollpane
	    final OfferTableModel model = (OfferTableModel) tmodel;
	    final JButton upButton = new JButton(new ArrowIcon(ArrowIcon.UP));
	    upButton.setEnabled(false); // starts off at 0, so can't go up
	    final JButton downButton = new JButton(new ArrowIcon(ArrowIcon.DOWN));
	    if (model.getPageCount() <= 1) {
	      downButton.setEnabled(false); // One page...can't scroll down
	    }

	    upButton.addActionListener(new ActionListener() {
	      public void actionPerformed(ActionEvent ae) {
	        model.pageUp();

	        // If we hit the top of the data, disable the up button.
	        if (model.getPageOffset() == 0) {
	          upButton.setEnabled(false);
	        }
	        downButton.setEnabled(true);
	      }
	    });

	    downButton.addActionListener(new ActionListener() {
	      public void actionPerformed(ActionEvent ae) {
	        model.pageDown();

	        // If we hit the bottom of the data, disable the down button.
	        if (model.getPageOffset() == (model.getPageCount() - 1)) {
	          downButton.setEnabled(false);
	        }
	        upButton.setEnabled(true);
	      }
	    });

	    // Turn on the scrollbars; otherwise we won't get our corners.
	    jsp
	        .setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
	    jsp
	        .setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

	    // Add in the corners (page up/down).
	    jsp.setCorner(ScrollPaneConstants.UPPER_RIGHT_CORNER, upButton);
	    jsp.setCorner(ScrollPaneConstants.LOWER_RIGHT_CORNER, downButton);

	    return jsp;
	  }
}
