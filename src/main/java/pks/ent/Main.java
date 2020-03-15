package pks.ent;

public class Main {
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
    }
}
