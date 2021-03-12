class ParkingSystem {

    private final Map<Integer, Integer> parking = new HashMap<>();
    
    public ParkingSystem(int big, int medium, int small) {
        parking.put(1, big);
        parking.put(2, medium);
        parking.put(3, small);
    }
    
    public boolean addCar(int carType) {
        final int size = parking.get(carType);
        
        if(size <= 0) {
            return false;
        }
        
        parking.put(carType, size - 1);
        return true;
    }
}

/**
 * Your ParkingSystem object will be instantiated and called as such:
 * ParkingSystem obj = new ParkingSystem(big, medium, small);
 * boolean param_1 = obj.addCar(carType);
 */
