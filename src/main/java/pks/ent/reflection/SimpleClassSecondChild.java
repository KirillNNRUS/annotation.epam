package pks.ent.reflection;

public class SimpleClassSecondChild extends SimpleClassSecond {
    @Override
    String welcome() {
        return super.welcome() + "!";
    }
}
