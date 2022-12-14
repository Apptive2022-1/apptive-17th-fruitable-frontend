package com.fruitable.Fruitable.app.presentation.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fruitable.Fruitable.app.presentation.event.LeaveAppEvent
import com.fruitable.Fruitable.app.presentation.state.TextFieldBoxState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import java.util.regex.Pattern
import javax.inject.Inject

@HiltViewModel
class LeaveAppViewModel @Inject constructor()
: ViewModel() {
    private val _password = mutableStateOf(
        TextFieldBoxState(
        title = "비밀번호",
        hint = "비밀번호를 입력해주세요.",
        error = "비밀번호가 일치하지 않습니다. 다시 입력해주세요."
    )
    )
    val password = _password

    private val _password2 = mutableStateOf(
        TextFieldBoxState(
        title = "비밀번호 재확인",
        hint = "비밀번호를 다시 한번 입력해주세요.",
        error = "비밀번호가 일치하지 않습니다. 다시 입력해주세요"
    )
    )
    val password2 = _password2

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private fun isPasswordValid(value: String): Boolean {
        return value.isNotBlank() && Pattern.matches("^[a-zA-Z0-9]*$", value) && value.length >= 8
    }
    fun isLeavable(): Boolean {
        val passwordList = listOf(password.value.text, password2.value.text)
        return passwordList.all{ isPasswordValid(it) } && passwordList[0] == passwordList[1]
    }

    fun onEvent(event: LeaveAppEvent){
        when (event) {
            is LeaveAppEvent.EnteredPassword -> {
                _password.value = password.value.copy(
                    text = event.value
                )
            }
            is LeaveAppEvent.ChangePasswordFocus -> {
                _password.value = password.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                            password.value.text.isBlank()
                )
            }
            is LeaveAppEvent.EnteredPassword2 -> {
                _password2.value = password2.value.copy(
                    text = event.value
                )
            }
            is LeaveAppEvent.ChangePassword2Focus -> {
                _password2.value = password2.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                            password2.value.text.isBlank()
                )
            }
            LeaveAppEvent.LeaveApp -> {
                _password.value = password.value.copy(
                    isError = !isPasswordValid(password.value.text)
                )
                _password2.value = password2.value.copy(
                    isError = !isPasswordValid(password2.value.text)
                            || password.value.text != password2.value.text
                )
                if (isLeavable()) {
                    viewModelScope.launch {
                        _eventFlow.emit(UiEvent.LeaveApp)
                    }
                }
            }
        }
    }

    sealed class UiEvent {
        object LeaveApp: UiEvent()
    }


}