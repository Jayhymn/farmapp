package com.jayhymn.farmapp.ui.presentation

import android.content.Intent
import android.view.View
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.jayhymn.farmapp.R
import com.jayhymn.farmapp.ui.state.FarmerItemUiState
import com.jayhymn.farmapp.ui.state.FarmersUiState
import com.jayhymn.farmapp.ui.viewmodels.FarmersViewModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import javax.inject.Inject

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class FarmersActivityTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Inject
    lateinit var viewModel: FarmersViewModel

    @Before
    fun setUp() {
        hiltRule.inject() // Inject the dependencies
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun testCreateProfileStartsFarmRegistrationActivity() {
        val scenario = ActivityScenario.launch(FarmersActivity::class.java)

        scenario.onActivity { activity ->
            activity.binding.createProfile.performClick()
            val expectedIntent = Intent(activity, FarmRegistrationActivity::class.java)
            assertEquals(expectedIntent.component, activity.intent?.component)
        }
    }

    @Test
    fun testObserveUiState_NoFarmersShowsNoRecord() = runTest {
        val uiState = FarmersUiState(emptyList(), emptyList())
        `when`(viewModel.uiState).thenReturn(MutableStateFlow(uiState))

        val scenario = ActivityScenario.launch(FarmersActivity::class.java)

        scenario.onActivity { activity ->
            activity.observeUiState()
            assertEquals(View.VISIBLE, activity.binding.noRecord.visibility)
            assertEquals(View.GONE, activity.binding.farmerList.visibility)
        }
    }

    @Test
    fun testObserveUiState_WithFarmersShowsFarmers() = runTest {
        val farmersList = listOf(FarmerItemUiState(1, "John Doe", "08145081945", "cassava", updatedAt = "2024-08-21 12:43:03"))
        val uiState = FarmersUiState(farmersList, emptyList())
        `when`(viewModel.uiState).thenReturn(MutableStateFlow(uiState))

        val scenario = ActivityScenario.launch(FarmersActivity::class.java)

        scenario.onActivity { activity ->
            activity.observeUiState()
            assertEquals(View.GONE, activity.binding.noRecord.visibility)
            assertEquals(View.VISIBLE, activity.binding.farmerList.visibility)
            assertTrue((activity.binding.farmerList.adapter?.itemCount ?: 0) > 0)
        }
    }

    @Test
    fun testHandleErrors_ShowsSnackbar() = runTest {
        val errorMessages = listOf(R.string.error_message)
        val uiState = FarmersUiState(emptyList(), errorMessages)
        `when`(viewModel.uiState).thenReturn(MutableStateFlow(uiState))

        val scenario = ActivityScenario.launch(FarmersActivity::class.java)

        scenario.onActivity { activity ->
            activity.observeUiState()
            activity.handleErrors(uiState)
            // Verify Snackbar visibility or behavior here
            // Note: You may need a different approach to verify Snackbar visibility
        }
    }
}
