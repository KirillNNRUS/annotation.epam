package pks.ent.reflection;

public class SimpleClassThirdChild extends SimpleClassThird {
    @Override
    void print() {
        System.out.println("SimpleClassThirdChild is here");
    }

    @Override
    void printSecond() {
        System.out.println("It is SimpleClassThirdChild.class");
    }
}
