package calendar.sample;


import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;

public class GraphView extends RelativeLayout {

    CalendarProvider calendarProvider = new CalendarProvider();
    ArrayList<CalendarModel> calendarList = new ArrayList<>();

    public GraphView(Context context, AttributeSet attrs) {
        super(context, attrs);

        Calendar dateFrom = Calendar.getInstance();
        dateFrom.set(0, 0, 0, 8, 0);

        Calendar dateTo = Calendar.getInstance();
        dateTo.set(0, 0, 0, 22, 0);

        try {
            calendarList = calendarProvider.getCalendarList(dateFrom, dateTo, 30);
            for (int i = 0; i < calendarList.size(); i++) {
                CalendarModel calendarModel = calendarList.get(i);
                Log.d("TIME", calendarModel.getTime());
            }

            for (int i = 0; i < calendarList.size(); i++) {
                CalendarModel calendarModel = calendarList.get(i);
                Log.d("MINUTES", "" + calendarModel.getMinutes());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    void addItems() {
        int columnSize = calendarProvider.columnSize;
        int rowSize = calendarProvider.rowSize;

        int screenWidth = getWidth();
        int itemHeight = (int) getResources().getDimension(R.dimen.item_size);//50dp
        int margin = (int) getResources().getDimension(R.dimen.item_margin);//5dp
        int firstColumnSize = (int) getResources().getDimension(R.dimen.first_column_size);
        int position = 0;
        int itemWidth;

        for (int row = 0; row < rowSize; row++) {
            for (int column = 0; column < columnSize; column++) {
                RelativeLayout.LayoutParams params = null;

                CalendarView calendarView = null;
                CalendarModel calendarModel = calendarList.get(position);

                int y = row * itemHeight + row * margin * 2;
                int x = 0;
                switch (calendarModel.type) {
                    case EMPTY_ITEM:
                        itemWidth = firstColumnSize;
                        params = new RelativeLayout.LayoutParams(itemWidth, itemHeight);
                        params.setMargins(margin, margin, margin, margin);
                        calendarView = new EmptyView(getContext());
                        break;
                    case ROW_TITLE_ITEM:
                        itemWidth = firstColumnSize;
                        params = new RelativeLayout.LayoutParams(itemWidth, itemHeight);
                        params.setMargins(margin, margin, margin, margin);
                        calendarView = new RowTitleView(getContext());
                        break;
                    case COLUMN_TITLE_ITEM:
                        itemWidth = (screenWidth - firstColumnSize - columnSize * margin * 2) / (columnSize-1);
                        params = new RelativeLayout.LayoutParams(itemWidth, itemHeight);
                        params.setMargins(margin, margin, margin, margin);
                        calendarView = new ColumnTitleView(getContext());
                        x = firstColumnSize + (margin * 2) + ((itemWidth + margin * 2) * (column - 1));
                        break;
                    case ITEM:
                        itemWidth = (screenWidth - firstColumnSize - columnSize * margin * 2) / (columnSize-1);
                        params = new RelativeLayout.LayoutParams(itemWidth, itemHeight);
                        params.setMargins(margin, margin, margin, margin);
                        calendarView = new ItemView(getContext());
                        x = firstColumnSize + (margin * 2) + ((itemWidth + margin * 2) * (column - 1));
                        break;
                }

                calendarView.set(calendarModel);
                View v = calendarView.getView();
                params.leftMargin = x + margin;
                params.topMargin = y + margin;
                addView(v, params);
                position++;
            }
        }
    }

    class CalendarView {

        CalendarModel calendarModel;

        public void set(CalendarModel categoryModel) {
            this.calendarModel = categoryModel;
        }

        public View getView() {
            return null;
        }
    }

    class EmptyView extends CalendarView {
        TextView textView;

        EmptyView(Context context) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_view, null, false);
            textView = view.findViewById(R.id.textView);
        }

        @Override
        public View getView() {
            return textView;
        }
    }

    class RowTitleView extends CalendarView {
        TextView textView;

        RowTitleView(Context context) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_view, null, false);
            textView = view.findViewById(R.id.textView);
        }

        @Override
        public void set(CalendarModel categoryModel) {
            super.set(categoryModel);
            textView.setText(categoryModel.getTime());
        }

        @Override
        public View getView() {
            return textView;
        }
    }

    class ColumnTitleView extends CalendarView {

        TextView textView;

        ColumnTitleView(Context context) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_view, null, false);
            textView = view.findViewById(R.id.textView);
        }

        @Override
        public void set(CalendarModel categoryModel) {
            super.set(categoryModel);
            textView.setText(categoryModel.getTime());
        }

        @Override
        public View getView() {
            return textView;
        }
    }

    class ItemView extends CalendarView {

        TextView textView;

        ItemView(Context context) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_view, null, false);
            textView = view.findViewById(R.id.textView);
        }

        @Override
        public void set(CalendarModel categoryModel) {
            super.set(categoryModel);
            if (calendarModel.selected) {
                textView.setTextColor(Color.WHITE);
                textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
                textView.setBackgroundColor(Color.parseColor("#46c3b1"));
                textView.setText(calendarModel.getTime());

            } else {
                textView.setTextColor(Color.parseColor("#46c3b1"));
                textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 30);
                textView.setBackgroundColor(Color.WHITE);
                textView.setText("+");
            }

            textView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    calendarModel.selected = !calendarModel.selected;
                    removeAllViews();
                    addItems();
                }
            });
        }

        @Override
        public View getView() {
            return textView;
        }
    }
}
