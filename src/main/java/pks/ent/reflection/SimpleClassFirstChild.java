package pks.ent.reflection;

public class SimpleClassFirstChild extends SimpleClassFirst {
    @Override
    int calculate(int a, int b) {
        return super.calculate(a, b);
    }

    int calculator(int a, int b) {
        int superCorrection = 1;
        return a + b + superCorrection;
    }
}
