package inflearn_java_middle.generic.test.ex3;

public class Shuttletest {

    public static void main(String[] args) {
        Shuttle<Marine> shuttle1 = new Shuttle<>();
        shuttle1.in(new Marine("마린", 40));
        shuttle1.showInfo();

        Shuttle<Marine> shuttle2 = new Shuttle<>();
        shuttle2.in(new Marine("저글링", 35));
        shuttle2.showInfo();

        Shuttle<Marine> shuttle3 = new Shuttle<>();
        shuttle3.in(new Marine("질럿", 100));
        shuttle3.showInfo();
    }
}
