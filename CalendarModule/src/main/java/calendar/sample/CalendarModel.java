package calendar.sample;

import java.util.concurrent.TimeUnit;

import static calendar.sample.CalendarModel.Type.EMPTY_ITEM;

public class CalendarModel {

    public enum Type {
        EMPTY_ITEM, ROW_TITLE_ITEM, COLUMN_TITLE_ITEM, ITEM
    }

    public Type type = EMPTY_ITEM;

    public boolean selected = false;

    public int minutes = 0;

    public String getTime() {
        switch (type) {
            case EMPTY_ITEM:
                return "-";
            case COLUMN_TITLE_ITEM:
                return String.format("%02d", minutes);
            default:
                long hours = TimeUnit.MINUTES.toHours(minutes);
                long min = minutes - TimeUnit.HOURS.toMinutes(hours);
                return String.format("%02d:%02d", hours, min);
        }
    }

    public int getMinutes() {
        return minutes;
    }
}
