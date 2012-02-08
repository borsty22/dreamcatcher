package edu.htw.magw.dreamcatcher.domain.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.htw.magw.dreamcatcher.domain.entity.Series;

public interface SeriesDao extends JpaRepository<Series, Long> {

}
