package com.example.plantsproject;

import java.text.SimpleDateFormat;
import java.util.Date;

 class DateDefiner {
    private String format;

     DateDefiner(String format) {
        this.format = format;
    }

     String defineDate () {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        String strDate= formatter.format(date);
        return strDate;
    }

}
