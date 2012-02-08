package edu.htw.magw.dreamcatcher;

import org.joda.time.DateTime;

public class Value {

	double value ;
	DateTime dateTime ;
	
	public Value() {}

	public Value(double value, DateTime dateTime) {
		super();
		this.value = value;
		this.dateTime = dateTime;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public DateTime getDateTime() {
		return dateTime;
	}

	public void setDateTime(DateTime dateTime) {
		this.dateTime = dateTime;
	}
}
