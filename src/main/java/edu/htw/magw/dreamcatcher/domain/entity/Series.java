package edu.htw.magw.dreamcatcher.domain.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import edu.htw.magw.dreamcatcher.domain.entity.base.AbstractEntityBase;

@Entity
public class Series extends AbstractEntityBase {

	private static final long serialVersionUID = 1L;

	@ManyToOne(cascade = CascadeType.ALL)
	private Device device;

	@OneToMany(cascade = CascadeType.ALL, mappedBy="series")
	private List<MeasurementData> measurementDataList;

	/**
	 * @return the device
	 */
	public Device getDevice() {
		return device;
	}

	public void setDevice(Device device) {
		this.device = device;
	}

	public List<MeasurementData> getMeasurementDataList() {
		return measurementDataList;
	}

	public void setMeasurementDataList(List<MeasurementData> measurementDataList) {
		this.measurementDataList = measurementDataList;
	}

	public void addToMeasurementDataList(MeasurementData data) {

		if (this.measurementDataList == null)
			this.measurementDataList = new ArrayList<MeasurementData>();

		if (!this.measurementDataList.contains(data))
			this.measurementDataList.add(data);

		data.setSeries(this);
	}

	public void removeFromMeasurementDataList(MeasurementData data) {

		if (this.measurementDataList != null)
			if (this.measurementDataList.contains(data)) {
				data.setSeries(null);
				this.measurementDataList.remove(data);
			}
	}
}
