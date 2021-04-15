package com.arihant.datemathapi.service;

import com.arihant.datemathapi.model.Result;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
public class DateMathController {

    @GetMapping("/calculate")
    public ResponseEntity<?> greeting(@RequestParam(value = "daysOrHours", defaultValue = "-1") String daysOrHours,
                                   @RequestParam(value = "unitOfTime", defaultValue = "-1") String unitOfTime,
                                   @RequestParam(value = "operator", defaultValue = "-1") String operator,
                                   @RequestParam(value = "userDateTime", defaultValue = "-1") String userDateTime) {

        Result resultModel = new Result("Answer is today");
        return ResponseEntity.ok()
                .body(resultModel);

    }
}