package task.model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString

public class Equipment {
    private int id;
    private String name;
    private int wellId;

    public Equipment(int id, String name) {
        this.id = id;
        this.name = name;
    }
}

