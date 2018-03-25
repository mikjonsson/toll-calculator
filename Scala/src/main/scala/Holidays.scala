package TollCalculator {

  object Holidays {
    // Holiday in Day of Year according to https://www.kalender.se/helgdagar
    // Could also be fetched from a service, database or other source
    def holidays2018 = List(1, 6, 89, 91, 92, 121, 130, 140, 157, 174, 307, 359, 360)
    // Holidays per year
    def holidays = Map(2018 -> holidays2018)
  }

}
