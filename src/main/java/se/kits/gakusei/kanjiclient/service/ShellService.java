package se.kits.gakusei.kanjiclient.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.util.ArrayList;

@ShellComponent
public class ShellService {

    private boolean answerRequired = false;
    private ArrayList<String> answers = new ArrayList<>();
    private String currentQuestion;

    public ShellService(){
        answers.add("yes");
        answers.add("no");
        answers.add("vetej");
    }

    @Autowired
    KanjiService kanjiService;

    @ShellMethod(value = "Get a random question from selected lesson.\n\t\t\t@Param Lesson name.", key = "get")
    public void getQuestion(@ShellOption String lessonName) {

        if(kanjiService.isLesson(lessonName)){
            currentQuestion = kanjiService.getQuestion(lessonName.replace('_', ' '));
            System.out.println(currentQuestion);
            System.out.println("\nDid you answer correctly? Provide an answer using the command 'answer' followed" +
                    " by 'yes', 'no' or 'vetej'.");
            answerRequired = true;
        }else{
            System.out.println("The specified lesson does not exist or is unavailable.");
        }
    }

    @ShellMethod(value = "Get a list of all available lessons.", key = "lessons")
    public void getAllLessons(){
        kanjiService.printLessons();
        System.out.println("\nUse the above list of available lessons to fetch questions with the " +
                "command 'get' followed by the name of lesson you want to fetch a random question from.\n" +
                "E.g. 'get KLL_01'\n");
    }

    @ShellMethod(value = "Provide an answer to a question.", key = "answer")
    public void answer(@ShellOption String answer){
        if(answer.equals("yes") || answer.equals("no") || answer.equals("vetej")){
            kanjiService.sendAnswer(answer, currentQuestion);
            answerRequired = false;
        }else{
            System.out.println("Answer is not valid. Please answer with 'yes', 'no' or 'vet ej'.");
        }
    }

    public Availability answerYesAvailability(){
        return answerRequired ? Availability.available() : Availability.unavailable("no question has been asked");
    }

    public Availability getQuestionAvailability(){
        return answerRequired ?
                Availability.unavailable("a question has been asked. An answer is required before proceeding.")
                : Availability.available();
    }

    public Availability getAllLessonsAvailability(){
        return answerRequired ?
                Availability.unavailable("a question has been asked. An answer is required before proceeding.")
                : Availability.available();
    }
}
