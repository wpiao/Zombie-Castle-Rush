package com.zombiecastlerush.building;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"question, attempts"})
@JsonIgnoreProperties({"solution"})
public class Puzzle extends Challenge {

    private int attempts;
    private String question, solution;

    //Constructor
    public Puzzle(String description, String question, String solution) {
        super(description);
        this.attempts = 0;
        this.setQuestion(question);
        this.setSolution(solution);
    }

    //Getters and setters
    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    public int getAttempts() {
        return attempts;
    }

    //Methods
    public int attemptPuzzle(String answer) {
        attempts += 1;
        if (answer.equalsIgnoreCase(getSolution())) {
            this.setCleared(true);
        } else if (getAttempts() < 3)
            System.out.println("Sorry wrong answer. Try again\n");
        return attempts;
    }

}
