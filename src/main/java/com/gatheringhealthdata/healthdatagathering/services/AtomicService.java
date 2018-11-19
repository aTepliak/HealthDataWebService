package com.gatheringhealthdata.healthdatagathering.services;

import com.gatheringhealthdata.healthdatagathering.entities.HealthDataAtomic;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AtomicService {

    List<HealthDataAtomic> listAll();

    List<HealthDataAtomic> findByName(String name);

    HealthDataAtomic getById(Long id);

    HealthDataAtomic saveOrUpdate(HealthDataAtomic dataAtomic, long id);

    void delete(Long id);

}
