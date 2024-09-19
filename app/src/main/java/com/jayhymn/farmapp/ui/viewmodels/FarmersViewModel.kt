package com.jayhymn.farmapp.ui.viewmodels

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
    private val formatDateUseCase: FormatDateUseCase,
) : ViewModel() {
    private val _uiState = MutableStateFlow(FarmersUiState())
    val uiState = _uiState.asStateFlow()

    private var fetchJob: Job? = null

    fun createFarmer(firstName: String,
                     lastName: String,
                     cropType: String
    ){
        viewModelScope.launch {
            try {
                farmersRepo.createFarmer(firstName, lastName, cropType)
            } catch (io: IOException){

            }
        }
    }

    fun fetchFarmers(){
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch{
            try {
                val farmers = farmersRepo.getFarmerRecords()
                _uiState.update { state ->
//                    when(farmers){
//                        is Result.Success -> {
//                            it.copy(farmers = result, isLoading = false)
//                        }
//                        is Result.Error -> {
//                            val errorMessages = it.errorMessages + R.string.load_error
//                            it.copy(errorMessages = errorMessages, isLoading = false)
//                        }
//                    }

                    // this maps the farmer model data to what will be the uiState
                    state.copy(farmers = farmers.map { it.toFarmerItemUiState() }, isLoading = false)
                }

            } catch (io: IOException){
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

    fun errorShown(errorId: Long){

    }
}