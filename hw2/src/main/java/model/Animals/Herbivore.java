package model.Animals;

import model.Food.Food;
import model.Food.Grass;

public abstract class Herbivore extends Animal {

    public Herbivore(int hunger, String name) {
        super(hunger, name);
    }

    @Override
    public boolean eat(Food food) {
        if (food instanceof Grass) {
            addHunger(food.getSaturation());
            food.eated();
            return true;
        } else {
            System.out.println("Ошибка! Это еда для хищников!");
        }
        return false;
    }
}