package com.example.projectmanagement.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.projectmanagement.R
import com.example.projectmanagement.constants.AppColor
import com.example.projectmanagement.constants.IFont

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ITextField(
    modifier: Modifier = Modifier,
    text: String,
    label: String? = null,
    hint: String? = null,
    trailingIcon: Painter? = null,
    keyboardType: KeyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
    onChange: (String) -> Unit,
) {
    var passwordVisible by rememberSaveable { mutableStateOf(false) }

    var isFocus by remember {
        mutableStateOf(false)
    }

    Column(modifier = modifier.fillMaxWidth()) {

        label?.let {
            AppTextMedium(
                text = it,
                color = AppColor.textLabelColor,
                fontSize = 16
            )
        }

        Spacer(modifier = Modifier.height(6.dp))
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .background(AppColor.white)
                .onFocusChanged {
                    isFocus = it.isFocused
                },
            value = text,
            maxLines = 5, // Add this line
            minLines = 3, // Add this line
            singleLine = false, // Add this line
            textStyle = TextStyle(
                fontFamily = IFont.customFontFamily,
                fontSize = 16.sp,
                textAlign = TextAlign.Start,
                fontWeight = FontWeight.Bold
            ),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = AppColor.textLabelColor,
                unfocusedTextColor = AppColor.textLabelColor,
                focusedBorderColor = AppColor.primaryColor,
                unfocusedBorderColor = AppColor.textBorderColor,
            ),
            shape = RoundedCornerShape(8.dp),
            onValueChange = onChange,
//            label = {
//                label?.let {
//                    if (text.isEmpty() && !isFocus)
//                        ITextMedium(
//                            text = it,
//                            fontSize = 16,
//                            color = IColor.textFieldHintColor
//                        )
//                }
//
//            },
            placeholder = {
                hint?.let {
                    if (text.isEmpty()&& !isFocus)
                        AppTextMedium(
                            text = hint,
                            fontSize = 16,
                            color = AppColor.textFieldHintColor
                        )
                }
            },
            visualTransformation = if (passwordVisible || keyboardType.keyboardType != KeyboardType.Password) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = keyboardType,
            trailingIcon = {
                if(keyboardType.keyboardType == KeyboardType.Password){
                    val image = if (passwordVisible)
                        painterResource(R.drawable.ic_eye_slash)
                    else painterResource(R.drawable.ic_eye)

                    // Please provide localized description for accessibility services
                    val description = if (passwordVisible) "Hide password" else "Show password"

                    IconButton(onClick = {passwordVisible = !passwordVisible}){
                        Icon(painter  = image, description)
                    }
                }else{
                    trailingIcon?.let {
                        Icon(
                            painter = trailingIcon,
                            contentDescription = "trailing"
                        )
                    }
                }
            }
        )
    }
}

