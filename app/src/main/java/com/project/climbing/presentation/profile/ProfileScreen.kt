package com.project.climbing.presentation.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.project.climbing.core.ui.theme.MainOrange
import com.project.climbing.core.ui.theme.SurfaceDark
import com.project.climbing.core.ui.theme.TextGrey
import com.project.climbing.core.ui.theme.TextWhite
import com.project.climbing.presentation.component.ClimbingPrimaryButton
import com.project.climbing.presentation.component.ClimbingSecondaryButton

@Composable
fun ProfileScreen(viewModel: ProfileViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
    ) {
        Spacer(modifier = Modifier.height(40.dp))

        ProfileImageSection(
            isEditing = uiState.isEditing,
            onImageEditClick = viewModel::onImageEditClick,
        )

        Spacer(modifier = Modifier.height(24.dp))

        NicknameSection(
            isEditing = uiState.isEditing,
            nicknameInput = uiState.nicknameInput,
            userNickname = uiState.user?.nickname,
            onNicknameChange = viewModel::onNicknameChange,
            onEditClick = viewModel::onEditClick,
        )

        Spacer(modifier = Modifier.weight(1f))

        ProfileActions(
            isEditing = uiState.isEditing,
            onCancelClick = viewModel::onCancelClick,
            onSaveClick = viewModel::onSaveClick,
            onLogoutClick = viewModel::onLogoutClick,
        )

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
private fun ProfileImageSection(
    isEditing: Boolean,
    onImageEditClick: () -> Unit,
) {
    Box(
        modifier = Modifier.size(120.dp),
        contentAlignment = Alignment.BottomEnd,
    ) {
        Box(
            modifier =
                Modifier
                    .fillMaxSize()
                    .clip(CircleShape)
                    .background(SurfaceDark)
                    .clickable(enabled = isEditing) { onImageEditClick() },
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = null,
                modifier = Modifier.size(70.dp),
                tint = TextGrey,
            )
        }

        if (isEditing) {
            Box(
                modifier =
                    Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(MainOrange)
                        .padding(8.dp),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    imageVector = Icons.Default.CameraAlt,
                    contentDescription = "Change Image",
                    tint = TextWhite,
                    modifier = Modifier.size(20.dp),
                )
            }
        }
    }
}

@Composable
private fun NicknameSection(
    isEditing: Boolean,
    nicknameInput: String,
    userNickname: String?,
    onNicknameChange: (String) -> Unit,
    onEditClick: () -> Unit,
) {
    if (isEditing) {
        OutlinedTextField(
            value = nicknameInput,
            onValueChange = onNicknameChange,
            modifier = Modifier.fillMaxWidth(),
            label = { Text("닉네임", color = TextGrey) },
            singleLine = true,
            colors =
                OutlinedTextFieldDefaults.colors(
                    focusedTextColor = TextWhite,
                    unfocusedTextColor = TextWhite,
                    focusedBorderColor = MainOrange,
                    unfocusedBorderColor = SurfaceDark,
                    cursorColor = MainOrange,
                ),
        )
    } else {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            Text(
                text = userNickname ?: "사용자",
                style = MaterialTheme.typography.headlineMedium,
                color = TextWhite,
            )
            Spacer(modifier = Modifier.width(8.dp))
            IconButton(onClick = onEditClick) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit Profile",
                    tint = TextGrey,
                    modifier = Modifier.size(20.dp),
                )
            }
        }
    }
}

@Composable
private fun ProfileActions(
    isEditing: Boolean,
    onCancelClick: () -> Unit,
    onSaveClick: () -> Unit,
    onLogoutClick: () -> Unit,
) {
    if (isEditing) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Box(modifier = Modifier.weight(1f)) {
                ClimbingSecondaryButton(
                    text = "취소",
                    onClick = onCancelClick,
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Box(modifier = Modifier.weight(1f)) {
                ClimbingPrimaryButton(
                    text = "저장",
                    onClick = onSaveClick,
                )
            }
        }
    } else {
        ClimbingSecondaryButton(
            text = "로그아웃",
            onClick = onLogoutClick,
        )
    }
}
