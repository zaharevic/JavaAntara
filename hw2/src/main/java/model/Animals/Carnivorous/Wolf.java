package model.Animals.Carnivorous;

import model.Animals.Actions.Run;
import model.Animals.Actions.Voice;
import model.Animals.Carnivorour;

import java.util.Random;

public class Wolf extends Carnivorour implements Run, Voice {
    Random rnd = new Random();
    private String voice;

    public Wolf(String name, int hunger, String voice){
        super(hunger,name);
        this.voice = voice;
        System.out.println("Волк создан!");
    }

    @Override
    public void run() {
        super.setHunger(super.getHunger()-10);
        System.out.println("*Волк бежит*");
    }

    @Override
    public String voice() {
        super.setHunger(super.getHunger()-5);
        return voice;
    }

    @Override
    public String toString() {
        return "Волк{" +
                "name='" + super.getName() + '\'' +
                "hunger= " + super.getHunger() + '\'' +
                ", voice='" + voice + '\'' +
                '}';
    }

    @Override
    public void makeSomeAction() {
        String[] noices = {"*Волк воет на луну*","*Волк лег на землю*","*Волк зашел под навес*"};
        int i = rnd.nextInt(3);
        System.out.println(noices[i]);
    }
}
