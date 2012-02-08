package edu.htw.magw.dreamcatcher.domain.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.htw.magw.dreamcatcher.domain.entity.Device;

public interface DeviceDao extends JpaRepository<Device, Long> {

	public Device findByDeviceId(long deviceId) ;
	
}
