package inflearn_java_middle.lang.clazz;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ClassMetaMain {
    public static void main(String[] args) throws Exception {
        Class clazz = String.class;

        Field[] fields = clazz.getDeclaredFields();
        for (Field field: fields) {
            System.out.println("Field: " + field.getType() + " " + field.getName());
        }

        Method[] methods = clazz.getDeclaredMethods();
        for (Method method: methods) {
            System.out.println("method = " + method);
        }

        System.out.println("SuperClass: " + clazz.getSuperclass().getName());

        Class[] interfaces = clazz.getInterfaces();
        for (Class i: interfaces) {
            System.out.println("interface = " + i.getName());
        }
    }
}
