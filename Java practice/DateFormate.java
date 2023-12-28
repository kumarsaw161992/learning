package it.sella.fabrickpfm.movements.statement.generator;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormate {
    public static final String YYYY_MM_DD = "yyyy-MM-dd";
    public static final String DD_MM_YYYY = "dd/MM/yyyy";

    public static  void  main(String args[]){
        System.out.println(getFormatedDateString("2020-05-29"));
    }

    private static String getFormatedDateString(String date) {
        return getStringFromDate(getDateFromString(date, YYYY_MM_DD), DD_MM_YYYY);
    }

    public static Date getDateFromString(String date, String format) {
        if (StringUtils.isEmpty(date)) return null;
        try {
            DateTimeFormatter formatter = DateTimeFormat.forPattern(format);
            DateTime dt = formatter.parseDateTime(date);
            return dt.toDate();
        } catch(Exception e) {
            System.out.println("getDateFromString: Unable to parse date:" + e.getMessage());
        }
        return null;
    }

    public static String getStringFromDate(Date date, String pattern) {
        if (date == null) return "";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(date);
    }
}
