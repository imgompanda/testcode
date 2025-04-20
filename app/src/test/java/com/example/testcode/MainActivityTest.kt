package com.example.testcode

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.lang.ArithmeticException
import java.lang.NullPointerException
import java.lang.NumberFormatException

/**
 * MainActivity 클래스의 함수들에 대한 단위 테스트
 * 이 테스트 클래스는 MainActivity에 포함된 여러 함수와 UI 컴포넌트를 검증합니다.
 * 
 * 학생들은 이 클래스를 참고하여 자신만의 테스트 코드를 작성하고
 * 함수들의 버그를 찾아 수정하는 연습을 할 수 있습니다.
 */
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [28])
class MainActivityTest {
    
    private lateinit var activity: MainActivity
    
    @Before
    fun setup() {
        activity = Robolectric.buildActivity(MainActivity::class.java).create().get()
    }
    
    /**
     * 테스트: formatGreeting 함수의 정상 입력 테스트
     * 예상 결과: 대문자로 변환된 이름으로 인사말 생성
     */
    @Test
    fun testFormatGreeting_normalInput_returnsUpperCaseGreeting() {
        val greeting = activity.formatGreeting("student")
        assertEquals("Hello, STUDENT!", greeting)
    }
    
    /**
     * 테스트: formatGreeting 함수에 null 입력 시 예외 발생 확인
     * 예상 결과: NullPointerException 발생
     * 
     * 참고: 이 테스트는 버그를 검증합니다.
     * 학생들은 NPE가 발생하지 않도록 코드를 수정해야 합니다.
     */
    @Test(expected = NullPointerException::class)
    fun testFormatGreeting_nullInput_throwsNullPointerException() {
        activity.formatGreeting(null)
    }
    
    /**
     * 테스트: calculateDueDate 함수의 정상 입력 테스트
     * 예상 결과: start 날짜에서 days만큼 더한 날짜 반환
     */
    @Test
    fun testCalculateDueDate_normalInput_returnsCorrectDate() {
        val dueDate = activity.calculateDueDate("2023-01-01", 5)
        assertEquals("2023-01-06", dueDate)
    }
    
    /**
     * 테스트: calculateDueDate 함수에 days=0 입력 시 예외 발생 확인
     * 예상 결과: ArithmeticException 발생 (0으로 나누기)
     * 
     * 참고: 이 테스트는 버그를 검증합니다.
     * 학생들은 0으로 나누기가 발생하지 않도록 코드를 수정해야 합니다.
     */
    @Test(expected = ArithmeticException::class)
    fun testCalculateDueDate_zeroDays_throwsArithmeticException() {
        activity.calculateDueDate("2023-01-01", 0)
    }
    
    /**
     * 테스트: parseAndSum 함수의 정상 입력 테스트
     * 예상 결과: 리스트의 모든 숫자의 합 반환
     */
    @Test
    fun testParseAndSum_normalInput_returnsCorrectSum() {
        val sum = activity.parseAndSum(listOf("1", "2", "3", "4"))
        assertEquals(10, sum)
    }
    
    /**
     * 테스트: parseAndSum 함수에 유효하지 않은 입력 시 예외 발생 확인
     * 예상 결과: NumberFormatException 발생
     * 
     * 참고: 이 테스트는 버그를 검증합니다.
     * 학생들은 예외가 발생하지 않도록 코드를 수정해야 합니다.
     */
    @Test(expected = NumberFormatException::class)
    fun testParseAndSum_invalidInput_throwsNumberFormatException() {
        activity.parseAndSum(listOf("1", "two", "3"))
    }
    
    /**
     * 테스트: parseAndSum 함수에 빈 리스트 입력 테스트
     * 예상 결과: 0 반환
     */
    @Test
    fun testParseAndSum_emptyList_returnsZero() {
        val sum = activity.parseAndSum(emptyList())
        assertEquals(0, sum)
    }
    
    // 학생들이 추가할 수 있는 더 많은 테스트 케이스:
    // 1. 날짜 형식이 잘못된 경우의 calculateDueDate 테스트
    // 2. days가 음수인 경우의 calculateDueDate 테스트
    // 3. 매우 큰 숫자를 포함한 parseAndSum 테스트
    // 4. 빈 문자열을 포함한 parseAndSum 테스트
    
    // Compose UI 테스트는 별도의 클래스로 구현하는 것이 좋습니다.
} 