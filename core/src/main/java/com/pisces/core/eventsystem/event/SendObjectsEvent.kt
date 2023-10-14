package com.pisces.core.eventsystem.event

class SendObjectsEvent(
    val values: Array<out Any?>
) : TemplateEvent<Unit>(Unit)