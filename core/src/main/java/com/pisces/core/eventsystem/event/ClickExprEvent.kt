package com.pisces.core.eventsystem.event

import android.view.View
import org.apache.commons.jexl3.JexlContext
import org.apache.commons.jexl3.JexlScript
import java.util.concurrent.Callable

class ClickExprEvent(
    source: View,
    context: JexlContext,
    script: JexlScript
) : ClickEvent(source), HasExprEvent {
    override val expr: Callable<Any> = script.callable(context)

    companion object Factory : TemplateEvent.Factory {
        override fun create(
            source: View?,
            args: Array<out Any?>?,
            dataContext: JexlContext,
            script: JexlScript
        ): TemplateEvent<*> {
            return ClickExprEvent(source!!, dataContext, script)
        }
    }
}