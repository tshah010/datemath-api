package com.arihant.datemathapi.service;

import com.arihant.datemathapi.model.Result;
import com.arihant.datemathapi.utils.DateTimeHelper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@CrossOrigin
@RestController
public class DateMathController {

    @GetMapping("/calculate")
    public ResponseEntity<?> greeting(@RequestParam(value = "daysOrHours", defaultValue = "-1") String daysOrHours,
                                      @RequestParam(value = "unitOfTime", defaultValue = "-1") String unitOfTime,
                                      @RequestParam(value = "operator", defaultValue = "-1") String operator,
                                      @RequestParam(value = "userDateTime", defaultValue = "-1") String userDateTime) {
        try {
            String answer = DateTimeHelper.computeDateTimeQuery(daysOrHours, unitOfTime, operator, userDateTime);
            Result resultModel = new Result(answer);
            return ResponseEntity.ok()
                    .body(resultModel);
        } catch (ParseException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest()
                    .body("ParseException: Please check format of " + userDateTime + " and try again.");
        }
    }
}