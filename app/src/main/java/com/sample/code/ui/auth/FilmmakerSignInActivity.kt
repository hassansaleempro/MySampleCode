package com.sample.code.ui.auth

import android.animation.ObjectAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.SpannableString
import android.text.TextWatcher
import android.text.style.UnderlineSpan
import android.widget.ProgressBar
import com.sample.code.viewModel.AuthViewModel
import com.casttypes.app.utils.extensions.showToast
import com.google.gson.Gson
import com.sample.code.R
import com.sample.code.databinding.ActivityFilmmakerSignInBinding
import com.sample.code.util.LoadingDialog
import com.sample.code.util.MyConstants
import com.sample.code.util.SharedPreferencesManager
import com.sample.code.util.network.isOnline
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import androidx.activity.viewModels

@AndroidEntryPoint
class FilmmakerSignInActivity : AppCompatActivity() {
    // View binding for the activity
    private lateinit var binding: ActivityFilmmakerSignInBinding

    // Injecting SharedPreferencesManager to handle shared preferences
    @Inject
    lateinit var sharedPreferencesManager: SharedPreferencesManager

    // Lazy initialization of the loading dialog
    private val loadingDialog: LoadingDialog by lazy { LoadingDialog(this) }

    // ViewModel for authentication logic
    private val viewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFilmmakerSignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        makeTextUnderLine() // Underline specific texts
        setupTextWatchers() // Setup text watchers for email and password inputs
        updateProgress(showError = false) // Initialize progress indicators
        initListners() // Initialize button listeners
        setupViewModel() // Setup ViewModel observers
    }

    /**
     * Sets up ViewModel observers to handle UI updates based on authentication events.
     */
    private fun setupViewModel() {
        with(viewModel) {

            // Observe changes in the progress bar
            progressBar.observe(this@FilmmakerSignInActivity) { oneShotEvent ->
                oneShotEvent.getContentIfNotHandled()?.let {
                    if (it) {
                        loadingDialog.showLoadingDialog() // Show loading dialog
                    } else
                        loadingDialog.dismissLoadingDialog() // Dismiss loading dialog
                }
            }

            // Observe login response data
            loginResponseLiveData.observe(this@FilmmakerSignInActivity) { videoModel ->
                videoModel?.let {
                    run {
                        // Check if the user has a profile image to determine the next activity
                        if (it.data?.user?.profileImage?.isNotEmpty() == true) {
                            // Save access token and user data
                            sharedPreferencesManager.saveString(
                                MyConstants.ACCESS_TOKEN,
                                it.data?.accessToken.toString()
                            )

                            sharedPreferencesManager.saveString(
                                MyConstants.LOCAL_SESSION,
                                MyConstants.GO_TO_TABS
                            )

                            sharedPreferencesManager.saveString(
                                MyConstants.LOCAL_USER,
                                Gson().toJson(it.data?.user)
                            )

                            // Uncomment and use the following block to navigate to the TabsActivity
                            // val intent =
                            //     Intent(
                            //         this@FilmmakerSignInActivity,
                            //         TabsActivity::class.java
                            //     )
                            // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                            // startActivity(intent)
                            // finish()
                        } else {
                            // Save access token and user data for onboarding form
                            sharedPreferencesManager.saveString(
                                MyConstants.ACCESS_TOKEN,
                                it.data?.accessToken.toString()
                            )

                            sharedPreferencesManager.saveString(
                                MyConstants.LOCAL_SESSION,
                                MyConstants.GO_TO_ONBOARDING_FORM
                            )

                            sharedPreferencesManager.saveString(
                                MyConstants.LOCAL_USER,
                                Gson().toJson(it.data?.user)
                            )

                            // Uncomment and use the following block to navigate to the FilmmakerForm1Activity
                            // val intent =
                            //     Intent(
                            //         this@FilmmakerSignInActivity,
                            //         FilmmakerForm1Activtiy::class.java
                            //     )
                            // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                            // startActivity(intent)
                            // finish()
                        }
                    }
                }
            }
        }
    }

    /**
     * Initializes button listeners.
     */
    private fun initListners() {
        binding.loginBtn.setOnClickListener {
            // Check if all input fields are valid
            if (binding.loadingProgressBar.progress == 100) {
                // Check internet connectivity
                if (isOnline(this@FilmmakerSignInActivity)) {
                    // Call login function from ViewModel
                    viewModel.login(
                        context = this@FilmmakerSignInActivity,
                        email = binding.etEmail.text.toString(),
                        password = binding.etPassword.text.toString(),
                        userType = MyConstants.USER_IS_FILM_MAKER
                    )
                } else
                    showToast(getString(R.string.no_internet)) // Show no internet message

            } else {
                updateProgress(showError = true) // Show error if inputs are invalid
            }
        }
    }

    /**
     * Underlines specific texts in the UI.
     */
    private fun makeTextUnderLine() {
        // Create a SpannableString with the text you want to underline
        val content = SpannableString("Forgot Password?")

        // Apply the UnderlineSpan to the entire text
        content.setSpan(UnderlineSpan(), 0, content.length, 0)

        // Set the SpannableString as the text of the TextView
        binding.tvForget.text = content

        // Text to be underlined
        val textToUnderline = "Signup Instead"

        // Create a SpannableString with the text you want to underline
        val content2 = SpannableString(textToUnderline)

        // Apply the UnderlineSpan to the entire text
        content2.setSpan(UnderlineSpan(), 0, textToUnderline.length, 0)

        // Set the SpannableString as the text of the TextView
        binding.tvSignup.text = content2
    }

    /**
     * Sets up text watchers to update the progress indicator as the user inputs data.
     */
    private fun setupTextWatchers() {
        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                updateProgress(showError = false) // Update progress bar without error
            }
        }

        // Attach text watchers to email and password fields
        binding.etEmail.addTextChangedListener(textWatcher)
        binding.etPassword.addTextChangedListener(textWatcher)
    }

    /**
     * Updates the progress indicator based on input validation.
     *
     * @param showError Indicates whether to show an error message for invalid input.
     */
    fun updateProgress(showError: Boolean) {
        val email = binding.etEmail.text.toString().trim()
        val password = binding.etPassword.text.toString().trim()

        // Check conditions for each field
        val isEmailValid =
            email.isNotEmpty() && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
        val isPasswordValid = password.isNotEmpty() && password.length >= 8

        if (showError) {
            // Show error messages for invalid input
            if (!isEmailValid) showToast("Please enter valid email")
            else if (!isPasswordValid) showToast("Please enter valid password")

        } else {
            // Calculate progress based on the completion of fields
            val progress = calculateProgress(isEmailValid, isPasswordValid)

            // Animate the progress bar
            animateProgress(binding.progressBar2, progress)
            animateProgress(binding.loadingProgressBar, progress)
        }
    }

    /**
     * Animates the progress bar to reflect current input validation progress.
     *
     * @param progressBar The ProgressBar to animate.
     * @param progress The target progress value.
     */
    private fun animateProgress(progressBar: ProgressBar, progress: Int) {
        val animation =
            ObjectAnimator.ofInt(progressBar, "progress", progressBar.progress, progress)
        animation.duration = 500 // Duration of the animation in milliseconds
        animation.start() // Start the animation
    }

    /**
     * Calculates the current progress of the input validation.
     *
     * @param isEmailValid Whether the email input is valid.
     * @param isPasswordValid Whether the password input is valid.
     * @return The calculated progress percentage.
     */
    private fun calculateProgress(isEmailValid: Boolean, isPasswordValid: Boolean): Int {
        val totalFields = 2
        var completedFields = 0

        if (isEmailValid) completedFields++
        if (isPasswordValid) completedFields++

        return (completedFields.toDouble() / totalFields.toDouble() * 100).toInt() // Calculate progress percentage
    }
}
