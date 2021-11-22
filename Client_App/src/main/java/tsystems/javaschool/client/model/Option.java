package tsystems.javaschool.client.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class Option {

    private int id;

    private String name;

    private int price;

    private int connectionPrice;

    private List<Option> dependentOptions;

    private List<Option> incompatibleOptions;
}
