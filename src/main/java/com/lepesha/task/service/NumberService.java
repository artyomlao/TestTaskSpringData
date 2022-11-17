package com.lepesha.task.service;

import com.lepesha.task.exception.NumberAlreadyExists;
import com.lepesha.task.exception.NumberIsMissing;
import com.lepesha.task.dto.SumDTO;
import com.lepesha.task.dto.NumberDTO;
import com.lepesha.task.model.Number;
import com.lepesha.task.repository.NumberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NumberService {
    private final NumberRepository numberRepository;

    @Autowired
    public NumberService(NumberRepository numberRepository) {
        this.numberRepository = numberRepository;
    }

    public void addNumber(NumberDTO numberDTO) throws NumberAlreadyExists {
        Number number = new Number(numberDTO.getCode(), numberDTO.getValue());
        numberRepository.save(number);
    }

    public void removeNumberByCode(String code) throws NumberIsMissing {
//        new NumberIsMissing("Number with such code is missing");
        numberRepository.findById(code)
                .orElseThrow(() -> new NumberIsMissing("Number with such code is missing"));
        numberRepository.deleteById(code);
    }

    public Double sumNumbers(SumDTO sumDTO) throws NumberIsMissing {
        Number firstNumber = numberRepository.findById(sumDTO.getFirst())
                .orElseThrow(() -> new NumberIsMissing("Number with such code is missing"));
        Number secondNumber = numberRepository.findById(sumDTO.getSecond())
                .orElseThrow(() -> new NumberIsMissing("Number with such code is missing"));

        Double result = firstNumber.getValue() + secondNumber.getValue();
        return result;
    }
}
