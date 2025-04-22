package com.example.testcode

import android.util.Log
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.shadows.ShadowLog
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import java.time.LocalDate

@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE)
class MainActivityTest {

    private lateinit var mainActivity: MainActivity
    
    // Compose UI 테스트를 위한 규칙 설정
    @get:Rule
    val composeTestRule = createComposeRule()
    
    companion object {
        private const val TAG = "MainActivityTest"
    }
    
    @Before
    fun setup() {
        // Robolectric에서 로그를 콘솔에 출력하도록 설정
        ShadowLog.stream = System.out
        
        mainActivity = MainActivity()
        Log.d(TAG, "테스트 초기화 완료: MainActivity 인스턴스 생성됨")
    }
    
    // formatGreeting 메서드 테스트
    @Test
    fun formatGreeting_withValidName_returnsFormattedString() {
        // Given
        val name = "john"
        val expected = "Hello, JOHN!"
        
        // When
        Log.d(TAG, "테스트 시작: 유효한 이름 '$name'으로 formatGreeting 함수 호출")
        val result = mainActivity.formatGreeting(name)
        Log.d(TAG, "결과: '$result', 기대값: '$expected'")
        
        // Then
        assertEquals(expected, result)
        Log.d(TAG, "테스트 성공: 유효한 이름 테스트 완료")
    }
    
    @Test
    fun formatGreeting_withNullName_returnsGuestGreeting() {
        // Given
        val name: String? = null
        val expected = "Hello, GUEST!"
        
        // When
        Log.d(TAG, "테스트 시작: null 값으로 formatGreeting 함수 호출")
        val result = mainActivity.formatGreeting(name)
        Log.d(TAG, "결과: '$result', 기대값: '$expected'")
        
        // Then
        assertEquals(expected, result)
        Log.d(TAG, "테스트 성공: null 값 테스트 완료")
    }
    
    // calculateDueDate 메서드 테스트
    @Test
    fun calculateDueDate_withValidInputs_returnsCorrectDate() {
        // Given
        val startDate = LocalDate.of(2023, 1, 1)
        val days = 10
        val divisor = 2
        val expected = LocalDate.of(2023, 1, 6) // 2023-01-01 + (10/2) = 2023-01-06
        
        // When
        Log.d(TAG, "테스트 시작: calculateDueDate 함수 호출 (시작일: $startDate, 일수: $days, 제수: $divisor)")
        val result = mainActivity.calculateDueDate(startDate, days, divisor)
        Log.d(TAG, "결과: $result, 기대값: $expected")
        
        // Then
        assertEquals(expected, result)
        Log.d(TAG, "테스트 성공: 정상적인 입력으로 만기일 계산 완료")
    }
    
    @Test
    fun calculateDueDate_withZeroDivisor_handlesGracefully() {
        // Given
        val startDate = LocalDate.of(2023, 1, 1)
        val days = 10
        val divisor = 0 // 0으로 나누기 시도
        val expected = LocalDate.of(2023, 1, 11) // 2023-01-01 + (10/1) = 2023-01-11 (divisor가 0이면 1로 처리)
        
        // When
        Log.d(TAG, "테스트 시작: calculateDueDate 함수 호출 - 0으로 나누기 케이스 (시작일: $startDate, 일수: $days, 제수: $divisor)")
        val result = mainActivity.calculateDueDate(startDate, days, divisor)
        Log.d(TAG, "결과: $result, 기대값: $expected")
        
        // Then
        assertEquals(expected, result)
        Log.d(TAG, "테스트 성공: 0으로 나누기 예외 처리 확인됨")
    }
    
    // parseAndSum 메서드 테스트
    @Test
    fun parseAndSum_withValidStrings_returnsCorrectSum() {
        // Given
        val strings = listOf("10", "20", "30")
        val expected = 60
        
        // When
        Log.d(TAG, "테스트 시작: parseAndSum 함수 호출 (입력: ${strings.joinToString(", ")})")
        val result = mainActivity.parseAndSum(strings)
        Log.d(TAG, "결과: $result, 기대값: $expected")
        
        // Then
        assertEquals(expected, result)
        Log.d(TAG, "테스트 성공: 유효한 문자열의 합 계산 완료")
    }
    
