package com.bintang.app.tipcalculator.viewmodel

import android.app.Application
import com.bintang.app.tipcalculator.R
import com.bintang.app.tipcalculator.model.RestaurantCalculator
import com.bintang.app.tipcalculator.model.TipCalculation
import com.bintang.app.tipcalculator.viewModel.CalculatorViewModel
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

class CalculatorViewModelTest {

    lateinit var calculatorViewModel: CalculatorViewModel

    @Mock
    lateinit var mockCalculator: RestaurantCalculator

    @Mock
    lateinit var application: Application

    @Before
    fun setup(){
        MockitoAnnotations.initMocks(this)
        stubResource(0.0, "$0.00")
        calculatorViewModel = CalculatorViewModel(application, mockCalculator)
    }

    private fun stubResource(given:Double, returnStub:String){
        `when`(application.getString(R.string.dollar_amount, given)).thenReturn(returnStub)
    }

    @Test
    fun testCalculateTip(){
        calculatorViewModel.inputCheckAmount = "10.00"
        calculatorViewModel.inputTipPercentage = "15"

        val stub = TipCalculation(checkAmount = 10.00, tipAmount = 1.5, grandTotal = 11.5)
        `when`(mockCalculator.calculateTip(10.00, 15)).thenReturn(stub)
        stubResource(10.0, "$10.00")
        stubResource(1.50, "$1.50")
        stubResource(11.50, "$11.50")

        calculatorViewModel.calculateTip()


        assertEquals("$10.00", calculatorViewModel.outputCheckAmount)
        assertEquals("$1.50", calculatorViewModel.outputTipAmount)
        assertEquals("$11.50", calculatorViewModel.outputTotalDollarAmount)


    }

    @Test
    fun testCalculateTipBadTipPercent() {

        calculatorViewModel.inputCheckAmount = "10.00"
        calculatorViewModel.inputTipPercentage = ""

        calculatorViewModel.calculateTip()

        verify(mockCalculator, never()).calculateTip(anyDouble(),anyInt())

    }

    @Test
    fun testCalculateTipBadCheckInputAmount() {

        calculatorViewModel.inputCheckAmount = ""
        calculatorViewModel.inputTipPercentage = "15"

        calculatorViewModel.calculateTip()

        verify(mockCalculator, never()).calculateTip(anyDouble(),anyInt())


    }

    @Test
    fun testSaveCurrentTip() {

        val stub = TipCalculation(checkAmount = 10.00, tipAmount = 1.5, grandTotal = 11.5)
        val stubLocationName = "Green Eggs and Bacon"

        fun setupTipCalculation() {
            calculatorViewModel.inputCheckAmount = "10.00"
            calculatorViewModel.inputTipPercentage = "15"

            `when`(mockCalculator.calculateTip(10.00, 15)).thenReturn(stub)
        }


        setupTipCalculation()
        calculatorViewModel.calculateTip()

        calculatorViewModel.saveCurrentTip(stubLocationName)
        verify(mockCalculator).saveTipCalculation(stub.copy(locationName = stubLocationName))
        assertEquals(stubLocationName, calculatorViewModel.locationName)

    }


}