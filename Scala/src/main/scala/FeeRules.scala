import com.github.nscala_time.time.Imports._
import scala.util.{ Try, Success, Failure }

package TollCalculator {

  object FeeRules {

    def maxDailyFee:SEK = 60
    def maxChargeFrequencyMinutes = 60

    def shouldBeCharged(vehicle: Vehicle, dateTime: DateTime): Boolean = {
      !isTollFreeVehicle(vehicle) &&
        !hasBeenRecentlyCharged(vehicle, dateTime) &&
        !hasReachedMaxDailyFee(vehicle, dateTime) &&
        !isTollFreeDate(dateTime) &&
        !isTollFreeDayOfWeek(dateTime)
    }

    def isTollFreeVehicle(vehicle: Vehicle): Boolean = vehicle match {
      case Car() => false
      case _ => true
    }

    def isTollFreeDayOfWeek(date: DateTime = DateTime.now()): Boolean =
      date.getDayOfWeek match {
        case 6 => true
        case 7 => true
        case _ => false
      }

    // Check if date is toll free. If there's no data for the year, make it free and log error
    def isTollFreeDate(date: DateTime = DateTime.now()): Boolean = {
      val year = date.getYear()
      val dayOfYear = date.dayOfYear()

      val isFree = Try(Holidays.holidays(year))

      isFree match {
        case Success(days) => days.contains(dayOfYear)
        case Failure(_) =>
          // Replace with proper error logging and alert
          println("ERROR: No public holiday data available for: " + year)
          true
      }
    }

    def hasBeenRecentlyCharged(vehicle: Vehicle, dateTime: DateTime = DateTime.now()): Boolean = {
      vehicle.lastCharged match {
        case Some(t) => t.plusMinutes(maxChargeFrequencyMinutes) >= dateTime
        case None => false
      }
    }

    def hasReachedMaxDailyFee(vehicle: Vehicle, date: DateTime): Boolean = {
      vehicle.lastCharged match {
        case Some(d) =>
          if (d.date != date.date) return false
          else return vehicle.currentDailyFee >= maxDailyFee
        case None => false
      }
    }

  }

}