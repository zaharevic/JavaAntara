package model.Animals;

import model.Enclusores.EnclosureSize;
import model.Exceptions.WrongFoodException;
import model.Food.Food;

import java.util.Objects;

public abstract class Animal {
    private int hunger;
    private String name;
    private EnclosureSize requiredSize;

    public Animal(int hunger, String name, EnclosureSize requiredSize){
        this.hunger=hunger;
        this.name = name;
        this.requiredSize = requiredSize;
    };

    public boolean eat(Food food) throws WrongFoodException {
        if(!food.getUsed()){
            addHunger(food.getSaturation());
            food.eated();
            return true;
        }else{
            System.out.println("Эта еда уже съедена!");
        }
        return false;
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

    public void addHunger(int addedHunger){
        int total = addedHunger + getHunger();
        if(total > 100){
            total = 100;
        } else if (total < 0) {
            total = 0;
        }
        setHunger(total);
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
