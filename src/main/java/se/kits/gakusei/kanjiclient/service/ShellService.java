package se.kits.gakusei.kanjiclient.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
public class ShellService {

    private boolean answerRequired = false;
    private String currentQuestion;

    public ShellService(){
    }

    @Autowired
    KanjiService kanjiService;

    @ShellMethod(value = "Get a random question from selected lesson.\n\t\t\t@Param Lesson name.", key = "get")
    public void getQuestion(@ShellOption String lessonName) {

        if(kanjiService.isLesson(lessonName)){
            currentQuestion = kanjiService.getQuestion(lessonName.replace('_', ' '));
            System.out.println(currentQuestion);
            System.out.println("\nDid you answer correctly? Provide an answer using the command 'answer' followed" +
                    " by 'yes', 'no' or 'vetej'.\n");
            answerRequired = true;
        }else{
            System.out.println("The specified lesson does not exist or is unavailable.\n");
        }
    }

    @ShellMethod(value = "Get a list of all available lessons.", key = "lessons")
    public void getAllLessons(){
        kanjiService.printLessons();
        System.out.println("\nUse the above list of available lessons to fetch questions with the " +
                "command 'get' followed by the name of lesson you want to fetch a random question from.\n" +
                "E.g. 'get KLL_01'\n");
    }

    @ShellMethod(value = "Provide an answer to a question. \n\t\t\t@Param 'yes', 'no' or 'vetej'", key = "answer")
    public void answer(@ShellOption String answer){
        if(answer.equals("yes") || answer.equals("no") || answer.equals("vetej")){
            ResponseEntity responseEntity = kanjiService.sendAnswer(answer, currentQuestion);
            System.out.println("Server response: " + responseEntity.getStatusCode());
            System.out.println("Answer submitted.\n");
            answerRequired = false;
        }else{
            System.out.println("Answer is not valid. Please answer with 'yes', 'no' or 'vetej'.\n");
        }
    }

    @ShellMethod(value = "Aborts the ongoing question.", key = "abort")
    public void abort(){
        System.out.println("Question aborted.\n");
        answerRequired = false;
    }

    public Availability answerAvailability(){
        return answerRequired ? Availability.available()
                : Availability.unavailable("no question has been asked.");
    }

    public Availability getQuestionAvailability(){
        return answerRequired ?
                Availability.unavailable("a question has been asked. \nProvide an answer before proceeding " +
                        "or use the command 'abort' if you do not wish to answer.")
                : Availability.available();
    }

    public Availability getAllLessonsAvailability(){
        return answerRequired ?
                Availability.unavailable("a question has been asked. \nProvide an answer before proceeding " +
                        "of use the command 'abort' if you do not wish to answer.")
                : Availability.available();
    }

    public Availability abortAvailability(){
        return answerRequired ? Availability.available()
                : Availability.unavailable("there is no question to abort.");
    }
}
