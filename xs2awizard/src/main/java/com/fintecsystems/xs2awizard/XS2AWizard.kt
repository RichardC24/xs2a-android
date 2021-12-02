package com.fintecsystems.xs2awizard

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.fintecsystems.xs2awizard.components.XS2AWizardConfig
import com.fintecsystems.xs2awizard.components.XS2AWizardError
import com.fintecsystems.xs2awizard.components.theme.XS2ATheme
import com.fintecsystems.xs2awizard.form.FormLineData
import com.fintecsystems.xs2awizard.form.FormResponse
import com.fintecsystems.xs2awizard.form.ParagraphLineData
import com.fintecsystems.xs2awizard.form.components.ParagraphLine
import com.fintecsystems.xs2awizard_networking.NetworkingInstance
import kotlinx.serialization.json.jsonPrimitive

class XS2AWizardViewModel : ViewModel() {
    private lateinit var networkingInstance: NetworkingInstance
    private lateinit var config: XS2AWizardConfig

    val form = MutableLiveData<List<FormLineData>>()

    fun onStart(context: Context, _config: XS2AWizardConfig) {
        networkingInstance = NetworkingInstance.getInstance(context)
        config = _config

        networkingInstance.apply {
            sessionKey = config.sessionKey
            backendURL = config.backendURL
        }
    }

    /**
     * Parses [FormResponse] for possible callbacks
     *
     * @param response [FormResponse] to parse.
     */
    private fun parseCallback(response: FormResponse) {
        when (response.callback) {
            "finish" -> config.onFinish(response.callbackParams?.getOrNull(0)?.jsonPrimitive?.content)
            "abort" -> config.onAbort()
        }

        if (response.error != null && response.error == "tech_error") {
            config.onError(
                XS2AWizardError(
                    response.error,
                    emptyList(),
                    false
                )
            )
        }
    }
}

@Composable
fun XS2AWizardComponent(
    xS2AWizardConfig: XS2AWizardConfig,
    xs2aWizardViewModel: XS2AWizardViewModel = viewModel()
) {
    val form by xs2aWizardViewModel.form.observeAsState(null)

    val localContext = LocalContext.current

    DisposableEffect(xs2aWizardViewModel) {
        xs2aWizardViewModel.onStart(localContext, xS2AWizardConfig)

        onDispose { }
    }

    // Helper methods

    // Render
    XS2ATheme(xS2ATheme = xS2AWizardConfig.theme) {
        form?.let { FormLines(it) }
    }
}

@Composable
fun FormLines(formData: List<FormLineData>) {
    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        for (formLineData in formData) {
            when (formLineData) {
                // is AutoSubmitLineData -> AutoSubmitLine(formLineData)
                is ParagraphLineData -> ParagraphLine(formLineData)
                // is DescriptionLineData -> DescriptionLine(formLineData)
                // is TextLineData -> TextLine(formLineData)
                // is PasswordLineData -> PasswordLine(formLineData)
                // is CaptchaLineData -> CaptchaLine(formLineData)
                // is SelectLineData -> SelectLine(formLineData)
                // is CheckBoxLineData -> CheckBoxLine(formLineData)
                // is HiddenLineData -> HiddenLine(formLineData)
                // is RadioLineData -> RadioLine(formLineData)
                // is ImageLineData -> ImageLine(formLineData)
                // is LogoLineData -> LogoLine(formLineData)
                // is FlickerLineData -> FlickerLine(formLineData)
                // is SubmitLineData -> SubmitLine(formLineData)
                // is AbortLineData -> AbortLine(formLineData)
                // is RestartLineData -> RestartLine(formLineData)
                // is TabsLineData -> TabsLine(formLineData)
                // is RedirectLineData -> RedirectLine(formLineData)
                // is MultiLineData -> MultiLine(formLineData)
            }
        }
    }
}

/**
 * Wrapper for the XS2A-Wizard Compose Component
 */
class XS2AWizard(
    private val xS2AWizardConfig: XS2AWizardConfig
) : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                XS2AWizardComponent(xS2AWizardConfig)
            }
        }
    }
}