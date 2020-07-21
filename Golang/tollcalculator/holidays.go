package tollcalculator

/*
 * Hardcoded 2018 holidays for simplicity. We'd probably want to implement this
 * with an interface allowing fetching holidays from files, databases and/or web services.
 */

// Holidays contains a list of all days of a year that are holidays
type Holidays []int

var holidays2018 = []int{1, 6, 89, 91, 92, 121, 130, 140, 157, 174, 307, 359, 360}

// HolidaysForYear returns holidays for the specific year or empty list if no data is found
func HolidaysForYear(year int) Holidays {
	if year != 2018 {
		consoleLogger.Warn("No holiday data for year ", year)
		return Holidays{}
	}

	return holidays2018
}

// IsHolliday returns true if dayOfYear is a holliday in year
func IsHolliday(dayOfYear int, year int) bool {
	// Change to binary search tree if performance is importnt?
	for _, day := range HolidaysForYear(year) {
		if dayOfYear == day {
			return true
		}
	}
	return false
}
