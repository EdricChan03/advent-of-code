package com.edricchan.aoc.model.tree

/**
 * Represents a node in a tree data structure.
 * @property data The data that this node contains.
 * @property parent The parent of this node, if any.
 * @property children The children of this node, if any.
 */
data class TreeNode<T>(
    val data: T,
    val parent: TreeNode<T>? = null,
    val children: MutableList<TreeNode<T>> = mutableListOf()
) {
    /** Adds a child [node] to this node. */
    fun addChild(node: TreeNode<T>) {
        children += node
    }

    /** Adds the specified list of child [nodes] to this node. */
    fun addChildren(nodes: List<TreeNode<T>>) {
        children += nodes
    }

    fun getPrettyTree(indentSize: Int = 0, prettyFn: (TreeNode<T>) -> String): String {
        return buildString {
            appendLine("${" ".repeat(indentSize)}- ${prettyFn(this@TreeNode)}")
            children.forEach {
                append(it.getPrettyTree(indentSize + 2, prettyFn))
            }
        }
    }
}

/** Converts the given [T] to a [TreeNode]. */
fun <T> T.toTreeNode(
    parent: TreeNode<T>? = null, children: List<TreeNode<T>> = emptyList()
) = TreeNode(this, parent, children.toMutableList())
