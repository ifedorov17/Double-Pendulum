package ru.igor17.doublependulum.problem;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.FileNotFoundException;
import java.io.FileReader;

public class Initializer {
    private String problemFile;
    private Problem problem;

    public Initializer(String fileName) {
        this.problemFile = fileName;
    }

    public Problem getProblem() {
        return problem;
    }

    public void readProblem() throws FileNotFoundException {
        Gson gson = new Gson();
        JsonReader reader = new JsonReader(new FileReader(this.problemFile));
        this.problem = gson.fromJson(reader, Problem.class);
    }

}
