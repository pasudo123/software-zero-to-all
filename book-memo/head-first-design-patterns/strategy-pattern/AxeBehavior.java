package pattern.design.strategy;

public class AxeBehavior implements WeaponBehavior {

    @Override
    public void useWeapon() {
        System.out.println("도끼를 사용한다.");
    }
}
