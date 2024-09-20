package com.jayhymn.farmapp.ui.presentation

import android.content.Intent
import android.view.View
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.jayhymn.farmapp.ui.state.FarmersUiState
import com.jayhymn.farmapp.ui.viewmodels.FarmersViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import com.jayhymn.farmapp.R


@RunWith(AndroidJUnit4::class)
class FarmersActivityTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var activity: FarmersActivity

    @Mock
    private lateinit var viewModel: FarmersViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        activity = FarmersActivity().apply {
        }
    }

    @Test
    fun testCreateProfileStartsFarmRegistrationActivity() {
        // Arrange
        val intent = Intent(activity, FarmRegistrationActivity::class.java)

        // Act
        activity.binding.createProfile.performClick()

        // Assert
        assertEquals(intent.component, activity.intent?.component)
    }

    @Test
    fun testObserveUiState_NoFarmersShowsNoRecord() {
        // Arrange
        val uiState = FarmersUiState(emptyList(), emptyList())
        `when`(viewModel.uiState).thenReturn(MutableStateFlow(uiState))

        // Act
        activity.observeUiState()

        // Assert
        assertEquals(View.VISIBLE, activity.binding.noRecord.visibility)
    }

    @Test
    fun testHandleErrors_ShowsSnackbar() {
        // Arrange
        val errorMessages = listOf(R.string.error_message)
        val uiState = FarmersUiState(listOf(), errorMessages)
        `when`(viewModel.uiState).thenReturn(MutableStateFlow(uiState))

        // Act
        activity.observeUiState()
    }
}
