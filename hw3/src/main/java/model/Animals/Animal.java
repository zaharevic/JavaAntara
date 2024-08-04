package model.Animals;

import model.Enclusores.EnclosureSize;
import model.Exceptions.WrongFoodException;
import model.Food.Food;

import java.util.Objects;

public abstract class Animal {
    private static final int MAX_HUNGER = 100;
    private static final int MIN_HUNGER = 0;

    private int hunger;
    private String name;
    private EnclosureSize requiredSize;

    public Animal(int hunger, String name, EnclosureSize requiredSize){
        this.hunger=hunger;
        this.name = name;
        this.requiredSize = requiredSize;
    };

    public boolean eat(Food food) throws WrongFoodException {
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

    public EnclosureSize getRequiredSize() {
        return requiredSize;
    }

    public void setRequiredSize(EnclosureSize requiredSize) {
        this.requiredSize = requiredSize;
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

    public boolean equals(Object o){
        if (this == o) {
            return true;
        } else if (o == null || getClass() != o.getClass()){
            return false;
        }
        Animal animal = (Animal) o;
        return Objects.equals(name, animal.getName());
    }

    public int hashCode(){
        return Objects.hash(name);
    }

    public abstract void makeSomeAction();
}
