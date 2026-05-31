# 26/05/14
너는 이제 시니어 개발자로서 코드 작업을 수행할꺼야
docs/gemini.md 파일에 너의 코딩 규칙에 대해서 작성해뒀어

우리의 목표는 개인의 클라이밍 기록을 하는 앱을 만들꺼야

우선 최초의 MVP의 목표는 다음과 같아
1. 한국 기준 전국 클라이밍장 목록
2. 각 클라이밍장 별 대표사진, 난이도 목록표, 위치를 보여주는 페이지
3. 개인별 프로필
    3-1. 프로필에는 내가 기록한 클라이밍장 별 기록들이 있어야해
4. 로그인의 경우 Kakao API를 사용
5. 지도의 경우 Kakao Maps를 쓰고싶어

이러한 규칙을 정하고 매 작업마다 기록사항을 업데이트 시켜서 진행할꺼야

진행된 내용은 아래와 같아
1. Gradle 설정: Hilt와 KSP 관련 버전 세팅 및 gradle파일에 추가.
2. Application 클래스 생성: Hilt를 사용하기 위해 ClimbingApp을 생성하고 MainActivity에서 DI가 되도록 작성
3. Manifest 업데이트: manifest에 생성한 앱 등록
4. MainActivity 설정: MainActivity에 @AndroidEntryPoint 어노테이션을 추가하여 Hilt DI

다음으로 진행된 내용은 아래와 같아
 Navigation Compose 설정을 완료했습니다. 최신 방식인 Type-safe Navigation
  (Navigation 2.8.0+ 및 Kotlin Serialization 활용)을 적용

1. 의존성 추가: libs.versions.toml 및 build.gradle.kts에 navigation-compose와 kotlinx-serialization 라이브러리 및 플러그인을 추가
2. Route 정의: navigation/Route.kt에 Login, Home, Gym, Profile 경로를 Serializable한 객체로 정의하여 타입 안전성을 확보
3. AppNavGraph 구현: navigation/AppNavGraph.kt에 NavHost를 설정하고, 각 경로에 대한 컴포저블(현재는 PlaceholderScreen)을 연결
4. MainActivity 연동: MainActivity에서 rememberNavController()를 통해 NavController를 생성하고 AppNavGraph를 호출하여 앱의 진입점에서 내비게이션이 작동하도록 설정

# 26/05/15

다음으로 진행된 내용은 아래와 같아
1. .editorconfig 생성 : 코딩 컨벤션 정의 
2. ktlint 추가 및 설정 완료 : 플러그인 적용, ktlint의 Format을 실행하여 기존에 작성되어있던 코드의 스타일 변경
3. detekt 추가 및 설정 완료 : 플러그인 적용, 기본 detekt의 설정파일 detekt.yml 생성
4. GitHub Actions 추가 : CI Action 생성
  Action Flow
  1. Main이나 Develop타겟으로 push, PR시 workflow 실행
  2. 코드 체크아웃 및 JDK 17 설정
  3. ktlint 검사 (ktlintCheck)
  4. detekt 검사 (detekt)
  5. 전체 빌드 및 테스트 실행 (build)
5. PR Template 추가 

# 26/05/16

바텀 네비게이션(Bottom Navigation) 기반의 앱 UI 기초 구조를 구현

1. 의존성 추가: 다양한 아이콘 사용을 위해 `androidx.compose.material:material-icons-extended`를 추가
2. BottomNavItem 정의: `navigation/BottomNavItem.kt`를 생성하여 홈, 암장, 프로필 등 하단 탭 항목들을 정의하고 각 항목에 해당하는 Route와 아이콘, 라벨을 연결
3. MainActivity UI 통합: `Scaffold`와 `NavigationBar`를 사용하여 하단 네비게이션 바를 구현했습니다. 현재 경로가 `Login`이 아닐 때만 하단 바가 보이도록 제어 로직을 추가
4. 화면별 MVVM 구조 구축: `login`, `home`, `gym`, `profile` 각 패키지에 프로젝트 규칙(Screen, ViewModel, UiState)에 따른 기본 클래스들을 생성
    - `LoginScreen`: 임시 로그인 버튼을 통해 메인 화면으로 진입할 수 있도록 구현
    - `HomeScreen`, `GymScreen`, `ProfileScreen`: 각 화면의 기초 UI 구조(Placeholder) 및 ViewModel 연결