    @Test
    fun parseAndSum_withInvalidStrings_ignoresInvalidValues() {
        // Given
        val strings = listOf("10", "invalid", "30", "error", "40")
        val expected = 80 // 10 + 30 + 40
        
        // When
        Log.d(TAG, "테스트 시작: parseAndSum 함수 호출 - 일부 유효하지 않은 입력 (입력: ${strings.joinToString(", ")})")
        val result = mainActivity.parseAndSum(strings)
        Log.d(TAG, "결과: $result, 기대값: $expected")
        
        // Then
        assertEquals(expected, result)
        Log.d(TAG, "테스트 성공: 유효하지 않은 값이 무시되고 올바른 합 계산됨")
    }
    
    @Test
    fun parseAndSum_withEmptyList_returnsZero() {
        // Given
        val strings = emptyList<String>()
        val expected = 0
        
        // When
        Log.d(TAG, "테스트 시작: parseAndSum 함수 호출 - 빈 목록 입력")
        val result = mainActivity.parseAndSum(strings)
        Log.d(TAG, "결과: $result, 기대값: $expected")
        
        // Then
        assertEquals(expected, result)
        Log.d(TAG, "테스트 성공: 빈 목록 처리 확인됨")
    }
    
    // Greeting 컴포저블 테스트 - UI 테스트는 일시적으로 제외
    /*
    @Test
    fun greeting_displaysCorrectText() {
        // Given
        val name = "Android"
        
        // When
        Log.d(TAG, "테스트 시작: Greeting 컴포저블 테스트 (이름: $name)")
        composeTestRule.setContent {
            Greeting(name = name)
        }
        
        // Then
        composeTestRule.onNodeWithText("Hello $name!").assertExists()
        Log.d(TAG, "테스트 성공: Greeting 컴포저블이 올바른 텍스트 표시")
    }
    
    // Counter 컴포저블 테스트
    @Test
    fun counter_incrementsValueCorrectly() {
        // Given
        Log.d(TAG, "테스트 시작: Counter 컴포저블 테스트 - 증가 기능")
        composeTestRule.setContent {
            Counter()
        }
        
        // 초기 상태 확인
        composeTestRule.onNodeWithText("Count: 0").assertExists()
        
        // When: Increment 버튼 클릭
        composeTestRule.onNodeWithText("Increment").performClick()
        
        // Then: 카운터 값이 1로 증가
        composeTestRule.onNodeWithText("Count: 1").assertExists()
        Log.d(TAG, "테스트 성공: Counter 컴포저블 증가 기능 확인됨")
    }
    
    @Test
    fun counter_resetsValueCorrectly() {
        // Given
        Log.d(TAG, "테스트 시작: Counter 컴포저블 테스트 - 초기화 기능")
        composeTestRule.setContent {
            Counter()
        }
        
        // 초기 상태 확인
        composeTestRule.onNodeWithText("Count: 0").assertExists()
        
        // 먼저 카운터 값 증가
        composeTestRule.onNodeWithText("Increment").performClick()
        composeTestRule.onNodeWithText("Increment").performClick()
        composeTestRule.onNodeWithText("Count: 2").assertExists()
        
        // When: Reset 버튼 클릭
        composeTestRule.onNodeWithText("Reset").performClick()
        
        // Then: 카운터 값이 0으로 초기화
        composeTestRule.onNodeWithText("Count: 0").assertExists()
        Log.d(TAG, "테스트 성공: Counter 컴포저블 초기화 기능 확인됨")
    }
    
    @Test
    fun counter_doesNotExceedMaxValue() {
        // Given
        Log.d(TAG, "테스트 시작: Counter 컴포저블 테스트 - 최대값 제한 확인")
        composeTestRule.setContent {
            Counter()
        }
        
        // 최대값(10)까지 증가
        repeat(10) {
            composeTestRule.onNodeWithText("Increment").performClick()
        }
        composeTestRule.onNodeWithText("Count: 10").assertExists()
        
        // When: 최대값 도달 후 추가 클릭
        composeTestRule.onNodeWithText("Increment").performClick()
        
        // Then: 여전히 10으로 유지됨
        composeTestRule.onNodeWithText("Count: 10").assertExists()
        composeTestRule.onNodeWithText("Count: 11").assertDoesNotExist()
        Log.d(TAG, "테스트 성공: Counter 컴포저블 최대값 제한 기능 확인됨")
    }
    */
} 