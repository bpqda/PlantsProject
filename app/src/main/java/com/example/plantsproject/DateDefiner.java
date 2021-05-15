package com.example.plantsproject;

import java.text.SimpleDateFormat;
import java.util.Date;

 class DateDefiner {
    String formate;

     DateDefiner(String formate) {
        this.formate = formate;
    }
     String defineDate () {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat(formate);
        String strDate= formatter.format(date);
        return strDate;
    }

}