5. 네비게이션 연동: `AppNavGraph`를 업데이트하여 새로 생성한 Screen 컴포저블들을 연결하고, 로그인 성공 시 홈 화면으로 이동하는 흐름을 완성
6. MainActivity 리팩토링: `MainActivity`에 집중되어 있던 `Scaffold` 및 `BottomNavigationBar` 로직을 별도의 Root Composable인 `presentation/ClimbingMain.kt`로 분리하여 코드 가독성과 유지보수성을 높이고 `Application` 클래스와의 이름 충돌을 피하기 위해 Composable 함수의 이름을 `ClimbingMain`으로 명명

# 26/05/17

기본 UI 디자인 시스템 구축 및 테마 적용

1. 테마 업데이트: `docs/design`의 가이드를 바탕으로 색상(Orange, Green, Blue, Dark Neutrals)과 타이포그래피(Heading, Body, Caption)를 `Color.kt`, `Type.kt`, `Theme.kt`에 적용. 기본 다크 모드 테마로 설정.
2. 공통 UI 컴포넌트 생성: 디자인 가이드에 맞춰 `ClimbingButton`, `ClimbingChip`, `ClimbingCard` 등 재사용 가능한 컴포넌트들을 `presentation/component` 패키지에 구현.
3. 내비게이션 확장: 디자인 가이드의 5개 탭 구성을 반영하기 위해 `Route.kt` 및 `BottomNavItem.kt`에 `Record`(기록), `Challenge`(도전) 경로를 추가.
4. 내비게이션 바 디자인 적용: `ClimbingMain.kt`의 `NavigationBar`를 디자인 가이드에 맞춰 검은색 배경과 오렌지색 활성 아이콘으로 스타일링.
5. 기능별 화면 구조 구축: 새롭게 추가된 `record`와 `challenge` 패키지에 각각 `Screen`, `ViewModel`, `UiState` 기초 클래스를 생성하고 `AppNavGraph`에 연결.
6. 기존 화면 리팩토링: `LoginScreen`에 커스텀 버튼 및 디자인 텍스트 스타일을 적용하여 디자인 가이드와 일치하도록 수정.


카카오 로그인(OAuth) 연동 및 인증 흐름 구축

1. 카카오 SDK 설정:
    - `libs.versions.toml`에 `kakao-user` SDK(v2-user) 의존성 추가
    - `app/build.gradle.kts`에 SDK 반영 및 `AndroidManifest.xml`에 인터넷 권한 추가
    - `AndroidManifest.xml`에 카카오 로그인 Redirect URI를 위한 `AuthCodeHandlerActivity` 등록
    - `strings.xml`에 `kakao_native_app_key` 플레이스홀더 추가
2. SDK 초기화: `ClimbingApp.kt`에서 `KakaoSdk.init` 호출하여 앱 시작 시 SDK 초기화
3. 인증 레이어 구현:
    - `AuthRepository`: 카카오 로그인/로그아웃 기능을 정의하는 도메인 인터페이스 생성
    - `AuthRepositoryImpl`: 카카오 SDK를 사용하여 `suspendCancellableCoroutine` 기반의 비동기 로그인 로직 구현 (카카오톡 및 카카오계정 로그인 지원)
    - `RepositoryModule`: Hilt를 이용해 `AuthRepository` 의존성 주입 설정
4. 로그인 UI 연동:
    - `LoginUiState`: `isLoading`, `error` 상태 필드 추가
    - `LoginViewModel`: `AuthRepository`를 주입받아 카카오 로그인 비즈니스 로직 처리 및 상태 관리 구현
    - `LoginScreen`: 실제 카카오 로그인 버튼 클릭 시 `LocalContext`를 통해 로그인을 트리거하고 로딩 및 에러 UI 표시
