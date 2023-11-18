package com.pisces.litho.factories

import com.facebook.litho.ComponentContext
import com.pisces.core.build.PropSet
import com.pisces.litho.Widget
import com.pisces.litho.factories.filler.PropsFiller
import com.pisces.litho.widget.Stack


internal object ToStack : ToComponent<Stack.Builder>() {

    override val propsFiller = PropsFiller
        .use<Stack.Builder>(CommonProps)

    override fun create(
        c: ComponentContext,
        visibility: Boolean,
        attrs: PropSet
    ): Stack.Builder {
        return Stack.create(c)
    }

    override fun onInstallChildren(
        owner: Stack.Builder,
        visibility: Boolean,
        attrs: PropSet,
        children: List<Widget>
    ) {
        if (children.isEmpty()) {
            return
        }
        owner.children(children)
    }
}