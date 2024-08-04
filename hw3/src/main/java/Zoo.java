import model.Animals.Actions.Swim;
import model.Animals.Animal;
import model.Animals.Carnivorous.Bear;
import model.Animals.Carnivorous.Pike;
import model.Animals.Carnivorous.Wolf;
import model.Animals.Herbivores.Cow;
import model.Animals.Herbivores.Duck;
import model.Animals.Herbivores.Sheep;
import model.Enclusores.Enclosure;
import model.Enclusores.EnclosureSize;
import model.Exceptions.WrongFoodException;
import model.Food.Carnicorous.Beef;
import model.Food.Carnicorous.Chicken;
import model.Food.Carnicorous.Pork;
import model.Food.Herbivores.FreshGrass;
import model.Food.Herbivores.Hay;
import model.Food.Herbivores.Silo;
import model.Worker;

public class Zoo {
    public static void main(String[] args) throws WrongFoodException {
        Beef beef = new Beef();
        Chicken chicken = new Chicken();
        Pork pork = new Pork();
        FreshGrass freshGrass = new FreshGrass();
        Hay hay = new Hay();
        Silo silo = new Silo();

        Bear bear = new Bear("Дмитрий",90, "Бееееер");
        Pike pike1 = new Pike("Анатолий", 70);
        Pike pike2 = new Pike("Владимир", 65);
        Wolf wolf = new Wolf("Валдай", 80, "Гав");
        Cow cow = new Cow("Люся", 85, "Мууууу");
        Duck duck1 = new Duck("Александр", 60, "Кря-кря");
        Duck duck2 = new Duck("Владислав", 88, "Крю-крю");
        Sheep sheep = new Sheep("Доли", 77, "Бееееее");

        Enclosure<Animal> enclosureS = new Enclosure<>(EnclosureSize.SMALL);
        Enclosure<Animal> enclosureM = new Enclosure<>(EnclosureSize.MEDIUM);
        Enclosure<Animal> enclosureL = new Enclosure<>(EnclosureSize.LARGE);
        Enclosure<Animal> enclosureXL = new Enclosure<>(EnclosureSize.EXTRALARGE);
        System.out.println("________________________________________________________________");
        enclosureS.addAnimal(pike1);
        enclosureS.addAnimal(duck1);
        enclosureS.addAnimal(cow);
        System.out.println("________________________________________________________________");
        enclosureM.addAnimal(wolf);
        enclosureM.addAnimal(duck2);
        enclosureM.addAnimal(bear);
        System.out.println("________________________________________________________________");
        enclosureL.addAnimal(cow);
        enclosureL.addAnimal(pike2);
        enclosureL.addAnimal(bear);
        System.out.println("________________________________________________________________");
        enclosureXL.addAnimal(bear);
        enclosureXL.addAnimal(sheep);
        System.out.println("________________________________________________________________");
        System.out.println(enclosureM.findAnimal("Валдай"));
        System.out.println(enclosureM.findAnimal("Доли"));
        System.out.println("________________________________________________________________");
        bear.eat(pork);
        //bear.eat(freshGrass);
        sheep.eat(silo);
        //sheep.eat(chicken);
    }
}