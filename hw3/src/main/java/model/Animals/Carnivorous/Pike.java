package model.Animals.Carnivorous;

import model.Animals.Actions.Swim;
import model.Animals.Carnivorour;
import model.Enclusores.EnclosureSize;

import java.util.Random;

public class Pike extends Carnivorour implements Swim {
    Random rnd = new Random();

    public Pike(String name, int hunger){
        super(hunger, name, EnclosureSize.SMALL);
        System.out.println("Щука создана!");
    }

    @Override
    public void swim() {
        super.setHunger(super.getHunger()-20);
        System.out.println("*Щука плывет*");
    }

    @Override
    public String toString() {
        return "Щука{" +
                "name='" + super.getName() + '\'' +
                "hunger= " + super.getHunger() + '\'' +
                '}';
    }

    @Override
    public void makeSomeAction() {
        String[] noices = {"*Щука поймала малекнькую рыбу*","*Щука заплыла под дерево*","*Щука легла на дно*"};
        int i = rnd.nextInt(3);
        System.out.println(noices[i]);
    }
}
