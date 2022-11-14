package com.tsa.dbclient;

import com.tsa.dbclient.domain.services.ResultViewerImpl;
import com.tsa.dbclient.domain.services.ResultWriterImpl;
import com.tsa.dbclient.domain.util.*;

import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.Properties;
import java.util.Scanner;

public class DbClient {
    private final static String NEW_LINE = "new Query: ";
    private final static String STOP = "stop";

    public static void main(String[] arg) {
        String input = "";
        String path = "";
        Properties properties = null;
        try (Scanner keyboard = new Scanner(System.in)) {
            if (arg.length == 0) {
                throw new RuntimeException("You should provide at least one parameter");
            } else if (arg.length == 1) {
                Path path1 = Path.of(arg[0]);
                if (path1.toFile().isDirectory()) {
                    path = path1.toString();
                    properties = PropertyParser.getProperties();
                } else {
                    throw new RuntimeException("When you provide only ONE parameter, it should be path to a Directory for REPORTS");
                }
            } else if (arg.length == 2) {
                Path path2 = Path.of(arg[1]);
                if (path2.toFile().isDirectory() && Path.of(arg[0]).toFile().isFile()) {
                    path = path2.toString();
                    properties = PropertyParser.getCustomProperties(arg[0]);
                } else {
                    throw new RuntimeException("When you provide TWO parameters, the fist is file with properties, the second is path to a Directory for REPORTS");
                }
            }
            try (var connection = DbConnector.getMySqlConnection(Objects.requireNonNull(properties))) {
                Greeter.greeting();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            while (!input.equals(STOP)) {
                System.out.print(NEW_LINE);
                try {
                    input = keyboard.nextLine();
                    switch (SqlMethod.valueOf(input.substring(0, input.indexOf(" ")).trim().toUpperCase())) {
                        case SHOW, SELECT -> {
                            List<?> result = QueryProcessor.select(DbConnector.getMySqlConnection(properties), input);
                            var viewer = new ResultViewerImpl(result);
                            viewer.view();
                            var writer = new ResultWriterImpl(path);
                            writer.write(input, result);
                        }
                        case INSERT, DELETE, UPDATE -> {
                            QueryProcessor.insert(DbConnector.getMySqlConnection(properties), input);
                            System.out.println("PROCESSED SUCCESSFULLY");
                        }
                        default -> System.out.println("NOT SUPPORTED SQL METHOD");
                    }
                } catch (IllegalArgumentException e) {
                    System.out.println("NOT SUPPORTED SQL METHOD");
                } catch (StringIndexOutOfBoundsException e) {
                    if (!input.equals(STOP)) System.out.println("White space is required after SQL method");
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
        System.out.println("App is closed");
    }
}
