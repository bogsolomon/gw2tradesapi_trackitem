package ca.bsolomon.gw2trade.util;

import java.text.DecimalFormat;
import java.text.FieldPosition;
import java.text.NumberFormat;
import java.text.ParsePosition;

public class MultipleGroupingDecimalFormat extends NumberFormat {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static DecimalFormat myFormatter = new DecimalFormat("##");

	@Override
	public StringBuffer format(double number, StringBuffer toAppendTo,
			FieldPosition pos) {
		int intNumber = (int)number;
		
		myFormatter.setMinimumIntegerDigits(2);
		if ((int)intNumber/10000 != 0)
				toAppendTo.append(myFormatter.format((int)intNumber/10000)).append(".");
		
		toAppendTo.append(myFormatter.format((int)(intNumber/100)%100)).append(".");
		toAppendTo.append(myFormatter.format(intNumber%100));
		return toAppendTo;
	}

	@Override
	public StringBuffer format(long number, StringBuffer toAppendTo,
			FieldPosition pos) {
		myFormatter.setMinimumIntegerDigits(2);
		toAppendTo.append((int)number/10000).append(".");
		toAppendTo.append((int)(number/100)%100).append(".");
		toAppendTo.append(number%100);
		return toAppendTo;
	}

	@Override
	public Number parse(String source, ParsePosition parsePosition) {
		return null;
	}
}
