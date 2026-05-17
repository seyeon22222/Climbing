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

- 작업을하게 되면 모든 작업을 한뒤에 어떤 내용을 작업한것인지 매번 설명해줘
- No use import wildcardpattern

# Linting & Formatting Rules

- **ktlint & detekt 준수:** 모든 코드는 `ktlint`와 `detekt` 설정 규칙을 엄격히 준수해야 한다.
- **주요 스타일 규칙:**
    - **Trailing Comma:** 함수 파라미터, 호출부, 컬렉션 선언 등 멀티라인 코드에는 항상 마지막에 쉼표(`,`)를 추가한다.
    - **Import Ordering:** `java`, `javax`, `kotlin`, 그리고 나머지 패키지 순으로 알파벳 정렬을 유지하며, 패키지 그룹 간의 공백 규정을 준수한다.
    - **No Wildcard Imports:** `import.*` 형태의 와일드카드 임포트는 금지한다.
    - **Annotation Formatting:** 어노테이션은 적절한 줄바꿈과 공백을 유지한다.

# UI Rules

- base design은 docs/design 폴더의 png이미지를 보고 작성한다