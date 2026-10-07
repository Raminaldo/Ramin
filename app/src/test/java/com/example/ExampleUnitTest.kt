package com.example

import com.example.data.MunicipalDataProvider
import com.example.viewmodel.AppScreen
import com.example.viewmodel.BottomNavTab
import com.example.viewmodel.MunicipalViewModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class ExampleUnitTest {

    @Test
    fun municipalDataProvider_hasInitialContent() {
        assertTrue("News list should not be empty", MunicipalDataProvider.newsList.isNotEmpty())
        assertTrue("Announcements should not be empty", MunicipalDataProvider.announcementsList.isNotEmpty())
        assertTrue("Hotlines should have emergency and municipal services", MunicipalDataProvider.hotlinesList.size >= 5)
        assertTrue("Officials list should be populated", MunicipalDataProvider.officialsList.isNotEmpty())
        assertTrue("Tax types should be configured", MunicipalDataProvider.taxTypes.isNotEmpty())
        assertTrue("Documents should be available", MunicipalDataProvider.documentsList.isNotEmpty())
    }

    @Test
    fun viewModel_tabSelection_resetsScreenStack() {
        val viewModel = MunicipalViewModel()
        viewModel.navigateTo(AppScreen.AboutMunicipality)
        assertEquals(AppScreen.AboutMunicipality, viewModel.uiState.value.currentScreen)

        viewModel.selectTab(BottomNavTab.NEWS)
        assertEquals(BottomNavTab.NEWS, viewModel.uiState.value.currentTab)
        assertEquals(AppScreen.Main, viewModel.uiState.value.currentScreen)
        assertEquals(1, viewModel.uiState.value.screenStack.size)
    }

    @Test
    fun viewModel_newsSearchAndFilter() {
        val viewModel = MunicipalViewModel()
        viewModel.setNewsSearchQuery("park")
        assertEquals("park", viewModel.uiState.value.newsSearchQuery)

        viewModel.setNewsCategory("Abadlıq")
        assertEquals("Abadlıq", viewModel.uiState.value.selectedNewsCategory)

        viewModel.setSelectedNewsTab(1)
        assertEquals(1, viewModel.uiState.value.selectedNewsTab)

        viewModel.setAnnouncementsFilterStatus("Aktiv")
        assertEquals("Aktiv", viewModel.uiState.value.announcementsFilterStatus)
    }

    @Test
    fun viewModel_propertyTaxCalculation() {
        val viewModel = MunicipalViewModel()
        // 85 sq.m residential: (85 - 30) = 55 sq.m * 0.20 AZN = 11.00 AZN
        viewModel.updatePropertyArea("85")
        assertEquals(11.0, viewModel.uiState.value.calculatedPropertyTax, 0.01)

        // Under 30 sq.m exemption: 25 sq.m should be 0 AZN
        viewModel.updatePropertyArea("25")
        assertEquals(0.0, viewModel.uiState.value.calculatedPropertyTax, 0.01)

        // Commercial: 100 sq.m * 0.40 AZN = 40.00 AZN (no 30 sq.m exemption)
        viewModel.updatePropertyArea("100")
        viewModel.setPropertyIsCommercial(true)
        assertEquals(40.0, viewModel.uiState.value.calculatedPropertyTax, 0.01)

        // Land tax: 10 sot * 0.60 AZN = 6.00 AZN
        viewModel.updateLandArea("10")
        assertEquals(6.0, viewModel.uiState.value.calculatedLandTax, 0.01)
    }

    @Test
    fun viewModel_appealSubmissionAndTracking() {
        val viewModel = MunicipalViewModel()

        // Validation fail on empty fields
        assertFalse(viewModel.submitAppeal())
        assertNotNull(viewModel.uiState.value.formError)

        // Fill required fields
        viewModel.updateFormField(
            name = "Rəşad Əliyev",
            phone = "+994 50 111 22 33",
            fin = "7AB123C",
            address = "M.Müşfiq küçəsi",
            category = "Abadlaşdırma",
            subject = "Küçə işıqlandırılması",
            desc = "Gecə lampası sıradan çıxıb"
        )
        assertNull(viewModel.uiState.value.formError)

        val success = viewModel.submitAppeal()
        assertTrue(success)
        val trackingCode = viewModel.uiState.value.formSubmittedSuccessCode
        assertNotNull(trackingCode)
        assertTrue(trackingCode!!.startsWith("MNG-2026-"))

        // Track the submitted appeal
        viewModel.setTrackingSearchQuery(trackingCode)
        viewModel.trackAppeal()
        val tracked = viewModel.uiState.value.trackedAppeal
        assertNotNull(tracked)
        assertEquals(trackingCode, tracked?.trackingCode)
        assertEquals("Rəşad Əliyev", tracked?.applicantName)
        assertEquals("Göndərildi", tracked?.status)

        // Clear tracking
        viewModel.clearTrackedAppeal()
        assertNull(viewModel.uiState.value.trackedAppeal)
    }

    @Test
    fun viewModel_civicSurveyVoting() {
        val viewModel = MunicipalViewModel()
        val initialVotes = viewModel.uiState.value.survey.totalVotes
        val firstOptionId = viewModel.uiState.value.survey.options.first().id

        viewModel.voteInSurvey(firstOptionId)
        assertEquals(initialVotes + 1, viewModel.uiState.value.survey.totalVotes)
        assertEquals(firstOptionId, viewModel.uiState.value.survey.userVotedOptionId)

        // Prevent double voting
        viewModel.voteInSurvey(firstOptionId)
        assertEquals(initialVotes + 1, viewModel.uiState.value.survey.totalVotes)
    }
}
