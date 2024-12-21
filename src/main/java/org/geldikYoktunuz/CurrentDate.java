package org.geldikYoktunuz;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CurrentDate {

    public static LocalDate currentDate = LocalDate.now();

    public static String getCurrentDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return currentDate.format(formatter);
    }

    public static void passDay() {
        currentDate = currentDate.plusDays(1);
    }
}