package com.zombiecastlerush.building;

class Puzzle extends Challenge{
    private int attempts;
    public Inventory inventory;
    private String question, solution;

    //Constructor
    public Puzzle(String description, String question, String solution){
        super(description);
        this.attempts=0;
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
    public int attemptPuzzle(String answer){
            attempts+=1;
            if(answer.equalsIgnoreCase(getSolution())){
                super.setCleared(true);
            }
            return attempts;
    }

}
