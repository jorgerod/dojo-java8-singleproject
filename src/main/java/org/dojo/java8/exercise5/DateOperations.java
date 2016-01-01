package org.dojo.java8.exercise5;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class DateOperations {
    
    private final static DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private final static DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    /**
     * Parse String date without times
     *
     * @param date
     *            format dd/MM/yyyy
     * @return
     */
    // TODO Replace with LocalDate and DateTimeFormatter
    // public static Date parseDate(String date) {
    public static LocalDate parseDate(String date) {
        // try { //SimpleDateFormat not thread safe must create new formater for
        // each request
        // SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        // return format.parse(date);
        // } catch (ParseException e) {
        // throw new IllegalArgumentException("bad format " + date);
        // }

        return LocalDate.parse(date, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    /**
     * parse String date with time
     *
     * @param date
     *            format dd/MM/yyyy HH:mm:ss
     * @return
     */
    // TODO Replace with LocalDateTime and DateTimeFormatter
    // public static Date parseDateTime(String date) {
    public static LocalDateTime parseDateTime(String date) {
        // try {
        // SimpleDateFormat format = new
        // SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        // return format.parse(date);
        // } catch (ParseException e) {
        // throw new IllegalArgumentException("bad format " + date);
        // }

        return LocalDateTime.parse(date,
                DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
    }

    // TODO Replace with LocalDate and use Period
    // public static int age(Date birthday, Date now) {
    public static int age(LocalDate birthday, LocalDate now) {
        // Calendar calBirthday = Calendar.getInstance();
        // calBirthday.setTime(birthday);
        //
        // Calendar calNow = Calendar.getInstance();
        // calNow.setTime(now);
        //
        // int age = calNow.get(Calendar.YEAR) - calBirthday.get(Calendar.YEAR);
        // if (calNow.get(Calendar.DAY_OF_YEAR) <
        // calBirthday.get(Calendar.DAY_OF_YEAR)) {
        // age--;
        // }
        //
        // return age;

        return Period.between(birthday, now).getYears();
    }

    // TODO Replace dayDate by LocalDate and result by LocalDateTime
    // public static Date dayDateWithTime(Date dayDate, int hour, int minute,
    // int second) {
    public static LocalDateTime dayDateWithTime(LocalDate dayDate, int hour, int minute,
            int second) {
//        Calendar calendarDayDate = Calendar.getInstance();
//        calendarDayDate.setTime(dayDate);
//
//        calendarDayDate.set(Calendar.HOUR, hour);
//        calendarDayDate.set(Calendar.MINUTE, minute);
//        calendarDayDate.set(Calendar.SECOND, second);
//
//        return calendarDayDate.getTime();
        
        return dayDate.atTime(hour, minute, second);
    }

    // TODO Replace By LocalDateTime
//    public static Date addDuration(Date date, int minute) {
    public static LocalDateTime addDuration(LocalDateTime date, int minute) {
//        Calendar calendarDate = Calendar.getInstance();
//        calendarDate.setTime(date);
//
//        calendarDate.add(Calendar.MINUTE, minute);
//
//        return calendarDate.getTime();
        
        return date.plusMinutes(minute);
    }

    // TODO Replace by LocalDateTime
    public static boolean dayAreEquals(LocalDateTime firstDateWithTime,
            LocalDateTime secondDateWithTime) {
//        Calendar calendarDay1 = Calendar.getInstance();
//        calendarDay1.setTime(firstDateWithTime);
//
//        Calendar calendarDay2 = Calendar.getInstance();
//        calendarDay2.setTime(secondDateWithTime);
//
//        return calendarDay1.get(Calendar.YEAR) == calendarDay2
//                .get(Calendar.YEAR)
//                && calendarDay1.get(Calendar.DAY_OF_YEAR) == calendarDay2
//                        .get(Calendar.DAY_OF_YEAR);
        
        return firstDateWithTime.toLocalDate().equals(secondDateWithTime.toLocalDate());
    }

    // TODO parse with LocalDateTime and use ZonedDateTime for conversion
    public static String convertToTimeZone(String dateWithTime,
            ZoneId timeZoneFrom, ZoneId timeZoneTo) {
//        try {
//            SimpleDateFormat parserFrom = new SimpleDateFormat(
//                    "dd/MM/yyyy HH:mm:ss");
//            parserFrom.setTimeZone(TimeZone.getTimeZone(timeZoneFrom));
//
//            Date date = parserFrom.parse(dateWithTime);
//
//            SimpleDateFormat formatWithTimeZone = new SimpleDateFormat(
//                    "dd/MM/yyyy HH:mm:ss");
//            formatWithTimeZone.setTimeZone(TimeZone.getTimeZone(timeZoneTo));
//
//            return formatWithTimeZone.format(date);
//
//        } catch (ParseException e) {
//            throw new IllegalArgumentException("bad format " + dateWithTime);
//        }
        return LocalDateTime
                .parse(dateWithTime, DATE_TIME_FORMATTER)
                .atZone(timeZoneFrom)
                .withZoneSameInstant(timeZoneTo)
                .format(DATE_TIME_FORMATTER);
    }
}
