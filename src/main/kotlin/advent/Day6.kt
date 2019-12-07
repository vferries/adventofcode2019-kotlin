package advent

import java.io.File

class Node(val label: String, var parent: Node? = null, val children: MutableList<Node> = mutableListOf())

fun generateTree(input: String): List<Node> {
    val pairs = input.lines().map {
        val (father, child) = it.split(')')
        father to child
    }
    val nodes = mutableListOf<Node>()
    pairs.forEach { (parent, child) ->
        var parentNode = nodes.find { it.label == parent }
        if (parentNode == null) {
            parentNode = Node(parent)
            nodes.add(parentNode)
        }
        var childNode = nodes.find { it.label == child }
        if (childNode == null) {
            childNode = Node(child)
            nodes.add(childNode)
        }
        parentNode.children.add(childNode)
        childNode.parent = parentNode
    }
    return nodes
}

fun level(node: Node): Int {
    val parent = node.parent
    return if (parent == null) 0 else 1 + level(parent)
}

fun main(args: Array<String>) {
    //val sample = "COM)B\nB)C\nC)D\nD)E\nE)F\nB)G\nG)H\nD)I\nE)J\nJ)K\nK)L"
    val sample = File("src/main/resources/day6.txt").readText()
    val nodes = generateTree(sample)
    println(nodes.foldRight(0) { node, acc ->
        acc + level(node)
    })
    val sample2 = "COM)B\nB)C\nC)D\nD)E\nE)F\nB)G\nG)H\nD)I\nE)J\nJ)K\nK)L\nK)YOU\nI)SAN"
    val nodes2 = generateTree(sample2)
    println(findPathBetween(nodes, "YOU", "SAN"))
}

fun findPathBetween(nodes: List<Node>, source: String, dest: String): Int {
    val visited = hashSetOf<String>()
    val sourceNode = nodes.find { it.label == source }!!
    var steps = 0
    var nextNodes = listOf(sourceNode)
    visited.add(sourceNode.label)
    while (!visited.contains(dest)) {
        nextNodes = nextNodes
                .flatMap {
                    val parent = it.parent
                    if (parent != null)
                        it.children + parent
                    else
                        it.children
                }
                .filter { !visited.contains(it.label) }
        visited.addAll(nextNodes.map(Node::label))
        steps++
    }
    return steps - 2
}
