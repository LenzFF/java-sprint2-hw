import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.lang.*;
import java.io.*;

public class Main {

    public static void main(String[] args) {
        MonthlyReport monthReports = null;
        YearlyReport yearReports = null;
        Scanner scanner = new Scanner(System.in);
        String command;
        int commandInt;

        while (true) {
            //вывод меню и считываение команд от пользователя
            printMenu();
            command = scanner.nextLine(); // считываем строку

            //обработка завершения программы
            if (command.equals("Exit") || command.equals("exit")) return;

            // обработка некорректного числового ввода
            try {
                commandInt = Integer.parseInt(command);
            } catch (NumberFormatException e) {
                System.out.println("Такой команды нет");
                commandInt = 0;
            }
            // обработка команд от пользователя
            switch (commandInt) {
                //обработка некорректного ввода
                case 0:
                    break;

                case 1:
                    // чтение всех месячных отчетов
                    monthReports = new MonthlyReport();

                    for (int i = 1; i <= 3; i++) {
                        String filePath = "resources" + File.separator + "m.20210" + i + ".csv";
                        //проверяем существует ли файл
                        if (Files.exists(Path.of(filePath))) {
                            //читаем
                            monthReports.readFile(filePath);
                        } else { // если нет, то сообщение об ошибке
                            System.out.println("Невозможно прочитать файл с месячным отчётом. Возможно, файл не находится в нужной директории.");
                            return;
                        }
                    }

                    System.out.println("Прочитано месячных отчетов - " + monthReports.GetCount());
                    System.out.println("");
                    break;

                case 2:
                    //чтение годовых
                    yearReports = new YearlyReport();
                    String filePath = "resources" + File.separator + "y.2021" + ".csv";
                    //проверяем существует ли файл
                    if (Files.exists(Path.of(filePath))) {
                        //читаем файл
                        yearReports.readFile(filePath);
                        System.out.println("Прочитано годовых отчетов - " + yearReports.GetCount());
                        System.out.println("");

                    } else { // если нет, то сообщение об ошибке
                        System.out.println("Невозможно прочитать файл с годовым отчётом. Возможно, файл не находится в нужной директории.");
                    }
                    break;

                case 3:
                    //сверка отчетов
                    if (yearReports != null && monthReports != null)
                        yearReports.compareWithMonthReport(2021, monthReports);
                    else System.out.println("Отчеты не прочитаны");
                    break;

                case 4:
                    // вывод статистики по месяцам
                    if (monthReports.GetCount() > 0) {
                        monthReports.printAllMonthInfo();
                    } else System.out.println("Месячные отчеты не прочитаны");
                    break;

                case 5:
                    // вывод статистики по годам
                    if (yearReports.GetCount() > 0) {
                        yearReports.printAllYearsInfo();
                    } else System.out.println("Годовые отчеты не прочитаны");
                    break;

                default:
                    System.out.println("Такой команды нет");
                    break;
            }
        }
    }

    public static void printMenu() {
        // вывод меню
        System.out.println("1 - Считать все месячные отчёты");
        System.out.println("2 - Считать годовой отчёт");
        System.out.println("3 - Сверить отчёты");
        System.out.println("4 - Вывести информацию о всех месячных отчётах");
        System.out.println("5 - Вывести информацию о годовом отчёте");
        System.out.println("Exit - Завершить работу программы");
        System.out.println("");
    }
}

