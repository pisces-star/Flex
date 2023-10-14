package com.pisces.core.build

import androidx.annotation.RestrictTo
import com.pisces.core.TemplateNode

import com.pisces.core.eventsystem.EventTarget
import org.apache.commons.jexl3.JexlContext
import org.apache.commons.jexl3.JexlEngine

@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
object Empty : Widget() {

    override val dataBinding: DataBinding
        get() = CommonDefine.dataBinding

    override fun onBuildWidget(
        buildTool: BuildTool,
        rawProps: Map<String, String>,
        children: List<TemplateNode>,
        factory: RenderNodeFactory<*>?,
        engine: JexlEngine,
        dataContext: JexlContext,
        eventDispatcher: EventTarget,
        other: Any?,
        upperDisplay: Boolean
    ): List<Any> {
        return super.onBuildWidget(
            buildTool,
            rawProps,
            children,
            factory,
            engine,
            dataContext,
            eventDispatcher,
            other,
            false
        )
    }
}