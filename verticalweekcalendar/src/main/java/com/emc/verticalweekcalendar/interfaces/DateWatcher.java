package com.emc.verticalweekcalendar.interfaces;

import com.emc.verticalweekcalendar.controller.VerticalWeekAdapter;

public interface DateWatcher {
    int getStateForDate(int position, int year, int month, int day, VerticalWeekAdapter.DayViewHolder view);
}
