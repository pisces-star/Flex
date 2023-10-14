package com.pisces.core.eventsystem.event

import android.view.View

abstract class ClickEvent(
    source: View
) : TemplateEvent<View>(source)