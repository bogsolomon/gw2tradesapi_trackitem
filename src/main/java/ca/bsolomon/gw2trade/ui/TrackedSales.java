package ca.bsolomon.gw2trade.ui;

import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

import javax.swing.JList;

import ca.bsolomon.gw2trade.dao.TrackedListingChange;

public class TrackedSales extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JList<TrackedListingChange> list;
	private DefaultListModel<TrackedListingChange> model = new DefaultListModel<>();
	
	public TrackedSales() {
		setLayout(new MigLayout("", "[grow]", "[grow]"));
		
		list = new JList<TrackedListingChange>(model);
		add(list, "cell 0 0,grow");
	}

	public void addChange(List<TrackedListingChange> changes) {
		for (TrackedListingChange change:changes) {
			model.add(0, change);
		}
	}

}
