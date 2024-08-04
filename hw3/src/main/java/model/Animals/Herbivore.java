package model.Animals;

import model.Enclusores.EnclosureSize;
import model.Exceptions.WrongFoodException;
import model.Food.Food;
import model.Food.Grass;

public abstract class Herbivore extends Animal {
    public Herbivore(int hunger, String name, EnclosureSize requiredSize){
        super(hunger, name, requiredSize);
    }

    public boolean eat(Food food) throws WrongFoodException {
        if(food instanceof Grass) {
            addHunger(food.getSaturation());
            food.eated();
            return true;
        }
        else {
            throw new WrongFoodException("Ошибка! Это еда для хищников!");
        }
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
