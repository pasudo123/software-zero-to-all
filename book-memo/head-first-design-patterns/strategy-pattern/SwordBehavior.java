package pattern.design.strategy;

public class SwordBehavior implements WeaponBehavior {

    @Override
    public void useWeapon() {
        System.out.println("검을 사용한다.");
    }
}
