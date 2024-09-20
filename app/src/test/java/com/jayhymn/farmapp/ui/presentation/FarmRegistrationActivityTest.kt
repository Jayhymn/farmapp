import android.content.Intent
import android.view.View
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ActivityScenario
import com.jayhymn.farmapp.CropList
import com.jayhymn.farmapp.R
import com.jayhymn.farmapp.data.repositories.FarmersRepository
import com.jayhymn.farmapp.domain.RegisterFarmerInteractor
import com.jayhymn.farmapp.ui.presentation.FarmRegistrationActivity
import com.jayhymn.farmapp.ui.presentation.FarmersActivity
import com.jayhymn.farmapp.ui.state.FarmerItemUiState
import com.jayhymn.farmapp.ui.state.FarmersUiState
import com.jayhymn.farmapp.ui.viewmodels.FarmersViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*

@ExperimentalCoroutinesApi
class FarmersActivityTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var activity: FarmersActivity
    @Mock
    private lateinit var viewModel: FarmersViewModel
    @Before
    fun setup() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        activity = FarmersActivity().apply {
            setContentView(R.layout.activity_farmer) // Ensure this layout is correct
        }
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        // Clean up any resources if necessary
    }

    @Test
    fun `test create profile button navigates to FarmRegistrationActivity`() {
        // Launch the activity
        val scenario = ActivityScenario.launch(FarmersActivity::class.java)

        // Act
        scenario.onActivity { activity ->
            activity.binding.createProfile.performClick()

            // Assert
            val expectedIntent = Intent(activity, FarmRegistrationActivity::class.java)
            assertEquals(expectedIntent.component, activity.intent?.component)
        }
    }


    @Test
    fun `test observeUiState updates UI for empty farmers list`() = runTest {
        // Arrange
        val uiState = FarmersUiState(emptyList(), emptyList())
        `when`(viewModel.uiState).thenReturn(MutableStateFlow(uiState))

        // Act
        activity.observeUiState()

        // Assert
        assertEquals(View.VISIBLE, activity.binding.noRecord.visibility)
    }

    @Test
    fun `test observeUiState updates UI for non-empty farmers list`() = runTest {
        // Arrange
        val farmersList = CropList.validCropTypes.map {
            FarmerItemUiState(1, "John Doe", "08145081945", "cassava")
        }
        val uiState = FarmersUiState(farmersList, emptyList())
        `when`(viewModel.uiState).thenReturn(MutableStateFlow(uiState))

        // Act
        activity.observeUiState()

        // Assert
        assertEquals(View.GONE, activity.binding.noRecord.visibility)
        assertTrue((activity.binding.farmerList.adapter?.itemCount ?: 0) > 0)
    }

    @Test
    fun `test handleErrors shows Snackbar when there are error messages`() {
        // Arrange
        val errorMessages = listOf(R.string.error_message)
        val uiState = FarmersUiState(emptyList(), errorMessages)

        // Act
        activity.handleErrors(uiState)
    }
}
