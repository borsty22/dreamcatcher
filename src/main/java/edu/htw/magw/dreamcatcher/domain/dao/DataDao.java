package edu.htw.magw.dreamcatcher.domain.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.htw.magw.dreamcatcher.domain.entity.MeasurementData;

public interface DataDao extends JpaRepository<MeasurementData, Long> {

}
