package com.pisces.litho.factories

import com.facebook.litho.ComponentContext
import com.pisces.core.build.PropSet
import com.pisces.litho.Widget
import com.pisces.litho.factories.filler.PropsFiller
import com.pisces.litho.widget.Pager


internal object ToPager : ToComponent<Pager.Builder>() {
    override val propsFiller by PropsFiller
        .create<Pager.Builder>(CommonProps) {
            bool("isCircular", Pager.Builder::circular)
            bool("indicatorEnable", Pager.Builder::indicatorEnable)
            value("timeSpan", Pager.Builder::timeSpan)
            enum("orientation", Pager.Builder::orientation)
            pt("indicatorSize", Pager.Builder::indicatorSize)
            pt("indicatorHeight", Pager.Builder::indicatorHeight)
            text("indicatorSelected", Pager.Builder::indicatorSelected)
            text("indicatorUnselected", Pager.Builder::indicatorUnselected)
        }

    override fun create(
        c: ComponentContext,
        visibility: Boolean,
        attrs: PropSet
    ): Pager.Builder {
        return Pager.create(c)
    }

    override fun onInstallChildren(
        owner: Pager.Builder,
        visibility: Boolean,
        attrs: PropSet,
        children: List<Widget>
    ) {
        owner.children(children)
    }

}