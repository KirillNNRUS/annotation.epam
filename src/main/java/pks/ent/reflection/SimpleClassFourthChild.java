package pks.ent.reflection;

@Deprecated
public class SimpleClassFourthChild extends SimpleClassFourth implements SimpleMethod {
    @Override
    void print() {
        System.out.println("Print from the SimpleClassFourthChild");
    }
}
