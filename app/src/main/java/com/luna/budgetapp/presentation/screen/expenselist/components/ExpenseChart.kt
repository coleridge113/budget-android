package com.luna.budgetapp.presentation.screen.expenselist.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.luna.budgetapp.presentation.model.ChartData
import com.luna.budgetapp.presentation.screen.utils.formatToPercentage

@Composable
fun ExpenseChart(
    modifier: Modifier = Modifier,
    chartDataList: List<ChartData>,
    totalAmount: Double
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        ExpenseDonutChart(
            chartDataList = chartDataList,
            totalAmount = totalAmount,
            modifier = Modifier.fillMaxWidth(0.6f)
                .offset(x = 16.dp)
        )
        Spacer(modifier = Modifier.weight(1f))
        ExpenseChartLegends(
            chartDataList = chartDataList,
            totalAmount = totalAmount
        )
    }

}

@Composable
fun ExpenseDonutChart(
    modifier: Modifier = Modifier,
    totalAmount: Double,
    chartDataList: List<ChartData>
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.aspectRatio(1f)
    ) {

        Canvas(
            modifier = Modifier.fillMaxSize()
        ) {
            if (totalAmount == 0.0) return@Canvas

            var startAngle = -90f

            chartDataList.forEach { slice ->
                val sweepAngle =
                    ((slice.value / totalAmount) * 360.0).toFloat()

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
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(6.dp),
        modifier = modifier
    ) {
        items(chartDataList) { item ->
            val portion = item.value / totalAmount
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .background(
                            color = item.color,
                            shape = CircleShape
                        )
                )

                Spacer(modifier = Modifier.width(6.dp))

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
fun ExpenseDonutChartPreview() {
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
        ),
        ChartData(
            category = "Grocery",
            value = 140.0
        ),
    )
    Column(
        verticalArrangement = Arrangement.spacedBy(32.dp),
    ) {
        ExpenseDonutChart(
            totalAmount = totalAmount,
            chartDataList = chartDataList
        )

        ExpenseChartLegends(
            totalAmount = totalAmount,
            chartDataList = chartDataList
        )
    }
}
