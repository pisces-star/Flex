package com.pisces.core.build

import androidx.annotation.RestrictTo
import com.pisces.core.TemplateNode
import com.pisces.core.eventsystem.EventTarget
import org.apache.commons.jexl3.JexlContext
import org.apache.commons.jexl3.JexlEngine

@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
class ToWidget(
    private val definition: Definition,
    private val factory: RenderNodeFactory<*>?
) {
    fun toWidget(
        bindings: BuildTool,
        template: TemplateNode,
        engine: JexlEngine,
        dataContext: JexlContext,
        eventDispatcher: EventTarget,
        other: Any?,
        upperDisplay: Boolean = true
    ): List<Any> {
        return definition.onBuildWidget(
            bindings,
            template.attrs ?: emptyMap(),
            template.children ?: emptyList(),
            factory,
            engine,
            dataContext,
            eventDispatcher,
            other,
            upperDisplay
        )
    }
}