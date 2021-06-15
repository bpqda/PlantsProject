package com.example.plantsproject.notifications;

import android.content.Context;
import android.content.res.Resources;

import com.example.plantsproject.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateDefiner {

    //private String format;

    //public DateDefiner(String format) {
    //   this.format = format;
    //}

    //public String defineDate () {
    //    Date date = new Date();
    //    SimpleDateFormat formatter = new SimpleDateFormat(format);
    //    String strDate= formatter.format(date);
    //    return strDate;
    //}
     Context ctx;
     boolean onlyDate;

    public DateDefiner(Context ctx, boolean onlyDate) {
        this.ctx = ctx;
        this.onlyDate = onlyDate;
    }

    public String defineDate() {

        Date currentDate = Calendar.getInstance().getTime();

        java.text.DateFormat dateFormat;
        dateFormat = android.text.format.DateFormat.getDateFormat(ctx);

        java.text.DateFormat timeFormat;
        timeFormat = android.text.format.DateFormat.getTimeFormat(ctx);

        String formattedDate = dateFormat.format(currentDate);
        String formattedTime = timeFormat.format(currentDate);
        if (onlyDate) {
            return formattedDate;
        }
            else{
                return ctx.getResources().getString(R.string.date) + formattedDate + "\n" +
                        ctx.getResources().getString(R.string.time) + formattedTime;
            }
        }
    }

