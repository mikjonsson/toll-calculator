import com.github.nscala_time.time.Imports._

package TollCalculator {

  class Vehicle {
    def uuid = java.util.UUID.randomUUID.toString // Map UUID to customer/registration in billing system

    var lastCharged: Option[DateTime] = None
    var currentDailyFee: SEK = 0

    val setCurrentDailyFee = (amount: SEK) => {
      currentDailyFee = amount
    }
  }

  // Known vehicle types
  case class Car() extends Vehicle
  case class Motorbike() extends Vehicle
  case class Tractor() extends Vehicle
  case class Emergency() extends Vehicle
  case class Diplomat() extends Vehicle
  case class Foreign() extends Vehicle
  case class Military() extends Vehicle
  // Unknown vehicle type
  case class UnknownVehicle() extends Vehicle

}