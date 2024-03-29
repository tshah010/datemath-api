package com.arihant.datemathapi.utils;

import com.arihant.datemathapi.service.DateMathController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;


public class DateTimeHelper {
    static final String MINUTES = "0";
    static final String HOURS = "1";
    static final String DAYS = "2";
    static final String WEEKS = "3";
    static final String MONTHS = "4";
    static final String YEARS = "5";

    public static String computeDateTimeQuery(Integer daysOrHours, Integer unitOfTime, Integer operator, String userDateTime) throws ParseException {
        String answer = "";
        Logger logger = LoggerFactory.getLogger(DateTimeHelper.class);
        logger.info("User Date " +userDateTime);
        SimpleDateFormat inputDateFormat = new SimpleDateFormat("MM-dd-yyyy HH:mm");
        inputDateFormat.setLenient(false);

        Calendar c = Calendar.getInstance();
        c.setTime(inputDateFormat.parse(userDateTime));

        int amount = operator * daysOrHours;
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

        SimpleDateFormat outputDateFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm z");
        String result = outputDateFormat.format(currentDatePlusOne);
        logger.info("Updated Date " + result);
        return result;
    }

    public static String addSubtractDates(String userStartTime, String userEndTime) throws ParseException {
        Logger logger = LoggerFactory.getLogger(DateTimeHelper.class);
        logger.debug(String.format("User Dates are %s amd %s", userStartTime, userEndTime));
        SimpleDateFormat inputDateFormat = new SimpleDateFormat("MM-dd-yyyy HH:mm");
        inputDateFormat.setLenient(false);

        Date startDate = inputDateFormat.parse(userStartTime);
        Date endDate = inputDateFormat.parse(userEndTime);

        Calendar startCalendar = Calendar.getInstance();
        startCalendar.setTime(startDate);
        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime(endDate);

        long resultInMillis = startCalendar.getTimeInMillis() - endCalendar.getTimeInMillis();
        long resultDays = TimeUnit.MILLISECONDS.toDays(resultInMillis);
        long resultHours = TimeUnit.MILLISECONDS.toHours(resultInMillis - resultDays*24*60*60*1000) ;
        long resultMinutes = TimeUnit.MILLISECONDS.toMinutes(resultInMillis - resultDays*24*60*60*1000 - resultHours*60*60*1000);

        String result = String.format("%d days, %d hours, %d minutes", resultDays,resultHours, resultMinutes );
        logger.info(result);

        return result;
    }

}
