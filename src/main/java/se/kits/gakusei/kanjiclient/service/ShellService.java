package se.kits.gakusei.kanjiclient.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
public class ShellService {

    public ShellService(){

    }

    @Autowired
    KanjiService kanjiService;

    @ShellMethod(value = "Get kanji lesson.", key = "get")
    public void getLesson(@ShellOption(defaultValue = "random") String lessonName) {

        System.out.println("Response: " + kanjiService.getLesson(lessonName.replace('_', ' ')));

    }

    @ShellMethod(value = "Get a list of all available lessons.", key = "lessons")
    public void getAllLessons(){
        /*String[] strings = kanjiService.getAllLessons().split("name\":\"");
        System.out.println("Available lessons are: ");
        for (int i=1; i<strings.length; i++){
            System.out.println(strings[i].substring(0, strings[i].indexOf('"')).replace(' ', '_'));
        }*/
        kanjiService.printLessons();
        System.out.println("\nUse the above list of available lessons to fetch questions with the " +
                "command 'get' followed by the name of lessons you want to fetch questions from.\n" +
                "E.g. 'get KLL_01'\n");
    }
}
