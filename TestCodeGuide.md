⸻

📘 TestCode 실습 가이드라인 - With Cursor AI 

⸻

🧪 실습 목표

이 프로젝트는 AI 코딩 도구(Cursor)를 활용해 다음을 실습하는 데 목적이 있습니다:
• 테스트 코드 자동 생성 및 활용
• 의도적으로 삽입된 버그 분석 및 디버깅
• Compose UI 및 Kotlin 백엔드 로직 테스트

⸻

📁 대상 파일
• MainActivity.kt에 다음과 같은 함수 및 컴포넌트가 정의되어 있습니다:
• formatGreeting(name: String?)
• calculateDueDate(start: String, days: Int)
• parseAndSum(values: List<String>)
• Greeting(...), Counter() (Compose UI)

• 그래프 관련 파일:
• Node.kt - 그래프 노드 클래스
• Edge.kt - 그래프 간선 클래스
• PathExplorer.kt - 경로 탐색 알고리즘 클래스 (여러 버그 포함)

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

✅ 실습 과제 - Cursor AI 활용 방법

1. 테스트 코드 생성하기
   • Cursor에 "MainActivity 클래스의 formatGreeting 함수를 테스트하는 JUnit 테스트 코드를 작성해줘" 와 같이 요청합니다.
   • 테스트 코드가 생성되면 app/src/test/java/com/example/testcode/ 디렉토리에 적절한 파일로 저장합니다.
   • 필요한 의존성을 추가하도록 Cursor에 요청합니다: "build.gradle.kts 파일에 JUnit과 Robolectric 의존성을 추가해줘"

2. 버그 분석 및 해결하기
   • Cursor에 "이 함수의 문제점을 찾고 해결 방법을 제안해줘" 와 같이 요청합니다.
   • 제안된 해결책을 검토하고 코드를 수정합니다.
   • 수정 후 테스트를 실행하여 버그가 해결되었는지 확인합니다.

3. Compose UI 테스트하기
   • Cursor에 "Greeting 컴포저블을 테스트하는 코드를 생성해줘" 와 같이 요청합니다.
   • 무한 루프 버그가 있는 Counter 컴포넌트에 대해 "이 컴포넌트의 문제를 해결하고 테스트 코드를 작성해줘" 라고 요청합니다.

4. PathExplorer 테스트 및 디버깅하기
   • Cursor에 "PathExplorer 클래스에 있는 버그를 분석해줘" 라고 요청합니다.
   • "PathExplorer의, findOptimalPath 메서드를 테스트하는 코드를 작성해줘" 와 같이 요청하여 각 메서드의 테스트 코드를 생성합니다.
   • 생성된 테스트 코드를 바탕으로 버그를 수정합니다.

⸻

💡 Cursor에 요청할 수 있는 예시 프롬프트

```
"formatGreeting 함수의 null 처리 문제를 해결해줘"

"PathExplorer 클래스의 calculatePathCost 메서드에 있는 0으로 나누기 버그를 수정해줘"

"Counter 컴포저블의 무한 루프 문제를 해결하고, 제대로 동작하는지 테스트하는 코드를 작성해줘"

"이 프로젝트에 단위 테스트를 위한 의존성을 추가하고, MainActivity의 모든 함수를 테스트하는 테스트 클래스를 작성해줘"

"Node와 Edge 클래스를 이용한 간단한 그래프에서 PathExplorer가 최적 경로를 올바르게 찾는지 테스트하는 코드를 작성해줘"
```

⸻

🧩 테스트 코드 조각 예시

아래는 참고용 코드 조각입니다. 실제로는 Cursor에 요청하여 전체 테스트 코드를 생성하세요:

```kotlin
// JUnit 테스트 의존성 추가 (app/build.gradle.kts)
dependencies {
    testImplementation("junit:junit:4.13.2")
    testImplementation("org.robolectric:robolectric:4.9")
    // ... 기존 의존성들
}

// MainActivity의 formatGreeting 함수를 테스트하는 코드
@Test
fun testFormatGreeting_nullName_returnsDefaultGreeting() {
    // 수정된 함수에서는 null 처리가 되어 있어야 함
    val result = activity.formatGreeting(null)
    assertEquals("Hello, GUEST!", result)
}

// 수정된 formatGreeting 함수
fun formatGreeting(name: String?): String {
    return "Hello, " + (name?.uppercase() ?: "GUEST") + "!"
}
```

⸻

🛠️ 학습 체크포인트

1. 모든 버그를 AI의 도움으로 찾아내고 수정했나요?
2. 모든 함수와 컴포넌트에 대한 테스트 코드를 AI를 활용해 작성했나요?
3. 테스트 코드가 모두 통과하나요?
4. 테스트 커버리지는 충분한가요? (모든 경계 조건이 테스트되었나요?)
5. AI에게 어떤 질문을 하면 더 효과적인 답변을 얻을 수 있었나요?

⸻
