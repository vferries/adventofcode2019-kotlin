package advent

import java.security.SecureRandom
import java.util.EnumMap
import kotlin.math.*

private val input by lazy { loadFile("day25.txt").readText() }

fun main() {
    println("--- Day 25: Cryostasis ---")

    val prog = input.split(',').map { it.toLong() }
    val vm = IntCodeVM(prog)
    //vm.runAsConsole()

    val ai = SaintBernardAI(vm)
    ai.go()

    val ans1 = ai.ans
    println("Part 1: $ans1")
}

private val dirLabels by lazy {
    EnumMap<Dir2, String>(Dir2::class.java).apply {
        put(Dir2.North, "north")
        put(Dir2.South, "south")
        put(Dir2.West, "west")
        put(Dir2.East, "east")
    }
}

private val Dir2.label: String get() = dirLabels.getValue(this)
private const val SECURITY_ROOM_NAME = "Security Checkpoint"
private const val CHECK_ROOM_NAME = "Pressure-Sensitive Floor"
private val dangerousItems by lazy {
    loadFile("day25.txt").readLines().toCollection(hashSetOf<String>())
}
private val passwordRegex by lazy { Regex("""\d+""") }
val Int.numTrailingZeros get() = Integer.numberOfTrailingZeros(this)

class SaintBernardAI(val vm: IntCodeVM) {
    val rooms = mutableMapOf<String, Room>()
    val items = mutableListOf<String>()
    var ans = ""

    fun addRoom(room: Room) {
        rooms[room.name] = room
    }

    fun dfs(pickup: Boolean, prev: Pair<Dir2, Room>?): Room {
        vm.execute()
        val out = vm.outputToAscii().lines().filter { it.isNotBlank() }
        vm.output.clear()

        val name = out[0].removeSurrounding("== ", " ==")
        rooms[name]?.let { room ->
            prev?.let {(dir, neighbor) ->
                room.doors[dir] = neighbor
            }
            return room
        }
        val desc = out[1]
        val room = Room(name, desc)
        addRoom(room)

        prev?.let {(dir, neighbor) ->
            room.doors[dir] = neighbor
            room.path = neighbor.path + (-dir)
        }

        var i = 3
        loop@ while(i < out.size) {
            val ln = out[i++]
            when {
                ln[0] == '-' -> {
                    val dir = Dir2.fromChar(ln[2])
                    if(dir == prev?.first) continue@loop
                    val neighbor =
                        if(name == SECURITY_ROOM_NAME) Room(CHECK_ROOM_NAME, "Analyzing...").also { addRoom(it) }
                        else {
                            vm.inputAscii(dir.label)
                            dfs(pickup, -dir to room)
                        }

                    room.doors[dir] = neighbor
                }
                ln.startsWith("Items") -> {
                    while(i < out.size) {
                        @Suppress("NAME_SHADOWING")
                        val ln = out[i++]
                        if(ln[0] == '-') {
                            val item = ln.removePrefix("- ")
                            if(pickup) {
                                if(item !in dangerousItems) {
                                    vm.inputAscii("take $item")
                                    vm.execute()
                                    vm.output.clear()
                                    items.add(item)
                                }
                            } else items.add(item)
                        }
                        else break
                    }
                    break@loop
                }
            }
        }

        prev?.let {(dir, _) ->
            vm.inputAscii(dir.label)
            vm.execute()
            vm.output.clear()
        }

        return room
    }

    fun go() {
        dfs(true, null)

        // go to security checkpoint
        val securityRoom = rooms[SECURITY_ROOM_NAME]!!
        for (dir in securityRoom.path.toList()) {
            vm.inputAscii(dir.label)
        }
        vm.execute()
        vm.output.clear()

        // find direction to pressure sensitive floor
        val dir = securityRoom.doors.entries.first { it.value.name == CHECK_ROOM_NAME }.key!!

        // bruteforce all 2^8 item combinations

        // Gray code - a sequence with only a single bit difference between consecutive items, which means only one
        // take/drop command

        // 1 bit means the item should be dropped, 0 bit means held. Reversed meaning because we hold all items
        // at the start
        val grayCode = IntArray(1 shl items.size) { it xor it.shr(1) }.iterator()
        var curr = grayCode.next()
        while(true) {
            vm.inputAscii(dir.label)
            vm.execute()
            val out = vm.outputToAscii()
            if("heavier" !in out && "lighter" !in out) {
                // found it! Parse Santa's answer
                ans = passwordRegex.find(out)!!.value
                break
            }

            if(grayCode.hasNext().not()) error("solution not found")
            val next = grayCode.nextInt()
            val d = curr - next
            val item = items[abs(d).numTrailingZeros]
            if(d > 0) vm.inputAscii("take $item")
            else vm.inputAscii("drop $item")
            vm.execute()
            vm.output.clear()
            curr = next
        }
    }
}

