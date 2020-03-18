package pks.ent.reflection;


public class SimpleClassFirst implements SimpleMethod {

    @Deprecated
    int calculate(int a, int b) {
        return a + b;
    }
}
