public class GetIntFromFilePath {
    //здесь методы для получения номера года и месяца из имени файла
    public static int getYear(String path) {
        int startIndex = path.indexOf('.') + 1;
        return (Integer.parseInt(path.substring(startIndex, startIndex + 4)));
    }

    public static int getMonth(String path) {
        int startIndex = path.indexOf('.') + 5;
        String subString = path.substring(startIndex, startIndex + 2);
        return (Integer.parseInt(subString));
    }
}
