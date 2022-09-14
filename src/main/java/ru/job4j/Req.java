package ru.job4j;

public class Req {

    private final String httpRequestType;
    private final String poohMode;
    private final String sourceName;
    private final String param;

    public Req(String httpRequestType, String poohMode, String sourceName, String param) {
        this.httpRequestType = httpRequestType;
        this.poohMode = poohMode;
        this.sourceName = sourceName;
        this.param = param;
    }

    public static Req of(String content) {
        String[] arrayContent = content.split(System.lineSeparator(), 8);
        String[] theArrayContent = arrayContent[0].split("/");
        String httpRT = theArrayContent[0].trim();
        String poohM = theArrayContent[1].trim();
        String sourceN = theArrayContent[2].split(" ")[0].trim();
        String parm;
        if ("GET".equals(httpRT) && "topic".equals(poohM)) {
            parm = theArrayContent[3].split(" ")[0];
        } else {
            parm = arrayContent[arrayContent.length - 1].trim();
        }
        return new Req(httpRT, poohM, sourceN, parm);
    }

    public String httpRequestType() {
        return httpRequestType;
    }

    public String getPoohMode() {
        return poohMode;
    }

    public String getSourceName() {
        return sourceName;
    }

    public String getParam() {
        return param;
    }
}
