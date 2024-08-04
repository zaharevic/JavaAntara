package model.Animals.Carnivorous;

import model.Animals.Actions.Run;
import model.Animals.Actions.Voice;
import model.Animals.Carnivorour;
import model.Enclusores.EnclosureSize;

import java.util.Random;

public class Wolf extends Carnivorour implements Run, Voice {
    private static final int RUN_HUNGER_DECREASE = 10;
    private static final int VOICE_HUNGER_DECREASE = 5;
    private static final int ACTIONS_COUNT = 3;

    Random rnd = new Random();
    private String voice;

    public Wolf(String name, int hunger, String voice){
        super(hunger,name,EnclosureSize.MEDIUM);
        this.voice = voice;
        System.out.printf("%s создан(а)!%n", getName());
    }

    @Override
    public void run() {
        super.setHunger(super.getHunger() - RUN_HUNGER_DECREASE);
        System.out.printf("*%s бежит*%n", getName());
    }

    @Override
    public String voice() {
        super.setHunger(super.getHunger() - VOICE_HUNGER_DECREASE);
        return voice;
    }

    @Override
    public String toString() {
        return String.format("Волк{name='%s', hunger=%d, voice='%s'}",
                super.getName(), super.getHunger(), voice);
    }

    @Override
    public void makeSomeAction() {
        String[] noises = {
                String.format("*%s воет на луну*", getName()),
                String.format("*%s лег(ла) на землю*", getName()),
                String.format("*%s зашел(ла) под навес*", getName())
        };
        int i = rnd.nextInt(ACTIONS_COUNT);
        System.out.println(noises[i]);
    }
}
