package model.Animals.Herbivores;

import model.Animals.Actions.Fly;
import model.Animals.Actions.Run;
import model.Animals.Actions.Swim;
import model.Animals.Actions.Voice;
import model.Animals.Herbivore;

import java.util.Random;

public class Duck extends Herbivore implements Fly, Run, Swim, Voice {
    private static final int FLY_HUNGER_DECREASE = 25;
    private static final int RUN_HUNGER_DECREASE = 10;
    private static final int SWIM_HUNGER_DECREASE = 7;
    private static final int VOICE_HUNGER_DECREASE = 3;
    private static final int ACTIONS_COUNT = 2;

    private final Random rnd = new Random();
    private final String voice;

    public Duck(String name, int hunger, String voice) {
        super(hunger, name);
        this.voice = voice;
        System.out.printf("%s создан(а)!%n", getName());
    }

    @Override
    public void fly() {
        super.setHunger(super.getHunger() - FLY_HUNGER_DECREASE);
        System.out.printf("*%s летит*%n", getName());
    }

    @Override
    public void run() {
        super.setHunger(super.getHunger() - RUN_HUNGER_DECREASE);
        System.out.printf("*%s бежит*%n", getName());
    }

    @Override
    public void swim() {
        super.setHunger(super.getHunger() - SWIM_HUNGER_DECREASE);
        System.out.printf("*%s плывет*%n", getName());
    }

    @Override
    public String voice() {
        super.setHunger(super.getHunger() - VOICE_HUNGER_DECREASE);
        return voice;
    }

    @Override
    public String toString() {
        return String.format("Утка{name='%s', hunger=%d, voice='%s'}",
                super.getName(), super.getHunger(), voice);
    }

    @Override
    public void makeSomeAction() {
        String[] noises = {
                String.format("*%s зашел(ла) в тень*", getName()),
                String.format("*%s на воде*", getName())
        };
        int i = rnd.nextInt(ACTIONS_COUNT);
        System.out.println(noises[i]);
    }
}