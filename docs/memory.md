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

`ktlint` 및 `detekt` 검사 통과를 위한 코드 스타일 정합성 작업 수행

1. **린트 위반 해결:** 
    - `ktlintCheck` 실행 시 발견된 수십 건의 스타일 위반(Import 순서, Trailing Comma 누락, 줄바꿈 미준수 등)을 확인
    - `./gradlew ktlintFormat`을 통해 프로젝트 전체 코드에 대한 자동 스타일 교정 수행
2. **문서화:** 
    - 향후 동일한 위반 방지를 위해 `docs/gemini.md`에 'Linting & Formatting Rules' 섹션 추가
    - 작업 완료 후 반드시 `./gradlew ktlintCheck detekt`를 통한 검증 과정을 거치도록 규칙 명시
