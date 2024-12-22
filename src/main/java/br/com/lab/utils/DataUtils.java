package br.com.lab.utils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class DataUtils {

    public static LocalDateTime toLocalDateTime(Date data) {
        return data.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }
}
