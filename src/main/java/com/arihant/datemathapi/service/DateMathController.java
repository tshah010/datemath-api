package com.arihant.datemathapi.service;

import com.arihant.datemathapi.model.Result;
import com.arihant.datemathapi.model.APIError;

import com.arihant.datemathapi.utils.DateTimeHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import java.text.ParseException;

@CrossOrigin
@RestController
public class DateMathController {

    @GetMapping(value = "/calculate", produces = "application/json")
    public ResponseEntity<?> calculate(@RequestParam(value = "daysOrHours", defaultValue = "-1") String daysOrHours,
                                      @RequestParam(value = "unitOfTime", defaultValue = "-1") String unitOfTime,
                                      @RequestParam(value = "operator", defaultValue = "-1") String operator,
                                      @RequestParam(value = "userDateTime", defaultValue = "-1") String userDateTime) {
        Logger logger = LoggerFactory.getLogger(DateMathController.class);
        try {
            String answer = DateTimeHelper.computeDateTimeQuery(daysOrHours, unitOfTime, operator, userDateTime);
            Result resultModel = new Result(answer);
            return ResponseEntity.ok()
                    .body(resultModel);
        } catch (ParseException e) {
            logger.error(e.getLocalizedMessage());
            return ResponseEntity.badRequest()
                    .body(new APIError(1, String.format("Can not parse '%s'.It must be in \"MM/dd/yyyy\" format.", userDateTime)));
        }
    }
}