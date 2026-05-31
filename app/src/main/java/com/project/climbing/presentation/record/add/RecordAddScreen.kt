package com.project.climbing.presentation.record.add

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.project.climbing.domain.model.Difficulty
import com.project.climbing.presentation.component.ClimbingPrimaryButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecordAddScreen(
    onBackClick: () -> Unit,
    onSaveSuccess: () -> Unit,
    viewModel: RecordAddViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(uiState.isSaved) {
        if (uiState.isSaved) {
            onSaveSuccess()
        }
    }

    Scaffold(
        topBar = {
            RecordAddTopBar(onBackClick = onBackClick)
        },
        containerColor = Color.Black,
    ) { paddingValues ->
        RecordAddContent(
            uiState = uiState,
            paddingValues = paddingValues,
            onDifficultySelected = viewModel::onDifficultySelected,
            onMemoChanged = viewModel::onMemoChanged,
            onSaveClick = viewModel::saveRecord,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RecordAddTopBar(onBackClick: () -> Unit) {
    TopAppBar(
        title = { Text(text = "기록 추가") },
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                )
            }
        },
        colors =
            TopAppBarDefaults.topAppBarColors(
                containerColor = Color.Black,
                titleContentColor = Color.White,
                navigationIconContentColor = Color.White,
            ),
    )
}

@Composable
private fun RecordAddContent(
    uiState: RecordAddUiState,
    paddingValues: PaddingValues,
    onDifficultySelected: (Difficulty) -> Unit,
    onMemoChanged: (String) -> Unit,
    onSaveClick: () -> Unit,
) {
    Box(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(paddingValues),
    ) {
        if (uiState.isLoading && uiState.gym == null) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                color = Color.White,
            )
        } else {
            uiState.gym?.let {
                RecordAddForm(
                    uiState = uiState,
                    onDifficultySelected = onDifficultySelected,
                    onMemoChanged = onMemoChanged,
                    onSaveClick = onSaveClick,
                )
            }
        }
    }
}

@Composable
private fun RecordAddForm(
    uiState: RecordAddUiState,
    onDifficultySelected: (Difficulty) -> Unit,
    onMemoChanged: (String) -> Unit,
    onSaveClick: () -> Unit,
) {
    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(16.dp),
    ) {
        Text(
            text = uiState.gym?.name ?: "",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = Color.White,
        )
        Spacer(modifier = Modifier.height(24.dp))

        DifficultySelectionSection(
            selectedDifficulty = uiState.selectedDifficulty,
            onDifficultySelected = onDifficultySelected,
        )

        Spacer(modifier = Modifier.height(32.dp))

        MemoInputSection(
            memo = uiState.memo,
            onMemoChanged = onMemoChanged,
        )

        Spacer(modifier = Modifier.weight(1.0f))

        ClimbingPrimaryButton(
            text = if (uiState.isLoading) "저장 중..." else "저장하기",
            onClick = onSaveClick,
            enabled = !uiState.isLoading && uiState.selectedDifficulty != null,
        )
    }
}

@Composable
private fun DifficultySelectionSection(
    selectedDifficulty: Difficulty?,
    onDifficultySelected: (Difficulty) -> Unit,
) {
    Text(
        text = "어떤 난이도를 성공하셨나요?",
        style = MaterialTheme.typography.titleMedium,
        color = Color.White,
    )
    Spacer(modifier = Modifier.height(12.dp))

    LazyRow(
        contentPadding = PaddingValues(vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        items(Difficulty.entries) { difficulty ->
            DifficultySelectableItem(
                difficulty = difficulty,
                isSelected = selectedDifficulty == difficulty,
                onClick = { onDifficultySelected(difficulty) },
            )
        }
    }
}

@Composable
private fun MemoInputSection(
    memo: String,
    onMemoChanged: (String) -> Unit,
) {
    Text(
        text = "메모 (선택)",
        style = MaterialTheme.typography.titleMedium,
        color = Color.White,
    )
    Spacer(modifier = Modifier.height(12.dp))

    OutlinedTextField(
        value = memo,
        onValueChange = onMemoChanged,
        modifier =
            Modifier
                .fillMaxWidth()
                .height(120.dp),
        placeholder = { Text(text = "기록에 남길 메모를 입력해 주세요.") },
        shape = RoundedCornerShape(12.dp),
        colors =
            OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                focusedBorderColor = Color(ORANGE_COLOR_HEX),
                unfocusedBorderColor = Color.DarkGray,
                focusedPlaceholderColor = Color.Gray,
                unfocusedPlaceholderColor = Color.Gray,
            ),
    )
}

private const val ORANGE_COLOR_HEX = 0xFFFFA500

@Composable
fun DifficultySelectableItem(
    difficulty: Difficulty,
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier =
            Modifier
                .clip(RoundedCornerShape(8.dp))
                .clickable { onClick() }
                .padding(4.dp),
    ) {
        Box(
            modifier =
                Modifier
                    .size(48.dp)
                    .background(
                        color = Color(android.graphics.Color.parseColor(difficulty.color)),
                        shape = CircleShape,
                    )
                    .border(
                        width = 2.dp,
                        color = if (isSelected) Color.White else Color.Transparent,
                        shape = CircleShape,
                    ),
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = difficulty.label,
            style = MaterialTheme.typography.labelSmall,
            color = if (isSelected) Color.White else Color.Gray,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
        )
    }
}
