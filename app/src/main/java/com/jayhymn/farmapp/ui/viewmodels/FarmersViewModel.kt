package com.jayhymn.farmapp.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import com.jayhymn.farmapp.data.repositories.FarmersRepository
import com.jayhymn.farmapp.ui.state.FarmersUiState
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.io.IOException
import androidx.lifecycle.viewModelScope
import com.jayhymn.farmapp.R
import com.jayhymn.farmapp.domain.FormatDateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class FarmersViewModel @Inject constructor(
    private val farmersRepo: FarmersRepository,
//    private val formatDateUseCase: FormatDateUseCase,
) : ViewModel() {
    private val _uiState = MutableStateFlow(FarmersUiState())
    val uiState = _uiState.asStateFlow()

    private var fetchJob: Job? = null


    fun fetchFarmers(){
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch{
            try {
                _uiState.update{ it.copy(isLoading = true) }

                val farmers = farmersRepo.getFarmerRecords()
                Log.d("FarmersViewModel", "fetchFarmers: $farmers")
                _uiState.update { state ->
                    // this maps the farmer model data to what will be in the uiState
                    state.copy(farmers = farmers.map { it.toFarmerItemUiState() }, isLoading = false)
                }

            } catch (io: IOException){
                Log.e("FarmersViewModel", "Error fetching farmers", io)

                _uiState.update {
                        it.copy(
                            errorMessages = it.errorMessages + R.string.load_error,
                            isLoading = false
                        )
//                    it.copy(errorMessages = getMessagesFromThrowable(io))
                }
            }
        }
    }


    fun errorShown(errorId: Int) {
        _uiState.update { state ->
            state.copy(errorMessages = state.errorMessages.filterNot { it == errorId })
        }
    }

    init {
        fetchFarmers()
    }
}