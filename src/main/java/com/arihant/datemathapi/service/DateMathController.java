package com.arihant.datemathapi.service;

import com.arihant.datemathapi.model.Result;
import com.arihant.datemathapi.model.APIError;

import com.arihant.datemathapi.utils.DateTimeHelper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.text.ParseException;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;

import javax.validation.constraints.Size;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.net.URI;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@CrossOrigin
@RestController
public class DateMathController {


    @GetMapping(value = "/v1/calculate-before-after", produces = "application/json")
    @Operation(description = "Subtract or Add minutes/hours/days/weeks/months/years from a given date", responses = {
            @ApiResponse(content = @Content(schema = @Schema(implementation = Result.class)), responseCode = "200"),
            @ApiResponse(responseCode = "400", description = "Unable to parse %s. It must be in \"MM-dd-yyyy HH:mm\" format.", content = @Content(schema = @Schema(implementation = APIError.class)))})
    public ResponseEntity<?> calculate(@Parameter(description = "Number of min/days/hours/weeks/months/years", required = true) @RequestParam("daysOrHours") int daysOrHours,
                                       @Parameter(description = "0=min, 1=hours, 2=days, 3=weeks, 4=months, 5=years", required = true) @RequestParam("unitOfTime") int unitOfTime,
                                       @Parameter(description = "1=after, -1=before", required = true) @RequestParam("operator") int operator,
                                       @Parameter(description = "Date to be adjusted", required = true, example = "12-31-1970 17:59") @RequestParam(value = "userDateTime") String userDateTime,
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

    @GetMapping(value = "/v1/calculate-date-difference", produces = "application/json")
    @Operation(description = "Find difference between two dates", responses = {
            @ApiResponse(content = @Content(schema = @Schema(implementation = Result.class)), responseCode = "200"),
            @ApiResponse(responseCode = "400", description = "Unable to parse %s. It must be in \"MM-dd-yyyy HH:mm\" format.", content = @Content(schema = @Schema(implementation = APIError.class)))})
    public ResponseEntity<?> calculateDateDifference(@Parameter(description = "Date subtracted from (minuend)", required = true, example = "12-31-1971 17:59") @RequestParam(value = "userStartDateTime") String userStartDateTime,
                                       @Parameter(description = "Date being subtracted (subtrahend)", required = true, example = "12-31-1970 17:59") @RequestParam(value = "userEndDateTime") String userEndDateTime,
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