package com.arihant.datemathapi.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class DateTimeHelper {
    static final String HOURS = "0";
    static final String DAYS = "1";

    public static String computeDateTimeQuery(String daysOrHours, String unitOfTime, String operator, String userDateTime) throws ParseException {
        // http://localhost:8080/calculate?daysOrHours=5&unitOfTime=1&operator=1&userDateTime=02/13/1974
        String answer = "";
        // SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("User Date " +userDateTime);
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        dateFormat.setLenient(false);
        // System.out.println("User Date " + dateFormat.format(userDateTime));

        // Convert Date to Calenda
        Calendar c = Calendar.getInstance();
        c.setTime(dateFormat.parse(userDateTime));

        // c.add(Calendar.YEAR, 2);
        // c.add(Calendar.MONTH, 1);
        int amount = Integer.parseInt(operator) * Integer.parseInt(daysOrHours);
        switch (unitOfTime) {
            case DAYS: c.add(Calendar.DATE, amount);
            case HOURS: c.add(Calendar.HOUR, amount);
        }
        // c.add(Calendar.MINUTE, 30);
        // c.add(Calendar.SECOND, 50);

        // Convert calendar back to Date
        Date currentDatePlusOne = c.getTime();

        System.out.println("Updated Date " + dateFormat.format(currentDatePlusOne));
        return dateFormat.format(currentDatePlusOne);
    }
}
