package pks.ent;

import pks.ent.annotations.Child;
import pks.ent.annotations.ChildDTO;
import pks.ent.annotations.Parent;
import pks.ent.reflection.*;

import java.util.*;

public class Main {
    static List<Object> interfaceList = Collections.EMPTY_LIST;
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

        System.out.println("!!!!!");
        addValuesToMapClassAndInterfaces(new SimpleClassFirst()); //1
        addValuesToMapClassAndInterfaces(new SimpleClassFirstChild()); //2
        addValuesToMapClassAndInterfaces(new SimpleClassSecond()); //3
        addValuesToMapClassAndInterfaces(new SimpleClassSecondChild()); //4
        addValuesToMapClassAndInterfaces(new SimpleClassThird()); //5
        addValuesToMapClassAndInterfaces(new SimpleClassThirdChild()); //6
        addValuesToMapClassAndInterfaces(new SimpleClassFourth()); //7
        addValuesToMapClassAndInterfaces(new SimpleClassFourthChild()); //8
        addValuesToMapClassAndInterfaces(new SimpleClassFourthSecondChild()); //9

        addValueToObjectArrayList();
        printClassIsDeprecated(objectArrayList);
        System.out.println("-=!!!!=-");
    }

    static void addValuesToMapClassAndInterfaces(Object object) {
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
        interfaceList = Arrays.asList((Object) object.getClass().getInterfaces());
    }

    static void printAnotherClassWithAllInterfaces(Object object) {
        //Деалю List интерфейсов для конкретного класса
        setInterfaceList(object);


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
                printAnotherClassWithAllInterfaces(object);
            }
        }
    }
}
