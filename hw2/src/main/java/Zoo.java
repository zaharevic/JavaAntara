import model.Animals.Actions.Swim;
import model.Animals.Carnivorous.Bear;
import model.Animals.Carnivorous.Pike;
import model.Animals.Carnivorous.Wolf;
import model.Animals.Herbivores.Cow;
import model.Animals.Herbivores.Duck;
import model.Animals.Herbivores.Sheep;
import model.Food.Carnicorous.Beef;
import model.Food.Carnicorous.Chicken;
import model.Food.Carnicorous.Pork;
import model.Food.Herbivores.FreshGrass;
import model.Food.Herbivores.Hay;
import model.Food.Herbivores.Silo;
import model.Worker;

public class Zoo {
    public static void main(String[] args) {
        Worker worker = new Worker();

        Beef beef = new Beef();
        Chicken chicken = new Chicken();
        Pork pork = new Pork();
        FreshGrass freshGrass = new FreshGrass();
        Hay hay = new Hay();
        Silo silo = new Silo();

        Bear bear = new Bear("Дмитрий",90, "Бееееер");
        Pike pike1 = new Pike("Анатолий", 70);
        Pike pike2 = new Pike("Владимир", 65);
        Pike pike3 = new Pike("Антонина", 63);
        Wolf wolf = new Wolf("Валдай", 80, "Гав");
        Cow cow = new Cow("Люся", 85, "Мууууу");
        Duck duck1 = new Duck("Александр", 60, "Кря-кря");
        Duck duck2 = new Duck("Владислав", 88, "Крю-крю");
        Duck duck3 = new Duck("Дарья", 90, "Кря-кря");
        Sheep sheep = new Sheep("Доли", 77, "Бееееее");

        Swim[] pound = {pike1, pike2, pike3, duck1, duck2, duck3};

        worker.getVoice(bear);
        worker.getVoice(wolf);
        worker.getVoice(cow);
        worker.getVoice(duck1);
        worker.getVoice(duck2);
        worker.getVoice(duck3);
        worker.getVoice(sheep);

        worker.feed(chicken, bear);
        worker.feed(hay, bear);
        worker.feed(silo, cow);
        worker.feed(beef, duck1);

        for(Swim anim : pound){
            anim.swim();
        }
    }
}