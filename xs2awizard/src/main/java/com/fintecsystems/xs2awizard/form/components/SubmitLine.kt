package com.fintecsystems.xs2awizard.form.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.fintecsystems.xs2awizard.XS2AWizardViewModel
import com.fintecsystems.xs2awizard.components.theme.XS2ATheme
import com.fintecsystems.xs2awizard.form.SubmitLineData

@Composable
fun SubmitLine(formData: SubmitLineData, viewModel: XS2AWizardViewModel) {
    Column(modifier = Modifier.fillMaxWidth()) {
        FormButton(label = formData.label!!, buttonStyle = XS2ATheme.CURRENT.submitButtonStyle) {
            viewModel.submitForm()
        }

        if (formData.backLabel != null) {
            FormButton(
                label = formData.backLabel,
                buttonStyle = XS2ATheme.CURRENT.backButtonStyle
            ) {
                viewModel.goBack()
            }
        }
    }
}