package com.github.leafee98.CSTI.core;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import com.github.leafee98.CSTI.core.bean.ScheduleObject;
import com.github.leafee98.CSTI.core.bean.loader.JSONLoader;
import com.github.leafee98.CSTI.core.generate.Generator;
import com.github.leafee98.CSTI.core.wrapper.schedule.BreakLine;

public class App {

    private static final String paramIn = "-i";
    private static final String paramOut = "-o";

    private static String inputPath = "-";
    private static String outputPath = "-";

    private static InputStream input;
    private static OutputStream output;

    private static void parseParameter(String[] args) {
        for (int i = 0; i < args.length; ++i) {
            if (args[i].equals(paramIn)) {
                inputPath = args[++i];
            } else if (args[i].equals(paramOut)) {
                outputPath = args[++i];
            }
        }
    }

    private static void initIO() throws FileNotFoundException {
        if (inputPath.equals("-")) {
            input = System.in;
        } else {
            input = new FileInputStream(inputPath);
        }

        if (outputPath.equals("-")) {
            output = System.out;
        } else {
            output = new FileOutputStream(outputPath);
        }
    }

    public static void main(String[] args) throws IOException {
        parseParameter(args);
        initIO();

        String confContent = new String(input.readAllBytes(), StandardCharsets.UTF_8);

        JSONLoader loader = new JSONLoader();
        ScheduleObject scheduleObj = loader.load(confContent);

        Generator generator = new Generator(scheduleObj);
        String result = generator.generate().toString();

        output.write(result.getBytes());
    }
}
