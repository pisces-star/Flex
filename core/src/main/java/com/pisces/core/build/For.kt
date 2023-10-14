package com.pisces.core.build

import androidx.annotation.RestrictTo
import com.pisces.core.TemplateNode
import com.pisces.core.context.ScopeContext
import com.pisces.core.eventsystem.EventTarget
import org.apache.commons.jexl3.JexlContext
import org.apache.commons.jexl3.JexlEngine
import java.util.*

@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
object For : Definition() {

    override val dataBinding by DataBinding
        .create {
            text("var")
            value("from")
            value("to")
        }

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
        val attrs = bindProps(rawProps, engine, dataContext, eventDispatcher)
        val name = attrs.getValue("var") as String
        val from = (attrs.getValue("from") as Float).toInt()
        val to = (attrs.getValue("to") as Float).toInt()
        val list = LinkedList<Any>()
        for (index in from..to) {
            val scope = ScopeContext(mapOf(name to index), dataContext)
            val subList = buildTool.buildAll(
                children,
                engine,
                scope,
                eventDispatcher,
                other,
                upperDisplay
            )
            list.addAll(subList)
        }
        return list
    }
}