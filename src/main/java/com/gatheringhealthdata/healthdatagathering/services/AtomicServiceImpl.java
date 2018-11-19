package com.gatheringhealthdata.healthdatagathering.services;

import com.gatheringhealthdata.healthdatagathering.entities.HealthDataAtomic;
import org.springframework.beans.factory.annotation.Autowired;
import com.gatheringhealthdata.healthdatagathering.repositories.HealthDataAtomicRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class AtomicServiceImpl implements AtomicService {


    private HealthDataAtomicRepository atomicRepository;

    @Autowired
    public AtomicServiceImpl(HealthDataAtomicRepository atomicRepository) {
        this.atomicRepository = atomicRepository;
    }


    @Override
    public List<HealthDataAtomic> listAll() {
        List<HealthDataAtomic> data = new ArrayList<>();
        atomicRepository.findAll().forEach(data::add);
        return data;
    }

    @Override
    public List<HealthDataAtomic> findByName(String name) {
        return atomicRepository.findByName(name);
    }

    @Override
    public HealthDataAtomic  getById(Long id) {
        return  atomicRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public HealthDataAtomic saveOrUpdate(HealthDataAtomic data, long id) {
        data.setId(id);
        atomicRepository.save(data);
        return data;
    }

    @Override
    public void delete(Long id) {
        atomicRepository.deleteById(id);

    }
}
