import figures.Cone

fun main(args: Array<String>) {
    val pointsCount = 1000
    val coneH = 40.0
    val coneR = 20.0
    val cone = Cone(coneH, coneR)

    val game = Game(cone, pointsCount, 1.0)

    println("cone radius: ${game.cone.r}")
    println("cone height: ${game.cone.h}")
    println("circumscribed sphere radius: ${game.cone.circumscribedSphere.r}")
    println("points: ${game.points}")
    val x = game.points.map { it.x }
    val y = game.points.map { it.y }
    val z = game.points.map { it.z }
    println("xp = $x")
    println("yp = $y")
    println("zp = $z")
    println()
    println(game.analyticCost())
    val netSize = 1000
    game.cone.circumscribedSphere.square.randomizer.setNetSize(netSize)
    println(game.numericCost(netSize*netSize))
}