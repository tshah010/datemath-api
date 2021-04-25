package com.arihant.datemathapi.utils;

import com.arihant.datemathapi.service.DateMathController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class DateTimeHelper {
    static final String MINUTES = "0";
    static final String HOURS = "1";
    static final String DAYS = "2";
    static final String WEEKS = "3";
    static final String MONTHS = "4";
    static final String YEARS = "5";

    public static String computeDateTimeQuery(String daysOrHours, String unitOfTime, String operator, String userDateTime) throws ParseException {
        String answer = "";
        Logger logger = LoggerFactory.getLogger(DateTimeHelper.class);
        logger.info("User Date " +userDateTime);
        SimpleDateFormat inputDateFormat = new SimpleDateFormat("MM-dd-yyyy HH:mm");
        inputDateFormat.setLenient(false);

        Calendar c = Calendar.getInstance();
        c.setTime(inputDateFormat.parse(userDateTime));

        int amount = Integer.parseInt(operator) * Integer.parseInt(daysOrHours);
        switch (String.valueOf(unitOfTime)) {
            case MINUTES -> c.add(Calendar.MINUTE, amount);
            case DAYS -> c.add(Calendar.DATE, amount);
            case HOURS -> c.add(Calendar.HOUR, amount);
            case WEEKS -> c.add(Calendar.DATE, amount*7);
            case MONTHS -> c.add(Calendar.MONTH, amount);
            case YEARS -> c.add(Calendar.YEAR, amount);
            default -> throw new IllegalStateException("Unexpected value: " + unitOfTime);
        }
        Date currentDatePlusOne = c.getTime();

        SimpleDateFormat outputDateFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm");
        String result = outputDateFormat.format(currentDatePlusOne);
        logger.info("Updated Date " + result);
        return result;
    }
}
