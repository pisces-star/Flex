package com.pisces.litho.factories.filler

import com.facebook.litho.Component
import com.facebook.litho.widget.HorizontalScroll
import com.facebook.litho.widget.VerticalScroll


internal object ScrollBarEnableFiller : PropFiller<Component.Builder<*>, Boolean> {
    override fun fill(
        c: Component.Builder<*>,
        display: Boolean,
        other: Map<String, Any>,
        value: Boolean
    ) {
        if (c is HorizontalScroll.Builder) {
            c.scrollbarEnabled(value)
        } else if (c is VerticalScroll.Builder) {
            c.scrollbarEnabled(value)
        }
    }
}