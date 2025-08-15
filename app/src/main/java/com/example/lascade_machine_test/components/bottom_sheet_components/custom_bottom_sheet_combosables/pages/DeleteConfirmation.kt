package com.example.lascade_machine_test.components.bottom_sheet_components.custom_bottom_sheet_combosables.pages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lascade_machine_test.components.bottom_sheet_components.custom_bottom_sheet_combosables.CenteredMessage
import com.example.lascade_machine_test.components.bottom_sheet_components.custom_bottom_sheet_combosables.PageHeader

@Composable
fun DeleteConfirmationPage(
    screenWidth: androidx.compose.ui.unit.Dp,
    onCloseClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .width(screenWidth - 32.dp)
            .padding(vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        PageHeader(
            title = "Delete Route",
            onCloseClick = onCloseClick
        )

        Spacer(modifier = Modifier.height(24.dp))

        CenteredMessage("ARE YOU SURE?")

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = onDeleteClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Red
            ),
            shape = RoundedCornerShape(30.dp)
        ) {
            Text(
                text = "Delete",
                color = Color.White,
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(20.dp))
    }
}