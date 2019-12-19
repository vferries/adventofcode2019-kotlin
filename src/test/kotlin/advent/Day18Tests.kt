package advent

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day18Tests {
    @Test
    fun `Find shortest path to collect all keys 1`() {
        val map = """#########
#b.A.@.a#
#########""".lines()
        assertEquals(8, shortestPathToCollectKeys(map))
    }

    @Test
    fun `Find shortest path to collect all keys 2`() {
        val map = """########################
#f.D.E.e.C.b.A.@.a.B.c.#
######################.#
#d.....................#
########################""".lines()
        assertEquals(86, shortestPathToCollectKeys(map))
    }

    @Test
    fun `Find shortest path to collect all keys 3`() {
        val map = """########################
#...............b.C.D.f#
#.######################
#.....@.a.B.c.d.A.e.F.g#
########################""".lines()
        assertEquals(132, shortestPathToCollectKeys(map))
    }

    @Test
    fun `Find shortest path to collect all keys 4`() {
        val map = """#################
#i.G..c...e..H.p#
########.########
#j.A..b...f..D.o#
########@########
#k.E..a...g..B.n#
########.########
#l.F..d...h..C.m#
#################""".lines()
        assertEquals(136, shortestPathToCollectKeys(map))
    }

    @Test
    fun `Find shortest path to collect all keys 5`() {
        val map = """#################
#i.G..c...e..H.p#
########.########
#j.A..b...f..D.o#
########@########
#k.E..a...g..B.n#
########.########
#l.F..d...h..C.m#
#################""".lines()
        assertEquals(81, shortestPathToCollectKeys(map))
    }
}
