package com.luna.budgetapp.presentation.screen.budget.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.Savings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.luna.budgetapp.presentation.screen.budget.model.OutlookDetails
import com.luna.budgetapp.presentation.screen.utils.toCurrency
import com.luna.budgetapp.ui.theme.GruvboxGreen
import com.luna.budgetapp.ui.theme.GruvboxOrange
import com.luna.budgetapp.ui.theme.GruvboxRed
import com.luna.budgetapp.ui.theme.LazyWalletTheme

@Composable
fun BudgetOutlookCard2(
    modifier: Modifier = Modifier,
    details: OutlookDetails
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        shape = RoundedCornerShape(28.dp),
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 8.dp
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.surface.copy(alpha = 0.8f),
                            Color.Black.copy(alpha = 0.4f)
                        )
                    )
                )
                .padding(24.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Left Section: Stats
                Column(
                    modifier = Modifier
                        .weight(1.3f) // FIX: Increased weight to give the large text more room
                        .padding(end = 12.dp),
                    verticalArrangement = Arrangement.spacedBy(20.dp) // FIX: Even, responsive spacing
                ) {
                    StatRow(
                        icon = Icons.Default.AccountBalanceWallet,
                        secondaryIcon = Icons.Default.ArrowUpward,
                        label = "Income",
                        value = details.income.toCurrency(),
                        color = GruvboxGreen
                    )

                    Column {
                        Text(
                            text = details.remaining.toCurrency(),
                            style = MaterialTheme.typography.displayMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface,
                                fontSize = 34.sp // FIX: Reduced slightly to fit single line securely
                            ),
                            maxLines = 1 // FIX: Force a single line
                        )
                        Text(
                            text = "Total Balance",
                            style = MaterialTheme.typography.bodyLarge.copy(
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                            )
                        )
                    }

                    StatRow(
                        icon = Icons.Default.ShoppingCart,
                        secondaryIcon = Icons.Default.ArrowDownward,
                        label = "Expenses",
                        value = details.actualSpend.toCurrency(),
                        color = GruvboxRed
                    )
                }

                // Right Section: Chart
                Box(
                    modifier = Modifier
                        .weight(1f) // Takes the remaining horizontal space
                        .aspectRatio(1f), // FIX: This mathematically guarantees a perfect circle!
                    contentAlignment = Alignment.Center
                ) {
                    OutlookChart(
                        actual = details.actualSpend,
                        projected = details.projectedSpend,
                        income = details.income
                    )

                    Icon(
                        imageVector = Icons.Default.Savings, // Piggy bank icon from image_9dd6d9.png
                        contentDescription = null,
                        modifier = Modifier.size(48.dp),
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
    }
}

@Composable
private fun StatRow(
    icon: ImageVector,
    secondaryIcon: ImageVector,
    label: String,
    value: String,
    color: Color
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(42.dp)
                .clip(CircleShape)
                .background(color.copy(alpha = 0.15f)),
            contentAlignment = Alignment.Center
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    modifier = Modifier.size(22.dp)
                        .offset(y = 4.dp),
                    tint = color
                )
                Icon(
                    imageVector = secondaryIcon,
                    contentDescription = null,
                    modifier = Modifier
                        .size(12.dp)
                        .offset(y = (-10).dp, x = (0).dp),
                    tint = color
                )
            }
        }
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = "$label: $value",
            style = MaterialTheme.typography.titleMedium.copy(
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp
            ),
            maxLines = 1
        )
    }
}

@Composable
private fun OutlookChart(
    actual: Long,
    projected: Long,
    income: Long
) {
    val actualRatio = if (income > 0) (actual.toFloat() / income).coerceAtMost(1f) else 0f
    val projectedRatio = if (income > 0) (projected.toFloat() / income).coerceAtMost(1f) else 0f

    Box(modifier = Modifier.fillMaxSize()) {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 28.dp, bottom = 4.dp, start = 8.dp) // FIX: Padding shifts the chart to make room for labels
        ) {
            val strokeWidth = 14.dp.toPx()
            val innerStrokeWidth = 12.dp.toPx()
            val spacing = 8.dp.toPx()

            // Outer Ring - Actual
            drawArc(
                color = GruvboxRed.copy(alpha = 0.2f),
                startAngle = 0f,
                sweepAngle = 360f,
                useCenter = false,
                style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
            )
            drawArc(
                color = GruvboxRed,
                startAngle = -90f, // Starts exact top-center
                sweepAngle = 360f * actualRatio,
                useCenter = false,
                style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
            )

            // Inner Ring - Projected
            val innerSize = size.copy(
                width = size.width - (strokeWidth + spacing) * 2,
                height = size.height - (strokeWidth + spacing) * 2
            )
            val innerOffset = androidx.compose.ui.geometry.Offset(
                (size.width - innerSize.width) / 2,
                (size.height - innerSize.height) / 2
            )

            drawArc(
                color = GruvboxOrange.copy(alpha = 0.2f),
                startAngle = 0f,
                sweepAngle = 360f,
                useCenter = false,
                style = Stroke(width = innerStrokeWidth, cap = StrokeCap.Round),
                topLeft = innerOffset,
                size = innerSize
            )
            drawArc(
                color = GruvboxOrange,
                startAngle = -90f,
                sweepAngle = 360f * projectedRatio,
                useCenter = false,
                style = Stroke(width = innerStrokeWidth, cap = StrokeCap.Round),
                topLeft = innerOffset,
                size = innerSize
            )
        }

        // FIX: Labels cleanly nested in the Top-Start quadrant outside the Canvas bounds
        Column(
            modifier = Modifier.align(Alignment.TopStart),
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = "Actual",
                style = MaterialTheme.typography.labelSmall.copy(
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold
                )
            )
            Text(
                text = "Projected",
                style = MaterialTheme.typography.labelSmall.copy(
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold
                )
            )
        }
    }
}

@Preview
@Composable
fun BudgetOutlookCardPreview2() {
    val details = OutlookDetails(
        income = 5_000_000L, // 50,000.00
        projectedSpend = 3_000_000L, // 30,000.00
        actualSpend = 3_500_000L // 35,000.00
    )
    LazyWalletTheme {
        Box(modifier = Modifier.padding(16.dp)) {
            BudgetOutlookCard2(
                details = details
            )
        }
    }
}
