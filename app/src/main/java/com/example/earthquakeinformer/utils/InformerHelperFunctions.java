package com.example.earthquakeinformer.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class InformerHelperFunctions {
    public static String getFormattedDateTime(long date, String format) {
        return new SimpleDateFormat(format)
                .format(new Date(date * 1000L));
    }
}
