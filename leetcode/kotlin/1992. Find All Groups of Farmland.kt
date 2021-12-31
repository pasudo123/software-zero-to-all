class Solution {
    companion object {
        val move1 = arrayListOf(-1, 1, 0, 0)
        val move2 = arrayListOf(0, 0, 1, -1)
    }

    fun findFarmland(land: Array<IntArray>): Array<IntArray> {

        val rows = land.size
        val cols = land[0].size
        val groups: MutableList<IntArray> = mutableListOf()

        for (row in 0 until rows) {
            for (col in 0 until cols) {
                if (land[row][col] == 0) {
                    continue
                }

                if (land[row][col] == -1) {
                    continue
                }

                // land[row][col] == 1
                groups.add(bfs(row, col, land))
            }
        }

        return groups.toTypedArray()
    }

    private fun bfs(row: Int, col: Int, land: Array<IntArray>): IntArray {
        val startPair: Pair<Int, Int> = Pair(row, col)
        var endPair: Pair<Int, Int>? = null
        var endSum = 0
        val queue = LinkedList<Pair<Int, Int>>()
        queue.add(startPair)
        land[row][col ] = -1

        while (queue.isEmpty().not()) {
            val pair = queue.pop()

            for (index in 0..3) {
                val mr = move1[index] + pair.first
                val mc = move2[index] + pair.second

                if (mr < 0 || mc < 0 || mr >= land.size || mc >= land[0].size) {
                    continue
                }

                if (land[mr][mc] == -1 || land[mr][mc] == 0) {
                    continue
                }

                // visited
                land[mr][mc] = -1
                queue.add(Pair(mr, mc))

                if (mr + mc > endSum) {
                    endSum = mr + mc
                    endPair = Pair(mr, mc)
                }
            }
        }

        return intArrayOf(startPair.first, startPair.second, endPair?.first ?: startPair.first, endPair?.second ?: startPair.second)
    }
}
