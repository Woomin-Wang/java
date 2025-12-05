package inflearn_java_middle.generic.ex3;

import inflearn_java_middle.generic.animal.Dog;
import inflearn_java_middle.generic.animal.Cat;

public class AnimalHospitalMainV2 {
    public static void main(String[] args) {
        AnimalHospitalV2<Dog> dogAnimalHospital = new AnimalHospitalV2<>();
        AnimalHospitalV2<Cat> catAnimalHospital = new AnimalHospitalV2<>();
        AnimalHospitalV2<Integer> integerAnimalHospital = new AnimalHospitalV2<>();
        AnimalHospitalV2<Object> objectAnimalHospital = new AnimalHospitalV2<>();
    }
}
