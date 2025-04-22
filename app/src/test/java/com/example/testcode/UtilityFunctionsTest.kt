package com.example.testcode

import android.util.Log
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.shadows.ShadowLog

@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE)
class UtilityFunctionsTest {

    companion object {
        private const val TAG = "UtilityFunctionsTest"
    }

    @Before
    fun setup() {
        // Robolectric에서 로그를 콘솔에 출력하도록 설정
        ShadowLog.stream = System.out
    }

    @Test
    fun formatGreeting_withValidName_returnsFormattedString() {
        // Given
        val name = "john"
        val expected = "Hello, JOHN!"
        
        // When
        Log.d(TAG, "테스트 시작: 유효한 이름 '$name'으로 formatGreeting 함수 호출")
        val result = UtilityFunctions.formatGreeting(name)
        Log.d(TAG, "결과: '$result', 기대값: '$expected'")
        
        // Then
        assertEquals(expected, result)
        Log.d(TAG, "테스트 성공: 유효한 이름 테스트 완료")
    }
    
    @Test
    fun formatGreeting_withEmptyName_returnsFormattedString() {
        // Given
        val name = ""
        val expected = "Hello, !"
        
        // When
        Log.d(TAG, "테스트 시작: 빈 문자열 '$name'으로 formatGreeting 함수 호출")
        val result = UtilityFunctions.formatGreeting(name)
        Log.d(TAG, "결과: '$result', 기대값: '$expected'")
        
        // Then
        assertEquals(expected, result)
        Log.d(TAG, "테스트 성공: 빈 문자열 테스트 완료")
    }
    
    @Test
    fun formatGreeting_withNullName_returnsGuestGreeting() {
        // Given
        val name: String? = null
        val expected = "Hello, GUEST!"
        
        // When
        Log.d(TAG, "테스트 시작: null 값으로 formatGreeting 함수 호출")
        val result = UtilityFunctions.formatGreeting(name)
        Log.d(TAG, "결과: '$result', 기대값: '$expected'")
        
        // Then
        assertEquals(expected, result)
        Log.d(TAG, "테스트 성공: null 값 테스트 완료")
    }
}
