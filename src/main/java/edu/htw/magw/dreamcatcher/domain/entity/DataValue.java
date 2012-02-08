package edu.htw.magw.dreamcatcher.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import edu.htw.magw.dreamcatcher.domain.entity.base.AbstractEntityBase;

@Entity
public class DataValue extends AbstractEntityBase {

	private static final long serialVersionUID = 1L;

	@Column(nullable = true)
	private String measurementType ;
	
	@Column(nullable = true)
	private double measurementValue ;
	
	@ManyToOne
	private MeasurementData data ;
	
	public DataValue() {
		super() ;
	}
	
	public DataValue(String type, double measurementValue) {
		super();
		this.measurementType = type;
		this.measurementValue = measurementValue ;
	}

	public String getMeasurementType() {
		return measurementType;
	}

	public void setMeasurementType(String measurementType) {
		this.measurementType = measurementType;
	}

	public double getMeasurementValue() {
		return measurementValue;
	}

	public void setMeasurementValue(double measurementValue) {
		this.measurementValue = measurementValue;
	}

	public MeasurementData getData() {
		return data;
	}

	public void setData(MeasurementData data) {
		this.data = data;
	}
}
