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

        // 프로필 이미지 영역
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
                        .clickable(enabled = uiState.isEditing) { viewModel.onImageEditClick() },
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null,
                    modifier = Modifier.size(70.dp),
                    tint = TextGrey,
                )
            }

            if (uiState.isEditing) {
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

        Spacer(modifier = Modifier.height(24.dp))

        // 닉네임 영역
        if (uiState.isEditing) {
            OutlinedTextField(
                value = uiState.nicknameInput,
                onValueChange = viewModel::onNicknameChange,
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
                    text = uiState.user?.nickname ?: "사용자",
                    style = MaterialTheme.typography.headlineMedium,
                    color = TextWhite,
                )
                Spacer(modifier = Modifier.width(8.dp))
                IconButton(onClick = viewModel::onEditClick) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit Profile",
                        tint = TextGrey,
                        modifier = Modifier.size(20.dp),
                    )
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        // 버튼 영역
        if (uiState.isEditing) {
            Row(modifier = Modifier.fillMaxWidth()) {
                Box(modifier = Modifier.weight(1f)) {
                    ClimbingSecondaryButton(
                        text = "취소",
                        onClick = viewModel::onCancelClick,
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Box(modifier = Modifier.weight(1f)) {
                    ClimbingPrimaryButton(
                        text = "저장",
                        onClick = viewModel::onSaveClick,
                    )
                }
            }
        } else {
            ClimbingSecondaryButton(
                text = "로그아웃",
                onClick = viewModel::onLogoutClick,
            )
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}
