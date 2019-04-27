package pattern.design.strategy;

public abstract class Character {

    WeaponBehavior weaponBehavior = null;

    public abstract void fight();

    public void setWeapon(WeaponBehavior weaponBehavior){
        this.weaponBehavior = weaponBehavior;
    }

}