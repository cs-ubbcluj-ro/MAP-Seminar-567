package org.example.seminar6_322_1;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.ArrayList;

class PrimeCountingJob implements Runnable {
    private int[] listOfNumbers;
    private int start;
    private int pasi;

    public PrimeCountingJob(int[] listOfNumbers, int start, int pasi) {
        this.listOfNumbers = listOfNumbers;
        this.start = start;
        this.pasi = pasi;
    }

    private int numberOfPrimes;

    @Override
    public void run() {
        System.out.println("Starting to run thread " + start + "...");
        this.numberOfPrimes = PrimeUtils.countPrimes(listOfNumbers, start, pasi);
    }

    public int getNumberOfPrimes() {
        return numberOfPrimes;
    }
}

public class HelloController {
    public ListView messagesListView;
    private ObservableList<String> messagesObservableList = FXCollections.observableList(new ArrayList<>());
    public Label listLengthLabel;
    public TextField listLengthTextField;
    //public Label numberOfThreadsLabel;
    public TextField numberOfThreadsTextField;
    public Button computeButton;

    @FXML
    public void initialize() {
        messagesListView.setItems(messagesObservableList);
    }

    @FXML
    protected void onComputeButtonClicked() {
        int lungimeLista = 1000;
        int numberOfThreads = 10;
        try {
            lungimeLista = Integer.parseInt(listLengthTextField.getText());
        } catch (NumberFormatException nfe) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Eroare");
            alert.setContentText("Lungimea listei trebuie sa fie un numar natural.");
        }

        try {
            numberOfThreads = Integer.parseInt(numberOfThreadsTextField.getText());
        } catch (NumberFormatException nfe) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Eroare");
            alert.setContentText("Numarul de thread-uri trebuie sa fie un numar natural.");
        }

        int[] listOfNumbers = PrimeUtils.generateList(lungimeLista);

        //cream "job"-ul pentru fiecare thread
        PrimeCountingJob[] jobArray = new PrimeCountingJob[numberOfThreads];
        for (int i = 0; i < numberOfThreads; i++) {
            jobArray[i] = new PrimeCountingJob(listOfNumbers, i, numberOfThreads);
        }

        //cream thread-urile si atribuim fiecaruia un job
        Thread[] threadArray = new Thread[numberOfThreads];
        for (int i = 0; i < numberOfThreads; i++) {
            threadArray[i] = new Thread(jobArray[i]);
        }


        long startTime = System.currentTimeMillis();
        //start threads
        for (int i = 0; i < numberOfThreads; i++) {
            threadArray[i].start();
        }
        int finalNumberOfThreads = numberOfThreads;

        int finalLungimeLista = lungimeLista;
        Thread waitingThread = new Thread(){
            @Override
            public void run() {
                super.run();
                for (int i = 0; i< finalNumberOfThreads; i++){
                    try {
                        threadArray[i].join();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                long endTime = System.currentTimeMillis();
                long duration = endTime - startTime;


                int totalNumberOfPrimes = 0;
                //calculeaza numarul total de numere prime
                for (int i = 0; i < finalNumberOfThreads; i++) {
                    totalNumberOfPrimes += jobArray[i].getNumberOfPrimes();
                }
                System.out.println(totalNumberOfPrimes);
                int finalTotalNumberOfPrimes = totalNumberOfPrimes;
                //https://docs.oracle.com/javase/8/javafx/api/javafx/application/Platform.html#runLater-java.lang.Runnable-
                //execute updates to GUI components from Java FX Application Thread
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        String line = "Lungime lista: " + finalLungimeLista + "; Numere prime: " + finalTotalNumberOfPrimes + "; Numar thread-uri: " + finalNumberOfThreads + "; Durata: " + duration;
                        messagesObservableList.add(line);
                    }
                });
            }
        };

        waitingThread.start();

    }
}