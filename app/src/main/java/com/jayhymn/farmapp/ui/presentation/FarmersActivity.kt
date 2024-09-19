package com.jayhymn.farmapp.ui.presentation

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.snackbar.Snackbar
import com.jayhymn.farmapp.R
import com.jayhymn.farmapp.databinding.ActivityFarmerBinding
import com.jayhymn.farmapp.ui.viewmodels.FarmersViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FarmersActivity : AppCompatActivity() {
    private val viewModel by viewModels<FarmersViewModel>()
    private lateinit var binding: ActivityFarmerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFarmerBinding.inflate(layoutInflater)

        enableEdgeToEdge()
        setContentView(binding.root)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.uiState.collect {
                    //check for error messages
                    if (it.errorMessages.isNotEmpty()){
                        val errorMessageText = resources.getString(it.errorMessages[0])
                        val retryMessageText = resources.getString(R.string.retry)

                        val snackBar = Snackbar.make(binding.root, errorMessageText, Snackbar.LENGTH_SHORT)
                        snackBar.setAction(retryMessageText) {
                            viewModel.fetchFarmers()
                        }
                        snackBar.show()
                    }

//                    onErrorDismissed(viewModel.uiState)

                    //update ui elements
                }
            }
        }

//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }
    }
}