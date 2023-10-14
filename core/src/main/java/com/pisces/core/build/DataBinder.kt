package com.pisces.core.build

import com.pisces.core.eventsystem.EventTarget
import org.apache.commons.jexl3.JexlContext
import org.apache.commons.jexl3.JexlEngine

internal interface DataBinder<T : Any>{
    fun cast(
        engine: JexlEngine,
        dataContext: JexlContext,
        eventDispatcher: EventTarget,
        raw: String
    ): T?
}
