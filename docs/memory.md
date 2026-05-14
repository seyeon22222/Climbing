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
  (Navigation 2.8.0+ 및 Kotlin Serialization 활용)을 적용했습니다.

1. 의존성 추가: libs.versions.toml 및 build.gradle.kts에 navigation-compose와 kotlinx-serialization 라이브러리 및 플러그인을 추가했습니다.
2. Route 정의: navigation/Route.kt에 Login, Home, Gym, Profile 경로를 Serializable한 객체로 정의하여 타입 안전성을 확보했습니다.
3. AppNavGraph 구현: navigation/AppNavGraph.kt에 NavHost를 설정하고, 각 경로에 대한 컴포저블(현재는 PlaceholderScreen)을 연결했습니다.
4. MainActivity 연동: MainActivity에서 rememberNavController()를 통해 NavController를 생성하고 AppNavGraph를 호출하여 앱의 진입점에서 내비게이션이 작동하도록 설정했습니다.