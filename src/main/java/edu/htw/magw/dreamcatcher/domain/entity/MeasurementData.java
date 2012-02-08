package edu.htw.magw.dreamcatcher.domain.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import edu.htw.magw.dreamcatcher.domain.entity.base.AbstractEntityBase;

@Entity
public class MeasurementData extends AbstractEntityBase {

	private static final long serialVersionUID = 1L;

	@Type(type = "org.joda.time.contrib.hibernate.PersistentDateTime")
	private DateTime recordedDateTime;

	@OneToMany(cascade = CascadeType.ALL, mappedBy="data")
	private List<DataValue> dataValueList = new ArrayList<DataValue>();

	@ManyToOne
	private Series series;

	public MeasurementData() {
	}

	/**
	 * @return the dateTime
	 */
	public DateTime getRecordedDateTime() {
		return recordedDateTime;
	}

	/**
	 * @param dateTime
	 *            the dateTime to set
	 */
	public void setRecordedDateTime(DateTime recordedDateTime) {
		this.recordedDateTime = recordedDateTime;
	}

	/**
	 * @return the series
	 */
	public Series getSeries() {
		return series;
	}

	/**
	 * @param series
	 *            the series to set
	 */
	public void setSeries(Series series) {
		this.series = series;
	}

	public List<DataValue> getDataValueList() {
		return dataValueList;
	}

	public void setDataValueList(List<DataValue> dataValueList) {
		this.dataValueList = dataValueList;
	}

	public void addToDataValue(DataValue dataValue) {

		if (this.dataValueList == null)
			this.dataValueList = new ArrayList<DataValue>();

		if (!this.dataValueList.contains(dataValue))
			this.dataValueList.add(dataValue);
		
		dataValue.setData(this) ;

	}

	public void removeFromDataList(DataValue dataValue) {

		if (this.dataValueList != null)
			if (this.dataValueList.contains(dataValue)) {
				dataValue.setData(null) ;
				this.dataValueList.remove(dataValue);
			}
	}
}
