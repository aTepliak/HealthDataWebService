package com.gatheringhealthdata.healthdatagathering.controllers;

import com.gatheringhealthdata.healthdatagathering.entities.HealthDataAtomic;
import com.gatheringhealthdata.healthdatagathering.entities.HealthDataComplex;
import com.gatheringhealthdata.healthdatagathering.services.AtomicService;
import com.gatheringhealthdata.healthdatagathering.services.ComplexService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.trace.http.HttpTrace;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.Calendar;
import java.util.List;

@Controller
@RequestMapping("collect")
public class CollectionController {

    private AtomicService atomicService;
    private ComplexService complexService;
    Calendar calendar = Calendar.getInstance();

    @Autowired
    public void setAtomicAndComplexServices(AtomicService atomicService, ComplexService complexService) {
        this.atomicService = atomicService;
        this.complexService = complexService;
    }

    @PostMapping(path = "/complex", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<HttpTrace.Response> readIncomingComplexData(@Valid @RequestBody List<HealthDataComplex> complexData) {
        Gson gson = new Gson();
        String json = gson.toJson(complexData);
        System.out.println("Input" + json);
        complexData.forEach(complex -> {
            complex.getAtomicValues().forEach(atomic ->{
                HealthDataAtomic pAtomic= atomicService.saveOrUpdate(atomic, atomic.getId());
                System.out.println("names:" + pAtomic.getName());
                 complex.getAtomicValues().add(pAtomic);

            });

            complexService.saveOrUpdate(complex, complex.getId());
        });
        List<HealthDataComplex> allComplex = complexService.listAll();
        String atomicsAsJson= new Gson().toJson(allComplex);
        System.out.println(atomicsAsJson);
        ResponseEntity responseEntity = new ResponseEntity(HttpStatus.OK);
        return responseEntity;
    }

    @PostMapping(path = "/atomics", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<HttpTrace.Response> readIncomingAtomicData(@RequestBody List<HealthDataAtomic> atomicData) {
        atomicData.forEach(atomic -> atomicService.saveOrUpdate(atomic, atomic.getId()));
        ResponseEntity responseEntity = new ResponseEntity(HttpStatus.OK);
        return responseEntity;
    }
}