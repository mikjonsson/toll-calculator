import com.github.nscala_time.time.Imports._

package object TollCalculator {
  type SEK = Double
}

package TollCalculator {

  object TollCalculator {

    /** Total fee for specific vehicle for the gives DateTimes, taking all rules into account */
    def getTollFee(vehicle: Vehicle, dateTimes: Array[DateTime]): SEK = {
      val sortedDates = dateTimes.sortBy(_.getMillis)
      val dailyFees: Array[SEK] = for (date <- sortedDates) yield tollFee(vehicle, date)
      return dailyFees.sum
    }

    /** Fee for specific vehicle and day, taking all rules into account */
    def tollFee(vehicle: Vehicle, dateTime: DateTime): SEK = {
      if (FeeRules.shouldBeCharged(vehicle, dateTime)) {

        val fee = tollFee(dateTime)

        val feeToCharge = {
          if ((fee + vehicle.currentDailyFee) < FeeRules.maxDailyFee) {
            vehicle.setCurrentDailyFee(fee + vehicle.currentDailyFee)
            fee
          }
          else {
            val temp = vehicle.currentDailyFee
            vehicle.setCurrentDailyFee(FeeRules.maxDailyFee)
            FeeRules.maxDailyFee - temp
          }
        }
        vehicle.lastCharged = Some(dateTime)
        return feeToCharge
      }
        return 0
    }

    /** Fee for time of day, without taking other rules into account */
    def tollFee(time: DateTime): SEK =
      (time.getHourOfDay(), time.getMinuteOfHour()) match {
        case (6, m) => if (m < 30) 8 else 13
        case (7, _) => 18
        case (8, m) => if (m < 30) 13 else 8

        case (9, _) => 8
        case (10, _) => 8
        case (11, _) => 8
        case (12, _) => 8
        case (13, _) => 8
        case (14, _) => 8

        case (15, m) => if (m < 30) 13 else 18
        case (16, _) => 18
        case (17, _) => 13
        case (18, m) => if (m < 30) 8 else 0

        case (_, _) => 0
      }
  }
}