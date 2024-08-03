import model.Kotik;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Application {
    public static void main(String[] args) {
        Kotik kot1 = new Kotik(73, "Вася", 12, "мяу");
        Kotik kot2 = new Kotik();
        kot2.setKotik(81, "Рыжик", 15, "мяу!!");
        kot1.liveAnotherDay();
        kot2.printStat();
        System.out.println(kot1.getMeow().equals(kot2.getMeow()));
        System.out.println(Kotik.getCatsCounter());
    }
}