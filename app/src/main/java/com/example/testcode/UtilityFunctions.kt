package com.example.testcode

/**
 * 유틸리티 함수를 모아둔 클래스
 */
object UtilityFunctions {
    /**
     * 인사말 형식 지정 함수 - null 처리 추가
     */
    fun formatGreeting(name: String?): String {
        return "Hello, " + (name?.uppercase() ?: "GUEST") + "!"
    }
    
    /**
     * 만기일 계산 함수
     */
    fun calculateDueDate(startDate: java.time.LocalDate, days: Int, divisor: Int): java.time.LocalDate {
        // 0으로 나누기 방지
        val safeDivisor = if (divisor == 0) 1 else divisor
        val adjustedDays = days / safeDivisor
        return startDate.plusDays(adjustedDays.toLong())
    }
    
    /**
     * 문자열 리스트의 숫자를 파싱하고 합산하는 함수 - 입력값 검증 추가
     */
    fun parseAndSum(strings: List<String>): Int {
        return strings.mapNotNull { 
            try {
                it.toInt()
            } catch (e: NumberFormatException) {
                null
            }
        }.sum()
    }
} 