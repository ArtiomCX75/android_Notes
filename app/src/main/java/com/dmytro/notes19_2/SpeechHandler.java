package com.dmytro.notes19_2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;

import java.util.LinkedList;
import java.util.List;


/**
 * SpeechHandler is a class which listening speech and create text from it
 * constructor receive 2 params: Activity and ArrayList of notes
 * it has 2 public methods:
 * startListening()
 * stopListening()
 * after speech recognition class call method (onResult() or onPartialResults())
 * which add new Note with recognized text
 */

public class SpeechHandler implements RecognitionListener {

    //array of recognized variants of text
    private List<String> recognizedText = new LinkedList<>();
    //pointer to current activity
    private Activity activity;

    //instance of NotesKeeper
  //  private NotesKeeper notesKeeper;

    private SpeechRecognizer sr;

    /**
     * Constructor
     *
     * @param activity current activity
     */

    public SpeechHandler(Activity activity) { //public
        this.activity = activity;
        //notesKeeper = NotesKeeper.getInstance(activity); ///????????
    }

    //public methods:

    /**
     * StartListening:
     * initialization of speech recognizers and activate them
     */
    public void startListening() {
        sr = SpeechRecognizer.createSpeechRecognizer(activity);
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        recognizeDirectly(intent);

        sr.startListening(intent);
    }

    /**
     * stop listening of voice
     */
    public void stopListening() {
        sr.stopListening();
    }


    //private methods:

    /**
     * initialization of SpeechRecognizer
     */
    private void recognizeDirectly(Intent recognizerIntent) {
        // SpeechRecognizer requires EXTRA_CALLING_PACKAGE, so add if it's not here
        if (!recognizerIntent.hasExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE)) {
            recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "ru-RU");
            //todo maybe try sometime:
            //intent.putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true);
            //recognizerIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, "com.dummy");
        }

        SpeechRecognizer recognizer = getSpeechRecognizer();
        recognizer.startListening(recognizerIntent);
    }

    private SpeechRecognizer getSpeechRecognizer() {
        SpeechRecognizer recognizer;
        recognizer = SpeechRecognizer.createSpeechRecognizer(activity);
        recognizer.setRecognitionListener(this);
        return recognizer;
    }

    /**
     * get list of variants of recognized text
     *
     * @param heard  - list of variants of recognizered text
     * @param scores - possibility of each variant is true
     */
    private void receiveRecognizedText(List<String> heard, float[] scores) {
        recognizedText.clear();
        for (String str : heard) {
            recognizedText.add(str);
        }
        //todo: sometime change void method with global variable to List-returned method
    }

    /**
     * get results of recognition
     *
     * @param results of recognition
     */
    private void receiveResults(Bundle results) {
        if ((results != null)
                && results.containsKey(SpeechRecognizer.RESULTS_RECOGNITION)) {
            List<String> heard =
                    results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
            float[] scores =
                    results.getFloatArray(SpeechRecognizer.CONFIDENCE_SCORES);

            receiveRecognizedText(heard, scores);
        }
    }

    /**
     * get recognized text:
     * for now - firs one in silt - the one with highest possibility
     *
     * @return recognized text
     */
    private String getRecognizedText() {
        //return first variant of recognized text, the most probably one
        return recognizedText.get(0);
    }

    /**
     * adds new Note to array of notes
     */
    private void handleResult() {
        Note newNote = new Note(getRecognizedText());
        NoteHelper.addNote(newNote);
        ViewCreator view = new ViewCreator(activity, newNote);
    }

    @Override
    public void onResults(Bundle results) {
        receiveResults(results);
        handleResult();
    }

    @Override
    public void onPartialResults(Bundle partialResults) {
        receiveResults(partialResults);
        handleResult();
    }

    @Override
    public void onReadyForSpeech(Bundle params) {

    }

    @Override
    public void onBeginningOfSpeech() {

    }

    @Override
    public void onRmsChanged(float rmsdB) {

    }

    @Override
    public void onBufferReceived(byte[] buffer) {

    }

    @Override
    public void onEndOfSpeech() {

    }

    @Override
    public void onError(int error) {

    }


    @Override
    public void onEvent(int eventType, Bundle params) {

    }
}
