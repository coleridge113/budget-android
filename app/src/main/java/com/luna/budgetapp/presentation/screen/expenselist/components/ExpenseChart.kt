package com.luna.budgetapp.presentation.screen.expenselist.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.luna.budgetapp.presentation.model.ChartData
import com.luna.budgetapp.presentation.screen.utils.formatToPercentage
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices.PIXEL_7
import androidx.compose.ui.unit.dp

@Composable
fun ExpenseChart(
    modifier: Modifier = Modifier,
    totalAmount: Double,
    chartDataList: List<ChartData>
) {
    Box(
        modifier = modifier.aspectRatio(1f),
        contentAlignment = Alignment.Center
    ) {

        Canvas(
            modifier = Modifier.fillMaxSize()
        ) {
            val total = chartDataList.sumOf { it.value }

            if (total == 0.0) return@Canvas

            var startAngle = -90f

            chartDataList.forEach { slice ->
                val sweepAngle =
                    ((slice.value / total) * 360.0).toFloat()

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

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Total",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Text(
                text = "₱%,.2f".format(totalAmount),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun ExpenseChartLegends(
    modifier: Modifier = Modifier,
    chartDataList: List<ChartData>,
    totalAmount: Double
) {
    LazyVerticalGrid(
        modifier = modifier.fillMaxWidth(),
        columns = GridCells.Fixed(3),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(chartDataList) { item ->
            val portion = item.value / totalAmount
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "${portion.formatToPercentage()} ${item.category}",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

@Preview
@Composable
fun ExpenseChartPreview() {
    val totalAmount = 240.0
    val chartDataList = listOf(
        ChartData(
            category = "Food",
            value = 100.0
        ),
        ChartData(
            category = "Beverage",
            value = 140.0
        ),
        ChartData(
            category = "Commute",
            value = 140.0
        )
    )
    Column(
        verticalArrangement = Arrangement.spacedBy(32.dp),
    ) {
        ExpenseChart(
            totalAmount = totalAmount,
            chartDataList = chartDataList
        )

        ExpenseChartLegends(
            totalAmount = totalAmount,
            chartDataList = chartDataList
        )
    }
}
