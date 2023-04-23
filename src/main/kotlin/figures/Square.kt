package figures

import kotlin.math.floor
import kotlin.math.sqrt
import kotlin.random.Random

data class Square(
    val side: Double,
) {

    inner class PointsRandomizer {
        private var netSize = 1
        private var netStep = calcNetStep()
        private var currentRow = 0
        private var currentCol = 0

        fun setNetSize(newNetSize: Int) {
            netSize = newNetSize
            netStep = calcNetStep()
        }

        fun next(): Point {
            if (currentRow == netSize) {
                throw IllegalStateException("reached next() calls limit, increase netSize")
            }
            return Point(
                x = (currentCol + 1).toDouble() * netStep,
                y = (currentRow + 1).toDouble() * netStep,
            ).also { moveToNext() }
        }

        private fun moveToNext() {
            if (currentCol == netSize - 1) {
                currentCol = 0
                currentRow++
            } else {
                currentCol++
            }
        }

        private fun calcNetStep(): Double {
            return this@Square.side / (netSize + 2).toDouble()
        }
    }

    val randomizer = PointsRandomizer()

    data class Point(
        val x: Double,
        val y: Double,
    )
    private val phi = (1+ sqrt(5.0) / 2.0)

    fun distributePoints(n: Int): List<Point> {
        return (0 until n).map { Point(x(it, n),  y(it, n)) }
    }

    private fun x(i: Int, n: Int): Double {
        val t = i.toDouble() / phi
        return (t - floor(t)) * side
    }

    private fun y(i: Int, n: Int): Double {
        return (i.toDouble() / n.toDouble()) * side
    }

    private val random = Random(42)
    fun randomPoint(): Point {
        return Point(side * random.nextDouble(), side * random.nextDouble())
        //return randomizer.next()
    }

}