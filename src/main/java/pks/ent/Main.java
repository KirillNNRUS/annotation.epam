package pks.ent;

import pks.ent.annotations.Child;
import pks.ent.annotations.ChildDTO;
import pks.ent.annotations.Parent;
import pks.ent.reflection.*;

import java.util.*;
import java.util.stream.Collectors;

public class Main {
    static List<Class<?>> interfaceList = Collections.EMPTY_LIST;
    static List<Object> objectArrayList = Collections.EMPTY_LIST;
    static Map<Object, HashSet<Object>> mapClassAndInterfaces = new HashMap<>();

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

//        objectArrayList.add(new SimpleClassFirst()); //1
//        objectArrayList.add(new SimpleClassFirstChild()); //2
//        objectArrayList.add(new SimpleClassSecond()); //3
//        objectArrayList.add(new SimpleClassSecondChild()); //4
//        objectArrayList.add(new SimpleClassThird()); //5
//        objectArrayList.add(new SimpleClassThirdChild()); //6
//        objectArrayList.add(new SimpleClassFourth()); //7
//        objectArrayList.add(new SimpleClassFourthChild()); //8
//        objectArrayList.add(new SimpleClassFourthSecondChild()); //9


        System.out.println("!!!!!");
        addValuesToMultiMap(new SimpleClassFirst()); //1
        addValuesToMultiMap(new SimpleClassFirstChild()); //2
        addValuesToMultiMap(new SimpleClassSecond()); //3
        addValuesToMultiMap(new SimpleClassSecondChild()); //4
        addValuesToMultiMap(new SimpleClassThird()); //5
        addValuesToMultiMap(new SimpleClassThirdChild()); //6
        addValuesToMultiMap(new SimpleClassFourth()); //7
        addValuesToMultiMap(new SimpleClassFourthChild()); //8
        addValuesToMultiMap(new SimpleClassFourthSecondChild()); //9

//        System.out.println(Arrays.asList(mapClassAndInterfaces.entrySet().toArray()));
        addValueToObjectArrayList();
//        System.out.println(objectArrayList.toString());
        printClassIsDeprecated(objectArrayList);
//        printClassIsDeprecated(mapClassAndInterfaces.keySet().stream());
        System.out.println("-=!!!!=-");
    }


    static void addValuesToMultiMap(Object object) {
        setInterfaceList(object);
        mapClassAndInterfaces.put(object, new HashSet<>(interfaceList));
    }

    static void addValueToObjectArrayList() {
        objectArrayList = new ArrayList<>(mapClassAndInterfaces.keySet());
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

    static void setInterfaceList(Object object) {
        interfaceList = Arrays.asList(object.getClass().getInterfaces());
    }

    static void printAnotherClassWithInterface(Class sClass) {
        setInterfaceList(sClass);

        if (interfaceList != Collections.EMPTY_LIST) {
            for (Class c : interfaceList) {
                System.out.println(c);
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
//                printAnotherClassWithInterface(getClass(object));
            }
        }
    }
}
