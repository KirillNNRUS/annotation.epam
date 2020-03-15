package pks.ent;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class Child {
    private Parent parent;
    private String childName;
    private int yearOfBirthday;
}
