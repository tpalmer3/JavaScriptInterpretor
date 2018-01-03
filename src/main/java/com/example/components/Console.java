package com.example.components;

import com.eclipsesource.v8.V8Object;
import com.example.annotations.JSComponent;
import com.example.annotations.JSRunnable;
import com.example.controllers.CLI;
import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;
import edu.cmu.sphinx.api.SpeechResult;
import edu.cmu.sphinx.result.WordResult;

import javax.sound.sampled.AudioSystem;
import java.io.IOException;
import java.util.Scanner;

@JSComponent
public class Console {

    private static Scanner in;
    private static Configuration configuration;
    private static LiveSpeechRecognizer recognizer;

    static {
        in = CLI.getScanner();
//        configureVoiceRecognition();
    }

    private static void configureVoiceRecognition() {
        configuration = new Configuration();

        configuration.setAcousticModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us");
        configuration.setDictionaryPath("resource:/edu/cmu/sphinx/models/en-us/cmudict-en-us.dict");
        configuration.setLanguageModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us.lm.bin");

        configuration.setSampleRate(8000);

        try {
            recognizer = new LiveSpeechRecognizer(configuration);
        } catch(IOException e) {
            System.err.println(e.getCause() + " : " + e.getMessage());
            System.err.println("Microphone/SpeachReconizer not configured properly.\n\tLiveSpeechRecognizer is null");
        }
    }
//    @JSRunnable
//    public void log(final String message) {
//        System.out.println("> " + message);
//    }

    @JSRunnable
    public void log(Object out) {
        print("> " + out);
    }

    @JSRunnable
    public void print(Object out) {
        System.out.println(" " + out.toString());
    }

    @JSRunnable
    public String input(String out) {
        System.out.print(out);
        return in.nextLine().trim();
    }

    @JSRunnable
    public String inputV(String out) {
        String ret = "";

        try {
            recognizer.startRecognition(true);
            print("Ready: (Start Speaking)");

            SpeechResult result;
            String command = "";
            while ((result = recognizer.getResult()) != null && !command.equals("stop")) {
                command = result.getHypothesis();
                if(command.length() == 0)
                    break;
                print(out + command);
                ret += command + " ";
            }

            recognizer.stopRecognition();

//            SpeechResult result = recognizer.getResult();
//            recognizer.stopRecognition();
//            print("Finished:");
//
////            SpeechAligner aligner = new SpeechAligner(configuration);
////            recognizer.align(new URL("101-42.wav"), "one oh one four two");
//
//            int count = 1;
//            for(WordResult w: result.getWords())
//                System.out.println(count++ + "> " + w);
//
//            ret = result.getHypothesis();
        } catch (IllegalStateException e) {
            System.err.println(e.getCause() + " : " + e.getMessage());
        }


        System.out.println(out + ret);
        return ret;
    }

    @JSRunnable
    public void clear() {
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
    }

    @JSRunnable
    public String add(int a, int b) { return Integer.toString(a+b); }

    @JSRunnable
    public void run(String cmd) throws IOException {
        ProcessBuilder builder = new ProcessBuilder(
                "cmd.exe", "/c", cmd);
        builder.redirectErrorStream(true);
        Process p = builder.start();
    }

}
