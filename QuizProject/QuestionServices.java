import java.util.Scanner;

public class QuestionServices {
    Scanner sc = new Scanner(System.in);
    Questions [] questions = new Questions[5];
    int[] answers = {3, 3, 1, 1, 3};
    int [] userAnswer = new int[5];

    public QuestionServices() {
        questions[0] = new Questions(1, "What is the capital of France?", "Berlin", "Madrid", "Paris", "Rome", "Paris");
        questions[1] = new Questions(2, "What is the largest planet in our solar system?", "Earth", "Mars", "Jupiter", "Saturn", "Jupiter");
        questions[2] = new Questions(3, "What is the chemical symbol for water?", "H2O", "CO2", "O2", "NaCl", "H2O");
        questions[3] = new Questions(4, "Who wrote 'To Kill a Mockingbird'?", "Harper Lee", "Mark Twain", "Ernest Hemingway", "F. Scott Fitzgerald", "Harper Lee");
        questions[4] = new Questions(5, "What is the capital of Japan?", "Beijing", "Seoul", "Tokyo", "Bangkok", "Tokyo");
    }



    public void displayQuestion(){
        int i = 0;
        for(Questions question : questions) {
            System.out.println("Question No: " + question.getQuesNo());
            System.out.println("Question: " + question.getQuestion());
            System.out.println("1. " + question.getOption1());
            System.out.println("2. " + question.getOption2());
            System.out.println("3. " + question.getOption3());
            System.out.println("4. " + question.getOption4());
            // System.out.println("Answer: " + question.getAnswer());
            System.out.println("======================");
            System.out.print("Enter your answer (1-4): ");
            userAnswer[i] = sc.nextInt();
            i++;
        }
        System.out.println("----User Answers----");
        for(int j = 0; j < userAnswer.length; j++) {
            System.out.println("For Question " + (j+1) + " User Answer: " + userAnswer[j]);
            System.out.println("======================");
        }
    }

    public void evaluateAnswer(){
        int score = 0;
        for (int i = 0; i < answers.length; i++) {
            if(userAnswer[i] == answers[i]){
                score ++;
            }
        }
        System.out.println("Your Score is: " + score);
    }
}
