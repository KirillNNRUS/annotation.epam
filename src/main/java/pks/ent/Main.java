package pks.ent;

import pks.ent.annotations.Child;
import pks.ent.annotations.ChildDTO;
import pks.ent.annotations.Parent;
import pks.ent.reflection.*;

import java.util.ArrayList;
import java.util.List;

public class Main {
    static Class[] interfaceArrayOfDeprecatedClass = null;
    static Class[] interfaceArrayOfCompareClass = null;
    static List<Object> mainClassList = new ArrayList<>();

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
        addValueToMainClassList(new SimpleClassFirst()); //1
        addValueToMainClassList(new SimpleClassFirstChild()); //2
        addValueToMainClassList(new SimpleClassSecond()); //3
        addValueToMainClassList(new SimpleClassSecondChild()); //4
        addValueToMainClassList(new SimpleClassThird()); //5
        addValueToMainClassList(new SimpleClassThirdChild()); // 6
        addValueToMainClassList(new SimpleClassFourth()); //7
        addValueToMainClassList(new SimpleClassFourthChild()); //8
        addValueToMainClassList(new SimpleClassFourthSecondChild()); //9

        printClassIsDeprecated(mainClassList);
        System.out.println("-=!!!!=-");
    }

    static void addValueToMainClassList(Object object) {
        mainClassList.add(object);
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

    static Class[] setInterfaceArray(Object object) {
        return object.getClass().getInterfaces();
    }

    /*
    Как же я повеселился, пока не придумал, простейшее решение, завести счетчик, и его минусовать, если
    необходимый интерфейс найден :-)
    Конечно это не идеальное решение, т.к. в моем алгоритме берутся только интерфейсы которые явно указаны
    в классе (implements ...), и вверх, по родителям, я не лезу. Но... я уже счастлив, что смог сделать хоть это :-)
    Т.к. вчера вечером, у меня был "идейный" ступор, я топтался на одном месте, и не видел выхода
     */
    static void printAnotherClassWithAllInterfaces(Object classDeprecatedObject) {
        //Делаю Array интерфейсов для конкретного класса
        interfaceArrayOfDeprecatedClass = setInterfaceArray(classDeprecatedObject);

        for (Object classForCompareFromMainClassListObject : mainClassList) {

            if (classForCompareFromMainClassListObject.getClass().isAnnotationPresent(Deprecated.class)) {
                //Пропускаем Deprecated, и в том числе самого себя
                continue;
            }

            if (classDeprecatedObject.getClass().getInterfaces().length >
                    classForCompareFromMainClassListObject.getClass().getInterfaces().length) {
                //Не хватает количества имплементируемых интерфейсов
                continue;
            }

            //Делаю счетчик, необходимого количества интерфейсов
            int contInterfaceOfOfDeprecatedClass = interfaceArrayOfDeprecatedClass.length;
            interfaceArrayOfCompareClass = setInterfaceArray(classForCompareFromMainClassListObject);

            for (Class deprecatedClass : interfaceArrayOfDeprecatedClass) {

                for (Class compareClass : interfaceArrayOfCompareClass) {

                    if (compareClass == deprecatedClass) {
                        //Ух ты, один из необходимых интерфейсов найден
                        contInterfaceOfOfDeprecatedClass--;
                    }

                    if (contInterfaceOfOfDeprecatedClass == 0) {
                        //Твою ж налево! Все необходимые интерфейсы найдены! Радуемся и выводим предложение
                        System.out.println("Try to use " + classForCompareFromMainClassListObject.getClass() + " This class has the same interfaces.");
                        break;
                        //Прерываю for (Class compareClass : interfaceArrayOfCompareClass), т.к. интерфейсы найдены,
                        //и незачем их искать дальше
                    }
                }
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
                printAnotherClassWithAllInterfaces(object);
            }
        }
    }
}