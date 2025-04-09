package inflearn_java_middle.nested.nested;

public class ShadowingMain {
    public int value = 1;

    class Inner {
        public int value = 2;

        void go() {
            int value = 3;
            System.out.println("value = " + value);
            System.out.println(this.value);
            System.out.println(ShadowingMain.this.value);
        }
    }
}
