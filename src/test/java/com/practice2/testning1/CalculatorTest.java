package com.practice2.testning1;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class CalculatorTest {

    @Test
    void add_TwoPositiveNumbers_ReturnsSumOfTwoPositiveNumbers() {
        // Arrange
        Calculator calc = new Calculator();

        // Act
        int result = calc.add(5, 3);

        // Assert (kontrollera)
        assertEquals(8, result);

    }

    @Test
    void subtract_FirstNumberLarger_ShouldReturnPositiveResult(){
        Calculator calc = new Calculator();
        int result = calc.subtract(5, 3);
        assertEquals(2, result);
    }

    @Test
    void isEven_EvenNumber_ReturnsTrue(){
        Calculator calc = new Calculator();
        boolean result = calc.isEven(4);
        assertTrue(result);
    }

    @Test
    void esEven_OddNumber_ReturnsFalse(){
        Calculator calc = new Calculator();
        boolean result = calc.isEven(3);
        assertFalse(result);
    }
}