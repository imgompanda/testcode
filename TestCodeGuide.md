⸻

📘 TestCode 실습 가이드라인 - With Cursor AI

⸻

🧪 실습 목표

이 프로젝트는 AI 코딩 도구(Cursor)를 활용해 다음을 실습하는 데 목적이 있습니다:
• 의도적으로 삽입된 버그 분석 및 디버깅
• 테스트 코드 자동 생성 및 활용
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

| 함수 / 컴포넌트  | 설명                   | 예상되는 문제                                                  |
| ---------------- | ---------------------- | -------------------------------------------------------------- |
| formatGreeting   | null 이름 처리 없음    | 앱 실행 중 NullPointerException 발생                           |
| calculateDueDate | 0으로 나누는 계산 포함 | days = 0일 때 ArithmeticException                              |
| parseAndSum      | 입력값 검증 없음       | 문자열 리스트에 숫자가 아닌 항목 포함 시 NumberFormatException |
| Counter()        | 무한 루프 포함         | UI 프리징 또는 앱 강제 종료 가능                               |

PathExplorer 여러 가지 버그 포함:

- null 체크 없이 edges 사용
- 무한 루프 가능성
- 0으로 나누기 오류
- 강제 언래핑(!!.cost)
- 안전하지 않은 문자열 변환

⸻

✅ 실습 과제 - Cursor AI 활용 방법 (실행 순서)

1. **개발 환경 설정**
   • Cursor에 "build.gradle.kts 파일에 JUnit과 필요한 테스트 의존성을 추가해줘" 라고 요청하여 필요한 의존성을 추가합니다.
   • Gradle 동기화를 실행합니다.

2. **버그 분석 및 해결하기** (컴파일 오류부터 먼저 해결)
   • Cursor에 "MainActivity의 formatGreeting 함수에 있는 null 처리 문제를 해결해줘" 와 같이 요청합니다.
   • Cursor에 "PathExplorer 클래스에 있는 모든 컴파일 오류를 수정해줘" 라고 요청합니다.
   • 제안된 해결책을 검토하고 코드를 수정합니다.
   • 코드가 컴파일되는지 확인합니다.

3. **테스트를 위한 유틸리티 클래스 생성하기**
   • 테스트를 용이하게 하기 위해, Cursor에 "MainActivity의 함수들을 테스트하기 쉽도록 UtilityFunctions 클래스로 추출해줘" 라고 요청합니다.
   • UtilityFunctions 클래스는 정적 메소드로 formatGreeting, calculateDueDate, parseAndSum 기능을 포함해야 합니다.
   • MainActivity에서는 이 유틸리티 함수들을 호출하도록 수정합니다.

4. **테스트 코드 생성하기**
   • Cursor에 "UtilityFunctions 클래스의 formatGreeting 함수를 테스트하는 JUnit 테스트 코드를 작성해줘" 와 같이 요청합니다.
   • 테스트 코드가 생성되면 app/src/test/java/com/example/testcode/ 디렉토리에 적절한 파일로 저장합니다.
   • 동일한 방식으로 모든 함수에 대한 테스트 코드를 생성합니다.

5. **남은 버그 수정 및 테스트**


   • 테스트를 실행하여 실패하는 테스트를 확인합니다.
   • Cursor에 "UtilityFunctions의 calculateDueDate 함수에서 0으로 나누기 문제를 해결해줘" 와 같이 구체적인 버그 수정을 요청합니다.
   • parseAndSum 함수의 입력값 검증 문제도 유사하게 해결합니다.
   • 수정 후 테스트를 다시 실행하여 버그가 해결되었는지 확인합니다.

6. **Compose UI 테스트하기**
   • Cursor에 "Greeting 컴포저블을 테스트하는 코드를 생성해줘" 와 같이 요청합니다.
   • 무한 루프 버그가 있는 Counter 컴포넌트에 대해 "이 컴포넌트의 문제를 해결하고 테스트 코드를 작성해줘" 라고 요청합니다.

7. **PathExplorer 심층 테스트 및 디버깅하기**
   • Cursor에 "PathExplorer 클래스의 각 메서드를 테스트하는 코드를 작성해줘" 라고 요청합니다.
   • "PathExplorer의 findOptimalPath 메서드에서 무한 루프 문제를 해결해줘" 와 같이 요청하여 남은 버그를 수정합니다.
   • 생성된 테스트 코드를 바탕으로 나머지 버그를 모두 수정합니다.
   • 최종 테스트를 실행하여 모든 테스트가 통과하는지 확인합니다.

⸻

💡 Cursor에 요청할 수 있는 예시 프롬프트입니다.


