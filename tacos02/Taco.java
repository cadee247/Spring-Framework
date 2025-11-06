package tacos.tacos02;

import java.util.List;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import jakarta.validation.Valid;
import lombok.Data;

@Data
public class Taco {

    @NotNull//its checking if there is a name as well as the size of the name
    @Size(min = 5, message = "Name must be at least 5 characters long")
    private String name;

    @NotNull//make sure there are no blocks that are null you have to choose one block atleast
    @Size(min = 1, message = "You must choose at least 1 ingredient")
    @Valid
    private List<Ingredient> ingredients;
}