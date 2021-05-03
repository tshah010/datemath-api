package com.arihant.datemathapi.service;

import com.arihant.datemathapi.model.Result;
import com.arihant.datemathapi.model.APIError;

import com.arihant.datemathapi.utils.DateTimeHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.text.ParseException;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
public class DateMathController {

    @GetMapping(value = "/calculate-before-after", produces = "application/json")
    public ResponseEntity<?> calculate(@RequestParam(value = "daysOrHours", defaultValue = "0") String daysOrHours,
                                      @RequestParam(value = "unitOfTime", defaultValue = "0") String unitOfTime,
                                      @RequestParam(value = "operator", defaultValue = "-1") String operator,
                                      @RequestParam(value = "userDateTime", defaultValue = "-1") String userDateTime,
                                       @RequestHeader Map<String, String> headers) {
        Logger logger = LoggerFactory.getLogger(DateMathController.class);
        logHeaders("calculate", headers);

        try {
            String answer = DateTimeHelper.computeDateTimeQuery(daysOrHours, unitOfTime, operator, userDateTime);
            Result resultModel = new Result(answer);
            return ResponseEntity.ok()
                    .body(resultModel);
        } catch (ParseException e) {
            logger.error(e.getLocalizedMessage());
            return ResponseEntity.badRequest()
                    .body(new APIError(1, String.format("Unable to parse %s. It must be in \"MM-dd-yyyy HH:mm\" format.", userDateTime)));
        } catch (Exception e) {
            logger.error("Unknown Exception",e);
            return ResponseEntity.badRequest()
                    .body(new APIError(2, e.getMessage()));
        }
    }

    @GetMapping(value = "/calculate-date-difference", produces = "application/json")
    public ResponseEntity<?> calculateDateDifference(@RequestParam(value = "userStartDateTime", defaultValue = "0") String userStartDateTime,
                                       @RequestParam(value = "userEndDateTime", defaultValue = "0") String userEndDateTime,
                                                     @RequestHeader Map<String, String> headers) {
        Logger logger = LoggerFactory.getLogger(DateMathController.class);
        logHeaders("calculate-date-difference", headers);
        try {
            String answer = DateTimeHelper.addSubtractDates(userStartDateTime, userEndDateTime);
            Result resultModel = new Result(answer);
            return ResponseEntity.ok()
                    .body(resultModel);
        } catch (ParseException e) {
            logger.error("ParseException", e);
            return ResponseEntity.badRequest()
                    .body(new APIError(1, String.format("Unable to parse %s or %s. It must be in \"MM-dd-yyyy HH:mm\" format.", userStartDateTime, userEndDateTime)));
        } catch (Exception e) {
            logger.error("Unknown Exception",e);
            return ResponseEntity.badRequest()
                    .body(new APIError(2, e.getMessage()));
        }
    }

    private void logHeaders(String requestName, Map<String, String> headers) {
        Logger logger = LoggerFactory.getLogger(DateMathController.class);
        logger.debug(String.format("Headers received for %s are:", requestName));
        headers.forEach((key, value) -> logger.debug(String.format("Header '%s' = %s", key, value)));

    }
}