package pks.ent.reflection;

@Deprecated
public class SimpleClassFourthChild extends SimpleClassFourth implements SimpleInterface {
    @Override
    void print() {
        System.out.println("Print from the SimpleClassFourthChild");
    }
}
