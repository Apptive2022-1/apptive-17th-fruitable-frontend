package com.fruitable.Fruitable.app.presentation.view.setting

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.fruitable.Fruitable.app.presentation.component.FruitableButton
import com.fruitable.Fruitable.app.presentation.component.FruitableDivider
import com.fruitable.Fruitable.app.presentation.component.FruitableTitle
import com.fruitable.Fruitable.app.presentation.component._feature.FruitablePopUp
import com.fruitable.Fruitable.app.presentation.component._feature.TextFieldBox
import com.fruitable.Fruitable.app.presentation.component._view.FruitableCheckBox
import com.fruitable.Fruitable.app.presentation.event.LeaveAppEvent
import com.fruitable.Fruitable.app.presentation.navigation.Screen
import com.fruitable.Fruitable.app.presentation.viewmodel.LeaveAppViewModel
import com.fruitable.Fruitable.ui.theme.MainGreen1
import com.fruitable.Fruitable.ui.theme.TextStyles
import kotlinx.coroutines.flow.collectLatest

@Composable
fun LeaveAppScreen(
    navController: NavController,
    viewModel: LeaveAppViewModel = hiltViewModel()
){
    val passwordState = viewModel.password.value
    val password2State = viewModel.password2.value

    val focusRequester = remember { FocusRequester() }
    var isChecked by remember { mutableStateOf(false) }
    var isDialogOpen by remember { mutableStateOf(false) }

    val isLeavable = viewModel.isLeavable() && isChecked


    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is LeaveAppViewModel.UiEvent.LeaveApp -> {
                    if (isChecked) isDialogOpen = true
                }
            }
        }
    }
    FruitablePopUp(
        text  = "?????? ??? ?????? ????????? ?????? ?????????\n" +
                "???????????? ???????????? ????????????.\n" +
                "?????? ?????????????????????????",
        cancelText = "??????",
        confirmText = "????????????",
        cancel = { isDialogOpen = false},
        confirm = { navController.navigate(Screen.SignInScreen.route) },
        isOpen = isDialogOpen
    )
    Scaffold(
        bottomBar = {
            Column(
                modifier = Modifier
                    .background(Color.White)
                    .fillMaxWidth()
                    .alpha(if (isLeavable) 1f else 0.7f)
            ) {
                FruitableDivider()
                FruitableButton(
                    text = "????????????",
                    color = MainGreen1,
                    textColor = Color.White,
                    modifier = Modifier
                        .padding(30.dp, 14.dp, 30.dp, 30.dp)
                        .fillMaxWidth()
                        .height(44.dp),
                    onClick = { viewModel.onEvent(LeaveAppEvent.LeaveApp) }
                )
            }
        }
    ) {
        FruitableTitle(
            title = "?????? ??????",
            subtitle = "?????????????????? ??????????????? ???????????????."
        ) {
            Text(
                text = "?????? ?????? ??????",
                style = TextStyles.TextBold6
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "???????????? ?????????????????? ?????????????????? ???????????????.\n" +
                        "????????? ??????????????? ??????????????? ????????? ??? ????????? " +
                        "????????? ???????????? ????????? ??? ????????????.",
                style = TextStyles.TextSmall2,
            )
            FruitableCheckBox(
                modifier = Modifier.padding(top = 30.dp, bottom = 40.dp),
                isChecked = isChecked,
                borderColor = MainGreen1,
                backgroundColor = MainGreen1,
                onClick = { isChecked = !isChecked }
            )
            TextFieldBox(
                state = passwordState,
                modifier = Modifier.focusRequester(focusRequester),
                visualTransformation = PasswordVisualTransformation(),
                onValueChange = { viewModel.onEvent(LeaveAppEvent.EnteredPassword(it)) },
                onFocusChange = { viewModel.onEvent(LeaveAppEvent.ChangePasswordFocus(it)) },
            )
            Spacer(modifier = Modifier.height(28.dp))
            TextFieldBox(
                state = password2State,
                modifier = Modifier.focusRequester(focusRequester),
                visualTransformation = PasswordVisualTransformation(),
                onValueChange = { viewModel.onEvent(LeaveAppEvent.EnteredPassword2(it)) },
                onFocusChange = { viewModel.onEvent(LeaveAppEvent.ChangePassword2Focus(it)) },
            )
            Spacer(modifier = Modifier.height(100.dp))
        }
    }
}