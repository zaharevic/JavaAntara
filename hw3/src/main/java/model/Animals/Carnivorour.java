package model.Animals;

import model.Enclusores.EnclosureSize;
import model.Exceptions.WrongFoodException;
import model.Food.Food;
import model.Food.Meat;

public abstract class Carnivorour extends Animal {
    public Carnivorour(int hunger, String name, EnclosureSize requiredSize){
        super(hunger, name, requiredSize);
    }

    public boolean eat(Food food) throws WrongFoodException {
        if(food instanceof Meat ){
            if(!food.getUsed()) {
                System.out.println("Животное поело");
                super.addHunger(food.getSaturation());
                food.eated();
                return true;
            }
            else {
                System.out.println("Эта еда уже съедена!");
            }
        }
        else {
            throw new WrongFoodException("Ошибка! Это еда для травоядных!");
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