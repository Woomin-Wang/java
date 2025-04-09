package this_is_java.ch08.sec09;

public class InterfaceCImpl implements InterfaceC {
    @Override
    public void methodC() {
        System.out.println("InterfaceC-Method 실행");
    }

    @Override
    public void methodA() {
        System.out.println("InterfaceA-Method 실행");
    }

    @Override
    public void methodB() {
        System.out.println("InterfaceB-Method 실행");
    }
}
