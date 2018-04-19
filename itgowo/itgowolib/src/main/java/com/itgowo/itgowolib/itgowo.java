package com.itgowo.itgowolib;

public class itgowo {
    private static final itgowoImageTool imageTool = new itgowoImageTool();
    private static final itgowoJsonTool jsonTool = new itgowoJsonTool();
    private static final itgowoNetTool netTool = new itgowoNetTool();

    public static itgowoImageTool imageTool() {
        return imageTool;
    }

    public static itgowoJsonTool jsonTool() {
        return jsonTool;
    }

    public static itgowoNetTool netTool() {
        return netTool;
    }
}
