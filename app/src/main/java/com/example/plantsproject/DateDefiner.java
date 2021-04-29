package com.example.plantsproject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateDefiner {
    String formate;

    public DateDefiner(String formate) {
        this.formate = formate;
    }
    public String defineDate () {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat(formate);
        String strDate= formatter.format(date);
        return strDate;
    }

}
