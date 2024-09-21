package com.jayhymn.farmapp.ui.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.jayhymn.farmapp.Event
import com.jayhymn.farmapp.R
import com.jayhymn.farmapp.domain.InputField
import com.jayhymn.farmapp.domain.ValidationResult
import com.jayhymn.farmapp.ui.state.FarmerRegisterUiState
import com.jayhymn.farmapp.ui.viewmodels.FarmerRegistrationViewModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import javax.inject.Inject

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class FarmRegistrationActivityTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Inject
    lateinit var viewModel: FarmerRegistrationViewModel

    @Before
    fun setUp() {
        hiltRule.inject() // Inject the dependencies
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun testSubmitFormWithValidInput() = runTest {
        val scenario = ActivityScenario.launch(FarmRegistrationActivity::class.java)

        scenario.onActivity { activity ->
            activity.binding.editTextFarmerFirstName.setText("John")
            activity.binding.editTextFarmerLastName.setText("Doe")
            activity.binding.editTextFarmerPhone.setText("08145081945")
            activity.binding.editTextCropType.setText("Cassava")

            activity.binding.btnSubmit.performClick()

            // Check that the submit button is disabled
            assertEquals(false, activity.binding.btnSubmit.isEnabled)

            // Mock the success response
            val successMessageId = R.string.success_message
            val state = FarmerRegisterUiState(successMessage = Event(successMessageId))
            Mockito.`when`(viewModel.state).thenReturn(MutableStateFlow(state))

            // Simulate receiving the state in the lifecycle
            activity.observeViewModelState()

            // Check for the Snackbar
            val snackbar = activity.showSnackbar(successMessageId)
            assertNotNull(snackbar)
        }
    }

    @Test
    fun testSubmitFormWithValidationErrors() = runTest {
        val scenario = ActivityScenario.launch(FarmRegistrationActivity::class.java)

        scenario.onActivity { activity ->
            // Simulate validation errors
            val validationErrors = listOf(
                ValidationResult.Error("First name is required", InputField.FIRST_NAME),
                ValidationResult.Error("Last name is required", InputField.LAST_NAME)
            )
            val state = FarmerRegisterUiState(validationErrors = validationErrors)
            Mockito.`when`(viewModel.state).thenReturn(MutableStateFlow(state))

            // Simulate receiving the state in the lifecycle
            activity.observeViewModelState()

            // Check that errors are displayed
            assertEquals("First name is required", activity.binding.inputLayoutFarmerFirstName.error)
            assertEquals("Last name is required", activity.binding.inputLayoutFarmerLastName.error)
        }
    }

    @Test
    fun testShowSnackbar() {
        val scenario = ActivityScenario.launch(FarmRegistrationActivity::class.java)

        scenario.onActivity { activity ->
            val messageId = R.string.success_message
            activity.showSnackbar(messageId)

            // Check that Snackbar is displayed
            // This would typically require UI testing framework like Espresso to check Snackbar visibility
        }
    }

    @Test
    fun testSetUpCropDropDown() {
        val scenario = ActivityScenario.launch(FarmRegistrationActivity::class.java)

        scenario.onActivity { activity ->
            activity.setUpCropDropDown()
            assertTrue((activity.binding.editTextCropType.adapter?.count ?: 0) > 0)
        }
    }
}
