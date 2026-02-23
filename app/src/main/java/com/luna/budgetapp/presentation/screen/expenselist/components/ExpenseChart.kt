package com.luna.budgetapp.presentation.screen.expenselist.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.luna.budgetapp.presentation.model.ChartData
import com.luna.budgetapp.presentation.model.CategoryOptions
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.aspectRatio

@Composable
fun ExpenseChart(
    modifier: Modifier = Modifier,
    chartData: List<ChartData>
) {
    Canvas(
        modifier = modifier.aspectRatio(1f)
    ) {
        val total = chartData.sumOf { it.value }
        var startAngle = -90f

        chartData.forEach { slice ->
            val sweepAngle = ((slice.value / total) * 360.0).toFloat()

            drawArc(
                color = slice.color,
                startAngle = startAngle,
                sweepAngle = sweepAngle,
                useCenter = false,
                style = Stroke(width = 80f)
            )

            startAngle += sweepAngle
        }
    }
}

@Preview
@Composable
fun ExpenseChartPreview() {
    val chartData = listOf(
        ChartData(
            category = "Food",
            value = 100.0
        ),
        ChartData(
            category = "Beverage",
            value = 140.0
        )
    )
    ExpenseChart(
        chartData = chartData 
    )
}
