package model.Animals.Herbivores;

import model.Animals.Actions.Run;
import model.Animals.Actions.Voice;
import model.Animals.Herbivore;

import java.util.Random;

public class Cow extends Herbivore implements Run, Voice {
    Random rnd = new Random();
    private String voice;

    public Cow(String name, int hunger, String voice){
        super(hunger, name);

        this.voice = voice;
        System.out.println("Корова создана!");
    }

    @Override
    public void run() {
        super.setHunger(super.getHunger()-10);
        System.out.println("*Корова бежит*");
    }

    @Override
    public String voice() {
        super.setHunger(super.getHunger()-4);
        return voice;
    }

    @Override
    public String toString() {
        return "Корова{" +
                "name='" + super.getName() + '\'' +
                "hunger= " + super.getHunger() + '\'' +
                ", voice='" + voice + '\'' +
                '}';
    }

    @Override
    public void makeSomeAction() {
        String[] noices = {"*Корова промычала*","*Корова легла под дерево*","*Корова пошла на водопой*"};
        int i = rnd.nextInt(3);
        System.out.println(noices[i]);
    }
}
