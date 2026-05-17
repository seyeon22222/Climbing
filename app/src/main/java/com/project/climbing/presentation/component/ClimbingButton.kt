package com.project.climbing.presentation.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.project.climbing.core.ui.theme.BackgroundBlack
import com.project.climbing.core.ui.theme.MainOrange
import com.project.climbing.core.ui.theme.SurfaceDark
import com.project.climbing.core.ui.theme.TextWhite

@Composable
fun ClimbingPrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    Button(
        onClick = onClick,
        modifier =
            modifier
                .fillMaxWidth()
                .height(56.dp),
        enabled = enabled,
        shape = RoundedCornerShape(12.dp),
        colors =
            ButtonDefaults.buttonColors(
                containerColor = MainOrange,
                contentColor = TextWhite,
                disabledContainerColor = SurfaceDark,
                disabledContentColor = BackgroundBlack,
            ),
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge,
        )
    }
}

@Composable
fun ClimbingSecondaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    Button(
        onClick = onClick,
        modifier =
            modifier
                .fillMaxWidth()
                .height(56.dp),
        enabled = enabled,
        shape = RoundedCornerShape(12.dp),
        colors =
            ButtonDefaults.buttonColors(
                containerColor = SurfaceDark,
                contentColor = TextWhite,
                disabledContainerColor = SurfaceDark,
                disabledContentColor = BackgroundBlack,
            ),
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge,
        )
    }
}
