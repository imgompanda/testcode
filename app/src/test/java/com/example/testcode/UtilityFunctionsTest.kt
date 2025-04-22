package com.example.testcode

import org.junit.Test
import org.junit.Assert.*
import java.time.LocalDate

class UtilityFunctionsTest {
    
    @Test
    fun formatGreeting_withValidName_returnsFormattedGreeting() {
        // Given
        val name = "John"
        
        // When
        val result = UtilityFunctions.formatGreeting(name)
        
        // Then
        assertEquals("Hello, JOHN!", result)
    }
    
    @Test
    fun formatGreeting_withNullName_returnsGuestGreeting() {
        // Given
        val name: String? = null
        
        // When
        val result = UtilityFunctions.formatGreeting(name)
        
        // Then
        assertEquals("Hello, GUEST!", result)
    }
    
    @Test
    fun formatGreeting_withEmptyName_returnsFormattedEmptyName() {
        // Given
        val name = ""
        
        // When
        val result = UtilityFunctions.formatGreeting(name)
        
        // Then
        assertEquals("Hello, !", result)
    }
    
    @Test
    fun calculateDueDate_withNormalInput_returnsCorrectDate() {
        // Given
        val startDate = LocalDate.of(2023, 1, 1)
        val days = 10
        val divisor = 2
        
        // When
        val result = UtilityFunctions.calculateDueDate(startDate, days, divisor)
        
        // Then
        assertEquals(LocalDate.of(2023, 1, 6), result)
    }
    
    @Test
    fun calculateDueDate_withZeroDivisor_handlesError() {
        // Given
        val startDate = LocalDate.of(2023, 1, 1)
        val days = 10
        val divisor = 0
        
        // When
        val result = UtilityFunctions.calculateDueDate(startDate, days, divisor)
        
        // Then
        assertEquals(LocalDate.of(2023, 1, 11), result)
    }
    
    @Test
    fun parseAndSum_withValidNumbers_returnsCorrectSum() {
        // Given
        val strings = listOf("1", "2", "3")
        
        // When
        val result = UtilityFunctions.parseAndSum(strings)
        
        // Then
        assertEquals(6, result)
    }
    
    @Test
    fun parseAndSum_withInvalidNumbers_ignoresInvalidAndSumsRest() {
        // Given
        val strings = listOf("1", "invalid", "3")
        
        // When
        val result = UtilityFunctions.parseAndSum(strings)
        
        // Then
        assertEquals(4, result)
    }
    
    @Test
    fun parseAndSum_withEmptyList_returnsZero() {
        // Given
        val strings = emptyList<String>()
        
        // When
        val result = UtilityFunctions.parseAndSum(strings)
        
        // Then
        assertEquals(0, result)
    }
}
