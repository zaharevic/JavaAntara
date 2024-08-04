package model.Animals.Herbivores;

import model.Animals.Actions.Fly;
import model.Animals.Actions.Run;
import model.Animals.Actions.Swim;
import model.Animals.Actions.Voice;
import model.Animals.Herbivore;
import model.Enclusores.EnclosureSize;

import java.util.Random;

public class Duck extends Herbivore implements Fly, Run, Swim, Voice {
    Random rnd = new Random();
    private String voice;

    public Duck(String name, int hunger, String voice){
        super(hunger, name, EnclosureSize.SMALL);
        this.voice = voice;
        System.out.println("Утка создана!");
    }

    @Override
    public void fly() {
        super.setHunger(super.getHunger()-25);
        System.out.println("*Утка летит*");
    }

    @Override
    public void run() {
        super.setHunger(super.getHunger()-10);
        System.out.println("*Утка бежит*");
    }

    @Override
    public void swim() {
        super.setHunger(super.getHunger()-7);
        System.out.println("*Утка плывет*");
    }

    @Override
    public String voice() {
        super.setHunger(super.getHunger()-3);
        return voice;
    }

    @Override
    public void makeSomeAction() {
        String[] noices = {"*Утка зашла в тень*","*Утка спит на воде*"};
        int i = rnd.nextInt(2);
        System.out.println(noices[i]);
    }

    @Override
    public String toString() {
        return "Утка{" +
                "name='" + super.getName() + '\'' +
                "hunger= " + super.getHunger() + '\'' +
                ", voice='" + voice + '\'' +
                '}';
    }
}
