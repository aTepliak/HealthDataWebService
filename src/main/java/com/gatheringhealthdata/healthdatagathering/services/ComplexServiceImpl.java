package com.gatheringhealthdata.healthdatagathering.services;

import com.gatheringhealthdata.healthdatagathering.entities.HealthDataComplex;
import com.gatheringhealthdata.healthdatagathering.repositories.HealthDataComplexRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class ComplexServiceImpl implements ComplexService {

    private HealthDataComplexRepository complexRepository;

    @Autowired
    public ComplexServiceImpl(HealthDataComplexRepository ComplexRepository) {
        this.complexRepository = ComplexRepository;
    }

    @Override
    public List<HealthDataComplex> listAll() {
        List<HealthDataComplex> data = new ArrayList<>();
        complexRepository.findAll().forEach(data::add);
        return data;
    }

    @Override
    public List<HealthDataComplex> findByName(String name) {
        return complexRepository.findByName(name);
    }

    @Override
    public HealthDataComplex getById(Long id) {
        return complexRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public HealthDataComplex saveOrUpdate(HealthDataComplex data, long id) {
        data.setId(id);
        complexRepository.save(data);
        return data;
    }

    @Override
    public void delete(Long id) {
        complexRepository.deleteById(id);

    }

}
