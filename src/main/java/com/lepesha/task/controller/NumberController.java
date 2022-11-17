package com.lepesha.task.controller;

import com.lepesha.task.dto.NumberDTO;
import com.lepesha.task.dto.SumDTO;
import com.lepesha.task.exception.NumberAlreadyExists;
import com.lepesha.task.exception.NumberIsMissing;
import com.lepesha.task.responseEntity.BasicResponse;
import com.lepesha.task.responseEntity.SumResponse;
import com.lepesha.task.service.NumberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NumberController {
    private final NumberService numberService;

    @Autowired
    public NumberController(NumberService numberService) {
        this.numberService = numberService;
    }

    @PostMapping("/add")
    public ResponseEntity<BasicResponse> add(@RequestBody NumberDTO numberDTO) {
        try {
            numberService.addNumber(numberDTO);
        } catch (NumberAlreadyExists e) {
            return new ResponseEntity<>(new BasicResponse(10), HttpStatus.UNPROCESSABLE_ENTITY);
        }
        return new ResponseEntity<>(new BasicResponse(0), HttpStatus.OK);
    }

    @PostMapping("/remove")
    public ResponseEntity<BasicResponse> remove(@RequestBody NumberDTO numberDTO) {
        try {
            numberService.removeNumberByCode(numberDTO.getCode());
        } catch (NumberIsMissing e) {
            return new ResponseEntity<>(new BasicResponse(11), HttpStatus.UNPROCESSABLE_ENTITY);
        }
        return new ResponseEntity<>(new BasicResponse(0), HttpStatus.OK);
    }

    @PostMapping("/sum")
    public ResponseEntity<SumResponse> sum(@RequestBody SumDTO sumDTO) {
        SumResponse sumResponse = new SumResponse(0);
        try {
            Double result = numberService.sumNumbers(sumDTO);
            sumResponse.setResult(result);
            return new ResponseEntity<>(sumResponse, HttpStatus.OK);
        } catch (NumberIsMissing e) {
            sumResponse.setCode(0);
            sumResponse.setDescription("At least one number with such code is missing");
            return new ResponseEntity<>(sumResponse, HttpStatus.UNPROCESSABLE_ENTITY);
        }

    }

}
