package model.Animals;

import model.Food.Food;
import model.Food.Grass;
import model.Food.Meat;

public abstract class Carnivorour extends Animal {

    public Carnivorour(int hunger, String name) {
        super(hunger, name);
    }

    @Override
    public boolean eat(Food food) {
        if (food instanceof Meat) {
            addHunger(food.getSaturation());
            food.eated();
            return true;
        } else {
            System.out.println("Ошибка! Это еда для травоядных!");
        }
        return false;
    }
}