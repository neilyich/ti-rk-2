import figures.Cone
import figures.Sphere
import kotlin.math.abs
import kotlin.math.asin
import kotlin.math.sqrt

data class Game(
    val cone: Cone,
    val pointsCount: Int,
    val e: Double,
) {

    val points = cone.distributePoints(pointsCount)

    val spheres = points.map { Sphere(e, it.toPoint3D()) }

    //private val pointsArea = pointsCount * Math.PI * e * e
    private val pointsArea = spheres.sumOf { area(it) }

    fun analyticCost(): Double {
        val solution = pointsArea / cone.area
        if (solution > 1) {
            return 1.0
        }
        return solution
    }

    private fun area(sphere: Sphere): Double {
        if (abs(sphere.center.z - cone.center.z) < 0.00001) {
            return Math.PI * sphere.r * sphere.r
        }
        val r = sqrt(sphere.center.x*sphere.center.x + sphere.center.y*sphere.center.y)
        val alpha = 2 * asin(sphere.r / (2*r))
        val arc = alpha * r
        return Math.PI * arc * sphere.r
    }

    fun numericCost(experiments: Int): Double {
        val successfulExperiments = (0 until experiments).map { experiment() }.count { it }
        return successfulExperiments.toDouble() / experiments.toDouble()
    }

    private fun experiment(): Boolean {
        val randomPoint = cone.randomPoint().toPoint3D()
        return spheres.any { randomPoint in it }
    }

}