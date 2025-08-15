package com.example.lascade_machine_test.components.bottom_sheet_components.custom_bottom_sheet_combosables.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.lascade_machine_test.components.bottom_sheet_components.custom_bottom_sheet_combosables.ActionButton
import com.example.lascade_machine_test.components.bottom_sheet_components.custom_bottom_sheet_combosables.PageHeader

@Composable
fun RouteSettingsPage(
    screenWidth: androidx.compose.ui.unit.Dp,
    onCloseClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onSaveClick: () -> Unit,
    onLoadClick: () -> Unit
) {
    Column(
        modifier = Modifier.width(screenWidth - 32.dp)
    ) {
        PageHeader(
            title = "Route settings",
            onCloseClick = onCloseClick
        )

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            ActionButton(
                text = "Delete",
                onClick = onDeleteClick,
                backgroundColor = Color.Red
            )
            ActionButton(
                text = "Save",
                onClick = onSaveClick,
                backgroundColor = Color.DarkGray
            )
            ActionButton(
                text = "Load",
                onClick = onLoadClick,
                backgroundColor = Color.DarkGray
            )
        }

        Spacer(modifier = Modifier.height(20.dp))
    }
}
