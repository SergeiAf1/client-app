package tsystems.javaschool.client.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class Tariff {

    private int id;

    private String tariffName;

    private  int price;

    private Boolean enabled;

    private List<Option> options;

}
