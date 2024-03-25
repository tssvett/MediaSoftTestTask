package com.warehousesystem.app.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.time.LocalDateTime;

@Converter(autoApply = true)
public class DateConverter implements AttributeConverter<java.time.LocalDateTime, String> {
    @Override
    public String convertToDatabaseColumn(java.time.LocalDateTime dateTime) {
        if (dateTime == null) {
            return null;
        }
        return dateTime.format(java.time.format.DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"));
    }


    @Override
    public LocalDateTime convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        String[] rawData = dbData.split(" ");
        String[] dateData = rawData[0].split("\\.");
        String[] timeData = rawData[1].split(":");
        String[] secondsData = timeData[2].split("\\.");
        int year = Integer.parseInt(dateData[0]);
        int month = Integer.parseInt(dateData[1]);
        int day = Integer.parseInt(dateData[2]);
        int hour = Integer.parseInt(timeData[0]);
        int minute = Integer.parseInt(timeData[1]);
        int seconds = Integer.parseInt(secondsData[0]);
        return LocalDateTime.of(day, month, year, hour, minute, seconds);
    }
}

