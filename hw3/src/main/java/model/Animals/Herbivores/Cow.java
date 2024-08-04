package model.Animals.Herbivores;

import model.Animals.Actions.Run;
import model.Animals.Actions.Voice;
import model.Animals.Herbivore;
import model.Enclusores.EnclosureSize;

import java.util.Random;

public class Cow extends Herbivore implements Run, Voice {
    private static final int RUN_HUNGER_DECREASE = 10;
    private static final int VOICE_HUNGER_DECREASE = 4;
    private static final int ACTIONS_COUNT = 3;

    Random rnd = new Random();
    private String voice;

    public Cow(String name, int hunger, String voice){
        super(hunger, name, EnclosureSize.LARGE);
        this.voice = voice;
        System.out.printf("%s создана!%n", getName());

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
        return String.format("Корова{name='%s', hunger=%d, voice='%s'}",
                super.getName(), super.getHunger(), voice);
    }

    @Override
    public void makeSomeAction() {
        String[] noises = {
                String.format("*%s промычала*", getName()),
                String.format("*%s легла под дерево*", getName()),
                String.format("*%s пошла на водопой*", getName())
        };
        int i = rnd.nextInt(ACTIONS_COUNT);
        System.out.println(noises[i]);
    }
}
