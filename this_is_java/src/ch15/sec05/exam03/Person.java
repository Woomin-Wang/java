package this_is_java.ch15.sec05.exam03;

public class Person implements Comparable<Person> {
    public String name;
    public int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public int compareTo(Person o) {
        return this.age < o.age ? -1 : (this.age == o.age) ? 0 : 1;
        //return Integer.compare(this.age, o.age);
    }
}
