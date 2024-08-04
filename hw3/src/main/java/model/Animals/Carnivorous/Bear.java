package model.Animals.Carnivorous;

import model.Animals.Actions.Run;
import model.Animals.Actions.Voice;
import model.Animals.Carnivorour;
import model.Enclusores.EnclosureSize;

import java.util.Random;

public class Bear extends Carnivorour implements Run, Voice {
    Random rnd = new Random();
    private String voice;

    public Bear(String name, int hunger, String voice){
        super(hunger, name, EnclosureSize.EXTRALARGE);
        this.voice = voice;
        System.out.println("Медеведь создан!");
    }

    @Override
    public void run() {
        super.setHunger(super.getHunger()-10);
        System.out.println("*Медведь бежит*");
    }
    @Override
    public String voice() {
        super.setHunger(super.getHunger()-3);
        return voice;
    }

    @Override
    public String toString() {
        return "Медведь{" +
                "name='" + super.getName() + '\'' +
                "hunger= " + super.getHunger() + '\'' +
                ", voice='" + voice + '\'' +
                '}';
    }

    @Override
    public void makeSomeAction() {
        String[] noices = {"*Медведь прорычал*","*Медведь зашел в домик*","*Медведь залез на дерево*"};
        int i = rnd.nextInt(3);
        System.out.println(noices[i]);
    }
}
