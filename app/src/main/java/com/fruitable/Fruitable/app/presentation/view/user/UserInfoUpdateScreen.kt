package com.fruitable.Fruitable.app.presentation.view

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.fruitable.Fruitable.app.presentation.component.FruitableButton
import com.fruitable.Fruitable.app.presentation.component.FruitableTitle
import com.fruitable.Fruitable.app.presentation.component._feature.FruitablePopUp
import com.fruitable.Fruitable.app.presentation.component._feature.TextFieldBox
import com.fruitable.Fruitable.app.presentation.event.UserInfoUpdateEvent
import com.fruitable.Fruitable.app.presentation.navigation.Screen
import com.fruitable.Fruitable.app.presentation.viewmodel.UserInfoUpdateViewModel
import com.fruitable.Fruitable.ui.theme.MainGreen1
import com.fruitable.Fruitable.ui.theme.MainGreen4
import kotlinx.coroutines.flow.collectLatest

@Composable
fun UserInfoUpdateScreen(
    navController: NavController,
    viewModel: UserInfoUpdateViewModel = hiltViewModel()
) {
    var nicknameState = viewModel.nickname.value
    val passwordState = viewModel.password.value
    val newPasswordState = viewModel.newPassword.value
    val newPasswordState2 = viewModel.newPassword2.value

    val scaffoldState = rememberScaffoldState()
    val focusRequester = remember { FocusRequester() }

    val nicknameColor = if (viewModel.isNicknameUpdatable()) MainGreen1 else MainGreen4
    val passwordColor = if (viewModel.isPasswordUpdatable()) MainGreen1 else MainGreen4

    var nicknameDialogOpen by remember { mutableStateOf(false) }
    var passwordDialogOpen by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is UserInfoUpdateViewModel.UiEvent.SaveUserNickname -> {
                    nicknameDialogOpen = true
                }
                is UserInfoUpdateViewModel.UiEvent.SaveUserPassword -> {
                    passwordDialogOpen = true
                }
            }
        }
    }
    FruitablePopUp(
        text = "???????????? ?????????????????????.",
        cancel = { nicknameDialogOpen = false},
        confirm = { navController.navigate(Screen.SalesScreen.route) },
        isOpen = nicknameDialogOpen
    )
    FruitablePopUp(
        text = "??????????????? ?????????????????????.",
        cancel = { passwordDialogOpen = false},
        confirm = { navController.navigate(Screen.SalesScreen.route) },
        isOpen = passwordDialogOpen
    )
    FruitableTitle(
        title = "???????????? ??????",
        subtitle = "???????????? ??????????????? ????????? ??? ???????????? !"
    ) {
        TextFieldBox(
            state = nicknameState,
            modifier = Modifier.focusRequester(focusRequester),
            onValueChange = { viewModel.onEvent(UserInfoUpdateEvent.EnteredNickname(it)) },
            onFocusChange = { viewModel.onEvent(UserInfoUpdateEvent.ChangeNicknameFocus(it)) },
        )
        Spacer(modifier = Modifier.height(16.dp))
        FruitableButton(
            text = "????????? ??????",
            color = nicknameColor,
            textColor = Color.White,
            modifier = Modifier
                .fillMaxWidth()
                .height(44.dp),
            onClick = { viewModel.onEvent(UserInfoUpdateEvent.NicknameSave) }
        )
        Spacer(modifier = Modifier.height(28.dp))
        TextFieldBox(
            state = passwordState,
            modifier = Modifier.focusRequester(focusRequester),
            visualTransformation = PasswordVisualTransformation(),
            onValueChange = { viewModel.onEvent(UserInfoUpdateEvent.EnteredPassword(it)) },
            onFocusChange = { viewModel.onEvent(UserInfoUpdateEvent.ChangePasswordFocus(it)) },
        )
        Spacer(modifier = Modifier.height(28.dp))
        TextFieldBox(
            state = newPasswordState,
            modifier = Modifier.focusRequester(focusRequester),
            visualTransformation = PasswordVisualTransformation(),
            onValueChange = { viewModel.onEvent(UserInfoUpdateEvent.EnteredNewPassword(it)) },
            onFocusChange = { viewModel.onEvent(UserInfoUpdateEvent.ChangeNewPasswordFocus(it)) },
        )
        Spacer(modifier = Modifier.height(28.dp))
        TextFieldBox(
            state = newPasswordState2,
            modifier = Modifier.focusRequester(focusRequester),
            visualTransformation = PasswordVisualTransformation(),
            onValueChange = { viewModel.onEvent(UserInfoUpdateEvent.EnteredNewPassword2(it)) },
            onFocusChange = { viewModel.onEvent(UserInfoUpdateEvent.ChangeNewPasswordFocus2(it)) },
        )
        Spacer(modifier = Modifier.height(16.dp))
        FruitableButton(
            text = "???????????? ??????",
            color = passwordColor,
            textColor = Color.White,
            modifier = Modifier
                .fillMaxWidth()
                .height(44.dp),
            onClick = { viewModel.onEvent(UserInfoUpdateEvent.PasswordSave) }
        )
    }

}
