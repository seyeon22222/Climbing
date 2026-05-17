package com.project.climbing.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.project.climbing.core.ui.theme.MainOrange
import com.project.climbing.core.ui.theme.SurfaceDark
import com.project.climbing.core.ui.theme.TextGrey
import com.project.climbing.core.ui.theme.TextWhite

@Composable
fun ClimbingChip(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val backgroundColor = if (isSelected) MainOrange else SurfaceDark
    val contentColor = if (isSelected) TextWhite else TextGrey

    Box(
        modifier =
            modifier
                .clip(RoundedCornerShape(20.dp))
                .background(backgroundColor)
                .clickable(onClick = onClick)
                .padding(horizontal = 16.dp, vertical = 8.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = text,
            color = contentColor,
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}