```
"formatGreeting 함수의 null 처리 문제를 해결해줘"

"MainActivity의 함수들을 테스트하기 쉽도록 UtilityFunctions 클래스로 추출해줘"

"UtilityFunctions 클래스의 함수들을 테스트하는 테스트 코드를 작성해줘"

"PathExplorer 클래스의 findOptimalPath 메서드가 null edges를 처리할 수 있도록 수정해줘"

"calculateDueDate 함수에서 days가 0일 때 ArithmeticException이 발생하지 않도록 수정해줘"

"Counter 컴포저블의 무한 루프 문제를 해결하고, 제대로 동작하는지 테스트하는 코드를 작성해줘"

"이 프로젝트에 단위 테스트를 위한 의존성을 추가하고, UtilityFunctions의 모든 함수를 테스트하는 테스트 클래스를 작성해줘"

"Node와 Edge 클래스를 이용한 간단한 그래프에서 PathExplorer가 최적 경로를 올바르게 찾는지 테스트하는 코드를 작성해줘"
```

⸻

🧩 예시 코드 (수정 전/후)

**버그가 있는 원본 코드:**

```kotlin
// 원래 MainActivity.kt 내부 코드
// null 처리 없는 코드
fun formatGreeting(name: String?): String {
    return "Hello, " + name.uppercase() + "!"  // name이 null이면 NPE 발생
}

// 0으로 나누기 버그가 있는 코드
fun calculateDueDate(start: String, days: Int): String {
    val secondsPerDay = 86400
    val calculation = secondsPerDay / days  // days가 0이면 ArithmeticException 발생

    // ... 나머지 코드 ...
}

// 안전하지 않은 변환을 사용하는 코드
fun parseAndSum(values: List<String>): Int {
    val numbers = values.map { it.toInt() }  // 유효하지 않은 문자열이면 NumberFormatException
    return numbers.sum()
}
```

**수정 방식 1: MainActivity 내에서 직접 수정:**

```kotlin
// MainActivity.kt 내부에서 직접 수정
// null 처리를 추가한 수정 코드
fun formatGreeting(name: String?): String {
    return "Hello, " + (name?.uppercase() ?: "GUEST") + "!"
}

// 0으로 나누기 처리를 추가한 수정 코드
fun calculateDueDate(start: String, days: Int): String {
    if (days == 0) {
        return start  // days가 0이면 시작일 그대로 반환
    }
    val secondsPerDay = 86400
    val calculation = secondsPerDay / days  // 이제 days가 0이 아님이 보장됨

    // ... 나머지 코드 ...
}

// 안전한 변환을 사용하는 코드
fun parseAndSum(values: List<String>): Int {
    val numbers = values.mapNotNull { it.toIntOrNull() }  // 실패하면 null 반환
    return numbers.sum()
}
```

**수정 방식 2: UtilityFunctions 클래스로 추출 (권장):**

```kotlin
// UtilityFunctions.kt
object UtilityFunctions {
    // null 처리 추가
    fun formatGreeting(name: String?): String {
        return "Hello, " + (name?.uppercase() ?: "GUEST") + "!"
    }

    // 0으로 나누기 처리 추가
    fun calculateDueDate(start: String, days: Int): String {
        if (days == 0) {
            return start  // days가 0이면 시작일 그대로 반환
        }
        val secondsPerDay = 86400
        val calculation = secondsPerDay / days  // 이제 days가 0이 아님이 보장됨

        // ... 나머지 코드 ...
    }

    // 안전한 변환을 사용하는 코드
    fun parseAndSum(values: List<String>): Int {
        val numbers = values.mapNotNull { it.toIntOrNull() }  // 실패하면 null 반환
        return numbers.sum()
    }
}

// MainActivity.kt
class MainActivity : ComponentActivity() {
    // UtilityFunctions로 위임
    fun formatGreeting(name: String?): String {
        return UtilityFunctions.formatGreeting(name)
    }

    fun calculateDueDate(start: String, days: Int): String {
        return UtilityFunctions.calculateDueDate(start, days)
    }

    fun parseAndSum(values: List<String>): Int {
        return UtilityFunctions.parseAndSum(values)
    }
}
```

⸻

🛠️ 학습 체크포인트

1. 컴파일 오류부터 해결했나요? (null 처리 등)
2. UtilityFunctions 클래스로 기능을 분리했나요?
3. 모든 함수와 컴포넌트에 대한 테스트 코드를 AI를 활용해 작성했나요?
4. 실행 시간 오류(RuntimeException)를 모두 해결했나요?
5. 모든 테스트 코드가 통과하나요?
6. 로직의 정확성까지 확인했나요? (최적 경로를 올바르게 찾는지 등)
7. AI에게 어떤 질문을 하면 더 효과적인 답변을 얻을 수 있었나요?

⸻
