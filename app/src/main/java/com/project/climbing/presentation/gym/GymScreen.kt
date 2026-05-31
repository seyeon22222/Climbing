package com.project.climbing.presentation.gym

import GymUiState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.project.climbing.domain.model.Gym
import com.project.climbing.presentation.component.ClimbingCard
import kotlinx.coroutines.flow.StateFlow
import android.graphics.Color as AndroidColor

@Composable
fun GymScreen(viewModel: GymViewModel = hiltViewModel()) {
    val uiStateFlow: StateFlow<GymUiState> = viewModel.uiState
    val uiState by uiStateFlow.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            Box(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
            ) {
                Text(
                    text = "클라이밍장 찾기",
                    style = MaterialTheme.typography.displayLarge,
                )
            }
        },
    ) { paddingValues ->
        Box(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
        ) {
            if (uiState.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                )
            } else if (uiState.error != null) {
                Text(
                    text = uiState.error ?: "알 수 없는 오류가 발생했습니다.",
                    modifier = Modifier.align(Alignment.Center),
                    color = MaterialTheme.colorScheme.error,
                )
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    items(uiState.gyms) { gym ->
                        GymItem(gym = gym)
                    }
                }
            }
        }
    }
}

@Composable
fun GymItem(
    gym: Gym,
    modifier: Modifier = Modifier,
) {
    ClimbingCard(
        modifier = modifier.fillMaxWidth(),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            AsyncImage(
                model = gym.imageUrl,
                contentDescription = gym.name,
                modifier =
                    Modifier
                        .size(80.dp)
                        .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop,
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1.0f),
            ) {
                Text(
                    text = gym.name,
                    style = MaterialTheme.typography.displayMedium,
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = gym.address,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                ) {
                    gym.difficultyLevels.take(MAX_DIFFICULTY_DISPLAY_COUNT).forEach { level ->
                        Box(
                            modifier =
                                Modifier
                                    .size(12.dp)
                                    .clip(CircleShape)
                                    .background(Color(AndroidColor.parseColor(level.color))),
                        )
                    }
                }
            }
        }
    }
}

private const val MAX_DIFFICULTY_DISPLAY_COUNT = 8
