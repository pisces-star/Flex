package com.pisces.core.eventsystem

import com.pisces.core.eventsystem.event.TemplateEvent
import java.util.*

interface EventHandler<in E : TemplateEvent<*>> : EventListener {
    fun handleEvent(e: E): Boolean
}