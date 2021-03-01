class ParkingSystem(big: Int, medium: Int, small: Int) {

    private var store = mutableMapOf<Int, Int>(1 to big, 2 to medium, 3 to small)
    
    fun addCar(carType: Int): Boolean {
        val size = store.get(carType)
        return if(size == 0) false else {
            store.put(carType, size!!.minus(1))
            return true
        }
    }
}

/**
 * Your ParkingSystem object will be instantiated and called as such:
 * var obj = ParkingSystem(big, medium, small)
 * var param_1 = obj.addCar(carType)
 */
