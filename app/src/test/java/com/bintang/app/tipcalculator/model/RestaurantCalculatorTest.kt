package com.bintang.app.tipcalculator.model

import com.bintang.app.tipcalculator.model.RestaurantCalculator
import junit.framework.Assert.assertEquals
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class RestaurantCalculatorTest {
    lateinit var calculator : RestaurantCalculator

    @Before
    fun setup(){
        calculator = RestaurantCalculator()
    }

    @Test
    fun testCalculateTip(){
        val baseTc = TipCalculation(checkAmount = 10.0)

        listOf(
            baseTc.copy(tipPct = 15, tipAmount = 1.50, grandTotal = 11.50),
            baseTc.copy(tipPct = 18, tipAmount = 1.80, grandTotal = 11.80),
            baseTc.copy(tipPct = 20, tipAmount = 2.00, grandTotal = 12.00)
        ).forEach {
            Assert.assertEquals(it, calculator.calculateTip(it.checkAmount, it.tipPct))
        }


    }
}