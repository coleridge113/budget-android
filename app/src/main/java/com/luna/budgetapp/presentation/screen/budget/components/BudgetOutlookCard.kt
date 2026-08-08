package com.luna.budgetapp.presentation.screen.budget.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.tooling.preview.AndroidUiModes
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.luna.budgetapp.presentation.screen.budget.model.OutlookDetails
import com.luna.budgetapp.presentation.screen.utils.toCurrency
import com.luna.budgetapp.ui.theme.GruvboxOrange
import com.luna.budgetapp.ui.theme.GruvboxRed
import com.luna.budgetapp.ui.theme.LazyWalletTheme

@Composable
fun BudgetOutlookCard(
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
                        .weight(1.3f)
                        .padding(end = 12.dp),
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    StatRow(
                        modifier = Modifier.alpha(0f),
                        icon = Icons.Default.AccountBalanceWallet,
                        secondaryIcon = Icons.Default.ArrowUpward,
                        label = "Income",
                        value = details.income.toCurrency(),
                        color = MaterialTheme.colorScheme.secondary
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
                        color = MaterialTheme.colorScheme.error
                    )
                }

                // Right Section: Chart
                Box(
                    modifier = Modifier
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    OutlookChart(
                        actual = details.actualSpend,
                        projected = details.projectedSpend
                    )
                }
            }
        }
    }
}

@Composable
private fun StatRow(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    secondaryIcon: ImageVector,
    label: String,
    value: String,
    color: Color
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
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
        Column {
            Text(
                text = value,
                style = MaterialTheme.typography.titleMedium.copy(
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp
                ),
                maxLines = 1
            )
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall.copy(
                    fontWeight = FontWeight.Normal
                ),
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                maxLines = 1
            )
        }
    }
}

@Composable
private fun OutlookChart(
    actual: Long,
    projected: Long
) {
    val maxBase = maxOf(actual, projected).toFloat()
    val actualRatio = if (maxBase > 0) (actual.toFloat() / maxBase) else 0f
    val projectedRatio = if (maxBase > 0) (projected.toFloat() / maxBase) else 0f
    val maxArcAngle = 290f
    val arcStartAngle = -90f

    val textMeasurer = rememberTextMeasurer()
    val labelStyle = MaterialTheme.typography.labelSmall.copy(
        fontSize = 12.sp,
        fontWeight = FontWeight.SemiBold
    )
    val onSurfaceColor = MaterialTheme.colorScheme.onSurface

    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Canvas(
            modifier = Modifier
                .aspectRatio(1f)
                .padding(8.dp)
                .drawWithContent {
                    drawContent()

                    val strokeWidth = 14.dp.toPx()
                    val innerStrokeWidth = 12.dp.toPx()
                    val spacing = 8.dp.toPx()

                    // Measure text dimensions dynamically
                    val actualLayout = textMeasurer.measure("Actual", labelStyle)
                    val projectedLayout = textMeasurer.measure("Projected", labelStyle)

                    // Center x-coordinate where -90° arc starts
                    val topCenterX = size.width / 2f

                    // Outer ring top edge Y-coordinate
                    val outerRingTopY = -18f

                    // Inner ring top edge Y-coordinate
                    val innerRingTopY = strokeWidth + spacing - 16f

                    // 3. Draw "Actual" label anchored right above the Outer Ring start
                    drawText(
                        textLayoutResult = actualLayout,
                        color = onSurfaceColor.copy(alpha = 0.8f),
                        topLeft = Offset(
                            x = topCenterX - actualLayout.size.width - 12.dp.toPx(), // Sits just to the left of top-center
                            y = outerRingTopY - (actualLayout.size.height / 2f) + (strokeWidth / 2f)
                        )
                    )

                    // 4. Draw "Projected" label anchored right above the Inner Ring start
                    drawText(
                        textLayoutResult = projectedLayout,
                        color = onSurfaceColor.copy(alpha = 0.6f),
                        topLeft = Offset(
                            x = topCenterX - projectedLayout.size.width - 12.dp.toPx(),
                            y = innerRingTopY - (projectedLayout.size.height / 2f) + (innerStrokeWidth / 2f)
                        )
                    )
                }
        ) {
            val strokeWidth = 14.dp.toPx()
            val innerStrokeWidth = 12.dp.toPx()
            val spacing = 8.dp.toPx()

            // Outer Ring - Actual Background Track
            drawArc(
                color = GruvboxRed.copy(alpha = 0.2f),
                startAngle = arcStartAngle,
                sweepAngle = maxArcAngle,
                useCenter = false,
                style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
            )
            // Outer Ring - Actual Progress
            drawArc(
                color = GruvboxRed,
                startAngle = arcStartAngle,
                sweepAngle = maxArcAngle * actualRatio,
                useCenter = false,
                style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
            )

            // Inner Ring - Projected (Dimensions)
            val innerSize = size.copy(
                width = size.width - (strokeWidth + spacing) * 2,
                height = size.height - (strokeWidth + spacing) * 2
            )
            val innerOffset = Offset(
                (size.width - innerSize.width) / 2,
                (size.height - innerSize.height) / 2
            )

            // Inner Ring - Projected Background Track
            drawArc(
                color = GruvboxOrange.copy(alpha = 0.2f),
                startAngle = arcStartAngle,
                sweepAngle = maxArcAngle,
                useCenter = false,
                style = Stroke(width = innerStrokeWidth, cap = StrokeCap.Round),
                topLeft = innerOffset,
                size = innerSize
            )
            // Inner Ring - Projected Progress
            drawArc(
                color = GruvboxOrange,
                startAngle = arcStartAngle,
                sweepAngle = maxArcAngle * projectedRatio,
                useCenter = false,
                style = Stroke(width = innerStrokeWidth, cap = StrokeCap.Round),
                topLeft = innerOffset,
                size = innerSize
            )
        }
    }
}

@Preview(
    device = Devices.PIXEL_7,
    uiMode = AndroidUiModes.UI_MODE_NIGHT_NO
)
@Composable
fun BudgetOutlookCardPreview() {
    val details = OutlookDetails(
        income = 5_000_000L, // 50,000.00
        projectedSpend = 3_000_000L, // 30,000.00
        actualSpend = 3_500_000L // 35,000.00
    )
    LazyWalletTheme {
        Box(modifier = Modifier.padding(16.dp)) {
            BudgetOutlookCard(
                details = details
            )
        }
    }
}

@Preview(
    device = Devices.PIXEL_7,
    uiMode = AndroidUiModes.UI_MODE_NIGHT_NO
)
@Composable
fun StatRowPreview() {
    LazyWalletTheme {
        Surface(
            color = MaterialTheme.colorScheme.surface
        ) {
            StatRow(
                icon = Icons.Default.AccountBalanceWallet,
                secondaryIcon = Icons.Default.ArrowUpward,
                label = "Income",
                value = "3,000.00",
                color = MaterialTheme.colorScheme.secondary,
            )

        }
    }
}

@Preview(
    device = Devices.PIXEL_7,
    uiMode = AndroidUiModes.UI_MODE_NIGHT_NO
)
@Composable
fun OutlookChartPreview() {
    LazyWalletTheme {
        Surface(
            color = MaterialTheme.colorScheme.surface,
            modifier = Modifier.height(240.dp)
        ) {
            OutlookChart(
                actual = 35_000L,
                projected = 32_000L
            )
        }
    }
}
