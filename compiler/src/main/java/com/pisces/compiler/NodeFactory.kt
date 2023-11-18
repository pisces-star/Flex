package com.pisces.compiler

interface NodeFactory<T> {
    fun createNode(
        type: String,
        attrs: Map<String, String>,
        children: List<T>
    ): T
}
