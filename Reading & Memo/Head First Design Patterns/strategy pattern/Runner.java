package pattern.design.strategy;

public class Runner {

    public static void main(String[]args){

        System.out.println("=====> King 의 무기 사용");

        King king = new King();
        king.fight();
        king.setWeapon(new AxeBehavior());          // 무기 변경
        king.fight();
        king.setWeapon(new BowAndArrowBehavior());  // 무기 변경
        king.fight();
        king.setWeapon(new SwordBehavior());        // 무기 변경
        king.fight();

        System.out.println("======================>");
    }
}
