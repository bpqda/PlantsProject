package com.example.plantsproject.notifications;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateDefiner {
    private String format;

    public DateDefiner(String format) {
        this.format = format;
    }

    public String defineDate () {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        String strDate= formatter.format(date);
        return strDate;
    }

}
