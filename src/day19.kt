import java.io.File
import kotlin.math.abs
import kotlin.math.max

fun main() {
    val input = File("src/input19.txt").readLines()
    val scanners = mutableListOf<Scanner>()
    var setupScanner = Scanner(-1)
    input.forEach {
        when {
            it.contains("scanner") -> {
                val id = it.split("scanner ")[1].split(" ")[0].toInt()
                setupScanner = Scanner(id)
            }
            it.isEmpty() -> scanners.add(setupScanner)
            else -> {
                val coords = it.trim().split(',').map{it.toInt()}
                setupScanner.addBeacon(Beacon(coords[0], coords[1], coords[2]))
            }
        }
    }
    scanners.add(setupScanner)
    val locatedScanners = mutableListOf<Scanner>()
    locatedScanners.add(scanners[0])
    scanners.remove(scanners[0])

    while (scanners.isNotEmpty()) {
        val toAdd = mutableSetOf<Scanner>()
        locatedScanners.forEach { locatedScanner ->
            scanners.forEach { scanner ->
                if (locatedScanner.check(scanner)) {
                    toAdd.add(scanner)
                }
            }
        }

        locatedScanners.addAll(toAdd)
        scanners.removeAll(locatedScanners)
    }
    val uniqueBeacons = locatedScanners.map {it.beacons}.flatten().distinct()

    var distance = 0
    locatedScanners.sortedBy { it.id }.forEachIndexed { idx, scanner1 ->
        for (i in idx + 1 .. locatedScanners.lastIndex) {
            distance = max(manhattan(scanner1, locatedScanners[i]), distance)
        }
    }

    println(uniqueBeacons.size)
    println(distance)
}


class Scanner(val id: Int) {
    var x: Int = 0
    var y: Int = 0
    var z: Int = 0
    var beacons = mutableListOf<Beacon>()
    val checkedScanners = mutableListOf<Scanner>()
    var found = false

    fun addBeacon(beacon: Beacon) {
        this.beacons.add(beacon)
    }

    fun print() {
        println("--- Scanner $id --- Located at $x, $y, $z")
        beacons.sortedBy { it.x }. forEach {
            println("${it.x}, ${it.y}, ${it.z}")
        }
        println()
    }

    // this function is only called by scanners that we already know the absolute location of
    fun check(scanner: Scanner): Boolean {
        if (checkedScanners.contains(scanner) || scanner.found) {
            return false
        }
        checkedScanners.add(scanner)
        for (i in 1..24) {
            // rotate the beacons across all 24 possible rotations
            val scannerBeacons = scanner.applyRotations(i)
            scannerBeacons.forEach { scannerBeacon ->
                beacons.forEach { beacon ->
                    // take a rotated beacon and a known beacon and set the scanner's position assuming they're the
                    // same beacon. Repeat for all known beacons and all rotated beacons in each scanner
                    scanner.x = beacon.x - scannerBeacon.x
                    scanner.y = beacon.y - scannerBeacon.y
                    scanner.z = beacon.z - scannerBeacon.z

                    // check and see how many beacons match with the position/rotation
                    val matches = beacons.map {Beacon(it.x - scanner.x, it.y - scanner.y,it.z -  scanner.z)}.count{scannerBeacons.contains(it)}

                    // if >= 12, map the relative beacon positions to absolute positions and mark the scanner as found
                    if (matches >= 12) {
                        scanner.beacons = scannerBeacons.map {Beacon (scanner.x + it.x, scanner.y + it.y, scanner.z + it.z)}.toMutableList()
                        scanner.found = true
                        return true
                    }
                }
            }
        }
        return false
    }

    private fun applyRotations(i: Int): List<Beacon> {
        return when (i) {
            // upright
            1 -> beacons.map {  Beacon( it.x,  it.y, it.z) }
            2 -> beacons.map {  Beacon( it.y, -it.x, it.z) }
            3 -> beacons.map {  Beacon(-it.x, -it.y, it.z) }
            4 -> beacons.map {  Beacon(-it.y,  it.x, it.z) }

            // upside down
            5 -> beacons.map {  Beacon(-it.x,  it.y, -it.z) }
            6 -> beacons.map {  Beacon( it.y,  it.x, -it.z) }
            7 -> beacons.map {  Beacon( it.x, -it.y, -it.z) }
            8 -> beacons.map {  Beacon(-it.y, -it.x, -it.z) }

            // y is now z-axis
            9 -> beacons.map {  Beacon( it.x, -it.z, it.y) }
            10 -> beacons.map { Beacon(-it.z, -it.x, it.y) }
            11 -> beacons.map { Beacon(-it.x,  it.z, it.y) }
            12 -> beacons.map { Beacon( it.z,  it.x, it.y) }

            // y is now z-axis but flipped
            13 -> beacons.map { Beacon(-it.x, -it.z, -it.y) }
            14 -> beacons.map { Beacon(-it.z,  it.x, -it.y) }
            15 -> beacons.map { Beacon( it.x,  it.z, -it.y) }
            16 -> beacons.map { Beacon( it.z, -it.x, -it.y) }

            // x is now z-axis
            17 -> beacons.map { Beacon(-it.z,  it.y, it.x) }
            18 -> beacons.map { Beacon( it.y,  it.z, it.x) }
            19 -> beacons.map { Beacon( it.z, -it.y, it.x) }
            20 -> beacons.map { Beacon(-it.y, -it.z, it.x) }

            // x is now z-axis but flipped
            21 -> beacons.map { Beacon( it.z,  it.y, -it.x) }
            22 -> beacons.map { Beacon( it.y, -it.z, -it.x) }
            23 -> beacons.map { Beacon(-it.z, -it.y, -it.x) }
            24 -> beacons.map { Beacon(-it.y,  it.z, -it.x) }

            else -> error("what")
        }
    }
}

data class Beacon(val x: Int, val y: Int, val z: Int)

fun manhattan(scanner1: Scanner, scanner2: Scanner) = abs(scanner1.x - scanner2.x) + abs(scanner1.y - scanner2.y) + abs(scanner1.z - scanner2.z)