5. 카카오 앱 키 보안 관리 설정:
    - `local.properties`에서 `KAKAO_NATIVE_APP_KEY`를 관리하도록 변경
    - `app/build.gradle.kts`에서 `local.properties`를 읽어 `BuildConfig` 및 `manifestPlaceholders`에 주입하도록 빌드 스크립트 수정
    - `ClimbingApp.kt`에서 `BuildConfig.KAKAO_NATIVE_APP_KEY`를 참조하여 SDK 초기화
    - `AndroidManifest.xml`에서 `${KAKAO_NATIVE_APP_KEY}` 자리표시자를 사용하여 리다이렉트 URI 설정
    - `strings.xml`에 하드코딩된 키 제거

# 26/05/18

사용자 세션(User Session) 전반에 걸친 기능 구현 완료

1. 도메인 모델 정의:
    - `domain/model/User.kt`: 사용자 정보를 담는 데이터 클래스(id, 닉네임, 프로필 이미지, 이메일) 생성
2. 인증 저장소(AuthRepository) 확장:
    - `authState`: `AuthState`를 관찰할 수 있는 Flow 추가 (Uninitialized, Authenticated, Unauthenticated)
    - `getUserInfo()`: 현재 로그인된 사용자의 정보를 가져오는 기능 추가
    - `isLoggedIn()`: 세션 유지 여부를 확인하는 로직 구현
3. 인증 저장소 구현(AuthRepositoryImpl) 업데이트:
    - Kakao SDK를 연동하여 실제 사용자 정보를 가져오고 `authState`를 업데이트하는 로직 구현
    - 로그인 성공 시 즉시 유저 정보를 동기화하도록 개선
    - 로그아웃 시 상태를 `Unauthenticated`로 변경
4. 앱 전역 세션 관리:
    - `MainViewModel`: 앱 시작 시 세션 상태를 체크하고 전역적으로 인증 상태를 제공
    - `ClimbingMain`: 인증 상태(`authState`)를 관찰하여 상태가 `Uninitialized`일 때 로딩 바를 표시하고, 로그인 여부에 따라 시작 화면을 동적으로 결정
    - `LaunchedEffect`를 이용해 로그인/로그아웃 시 자동으로 화면 전환이 일어나도록 내비게이션 로직 강화
5. 프로필 화면 연동:
    - `ProfileViewModel`: `AuthRepository`의 유저 정보를 UI 상태에 반영하고 로그아웃 명령 처리
    - `ProfileScreen`: 유저의 닉네임, 이메일 등을 표시하고 로그아웃 버튼을 통해 세션을 종료할 수 있도록 UI 구현
    - 프로필 이미지 영역에 기본 아이콘 적용 및 디자인 가이드 준수
추가 작업사항

1. 이메일 노출 제거: 사용자의 개인정보 보호 및 요청 사항에 따라 이메일 표시 영역을 삭제
   2. 프로필 편집 모드 추가:
       * 닉네임 옆의 편집(아이콘) 버튼을 누르면 편집 모드로 전환
       * 편집 모드에서는 닉네임을 입력할 수 있는 텍스트 필드 노출
       * 프로필 이미지 우측 하단에 카메라 아이콘이 추가되어 이미지 변경이 가능함을 시각적 표시
   3. 저장 및 취소 로직:
       * 편집 중 마음이 바뀌면 취소 버튼을 통해 이전 닉네임으로 롤백
       * 저장 버튼을 누르면 변경된 닉네임이 반영 (현재는 UI 상태만 변경되며, 추후 DB 연동 시 실제 저장)
   4. 디자인 개선: Material3의 OutlinedTextField와 커스텀 버튼들을 사용하여 앱의 다크 테마와 어울리는 세련된 편집 환경을 구축

# 26/05/31

도메인 구성

1. 도메인 모델 정의:
    * Gym: 클라이밍장 정보를 담는 데이터 클래스(이름, 주소, 이미지, 난이도 목록 등)를 domain/model/Gym.kt에 생성
    * DifficultyLevel: 난이도 이름과 색상 코드를 포함하는 클래스를 정의
