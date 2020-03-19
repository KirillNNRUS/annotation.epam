package pks.ent.reflection;

@Deprecated
public class SimpleClassThird implements SimpleInterface, SimpleInterfaceSecond {

    @Deprecated
    void print() {
        System.out.println("SimpleClassThird is here");
    }

    void printSecond() {
        System.out.println("It is SimpleClassThird.class");
    }
}
