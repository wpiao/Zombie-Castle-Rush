package com.zombiecastlerush.building;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.zombiecastlerush.entity.Entity;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * challenge class provides the challenge description and validation
 * TODO: more methods and attributes
 */

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME) @JsonSubTypes({
        @JsonSubTypes.Type(value = Puzzle.class, name = "puzzle"),
        @JsonSubTypes.Type(value = Combat.class, name = "combat")
})
@JsonPropertyOrder({"description, inventory, cleared"})
public class Challenge extends Entity {

    private boolean cleared;

    public Challenge() {}

    public Challenge(String description) {
        super.setDescription(description);
        this.setCleared(false);
    }

    /**
     * @param flag
     * @return
     */
    public void setCleared(boolean flag) {
        this.cleared = flag;
    }

    public boolean isCleared() {
        return cleared;
    }
}
