package pks.ent.reflection;


public class SimpleClassFirst implements SimpleInterface {

    @Deprecated
    int calculate(int a, int b) {
        return a + b;
    }
}
