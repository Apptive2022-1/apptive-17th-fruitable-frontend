package com.fruitable.Fruitable.app.presentation.component

import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.fruitable.Fruitable.R
import com.fruitable.Fruitable.app.domain.utils.NoRippleInteractionSource
import com.fruitable.Fruitable.ui.theme.*

@Composable
fun SignButton(
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier,
    text: String = "회원가입",
    isRipple: Boolean = true,
    style: TextStyle = TextStyles.TextBasic3,
    isCancellable: Boolean = false,
    color: Color = Color.White,
    textColor: Color = MainGreen1,
){
    val buttonColor = ButtonDefaults.buttonColors(color)
    Button(
        onClick = onClick,
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .border(1.dp,textColor,RoundedCornerShape(10.dp)),
        contentPadding = if (isCancellable) PaddingValues(start = 9.dp, end = 12.dp,top=0.dp,bottom=0.dp)
                         else PaddingValues(horizontal = 15.dp),
        colors = buttonColor,
        interactionSource = if (isRipple) MutableInteractionSource()
                            else NoRippleInteractionSource(),
    ) {
        Text(
            text = text,
            color = textColor,
            style = style,
            textAlign = TextAlign.Center,
            modifier = Modifier.align(Alignment.CenterVertically)
        )
    }
}