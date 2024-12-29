package org.example.seminar6_321;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.util.ArrayList;

class CountingJob implements Runnable {
    private int[] listOfNumbers;
    private int startingIndex;
    private int step;
    private int numberOfPrimes;

    public CountingJob(int[] listOfNumbers, int startingIndex, int step) {
        this.listOfNumbers = listOfNumbers;
        this.startingIndex = startingIndex;
        this.step = step;
    }

    public int getNumberOfPrimes() {
        return this.numberOfPrimes;
    }

    @Override
    public void run() {
        numberOfPrimes = PrimeUtils.countPrimes(listOfNumbers, startingIndex, step);
        System.out.println(numberOfPrimes);
    }
}

public class HelloController {
    @FXML
    private ListView<String> messageListView;

    @FXML
    private TextField listLengthTxtField;
    @FXML
    private TextField numberOfThreadsTxtField;

    private ObservableList<String> messageList = FXCollections.observableList(new ArrayList<>());

    @FXML
    public void initialize() {
        messageListView.setItems(messageList);
    }

    @FXML
    protected void onComputeButtonClick() {
        int listLength = 0;
        try {
            listLength = Integer.parseInt(listLengthTxtField.getText());
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Lungimea listei trebuie sa fie un numar.");
            alert.show();
        }

        int numberOfThreads = 0;
        try {
            numberOfThreads = Integer.parseInt(numberOfThreadsTxtField.getText());
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Numarul de thread-uri trebuie sa fie un numar.");
            alert.show();
        }
        int[] listOfNumbers = PrimeUtils.generateRandomNumbers(listLength);

        //cream "job"-ul pentru fiecare thread
        CountingJob[] listOfJobs = new CountingJob[numberOfThreads];
        for (int i = 0; i < numberOfThreads; i++) {
            listOfJobs[i] = new CountingJob(listOfNumbers, i, numberOfThreads);
        }
        //cream thread-urile si atribuim fiecaruia un job
        Thread[] listOfThreads = new Thread[numberOfThreads];
        for (int i = 0; i < numberOfThreads; i++) {
            listOfThreads[i] = new Thread(listOfJobs[i]);
        }
        long t1 = System.currentTimeMillis();
        //start threads
        for (int i = 0; i < numberOfThreads; i++) {
            listOfThreads[i].start();
        }

        int finalNumberOfThreads = numberOfThreads;
        Thread collectorThread = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < finalNumberOfThreads; i++) {
                    try {
                        listOfThreads[i].join();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                long t2 = System.currentTimeMillis();
                long duration = t2 - t1;
                System.out.println("Duration: " + duration);
                int totalPrimes = 0;

                //calculeaza numarul total de numere prime
                for (int i = 0; i < finalNumberOfThreads; i++) {
                    totalPrimes += listOfJobs[i].getNumberOfPrimes();
                }
                System.out.println("Total number of primes:" + totalPrimes);

                int finalTotalPrimes = totalPrimes;
                //https://docs.oracle.com/javase/8/javafx/api/javafx/application/Platform.html#runLater-java.lang.Runnable-
                //execute updates to GUI components from Java FX Application Thread
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        String line = "Number of primes: "+ finalTotalPrimes+"; ";
                        line+="Number of threads: "+finalNumberOfThreads+"; ";
                        line+="Duration: "+duration;
                        messageList.add(line);
                    }
                });
            }
        });
        collectorThread.start();
        System.out.println(Thread.currentThread().getName());
//        System.out.println(listLength);
//        for (int nr : listOfNumbers) {
//            System.out.println(nr + " " + PrimeUtils.isPrime(nr));
//        }

    }
}