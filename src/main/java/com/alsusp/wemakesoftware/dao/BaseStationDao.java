package com.alsusp.wemakesoftware.dao;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alsusp.wemakesoftware.model.BaseStation;

@Repository
public interface BaseStationDao extends JpaRepository<BaseStation, UUID> {

	public Optional<BaseStation> findByName(String name);
}
