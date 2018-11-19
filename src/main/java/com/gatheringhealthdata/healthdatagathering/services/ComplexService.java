package com.gatheringhealthdata.healthdatagathering.services;

import com.gatheringhealthdata.healthdatagathering.entities.HealthDataComplex;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ComplexService {
    List<HealthDataComplex> listAll();

    List<HealthDataComplex> findByName(String name);

    HealthDataComplex getById(Long id);

    HealthDataComplex saveOrUpdate(HealthDataComplex complex, long id);

    void delete(Long id);
}
