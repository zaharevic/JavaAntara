package model.Animals.Carnivorous;

import model.Animals.Actions.Swim;
import model.Animals.Carnivorour;

import java.util.Random;

public class Pike extends Carnivorour implements Swim {
    private static final int SWIM_HUNGER_DECREASE = 20;
    private static final int ACTIONS_COUNT = 3;

    private final Random rnd = new Random();

    public Pike(String name, int hunger) {
        super(hunger, name);
        System.out.printf("%s создан(а)!%n", name);
    }

    @Override
    public void swim() {
        super.setHunger(super.getHunger() - SWIM_HUNGER_DECREASE);
        System.out.printf("*%s плывет*%n", getName());
    }

    @Override
    public String toString() {
        return String.format("Щука{name='%s', hunger=%d}",
                super.getName(), super.getHunger());
    }

    @Override
    public void makeSomeAction() {
        String[] noises = {
                String.format("*%s поймал(а) маленькую рыбу*", getName()),
                String.format("*%s заплыл(а) под дерево*", getName()),
                String.format("*%s лег(ла) на дно*", getName())
        };
        int i = rnd.nextInt(ACTIONS_COUNT);
        System.out.println(noises[i]);
    }
}
