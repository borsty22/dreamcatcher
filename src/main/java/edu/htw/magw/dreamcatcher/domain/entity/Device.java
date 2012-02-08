package edu.htw.magw.dreamcatcher.domain.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import edu.htw.magw.dreamcatcher.domain.entity.base.AbstractEntityBase;

@Entity
public class Device extends AbstractEntityBase {

	private static final long serialVersionUID = 1L;

	@Column(nullable = true)
	private long deviceId;

	public Device() {

	}

	public Device(long deviceId, List<Series> seriesList) {
		super();
		this.deviceId = deviceId;
		this.seriesList = seriesList;
	}

	@OneToMany(cascade = CascadeType.ALL, mappedBy="device")
	private List<Series> seriesList;

	/**
	 * @return the deviceId
	 */
	public long getDeviceId() {
		return deviceId;
	}

	/**
	 * @param deviceId
	 *            the deviceId to set
	 */
	public void setDeviceId(long deviceId) {
		this.deviceId = deviceId;
	}

	public List<Series> getSeriesList() {
		return seriesList;
	}

	public void setSeriesList(List<Series> seriesList) {
		this.seriesList = seriesList;
	}

	public void addToSeriesList(Series series) {

		if (this.seriesList == null)
			this.seriesList = new ArrayList<Series>();

		if (!this.seriesList.contains(series))
			this.seriesList.add(series);

		series.setDevice(this);
	}

	public void removeFromSeriesList(Series series) {

		if (this.seriesList != null)
			if (this.seriesList.contains(series)) {
				series.setDevice(null);
				this.seriesList.remove(series);
			}
	}
}
