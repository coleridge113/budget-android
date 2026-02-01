package com.luna.budgetapp.presentation.screen.addexpense.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import android.annotation.SuppressLint
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.MenuDefaults.itemColors
import androidx.compose.material3.MenuItemColors
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpensePresetDialog(
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier
) {

    BasicAlertDialog(
        onDismissRequest = onDismissRequest,
        modifier = modifier,
    ) {
        var categoryExpanded by remember { mutableStateOf(false) }
        var typeExpanded by remember { mutableStateOf(false) }
        var selectedCategory by remember { mutableStateOf("Selected Category")}

        Box(modifier = Modifier.wrapContentSize()) {
            OutlinedButton(onClick = { categoryExpanded = true }) {
                Text(selectedCategory)
                Icon(imageVector = Icons.Default.Add, contentDescription = null)
            }

            DropdownMenu(categoryExpanded, onDismissRequest) {
                DropdownMenuItem(
                    text = { Text("Food") },
                    onClick = {
                        selectedCategory = "Food"
                        categoryExpanded = false
                    }
                )
                DropdownMenuItem(
                    text = { Text("Commute") },
                    onClick = {
                        selectedCategory = "Commute"
                        categoryExpanded = false
                    }
                )
            }
        }
    }
}


@Composable
@SuppressLint("ComposableNaming")
fun DropdownMenu(
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    offset: DpOffset = DpOffset(0.dp, 0.dp),
    scrollState: ScrollState = rememberScrollState(),
    properties: PopupProperties = PopupProperties(focusable = true),
    content: @Composable ColumnScope.() -> Unit,
) =
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = onDismissRequest,
        modifier = modifier,
        offset = offset,
        scrollState = scrollState,
        properties = properties,
        shape = MenuDefaults.shape,
        containerColor = MenuDefaults.containerColor,
        tonalElevation = MenuDefaults.TonalElevation,
        shadowElevation = MenuDefaults.ShadowElevation,
        border = null,
        content = content,
    )

@Composable
fun DropdownMenuItem(
    text: @Composable () -> Unit,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    enabled: Boolean = true,
    colors: MenuItemColors = MenuDefaults.itemColors(),
    contentPadding: PaddingValues = MenuDefaults.DropdownMenuItemContentPadding,
    interactionSource: MutableInteractionSource? = null,
) {
    ListItem(
        headlineContent = text,
        modifier = modifier
            .clickable(
                enabled = enabled,
                onClick = onClick,
                interactionSource = interactionSource,
                indication = ripple()
            )
            .fillMaxWidth(),
        leadingContent = leadingIcon,
        trailingContent = trailingIcon,
        colors = ListItemDefaults.colors(
            containerColor = Color.Transparent,
            headlineColor = if (enabled) itemColors().textColor else itemColors().disabledTextColor,
            leadingIconColor = if (enabled) itemColors().leadingIconColor else itemColors().disabledLeadingIconColor
        )
    )
}

@Preview(
    showBackground = true,
    showSystemUi = true,
    device = Devices.PIXEL_7
)
@Composable
fun ExpensePresetDialogPreview() {
    Spacer(modifier = Modifier.height(50.dp))
    Box(modifier = Modifier.fillMaxSize()){
        ExpensePresetDialog(onDismissRequest = {})
    }
}
