package model.Enclusores;

import model.Animals.Animal;
import model.Animals.Carnivorour;
import model.Animals.Herbivore;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class Enclosure<T extends Animal> {
    private Set<T> animals;
    private EnclosureSize size;
    private EnclosureType type = null;

    public Enclosure(EnclosureSize size){
        this.animals = new HashSet<>();
        this.size = size;
        this.type = type;
    }

    public boolean addAnimal(T animal) {
        if (size.ordinal() >= animal.getRequiredSize().ordinal()) {
            if ((animal instanceof Carnivorour && type == EnclosureType.CARNIVOROUROUR) ||
                    (animal instanceof Herbivore && type == EnclosureType.HERBIVORE) ||
                    (type == null)) {
                if(type == null){
                    if (animal instanceof Carnivorour){
                        type = EnclosureType.CARNIVOROUROUR;
                    } else if (animal instanceof Herbivore) {
                        type = EnclosureType.HERBIVORE;
                    }
                }
                if (animals.add(animal)) {
                    System.out.printf("%s успешно помещенн(a) в вольер!\n", animal.getName());
                    return true;
                } else {
                    System.out.println("Системная ошибка");
                    return false;
                }
            } else {
                System.out.println("Этот вольер не подходит для данного типа животного!");
                return false;
            }
        } else {
            System.out.println("Животному этот вольер будет маленьким! Выберите другой!");
            return false;
        }
    }

    public boolean removeAnimal(String name) {
        return animals.removeIf(animal -> animal.getName().equals(name));
    }

    public Optional<T> findAnimal(String name) {
        Optional<T> a = animals.stream().filter(animal -> animal.getName().equals(name)).findFirst();
        if (!a.isPresent()) {
            System.out.println("Такого животного нет!");
            return Optional.empty();
        } else {
            return a;
        }
    }
}