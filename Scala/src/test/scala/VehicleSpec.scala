import TollCalculator.Vehicle
import org.scalatest.FlatSpec

class VehicleSpec extends FlatSpec {
  "A Vehicle" should "have a unique identifier" in {
    val vehicle1 = new Vehicle
    val vehicle2 = new Vehicle
    assert(vehicle1.uuid != vehicle2.uuid)
  }
}
