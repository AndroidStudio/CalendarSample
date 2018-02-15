package calendar.sample;

import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class CalendarProvider {

    int columnSize;
    int rowSize;

    public ArrayList<CalendarModel> getCalendarList(Calendar dateFrom, Calendar dateTo, int interval)
            throws Exception {
        ArrayList<CalendarModel> calendarList = new ArrayList<>();


        rowSize = getRowSize(dateFrom, dateTo);
        columnSize = getColumnSize(interval);

        int startMinutes = (int) TimeUnit.HOURS.toMinutes(dateFrom.get(Calendar.HOUR_OF_DAY));
        int increasedInterval = startMinutes;

        for (int row = 0; row < rowSize; row++) {
            for (int column = 0; column < columnSize; column++) {
                CalendarModel calendarModel = new CalendarModel();
                int minutes = 0;

                if (row == 0) {
                    if (column == 0) {
                        calendarModel.type = CalendarModel.Type.EMPTY_ITEM;
                    } else {
                        calendarModel.type = CalendarModel.Type.COLUMN_TITLE_ITEM;
                        minutes = interval * (column - 1);
                    }
                } else {
                    if (column == 0) {
                        calendarModel.type = CalendarModel.Type.ROW_TITLE_ITEM;
                        minutes = increasedInterval;
                    } else {
                        calendarModel.type = CalendarModel.Type.ITEM;
                        minutes = increasedInterval;
                        increasedInterval += interval;
                    }
                }

                calendarModel.minutes = minutes;
                calendarList.add(calendarModel);
            }
        }

        return calendarList;
    }

    private int getColumnSize(int interval) {
        int size = 60 / interval + 1;
        Log.d("getColumnSize", String.valueOf(size));
        return size;
    }


    private int getRowSize(Calendar dateFrom, Calendar dateTo) throws Exception {
        long endMinutes = TimeUnit.HOURS.toMinutes(dateTo.get(Calendar.HOUR_OF_DAY));
        long startMinutes = TimeUnit.HOURS.toMinutes(dateFrom.get(Calendar.HOUR_OF_DAY));

        long size = (endMinutes - startMinutes) / 60 + 1;
        Log.d("getRowSize", String.valueOf(size));
        return (int) size;
    }
}
