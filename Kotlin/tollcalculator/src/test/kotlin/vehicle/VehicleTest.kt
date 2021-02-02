package vehicle

import io.kotest.core.spec.style.StringSpec
import java.time.LocalDateTime

class VehicleTest : StringSpec({

    "New vehicles has 0 fee" {
        val v = Vehicle.Car()
        v.currentDailyFee == 0
    }

    "Can update fee" {
        val v = Vehicle.Car()
        v.updateCurrentDailyFee(10)
        v.currentDailyFee == 10
    }

    "New vehicles has ${LocalDateTime.MIN} lastBilled" {
        val v = Vehicle.Car()
        v.lastBilled == LocalDateTime.MIN
    }

    "Can update lastBilled" {
        val v = Vehicle.Car()
        val dateTime = LocalDateTime.now()
        v.setLastBilled(dateTime)
        v.lastBilled == dateTime
    }
})