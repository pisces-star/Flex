package com.pisces.litho.factories

import com.facebook.litho.ComponentContext
import com.facebook.litho.widget.TextInput
import com.pisces.core.build.PropSet
import com.pisces.litho.factories.filler.PropsFiller

internal object ToTextInput : ToComponent<TextInput.Builder>() {
    override val propsFiller by PropsFiller
        .create<TextInput.Builder>(CommonProps) {
            value("maxLines", TextInput.Builder::maxLines)
            value("minLines", TextInput.Builder::minLines)
            pt("textSize", TextInput.Builder::textSizePx)
            enum("ellipsize", TextInput.Builder::ellipsize)
            event("onTextChanged", TextInput.Builder::textChangedEventHandler)
            textStyle("textStyle", TextInput.Builder::typeface)
        }

    override fun create(
        c: ComponentContext,
        visibility: Boolean,
        attrs: PropSet
    ): TextInput.Builder {
        return TextInput.create(c)
            .inputBackground(null)
    }
}