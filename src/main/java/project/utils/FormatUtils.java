package project.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FormatUtils {
    /**
     * Переводим дату из формата dd.MM.yyyy в формат yyyy-MM-dd
     *
     * @param dateStr дата
     * @return отформатированная строка с датой
     */
    public static String convertDateFormat(String dateStr) {
        SimpleDateFormat inputFormat = new SimpleDateFormat("dd.MM.yyyy");
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = inputFormat.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return outputFormat.format(date);
    }
}
