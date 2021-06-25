package com.example.plantsproject.notifications;

import android.content.Context;
import android.content.res.Resources;

import com.example.plantsproject.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateDefiner {

    Context ctx;

    public DateDefiner(Context ctx) {
        this.ctx = ctx;
    }

    public String defineDate(long date) {

        Date currentDate = new Date(date);

        java.text.DateFormat dateFormat;
        dateFormat = android.text.format.DateFormat.getDateFormat(ctx);

        java.text.DateFormat timeFormat;
        timeFormat = android.text.format.DateFormat.getTimeFormat(ctx);

        String formattedDate = dateFormat.format(currentDate);
        String formattedTime = timeFormat.format(currentDate);


        return ctx.getResources().getString(R.string.date) + formattedDate + "\n" +
                ctx.getResources().getString(R.string.time) + formattedTime;

    }

    public String defineDate() {

        Date currentDate = Calendar.getInstance().getTime();

        java.text.DateFormat dateFormat;
        dateFormat = android.text.format.DateFormat.getDateFormat(ctx);

        String formattedDate = dateFormat.format(currentDate);


        return formattedDate;

    }
}

