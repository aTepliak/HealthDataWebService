package com.gatheringhealthdata.healthdatagathering.repositories;

import com.gatheringhealthdata.healthdatagathering.entities.HealthDataComplex;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface HealthDataComplexRepository extends CrudRepository<HealthDataComplex, Long> {
    List<HealthDataComplex> findByName(String name);

}
