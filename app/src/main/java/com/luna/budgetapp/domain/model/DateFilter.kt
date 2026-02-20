package com.luna.budgetapp.domain.model

import java.time.temporal.WeekFields
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.Locale

sealed class DateFilter {

    abstract fun resolve(
        now: LocalDate = LocalDate.now(),
        locale: Locale = Locale.getDefault()
    ): DateRange

    data object Daily : DateFilter() {
        override fun resolve(now: LocalDate, locale: Locale) =
            DateRange(
                start = now.atStartOfDay(),
                end = now.atTime(LocalTime.MAX)
            )
    }

    data object Weekly : DateFilter() {
        override fun resolve(now: LocalDate, locale: Locale): DateRange {
            val weekFields = WeekFields.of(locale)

            val start = now.with(weekFields.dayOfWeek(), 1)
            val end = now.with(weekFields.dayOfWeek(), 7)

            return DateRange(
                start = start.atStartOfDay(),
                end = end.atTime(LocalTime.MAX)
            )
        }
    }

    data object Monthly : DateFilter() {
        override fun resolve(now: LocalDate, locale: Locale) =
            DateRange(
                start = now.withDayOfMonth(1).atStartOfDay(),
                end = now.withDayOfMonth(now.lengthOfMonth()).atTime(LocalTime.MAX)
            )
    }

    data class Custom(
        val start: LocalDateTime,
        val end: LocalDateTime
    ) : DateFilter() {
        override fun resolve(now: LocalDate, locale: Locale) =
            DateRange(start, end)
    }
}