class Room(val name: String, val desc: String) {
    val doors = EnumMap<Dir2, Room>(Dir2::class.java)
    var path: PathNode<Dir2>? = null

    override fun toString(): String = name
}


data class Vec2(val x: Int, val y: Int) {
    companion object {
        val ZERO = Vec2(0, 0)
        inline val ORIGIN get() = ZERO
        val READING_ORDER = Comparator(Vec2::compareReadingOrder)
    }

    // Manhattan distance
    fun manDist(other: Vec2) = abs(x - other.x) + abs(y - other.y)
    fun manDist() = abs(x) + abs(y)

    operator fun plus(dir: Dir2) = plus(dir.vec)

    operator fun plus(other: Vec2) = Vec2(x + other.x, y + other.y)
    operator fun minus(other: Vec2) = Vec2(x - other.x, y - other.y)
    operator fun times(scale: Int) = Vec2(x * scale, y * scale)

    fun opposite() = Vec2(-x, -y)
    inline operator fun unaryMinus() = opposite()

    // cross product
    infix fun cross(b: Vec2) = x.toLong() * b.y - y.toLong() * b.x

    override fun hashCode(): Int = x.bitConcat(y).hash().toInt()

    fun compareReadingOrder(b: Vec2): Int {
        y.compareTo(b.y).let { if(it != 0) return it }
        return x.compareTo(b.x)
    }
}

enum class Dir2(val vec: Vec2) {
    Right(Vec2(1, 0)),
    Down(Vec2(0, 1)),
    Left(Vec2(-1, 0)),
    Up(Vec2(0, -1));
    companion object {
        inline val East get() = Right
        inline val South get() = Down
        inline val West get() = Left
        inline val North get() = Up

        val values = values().asList()

        private val fromChar by lazy {
            val arr = arrayOfNulls<Dir2>(128)
            for(c in "RrEe>") arr[c.toInt()] = Right
            for(c in "DdSsv") arr[c.toInt()] = Down
            for(c in "LlWw<") arr[c.toInt()] = Left
            for(c in "UuNn^") arr[c.toInt()] = Up
            arr
        }

        fun fromChar(char: Char) = fromChar.getOrNull(char.toInt()) ?: error("Unrecognized direction: $char")
    }

    fun right() = values[ordinal + 1 and 3]
    inline operator fun inc() = right()

    fun left() = values[ordinal + 3 and 3]
    inline operator fun dec() = left()

    fun opposite() = values[ordinal + 2 and 3]
    inline operator fun unaryMinus() = opposite()
}

fun Int.bitConcat(other: Int) = toLong().shl(32) or other.toLong().and(0xffff_ffff)
fun splitmix64(seed: Long): Long {
    var x = seed - 7046029254386353131
    x = (x xor (x ushr 30)) * -4658895280553007687
    x = (x xor (x ushr 27)) * -7723592293110705685
    return (x xor (x ushr 31))
}
val _seed1 = SecureRandom().nextLong()
fun Long.hash() = splitmix64(_seed1 xor this)

class PathNode<T>(val data: T, val parent: PathNode<T>? = null) {
    operator fun plus(childData: T) = PathNode(childData, this)

    fun toList() = sequence {
        var node = this@PathNode
        while(true) {
            yield(node.data)
            node = node.parent ?: break
        }
    }.toList().asReversed()
}

operator fun <T> PathNode<T>?.plus(childData: T) = PathNode(childData, this)
fun <T> PathNode<T>?.toList() = this?.toList().orEmpty()