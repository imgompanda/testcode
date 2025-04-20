⸻

📘 TestCode 실습 가이드라인

⸻

🧪 실습 목표

이 프로젝트는 AI 코딩 도구(Cursor 등)를 활용해 다음을 실습하는 데 목적이 있습니다:
• 단위 테스트 코드 생성
• 의도적으로 삽입된 버그 분석 및 디버깅
• Compose UI 및 Kotlin 백엔드 로직 테스트 적용

⸻

📁 대상 파일
• MainActivity.kt에 다음과 같은 함수 및 컴포넌트가 정의되어 있습니다:
• formatGreeting(name: String?)
• calculateDueDate(start: String, days: Int)
• parseAndSum(values: List<String>)
• Greeting(...), Counter() (Compose UI)

    •	그래프 관련 파일:
    •	Node.kt - 그래프 노드 클래스
    •	Edge.kt - 그래프 간선 클래스
    •	PathExplorer.kt - 경로 탐색 알고리즘 클래스 (여러 버그 포함)

⸻

🐞 포함된 버그 목록

함수 / 컴포넌트 설명 예상되는 문제
formatGreeting null 이름 처리 없음 앱 실행 중 NullPointerException 발생
calculateDueDate 0으로 나누는 계산 포함 days = 0일 때 ArithmeticException
parseAndSum 입력값 검증 없음 문자열 리스트에 숫자가 아닌 항목 포함 시 NumberFormatException
Counter() 무한 루프 포함 UI 프리징 또는 앱 강제 종료 가능

PathExplorer 여러 가지 버그 포함:

- null 체크 없이 edges 사용
- 무한 루프 가능성
- 0으로 나누기 오류
- 강제 언래핑(!!.cost)
- 안전하지 않은 문자열 변환

⸻

✅ 실습 과제

1. 테스트 코드 작성

각 함수에 대해 다음 항목을 포함한 테스트 코드를 작성하세요:
• 정상 입력에 대한 결과 검증
• 엣지 케이스 테스트 (null, 0, empty list 등)
• 예외 발생 여부 테스트 (assertFailsWith, try-catch 등)

2. Compose 컴포넌트 테스트
   • Greeting() 컴포저블에 적절한 텍스트가 출력되는지 테스트
   • Counter()가 정상 작동하도록 수정하고 테스트

3. 버그 수정
   • 각 함수의 로직을 수정하여 테스트가 통과하도록 변경

4. PathExplorer 테스트 및 디버깅
   • PathExplorer 클래스의 각 메서드를 테스트하는 코드 작성
   • findOptimalPath, findAllPaths, calculatePathCost 모두 테스트
   • 다양한 그래프 구조에서의 동작 검증
   • 여러 버그를 찾아내고 수정

⸻

🧠 힌트
• formatGreeting → name?.uppercase() + ?: 처리
• calculateDueDate → days == 0 예외처리 또는 로직 분기
• parseAndSum → .toIntOrNull()로 안전 파싱
• Counter() → delay() 또는 repeat 구조로 변경
• PathExplorer → null 체크, 순환 검사, 예외 처리 추가

⸻

💡 추가 팁
• 단위 테스트용 라이브러리는 JUnit, Turbine, Robolectric 등을 사용할 수 있습니다.
• MainActivity 클래스의 함수를 별도 클래스로 분리하면 테스트가 쉬워집니다.
• AI 도구에 "이 함수에 대한 단위 테스트 코드를 생성해줘"라고 직접 지시하는 식으로 실습해보세요.
• PathExplorer 클래스는 복잡한 알고리즘을 포함하므로 단계적으로 테스트하세요.
• 그래프 테스트를 위해 다양한 케이스(순환 그래프, 비연결 그래프 등)를 구성해보세요.

⸻

🎯 결과 예시
• testFormatGreeting_nullInput_throwsException()
• testCalculateDueDate_zeroDays_throwsException()
• testParseAndSum_invalidInput_throwsNumberFormatException()
• testGreeting_showsExpectedText()
• testCounter_incrementsCorrectly() ← 무한루프 수정 후
• testPathExplorer_findOptimalPath_simpleGraph()
• testPathExplorer_calculatePathCost_withNullEdge_throwsException()

⸻
