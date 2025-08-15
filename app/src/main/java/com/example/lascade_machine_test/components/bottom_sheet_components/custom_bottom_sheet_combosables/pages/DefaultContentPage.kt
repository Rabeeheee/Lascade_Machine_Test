package com.example.lascade_machine_test.components.bottom_sheet_components.custom_bottom_sheet_combosables.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lascade_machine_test.components.bottom_sheet_components.custom_bottom_sheet_combosables.CircularIconButton
import com.example.lascade_machine_test.components.bottom_sheet_components.custom_bottom_sheet_combosables.PrimaryButton

@Composable
fun DefaultContentPage(
    screenWidth: androidx.compose.ui.unit.Dp,
    onMenuClick: () -> Unit,
    onAddClick: () -> Unit
) {
    Column(
        modifier = Modifier.width(screenWidth - 32.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            CircularIconButton(
                icon = Icons.Filled.MoreVert,
                contentDescription = "Menu",
                onClick = onMenuClick
            )

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Kochi - Los Angeles",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = "2 Points",
                    color = Color.Gray,
                    fontSize = 12.sp
                )
            }

            CircularIconButton(
                icon = Icons.Filled.Add,
                contentDescription = "Add Location",
                onClick = onAddClick
            )
        }

        Spacer(modifier = Modifier.height(15.dp))

        PrimaryButton(
            text = "Create video",
            onClick = { /* Handle Create Video */ }
        )

        Spacer(modifier = Modifier.height(20.dp))
    }
}