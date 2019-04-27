package pattern.design.strategy;

public class Troll extends Character {
    @Override
    public void fight() {
        weaponBehavior.useWeapon();
    }
}
