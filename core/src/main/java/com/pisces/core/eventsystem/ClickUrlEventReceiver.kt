package com.pisces.core.eventsystem

import android.view.View
import com.pisces.core.build.DataBinder
import com.pisces.core.build.innerExpr
import com.pisces.core.build.isExpr
import com.pisces.core.eventsystem.event.ClickUrlEvent

import org.apache.commons.jexl3.JexlContext
import org.apache.commons.jexl3.JexlEngine

internal class ClickUrlEventReceiver(
    private val eventDispatcher: EventTarget,
    private val url: String
) : ExternalEventReceiver {

    override fun receive(v: View?, args: Array<out Any?>?) {
        eventDispatcher.dispatchEvent(
            ClickUrlEvent(v!!, url)
        )
    }

    companion object Convertor : DataBinder<ClickUrlEventReceiver> {
        override fun cast(
            engine: JexlEngine,
            dataContext: JexlContext,
            eventDispatcher: EventTarget,
            raw: String
        ): ClickUrlEventReceiver {
            val url = if (raw.isExpr) {
                engine.createExpression(raw.innerExpr)
                    .evaluate(dataContext) as? String ?: ""
            } else {
                raw
            }
            return ClickUrlEventReceiver(eventDispatcher, url)
        }
    }
}