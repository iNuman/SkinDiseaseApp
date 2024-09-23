package com.example.skindiseaseapp.ui.screens.activity

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import com.example.skindiseaseapp.ui.theme.SkinDiseaseAppTheme
import com.example.skindiseaseapp.SkinApp
import com.example.skindiseaseapp.ui.screens.login.googlesignin.GoogleAuthUiClient
import com.google.android.gms.auth.api.identity.Identity
import javax.inject.Inject
import androidx.compose.material3.Surface
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.skindiseaseapp.core.utils.helper.NetworkMonitor
import com.example.skindiseaseapp.ui.screens.appstate.SkinDiseaseApp
import dagger.hilt.android.AndroidEntryPoint
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.skindiseaseapp.ui.permission.CheckExactAlarmPermission
import com.example.skindiseaseapp.ui.permission.RequestNotificationPermission
import com.example.skindiseaseapp.ui.screens.splash.SplashViewModel
import com.google.accompanist.permissions.rememberPermissionState

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var application: SkinApp

    @Inject
    lateinit var networkMonitor: NetworkMonitor

    private val viewModel: MainViewModel by viewModels()
    private val splashViewModel: SplashViewModel by viewModels()
    

    private val googleAuthUiClient by lazy {
        GoogleAuthUiClient(
            context = application.applicationContext,
            oneTapClient = Identity.getSignInClient(application.applicationContext)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        installSplashScreen().apply {
            setKeepOnScreenCondition {
                !splashViewModel.isReady.value
            }
        }
        setContent {

            val mainState by viewModel.appPreferencesState.collectAsStateWithLifecycle()

            LifecycleEventEffect(event = Lifecycle.Event.ON_CREATE) {
                viewModel.handleScreenEvents(MainScreenEvent.CheckUserAuthState)
                viewModel.handleScreenEvents(MainScreenEvent.ThemeChanged)
            }


            LaunchedEffect(application.theme.value) {
                viewModel.handleScreenEvents(MainScreenEvent.ThemeChanged)
            }
            val localApplication = staticCompositionLocalOf {
                SkinApp()
            }
            CompositionLocalProvider(localApplication provides application) {
                SkinDiseaseAppTheme(appTheme = application.theme.value) {
                    Surface(
                        modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                    ) {
                        SkinDiseaseApp(
                            isAuthenticated = mainState.userAuth,
                            networkMonitor = networkMonitor,
                            googleAuthUiClient = googleAuthUiClient,
                        )
                    }
                }
            }
        }
    }

}


//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    SkinDiseaseAppTheme {
//        Column {
//            MainScreen()
//        }
//    }
//}