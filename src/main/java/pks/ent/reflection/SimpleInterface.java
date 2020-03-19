package pks.ent.reflection;

interface SimpleInterface {
    default void printSimple() {
        System.out.println("SimpleInterface");
    }
}
