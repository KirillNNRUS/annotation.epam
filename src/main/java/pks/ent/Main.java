package pks.ent;

import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import pks.ent.annotations.Child;
import pks.ent.annotations.ChildDTO;
import pks.ent.annotations.Parent;
import pks.ent.reflection.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Parent parent = new Parent();
        parent.setName("Kirill");
        System.out.println(parent);
        Child child = new Child();
        child.setParent(parent);
        child.setChildName("Dariya");
        child.setYearOfBirthday(2012);
        System.out.println(child);

        ChildDTO childDTO = new ChildDTO(child);
        System.out.println(childDTO.getChild());

        List<Object> objectArrayList = new ArrayList<>();
        objectArrayList.add(new SimpleClassFirst());
        objectArrayList.add(new SimpleClassFirstChild());
        objectArrayList.add(new SimpleClassSecond());
        objectArrayList.add(new SimpleClassSecondChild());
        objectArrayList.add(new SimpleClassThird());
        objectArrayList.add(new SimpleClassThirdChild());
        objectArrayList.add(new SimpleClassFourth());
        objectArrayList.add(new SimpleClassFourthChild());
        objectArrayList.add(new SimpleClassFourthSecondChild());

        List<Object> interfaceList = new ArrayList<>();


        printClassIsDeprecated(objectArrayList);
    }

    static Class getClass(Object object) {
        return object.getClass();
    }

    static Class getSuperClass(Object object) {
        return getClass(object).getSuperclass();
    }

    static void printAnotherNotDeprecatedChildSuperClass(List<Object> list, Class parent) {
        for (Object object : list) {
            if (getSuperClass(object) == parent &&
                    !getClass(object).isAnnotationPresent(Deprecated.class)) {
                System.out.println(getClass(object));
            }
        }
    }

    static void printClassIsDeprecated(List<Object> list) {
        for (Object object : list) {
            if (getClass(object).isAnnotationPresent(Deprecated.class)) {
                System.out.println("");
                System.out.println(getClass(object) + " is Deprecated");
                if (getSuperClass(object) != Object.class) {
                    System.out.println("Try to use Parent " + getSuperClass(object));
                    System.out.println("Or try to use another Child of the " + getSuperClass(object) + ":");
                    printAnotherNotDeprecatedChildSuperClass(list, getSuperClass(object));
                }
                /*
                Может и зря, но сделал проверку, нет ли не Deprecated детей у класса родителя,
                и предложение использовать их.
                 */
                if (getSuperClass(object) == Object.class) {
                    System.out.println("Try to use Child of the " + getClass(object) + ":");
                    printAnotherNotDeprecatedChildSuperClass(list, getClass(object));
                }

                List<Class<?>> tempList = Arrays.asList(getClass(object).getInterfaces());
                Class[] temp = getClass(object).getInterfaces();

                if (tempList != Collections.EMPTY_LIST) {
                }
            }
        }
    }
}
