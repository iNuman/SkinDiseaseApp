package com.example.skindiseaseapp.ui.screens.login

import android.app.Activity
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.skindiseaseapp.R
import com.example.skindiseaseapp.ui.screens.login.components.ButtonGoogleSignIn
import com.example.skindiseaseapp.ui.screens.login.googlesignin.GoogleAuthUiClient
import com.example.skindiseaseapp.ui.theme.bold
import com.example.skindiseaseapp.ui.theme.regular
import kotlinx.coroutines.launch

@Composable
fun LoginScreenRoute(
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = hiltViewModel(),
    googleAuthUiClient: GoogleAuthUiClient,
    stopOverridingBackPressForStartScreen: Boolean = true, // to navigate up if the user again open login screen from somewhere we'll pass false to this
    navigateToHome: () -> Unit,
    onContinueWithoutLoginClick: () -> Unit,
    navigateBack: () -> Unit,
) {

    val loginState by viewModel.loginState.collectAsStateWithLifecycle()

    if (!stopOverridingBackPressForStartScreen) {
        BackHandler(enabled = true) {
            navigateBack.invoke()
        }
    }

    Scaffold(
        modifier = modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            LoginScreenContent(state = loginState,
                googleAuthUiClient = googleAuthUiClient,
                onContinueWithoutLoginClick = {
                    onContinueWithoutLoginClick.invoke()
                },
                onGoogleSignInButtonClicked = { idToken ->
                    viewModel.handleUIEvent(LoginScreenEvent.GoogleButton(idToken = idToken))
                },
                navigateToHome = {
                    navigateToHome.invoke()
                })
        }
    }
}

@Composable
fun LoginScreenContent(
    state: LoginState,
    modifier: Modifier = Modifier,
    googleAuthUiClient: GoogleAuthUiClient,
    onContinueWithoutLoginClick: () -> Unit,
    onGoogleSignInButtonClicked: (String) -> Unit,
    navigateToHome: () -> Unit,
) {

    var loading by remember {
        mutableStateOf(false)
    }
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.StartIntentSenderForResult(),
            onResult = { result ->
                Log.d("ffnet", "Activity")
                if (result.resultCode == Activity.RESULT_OK) {
                    scope.launch {
                        val signInResult = googleAuthUiClient.signInWithIntent(
                            intent = result.data ?: return@launch
                        )
                        onGoogleSignInButtonClicked.invoke(signInResult.orEmpty())
                    }
                }
            })

    LaunchedEffect(state) {
        when (state) {
            is LoginState.UserSignIn -> {
                navigateToHome.invoke()
            }

            is LoginState.ErrorSignIn -> {
                Toast.makeText(context, state.errorMessage, Toast.LENGTH_LONG).show()
            }

            is LoginState.UserNotSignIn -> {}
            is LoginState.Loading -> {
                loading = state.loading
            }

            else -> {}
        }
    }

    Box(modifier = modifier.fillMaxSize()) {

        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(16.dp)
                .wrapContentSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_launcher_background), contentDescription = stringResource(
                    id = R.string.app_name
                ), tint = Color.Unspecified, modifier = Modifier
                    .size(width = 72.dp, height = 72.dp)
                    .clip(RoundedCornerShape(64.dp))
            )
            Text(
                text = stringResource(R.string.app_name),
                fontSize = 16.sp,
                fontFamily = regular,
                textAlign = TextAlign.Center
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .wrapContentHeight()
                .align(Alignment.BottomCenter),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ButtonGoogleSignIn(
                onGoogleSignInButtonClick = {
                    onContinueWithoutLoginClick.invoke()
// TODO::Unless the backend setup is done leave as it's
//                    scope.launch {
//                        val intentSender = googleAuthUiClient.signIn()
//                        launcher.launch(
//                            IntentSenderRequest.Builder(
//                                intentSender = intentSender ?: return@launch
//                            ).build()
//                        )
//                    }
                }, loading = loading
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .wrapContentHeight(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                HorizontalDivider(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )

                Text(
                    text = stringResource(id = R.string.or),
                    modifier = Modifier.padding(horizontal = 12.dp),
                    fontSize = 16.sp,
                    fontFamily = bold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                )

                HorizontalDivider(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
            Text(text = stringResource(id = R.string.continue_without_registration),
                fontSize = 16.sp,
                fontFamily = bold,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                textAlign = TextAlign.Center,
                modifier = Modifier.clickable {
                    onContinueWithoutLoginClick.invoke()
                })
        }
    }
}