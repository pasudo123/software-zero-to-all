package pattern.design.strategy;

public class Queen extends Character {
    @Override
    public void fight() {
        weaponBehavior.useWeapon();
    }
}
