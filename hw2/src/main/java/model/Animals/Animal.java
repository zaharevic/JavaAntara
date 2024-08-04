package model.Animals;

import model.Food.Food;
import model.Food.Grass;
import model.Food.Meat;

public abstract class Animal {
    private static final int MAX_HUNGER = 100;
    private static final int MIN_HUNGER = 0;

    private int hunger;
    private String name;

    public Animal(int hunger, String name) {
        this.hunger = hunger;
        this.name = name;
    }

    public boolean eat(Food food) {
        addHunger(food.getSaturation());
        food.eated();
        return true;
    }

    public int getHunger() {
        return hunger;
    }

    public void setHunger(int hunger) {
        this.hunger = hunger;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addHunger(int addedHunger) {
        int total = addedHunger + hunger;
        if (total > MAX_HUNGER) {
            total = MAX_HUNGER;
        } else if (total < MIN_HUNGER) {
            total = MIN_HUNGER;
        }
        hunger = total;
    }

    public abstract void makeSomeAction();
}
