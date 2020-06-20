package tollcalculator

/*
 * Hardcoded 2018 holidays for simplicity. We'd probably want to implement this
 * with an interface allowing fetching holidays from files, databases and/or web services.
 */

// Holidays contains a list of all days of a year that are holidays
type Holidays []int

var holidays2018 = []int{1, 6, 89, 91, 92, 121, 130, 140, 157, 174, 307, 359, 360}

// HolidaysForYear return holidays for the specific year
func HolidaysForYear(year int) Holidays {
	if year != 2018 {
		consoleLogger.Warn("No holiday data for year ", year, " all days counted as non-holidays.")
		return Holidays{}
	}

	consoleLogger.Info("Holidays for ", year, ": ", holidays2018)
	return holidays2018
}
