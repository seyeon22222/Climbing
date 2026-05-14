# Project Overview
This project is an Android climbing management application.

Main features:
- Climbing gym list
- Gym detail
- User profile
- Climbing records
- Kakao login
- Kakao maps
- Supabase backend

Tech stack:
- Kotlin
- Jetpack Compose
- MVVM
- Hilt
- Supabase
- Kakao Login API, Map API
- PostgreSQL
- Coroutine
- Retrofit / Ktor
- Room or DataStore

# Architecture Rules

- Use MVVM architecture only
- Use feature-based package structure
- Separate presentation, domain, and data layers
- Repository pattern is mandatory
- Business logic must not exist in composables
- ViewModel must not directly access Supabase APIs

# Package Structure

presentation/
- UI layer
- Screens
- ViewModels
- UiState classes

domain/
- Use cases
- Domain models
- Repository interfaces

data/
- Repository implementations
- DTOs
- Remote APIs
- Local storage

navigation/
- Navigation graph
- Route definitions

di/
- Hilt modules

util/
- Constants
- Extensions

# MVVM Rules

- Every screen must have:
  - Screen composable
  - ViewModel
  - UiState

- ViewModel is responsible for:
  - state management
  - async calls
  - business coordination

- Composables must only render UI

- Repository calls must happen inside ViewModel or UseCase

- Use immutable UiState data classes

# Compose Rules

- Use Jetpack Compose only
- XML layouts are forbidden
- Use Material3
- Avoid large composable functions
- Extract reusable UI components
- UI must be stateless whenever possible

# State Management

- Use StateFlow for screen state
- Expose immutable StateFlow
- Use collectAsStateWithLifecycle
- Avoid mutableStateOf inside ViewModel

# Repository Rules

- Repository interfaces belong to domain layer
- Repository implementations belong to data layer
- Remote APIs must not be called directly from ViewModel

# Naming Rules

- Screen composables must end with Screen
- ViewModels must end with ViewModel
- Ui state classes must end with UiState
- Repository interfaces must end with Repository
- UseCase classes must end with UseCase

# Dependency Injection

- Use Hilt only
- Avoid manual dependency creation
- Inject repositories through constructors

# Testing Rules

- Repository logic requires unit tests
- Use fake repositories for testing
- Avoid business logic inside UI

# Forbidden Patterns

- No business logic inside composables
- No direct API calls inside screens
- No mutable public state
- No hardcoded strings
- No XML layouts

# Markdown Rules
- Not delete markdown

# Coding Rules

- 작업을하게 되면 각 단위 작업을 수행하며 해당 작업에 대해서 매번 설명해줘
- import 시 wildcard(*) 패턴 금지하며 개별 임포트로 작성 필수