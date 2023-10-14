package com.pisces.litho.factories.filler

import com.facebook.litho.ClickEvent
import com.facebook.litho.Component
import com.pisces.core.eventsystem.ExternalEventReceiver
import com.pisces.litho.LithoEventHandler

internal object ClickUrlFiller : PropFiller<Component.Builder<*>, ExternalEventReceiver> {
    override fun fill(
        c: Component.Builder<*>,
        display: Boolean,
        other: Map<String, Any>,
        value: ExternalEventReceiver
    ) {
        if (!other.containsKey("onClick")) {
            c.clickHandler(LithoEventHandler<ClickEvent>(value))
        }
    }
}