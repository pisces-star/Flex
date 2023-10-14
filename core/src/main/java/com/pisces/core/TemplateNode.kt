package com.pisces.core

data class TemplateNode(
    val type: String,
    val attrs: Map<String, String>?,
    val children: List<TemplateNode>?
)
