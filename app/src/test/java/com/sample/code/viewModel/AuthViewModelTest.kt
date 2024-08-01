import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import android.content.Context
import com.sample.code.data.network.apiResponses.GeneralResponse
import com.sample.code.data.network.apiResponses.SuccessData
import com.sample.code.data.network.apiResponses.models.UserData
import com.sample.code.data.repository.AuthRepository
import com.sample.code.util.network.ResultWrapper
import com.sample.code.viewModel.AuthViewModel

@ExperimentalCoroutinesApi
class AuthViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()
    private val testScope = TestScope(testDispatcher)

    // Mock dependencies
    private lateinit var authViewModel: AuthViewModel
    private val authRepository: AuthRepository = mockk()
    private val context: Context = mockk()

    // Observers for LiveData
    private val loginResponseObserver: Observer<GeneralResponse?> = mockk(relaxed = true)

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)

        authViewModel = AuthViewModel(authRepository).apply {
            loginResponseLiveData.observeForever(loginResponseObserver)
        }
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `login success updates loginResponseLiveData`() = testScope.runTest {
        // Create a successful response with SuccessData
        val successData = SuccessData("1", "abc123", UserData()) // Correct field types
        val successResponse = GeneralResponse(code = 200, message = "Success", data = successData)

        // Mock the repository login method to return a success response
        coEvery { authRepository.login(any(), any(), any()) } returns ResultWrapper.Success(successResponse)

        // Perform the login action
        authViewModel.login(context, "test@example.com", "password", 1)

        // Advance coroutine execution to complete pending tasks
        testDispatcher.scheduler.advanceUntilIdle()

        // Verify that the LiveData observer received the correct data
        verify { loginResponseObserver.onChanged(successResponse) }
    }

    @Test
    fun `login failure shows toast`() = testScope.runTest {
        // Mock a failure response with no data
        val failureResponse = GeneralResponse(code = 401, message = "Unauthorized", data = null)

        // Mock the repository login method to return a failure response
        coEvery { authRepository.login(any(), any(), any()) } returns ResultWrapper.Success(failureResponse)

        // Perform the login action
        authViewModel.login(context, "test@example.com", "wrongpassword", 1)

        // Advance coroutine execution
        testDispatcher.scheduler.advanceUntilIdle()

        // Verify that the LiveData observer received a null response due to failure
        verify { loginResponseObserver.onChanged(null) }
    }
}
