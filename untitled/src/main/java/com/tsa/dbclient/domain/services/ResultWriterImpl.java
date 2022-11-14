package com.tsa.dbclient.domain.services;

import com.tsa.dbclient.domain.interfaces.ResultWriter;

import java.io.FileOutputStream;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class ResultWriterImpl implements ResultWriter {

    private final String path;

    private final static String HTML_HEAD = "<!DOCTYPE html>\n" +
            "<html lang=\"en\">\n" +
            "<head>\n" +
            "    <meta charset=\"UTF-8\">\n" +
            "    <title>MyDb</title>\n" +
            "</head>\n" +
            "<body>\n" +
            "<table>";

    private final static String HTML_BOTTOM = "</table> \n" +
            "</body>\n" +
            "</html>";
    private final static String REPORT = "report";
    private final static String EXTENTION = ".html";
    public ResultWriterImpl(String path) {
        this.path = path;
    }

    @Override
    public void write(String query,List<?> list) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<!--");
        stringBuilder.append(query);
        stringBuilder.append("-->");
        stringBuilder.append("\n");
        stringBuilder.append(HTML_HEAD);
        stringBuilder.append("\n");
        stringBuilder.append("<tr>\n");
        Map<?,?> mapForHead = (Map<?,?>) list.get(0);
        for (Object o : mapForHead.keySet()) {
            stringBuilder.append("<th>");
            stringBuilder.append(o.toString());
            stringBuilder.append("</th>");
            stringBuilder.append("\n");
        }
        stringBuilder.append("</tr>\n");
        for (Object o : list) {
            stringBuilder.append("<tr>\n");
            Map<?,?> map = (Map<?,?>) o;
            for (Object o1 : map.keySet()) {
                stringBuilder.append("<td>");
                stringBuilder.append(map.get(o1.toString()));
                stringBuilder.append("</td>");
                stringBuilder.append("\n");
            }
            stringBuilder.append("</tr>\n");
        }
        stringBuilder.append(HTML_BOTTOM);
//        System.out.println(stringBuilder);
        StringBuilder fileName = new StringBuilder();
        fileName.append(REPORT);
        fileName.append("-");
        fileName.append(query.substring(0, query.indexOf(" ")).trim());
        fileName.append("-");
        fileName.append(new Date());
        fileName.append(EXTENTION);
        try (var out = new FileOutputStream(Paths.get(path, fileName.toString().replaceAll(":", "-")).toFile())){
            out.write(stringBuilder.toString().getBytes());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
