package model.Animals.Herbivores;

import model.Animals.Actions.Run;
import model.Animals.Actions.Voice;
import model.Animals.Herbivore;
import model.Enclusores.EnclosureSize;

import java.util.Random;

public class Sheep extends Herbivore implements Run, Voice {
    Random rnd = new Random();
    private String voice;

    public Sheep(String name, int hunger, String voice){
        super(hunger, name, EnclosureSize.MEDIUM);
        this.voice = voice;
        System.out.println("Овечка создана!");
    }

    @Override
    public void run() {
        super.setHunger(super.getHunger()-8);
        System.out.println("*Овечка беджит*");
    }

    @Override
    public String voice() {
        super.setHunger(super.getHunger()-3);
        return voice;
    }

    @Override
    public String toString() {
        return "Овечка{" +
                "name='" + super.getName() + '\'' +
                "hunger= " + super.getHunger() + '\'' +
                ", voice='" + voice + '\'' +
                '}';
    }

    @Override
    public void makeSomeAction() {
        String[] noices = {"*Овечка легла в тень*","*Овечка уснула*"};
        int i = rnd.nextInt(2);
        System.out.println(noices[i]);
    }
}
