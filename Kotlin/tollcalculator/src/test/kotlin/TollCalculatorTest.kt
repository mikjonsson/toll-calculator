import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.property.Arb
import io.kotest.property.arbitrary.localDateTime
import io.kotest.property.forAll
import vehicle.Vehicle
import java.time.LocalDateTime

class TollCalculatorTest : StringSpec({
    val tollCalculator = TollCalculator()

    "No added fee within 60 minutes" {
        val car = Vehicle.Car()
        val currentFee = 8
        car.updateCurrentDailyFee(currentFee)
        car.setLastBilled(LocalDateTime.of(2018,1, 5, 14,0))

        val fee = tollCalculator.calculateDailyFee(car, car.lastBilled.plusMinutes(55))

        fee shouldBe currentFee
    }

    "Should add 13 at 15:05 after 65 minutes" {
        val car = Vehicle.Car()
        val currentFee = 8
        car.updateCurrentDailyFee(currentFee)
        car.setLastBilled(LocalDateTime.of(2018,1, 5, 14,0))

        val fee = tollCalculator.calculateDailyFee(car, car.lastBilled.plusMinutes(65))

        fee shouldBe currentFee + 13
    }

    "Shouldn't be charged more than 60" {
        val car = Vehicle.Car()
        car.updateCurrentDailyFee(60)
        car.setLastBilled(LocalDateTime.of(2018,1, 1, 1,0))

        forAll(Arb.localDateTime(2018, 2018)) { date ->
            tollCalculator.calculateDailyFee(car, date) == 60
        }
    }

})