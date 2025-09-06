package com.knitting.diary.utils;

import java.time.LocalDate;

public class DateUtil {
    public LocalDate stringToLocalDate(String dateString) {
        return LocalDate.parse(dateString);
    }
}
