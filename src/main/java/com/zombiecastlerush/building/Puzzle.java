package com.zombiecastlerush.building;

import java.util.*;

class Puzzle extends Challenge{
    private String name;
    private int attempts;
    private final int max_attempts=3;
    private Inventory puzzInventory;
    private HashMap<String, String> questionAnswers = new HashMap<>();

    //Constructor
    public Puzzle(String name,String description){
        super(description);
        this.setName(name);
        this.attempts=0;
    }

    //Getters and setters
    public void setName(String name) {
        this.name = name;
    }

    public int getAttempts() {
        return attempts;
    }

    public Inventory getPuzzInventory() {
        return puzzInventory;
    }

    //Methods
    //initializes the questionAnswers Map of Puzzle with question as the key and solution as the value
    public void setPuzzle(String puzzleQuestion, String solution) {
        questionAnswers.put(puzzleQuestion,solution);
    }

    public void presentPuzzle(){
        System.out.println(getDescription());
    }

    public void attemptPuzzle(){
        List<String> listOfQuestions = new ArrayList<>(questionAnswers.keySet());

        if(getAttempts()<max_attempts){
            attempts += 1;
            Scanner myScanner = new Scanner(System.in);
            System.out.println(listOfQuestions.get(0));
            String userInput = myScanner.nextLine();

            if(userInput.equalsIgnoreCase(questionAnswers.get(listOfQuestions.get(0)))) {
                System.out.println("Right answer. Here is your reward");
                dropAll(); //show the puzzle inventory to the player
                super.setCleared(true); //set the puzzle flag to be clear
                listOfQuestions.remove(0);
            }
            else if (getAttempts()<max_attempts){
                    System.out.println("Sorry wrong answer. Try again\n");
                    attemptPuzzle();
                }
            else{
                    System.out.println("Wrong Answer!! You have had your chances...You failed...Game Over!!!");
                    System.exit(0);
                }
        }
    }

    //displays the puzzles inventory if Puzzle is solved
    void dropAll(){
        System.out.println("This will print out the inventory of the puzzle by calling puzzInventory.getItems()");
        //puzzInventory.getItems();
    }

    public static void main(String[] args) {
        Puzzle p1 = new Puzzle("Puzzle1","I'm the formidable puzzle....I dare you solve me." +
                "\n\nAnswer the below correctly and be rewarded or be ready to meet your fate!\n");
        p1.setPuzzle("What is 2+2?","4");
        //p1.getPuzzInventory().addItems(Item item1);
        p1.presentPuzzle();
        p1.attemptPuzzle();
    }
}