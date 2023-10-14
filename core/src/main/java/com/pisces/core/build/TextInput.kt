package com.pisces.core.build

import androidx.annotation.RestrictTo
import com.pisces.core.eventsystem.event.TextChangedEvent

@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
object TextInput : Widget() {
    override val dataBinding by DataBinding
        .create(CommonDefine) {
            event("onTextChanged", TextChangedEvent.Factory)
        }
}