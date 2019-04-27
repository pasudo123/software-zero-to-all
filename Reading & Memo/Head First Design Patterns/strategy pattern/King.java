package pattern.design.strategy;

public class King extends Character{

    public King(){
        weaponBehavior = new SwordBehavior();
    }

    @Override
    public void fight() {
        weaponBehavior.useWeapon();
    }
}
