package model;

import model.Animals.Actions.Voice;
import model.Animals.Animal;
import model.Food.Food;

public class Worker {

    public void feed(Food food, Animal animal) {
        if (animal.eat(food)) {
            System.out.println("Животное успешно покормлено!");
        } else {
            System.out.println("Ошибка! Не получилось покормить животное!");
        }
    }

    public void getVoice(Voice animal) {
        System.out.println(animal.voice());
    }
}
