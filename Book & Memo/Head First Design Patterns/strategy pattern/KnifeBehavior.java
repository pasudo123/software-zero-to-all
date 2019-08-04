package pattern.design.strategy;

public class KnifeBehavior implements WeaponBehavior{

    @Override
    public void useWeapon() {
        System.out.println("칼을 사용한다.");
    }
}