2. 저장소 인터페이스 및 가짜 데이터 구현:
    * GymRepository: 클라이밍장 목록 및 상세 정보를 가져오는 인터페이스를 domain/repository/GymRepository.kt에 정의
    * GymRepositoryImpl: Supabase 연동 전까지 사용할 테스트용 가짜 데이터(더클라이밍 마포점, 서울볼더스 등)를 포함하여 data/repository/GymRepositoryImpl.kt에 구현
3. 의존성 주입(Hilt) 설정:
    * RepositoryModule: 새롭게 생성한 GymRepository를 Hilt가 주입할 수 있도록 바인딩 설정을 추가
4. 이미지 로딩 라이브러리 추가:
    * 목록에서 이미지를 표시하기 위해 Coil 라이브러리를 프로젝트에 추가했습니다 (libs.versions.toml, build.gradle.kts 수정).
5. 화면 구현 및 데이터 연동:
    * GymUiState: 클라이밍장 목록 상태를 관리할 수 있도록 필드를 추가
    * GymViewModel: GymRepository를 주입받아 초기 로드 시 목록 데이터를 가져오도록 로직을 추가
    * GymScreen: LazyColumn과 ClimbingCard를 사용하여 클라이밍장 목록 UI를 구현했습니다. 각 항목에는 이미지, 이름, 주소, 그리고 해당 암장의 난이도 구성을 시각적으로 보여주는 색상 칩들이 포함

상세 페이지 구현

1. 내비게이션 설정:
    * Route.kt: gymId를 파라미터로 받는 GymDetail 경로를 추가
    * AppNavGraph.kt: GymDetailScreen을 내비게이션 그래프에 연결하고, 목록에서 상세로 이동하는 로직을 추가
2. 상세 페이지 MVVM 구조 구축:
    * presentation/gym/detail 패키지를 생성하여 상세 화면 관련 파일들을 정리
    * GymDetailUiState: 로딩 상태, 조회된 암장 데이터, 에러 메시지를 관리
    * GymDetailViewModel: SavedStateHandle을 통해 전달받은 gymId로 가짜 저장소(GymRepository)에서 상세 정보를 조회
    * GymDetailScreen: 조회된 데이터를 바탕으로 UI를 구성
3. UI 디자인 및 기능:
    * 대표 이미지: 화면 상단에 암장의 대표 사진을 크게 표시
    * 정보 표시: 암장 이름과 위치(아이콘 포함)를 표시
    * 난이도 리스트: 해당 암장의 모든 난이도 정보를 색상 칩과 함께 리스트 형태로 상세히 표시
    * 뒤로가기: 상단 앱바에 뒤로가기 버튼을 배치
4. 목록 화면 연동:
    * GymScreen.kt: 각 클라이밍장 카드를 클릭할 수 있도록 clickable 속성을 추가하고, 클릭 시 상세 페이지로 이동하는 콜백을 연결


카카오 지도 SDK 연동 및 상세 화면 내 지도 표시

1. SDK 의존성 및 환경 설정:
    * settings.gradle.kts: 카카오 지도 전용 Maven 저장소를 추가했습니다.
    * libs.versions.toml & build.gradle.kts: 카카오 지도 SDK(v2, 2.13.2 버전) 의존성을 추가하고, 네이티브 라이브러리 지원을 위한 abiFilters(arm64-v8a,
        armeabi-v7a)를 설정했습니다.
2. 권한 및 보안 설정:
    * AndroidManifest.xml: 위치 권한(ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION)을 추가하고, SDK 연동에 필요한 com.kakao.vectormap.APP_KEY 메타데이터를
        등록했습니다.
3. 데이터 보강:
    * GymRepositoryImpl.kt: 가짜 데이터(더클라이밍 마포점 등)에 각 암장의 실제 위도/경도 좌표를 추가했습니다.
4. 상세 화면 지도 UI 구현:
    * GymDetailScreen.kt:
        * AndroidView를 사용하여 Compose 내부에 MapView를 배치했습니다.
        * 해당 암장의 좌표를 중심으로 카메라가 이동하고, 암장 이름이 표시된 마커(Label)가 찍히도록 구현했습니다.
        * 지도 하단에 실제 위도/경도 정보를 텍스트로 표시하여 가시성을 높였습니다.
