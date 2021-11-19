package one.digitalinnovation.WineStock.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum WineType {

    RED("Red"),
    WHITE("White"),
    ROSE("Rosé"),
    SPARKLING("Sparkling"),
    FORTIFIED("Fortified");

    private final String description;
}
