import TollCalculator._
import org.scalatest.{ FlatSpec, Matchers }
import com.github.nscala_time.time.Imports._

class FeeRulesSpec extends FlatSpec with Matchers {

  // TODO Add tests for
  // * shouldBeCharged
  // * isTollFreeDate


  // isTollFreeDayOfWeek
  "Saturday" should "be free" in {
    val dateTime:DateTime = new DateTime(2018, 1, 6, 0, 0)
    assert(FeeRules.isTollFreeDayOfWeek(dateTime))
  }
  "Sunday" should "be free" in {
    val dateTime:DateTime = new DateTime(2018, 1, 7, 0, 0)
    assert(FeeRules.isTollFreeDayOfWeek(dateTime))
  }
  "Monday" should "not be free" in {
    val dateTime:DateTime = new DateTime(2018, 1, 1, 0, 0)
    assert(!FeeRules.isTollFreeDayOfWeek(dateTime))
  }
  "Tuesday" should "not be free" in {
    val dateTime:DateTime = new DateTime(2018, 1, 2, 0, 0)
    assert(!FeeRules.isTollFreeDayOfWeek(dateTime))
  }
  "Wednesday" should "not be free" in {
    val dateTime:DateTime = new DateTime(2018, 1, 3, 0, 0)
    assert(!FeeRules.isTollFreeDayOfWeek(dateTime))
  }
  "Thursday" should "not be free" in {
    val dateTime:DateTime = new DateTime(2018, 1, 4, 0, 0)
    assert(!FeeRules.isTollFreeDayOfWeek(dateTime))
  }
  "Friday" should "not be free" in {
    val dateTime:DateTime = new DateTime(2018, 1, 5, 0, 0)
    assert(!FeeRules.isTollFreeDayOfWeek(dateTime))
  }


  // isTollFreeVehicle
  "Car" should "not be free" in {
    FeeRules.isTollFreeVehicle(new Car) shouldBe false
  }
  "Other vehicles" should "be free" in {
    FeeRules.isTollFreeVehicle(new Motorbike) shouldBe true
    FeeRules.isTollFreeVehicle(new Diplomat) shouldBe true
    FeeRules.isTollFreeVehicle(new Tractor) shouldBe true
    FeeRules.isTollFreeVehicle(new Military) shouldBe true
    FeeRules.isTollFreeVehicle(new Emergency) shouldBe true
    FeeRules.isTollFreeVehicle(new Foreign) shouldBe true
  }


  // hasBeenRecentlyCharged
  "A car without previous charges" should "be charged" in {
    val car = new Car
    FeeRules.hasBeenRecentlyCharged(car, new DateTime(2018,1,3,15,50)) shouldBe false
  }
  "A car" should "not be charged again after 5 minutes" in {
    val car = new Car
    car.lastCharged = Some(new DateTime(2018,1,3,15,45))
    FeeRules.hasBeenRecentlyCharged(car, new DateTime(2018,1,3,15,50)) shouldBe true
  }
  "A car" should "not be charged again after 60 minutes" in {
    val car = new Car
    val timeOfFirstFee = new DateTime(2018,1,3,15,45)
    car.lastCharged = Some(timeOfFirstFee)
    FeeRules.hasBeenRecentlyCharged(car, timeOfFirstFee.plusMinutes(60)) shouldBe true
  }
  "A car" should "be charged again after 61 minutes" in {
    val car = new Car
    val timeOfFirstFee = new DateTime(2018,1,3,15,45)
    car.lastCharged = Some(timeOfFirstFee)
    FeeRules.hasBeenRecentlyCharged(car, timeOfFirstFee.plusMinutes(61)) shouldBe false
  }

  // hasReachedMaxDailyFee
  "A car with 55 in daily fee" must "not have ReachedMaxDailyFee" in {
    val car = new Car
    val timeOFee = new DateTime(2018,1,3,15,45)
    car.lastCharged = Some(timeOFee) // This doesn't test time, only make sure there's been a charge today
    car.setCurrentDailyFee(55)
    FeeRules.hasReachedMaxDailyFee(car, new DateTime(2018,1,3,15,45)) shouldBe false
  }
  "A car with 60 in daily fee" should "have ReachedMaxDailyFee" in {
    val car = new Car
    val timeOFee = new DateTime(2018,1,3,15,45)
    car.lastCharged = Some(timeOFee) // This doesn't test time, only make sure there's been a charge today
    car.setCurrentDailyFee(60)
    FeeRules.hasReachedMaxDailyFee(car, new DateTime(2018,1,3,15,45)) shouldBe true
  }
  "A car with 65 in daily fee" should "have ReachedMaxDailyFee" in {
    val car = new Car
    val timeOFee = new DateTime(2018,1,3,15,45)
    car.lastCharged = Some(timeOFee) // This doesn't test time, only make sure there's been a charge today
    car.setCurrentDailyFee(65)
    FeeRules.hasReachedMaxDailyFee(car, new DateTime(2018,1,3,15,45)) shouldBe true
  }
  "A car with 60+ in daily fee, the day before" should "not have ReachedMaxDailyFee" in {
    val car = new Car
    val timeOFee = new DateTime(2018,1,3,15,45)
    car.lastCharged = Some(timeOFee) // This doesn't test time, only make sure there's been a charge today
    car.setCurrentDailyFee(60)
    FeeRules.hasReachedMaxDailyFee(car, new DateTime(2018,1,3,15,45).plusDays(1)) shouldBe false
    // should really be split up into different tests...
    car.setCurrentDailyFee(65)
    FeeRules.hasReachedMaxDailyFee(car, new DateTime(2018,1,3,15,45).plusDays(1)) shouldBe false
  }

}
