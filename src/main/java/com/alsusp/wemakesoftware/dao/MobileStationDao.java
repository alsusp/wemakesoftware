package com.alsusp.wemakesoftware.dao;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alsusp.wemakesoftware.model.MobileStation;

@Repository
public interface MobileStationDao extends JpaRepository<MobileStation, UUID> {

}
