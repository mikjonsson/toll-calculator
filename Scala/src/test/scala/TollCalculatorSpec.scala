import TollCalculator._
import org.scalatest.{ FlatSpec, Matchers }
import com.github.nscala_time.time.Imports._

class TollCalculatorSpec extends FlatSpec with Matchers {

  // TODO add more tests

  "On a workday, a car without previous fees" should "pay 18 @ 15:45" in {
    val car = new Car
    val dateTime = new DateTime(2018,1,3,15,45)

    val calculator = TollCalculator

    calculator.getTollFee(car, Array(dateTime)) shouldBe 18
  }

  "On a workday, a car without previous fees" should
    "pay 18 if it passes toll at 15:45 and 15:50" in {
    val car = new Car
    val dateTimes = Array(new DateTime(2018,1,3,15,45), new DateTime(2018,1,3,15,50))

    val calculator = TollCalculator

    calculator.getTollFee(car, dateTimes) shouldBe 18
  }

  "On a workday, a car without previous fees" should
    "pay 26 if it passes toll at 06:15 and 15:50" in {
    val car = new Car
    val dateTimes = Array(new DateTime(2018,1,3,6,15), new DateTime(2018,1,3,15,50))

    val calculator = TollCalculator

    calculator.getTollFee(car, dateTimes) shouldBe 26
  }

  "On a workday, a car without previous fees" should
    "pay 60 if it passes a toll every 90 minutes from 05:00 to 18:00" in {
    val car = new Car
    val firstToll = new DateTime(2018,1,3,5,0)
    val dateTimes = Array(firstToll, firstToll.plusMinutes(90),
        firstToll.plusMinutes(180), firstToll.plusMinutes(270),
        firstToll.plusMinutes(360), firstToll.plusMinutes(450),
        firstToll.plusMinutes(540), firstToll.plusMinutes(630),
        firstToll.plusMinutes(720))

    val calculator = TollCalculator

    calculator.getTollFee(car, dateTimes) shouldBe 60
  }
}
