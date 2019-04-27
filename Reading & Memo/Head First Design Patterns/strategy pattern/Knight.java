package pattern.design.strategy;

public class Knight extends Character {
    @Override
    public void fight() {
        weaponBehavior.useWeapon();
    }
}
