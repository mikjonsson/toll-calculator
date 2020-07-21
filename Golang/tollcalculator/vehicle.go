package tollcalculator

import (
	"time"

	"github.com/google/uuid"
)

// VehicleType identifier
type VehicleType int

// Vehicle types
const (
	UnknownVehicle VehicleType = iota
	Car
	Motorbike
	Tractor
	Emergency
	Diplomat
	Foreign
	Military
)

// Vehicle defintion
type Vehicle struct {
	ID              uuid.UUID
	VehicleType     VehicleType
	LastCharged     time.Time
	LastFee         SEK
	CurrentDailyFee SEK
}

// IsTollFreeVehicle checks if vehicle should pay toll
func IsTollFreeVehicle(vehicle Vehicle) bool {
	return vehicle.VehicleType != Car
}
