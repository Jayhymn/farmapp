package com.jayhymn.farmapp.ui.presentation

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.animation.AnticipateInterpolator
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.animation.doOnEnd
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.jayhymn.farmapp.R
import com.jayhymn.farmapp.databinding.ActivityFarmerBinding
import com.jayhymn.farmapp.ui.state.FarmersUiState
import com.jayhymn.farmapp.ui.viewmodels.FarmersViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FarmersActivity : AppCompatActivity() {
    private val viewModel by viewModels<FarmersViewModel>()
    private lateinit var binding: ActivityFarmerBinding
    private lateinit var adapter: FarmerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S){
            animateSplashScreen()
        }

        binding = ActivityFarmerBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        binding.createProfile.setOnClickListener {
            val intent = Intent(this, FarmRegistrationActivity::class.java)
            startActivity(intent)
        }

        adapter = FarmerAdapter(emptyList())
        binding.farmerList.adapter = adapter
        binding.farmerList.layoutManager = LinearLayoutManager(this)

//        handleWindowInsets()

        observeUiState()
    }

    override fun onResume() {
        super.onResume()
        viewModel.fetchFarmers()
    }


    private fun handleWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.farmer_list)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    @RequiresApi(Build.VERSION_CODES.S)
    // this is used for animating the transition from the splashscreen to this activity
    private fun animateSplashScreen() {
        splashScreen.setOnExitAnimationListener { splashScreenView ->
            // Delay the exit animation to ensure the splash screen is displayed for the full duration
            splashScreenView.postDelayed({
                val slideLeft = ObjectAnimator.ofFloat(
                    splashScreenView,
                    View.TRANSLATION_X,
                    0f,
                    -splashScreenView.width.toFloat()
                )

                slideLeft.interpolator = AnticipateInterpolator()
                slideLeft.duration = 400L

                // Remove the splash screen view after the animation ends
                slideLeft.doOnEnd { splashScreenView.remove() }

                slideLeft.start() // Start the slide animation
            }, 3000) // Delay before starting the exit animation
        }
    }



    private fun observeUiState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { uiState ->
                    binding.noRecord.visibility = if (uiState.farmers.isEmpty()) View.VISIBLE else View.GONE

                    if (uiState.farmers.isNotEmpty()){
                        adapter.updateList(uiState.farmers)
                    }

                    handleErrors(uiState)
                    // Update other UI elements based on uiState
                }
            }
        }
    }

    private fun handleErrors(uiState: FarmersUiState) {
        if (uiState.errorMessages.isNotEmpty()) {
            val errorMessageText = resources.getString(uiState.errorMessages[0])
            val retryMessageText = resources.getString(R.string.retry)

            Snackbar.make(binding.root, errorMessageText, Snackbar.LENGTH_SHORT)
                .setAction(retryMessageText) {
                    viewModel.fetchFarmers()
                }
                .show()
        }
    }
}
