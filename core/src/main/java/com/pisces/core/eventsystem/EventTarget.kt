package com.pisces.core.eventsystem

import androidx.annotation.RestrictTo
import com.pisces.core.eventsystem.event.TemplateEvent


@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
interface EventTarget {
    @RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
    fun dispatchEvent(e: TemplateEvent<*>): Boolean
}