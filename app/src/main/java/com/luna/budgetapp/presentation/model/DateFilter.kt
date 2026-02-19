package com.luna.budgetapp.presentation.model

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.temporal.WeekFields
import java.util.Locale

enum class DateFilter(val value: String) {
    DAILY("Daily"),
    WEEKLY("Weekly"),
    MONTHLY("Monthly")
}

fun DateFilter.resolve(
    now: LocalDate = LocalDate.now(),
    locale: Locale = Locale.getDefault()
): Pair<LocalDateTime, LocalDateTime> {

    return when (this) {

        DateFilter.DAILY -> {
            val start = now.atStartOfDay()
            val end = now.atTime(LocalTime.MAX)
            start to end
        }

        DateFilter.WEEKLY -> {
            val weekFields = WeekFields.of(locale)

            val startOfWeek = now.with(weekFields.dayOfWeek(), 1)
            val endOfWeek = now.with(weekFields.dayOfWeek(), 7)

            startOfWeek.atStartOfDay() to
                    endOfWeek.atTime(LocalTime.MAX)
        }

        DateFilter.MONTHLY -> {
            val start = now.withDayOfMonth(1)
            val end = now.withDayOfMonth(now.lengthOfMonth())

            start.atStartOfDay() to
                    end.atTime(LocalTime.MAX)
        }
    }
}
