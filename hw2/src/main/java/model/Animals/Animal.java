package model.Animals;

import model.Food.Food;
import model.Food.Grass;
import model.Food.Meat;

public abstract class Animal {
    private int hunger;
    private String name;

    public Animal(int hunger, String name){
        this.hunger=hunger;
        this.name = name;
    };

    public boolean eat(Food food){
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

    public void addHunger(int addedHunger){
        int total = addedHunger + getHunger();
        if(total > 100){
            total = 100;
        } else if (total < 0) {
            total = 0;
        }
        setHunger(total);
    }

    public abstract void makeSomeAction();
}
