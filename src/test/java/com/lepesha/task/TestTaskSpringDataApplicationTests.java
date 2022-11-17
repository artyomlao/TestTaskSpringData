package com.lepesha.task;

import com.lepesha.task.dto.NumberDTO;
import com.lepesha.task.dto.SumDTO;
import com.lepesha.task.exception.NumberAlreadyExists;
import com.lepesha.task.exception.NumberIsMissing;
import com.lepesha.task.repository.NumberRepository;
import com.lepesha.task.service.NumberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class TestTaskSpringDataApplicationTests {
    private NumberRepository numberRepository;

    private NumberService numberService;

    @Autowired
    public TestTaskSpringDataApplicationTests(NumberService numberService, NumberRepository numberRepository) {
        this.numberService = numberService;
        this.numberRepository = numberRepository;
    }

    @Test
    public void removeAndAdd() {
        try {
            numberService.removeNumberByCode("first");
        } catch (NumberIsMissing e) {
            e.printStackTrace();
        }
        NumberDTO numberDTO = new NumberDTO();
        numberDTO.setCode("first");
        numberDTO.setValue(4.0);
        try {
            numberService.addNumber(numberDTO);
        } catch (NumberAlreadyExists e) {
            e.printStackTrace();
        }

        assertEquals(4, numberRepository.findById("first").get().getValue());
    }

    @Test
    public void checkSum() throws NumberIsMissing {
        try {
            numberService.removeNumberByCode("first");
        } catch (NumberIsMissing e) {
        }

        try {
            numberService.removeNumberByCode("second");
        } catch (NumberIsMissing e) {
            e.printStackTrace();
        }

        NumberDTO firstNumber = new NumberDTO();
        firstNumber.setCode("first");
        firstNumber.setValue(4.0);
        try {
            numberService.addNumber(firstNumber);
        } catch (NumberAlreadyExists e) {
        }

        NumberDTO secondNumber = new NumberDTO();
        secondNumber.setCode("second");
        secondNumber.setValue(4.0);

        try {
            numberService.addNumber(secondNumber);
        } catch (NumberAlreadyExists e) {
            e.printStackTrace();
        }

        SumDTO sum = new SumDTO();
        sum.setFirst("first");
        sum.setSecond("second");
        assertEquals(8, numberService.sumNumbers(sum));
    }
}
