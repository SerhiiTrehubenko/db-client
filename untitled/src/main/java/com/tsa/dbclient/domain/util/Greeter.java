package com.tsa.dbclient.domain.util;

public class Greeter {
    private final static String SUCCESSFUL_CONNECTION = "Connection to DB is successful";
    private final static String INPUT_QUERY = "We are ready to execute Your queries";
    private final static String STOP_MESSAGE = "For quiting from App, type: \"stop\"";
    public static void greeting() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(SUCCESSFUL_CONNECTION);
        stringBuilder.append("\r\n");
        stringBuilder.append(INPUT_QUERY);
        stringBuilder.append("\r\n");
        stringBuilder.append(STOP_MESSAGE);
        stringBuilder.append("\r\n");
        System.out.println(stringBuilder);
    }
}
