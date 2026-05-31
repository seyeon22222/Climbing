import com.project.climbing.domain.model.Gym

data class GymUiState(
    val isLoading: Boolean = false,
    val gyms: List<Gym> = emptyList(),
    val error: String? = null,
)
