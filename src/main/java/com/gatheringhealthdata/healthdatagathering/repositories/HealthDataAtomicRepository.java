package com.gatheringhealthdata.healthdatagathering.repositories;

import com.gatheringhealthdata.healthdatagathering.entities.HealthDataAtomic;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface HealthDataAtomicRepository extends CrudRepository<HealthDataAtomic, Long> {

      List<HealthDataAtomic> findByName(String name);
}