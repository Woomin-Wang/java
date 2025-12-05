package this_is_java.ch15.sec05.exam04;

import java.util.Comparator;

public class FruitComparator implements Comparator<Fruit> {
    @Override
    public int compare(Fruit o1, Fruit o2) {
        return o1.price < o2.price ? -1 : (o1.price == o2. price) ? 0 : 1;
        //return Integer.compare(o1.price, o2.price);
    }
}
