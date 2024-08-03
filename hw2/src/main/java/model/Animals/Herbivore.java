package model.Animals;

import model.Food.Food;
import model.Food.Grass;
import model.Food.Meat;

public abstract class Herbivore extends Animal {
    public Herbivore(int hunger, String name){
        super(hunger, name);
    }

    public boolean eat(Food food){
        if(food instanceof Grass){
            if(!food.getUsed()) {
                super.addHunger(food.getSaturation());
                food.eated();
                return true;
            }
            else {
                System.out.println("Эта еда уже съедена!");
            }
        }
        else {
            System.out.println("Ошибка! Это еда для хищников!");
        }
        return false;
    }

    public int getHunger() {
        return super.getHunger();
    }

    public void setHunger(int hunger) {
        super.setHunger(hunger);
    }

    public String getName() {
        return super.getName();
    }

    public void setName(String name) {
        super.setName(name);
    }
}
