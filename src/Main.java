import java.util.*;
import java.lang.*;
import java.io.*;
public class Main {

    static ArrayList<MonthlyReport> monthReports; //отчеты по всем месяцам
    static ArrayList<YearlyReport> yearReports; //отчеты по всем годам

    static String[] monthNames = {"Январь", "Февраль", "Март", "Апрель", "Май", "Июнь", "Июль", "Август",
                                  "Сентябрь", "Октябрь", "Ноябрь", "Декабрь"};

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String command;
        int commandInt;

        monthReports = new ArrayList<>();
        yearReports = new ArrayList<>();

        while (true) {
            //вывод меню и считываение команд от пользователя
            printMenu();
            command = scanner.nextLine(); // считываем строку
            commandInt = 0;

            //обработка завершения программы
            if (command.equals("Exit") || command.equals("exit")) return;

            // обработка некорректного числового ввода
            try {
                commandInt = Integer.parseInt(command);
            }
            catch (NumberFormatException e) {
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
                    readAllMonthReports();
                    break;

                case 2:
                    //чтение годовых
                    readAllYearReports();
                    break;

                case 3:
                    //сверка дарнных
                    if (!monthReports.isEmpty() && !yearReports.isEmpty()) {
                        checkMonthAndYearData();
                    } else System.out.println("Отчеты не прочитаны");
                    break;

                case 4:
                    // вывод статистики по месяцам
                    if (!monthReports.isEmpty()) {
                        printAllMonthInfo();
                    } else System.out.println("Месячные отчеты не прочитаны");
                    break;

                case 5:
                    // вывод статистики по годам
                    if (!yearReports.isEmpty()) {
                        printAllYearInfo();
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

    public static void readAllMonthReports(){
        // читаем все месячные отчеты из файлов
        monthReports.clear();

        for (int i = 1; i <= 3; i++) {
            MonthlyReport month = new MonthlyReport();
            month.readFile("resources\\m.20210" + i + ".csv");
            monthReports.add(month);
        }

        System.out.println("Прочитано месячных отчетов - " + monthReports.size());
        System.out.println("");
    }

    public static void readAllYearReports(){
        // читаем все годовые отчеты из файлов
        // годовой отчет всего один, но логичнее хранить его в ArrayList, на случай масштабирования программы
        yearReports.clear();
        YearlyReport year = new YearlyReport();
        year.readFile("resources\\y.2021.csv");

        yearReports.add(year);

        System.out.println("Прочитано годовых отчетов - " + yearReports.size());
        System.out.println("");
    }

    public static void printAllMonthInfo() {
        //выводи статистику под каждому месяцу
        int year = monthReports.get(0).year; //год
        System.out.println("Информация о всех месячных отчётах за " + year + " год:");

        for (MonthlyReport month : monthReports) {
            //имя месяца
            System.out.println(monthNames[month.month - 1]);

            //выводим самую прибыльную позицию и самую большую трату

            System.out.println("Самый прибыльный товар:");
            System.out.println(month.maxProfitName + ". Сумма - " + month.maxProfit);
            System.out.println();

            System.out.println("Самый большая трата:");
            System.out.println(month.maxExpenseName + ". Сумма - " + month.maxExpense);
            System.out.println();

            //общие показатели за месяц
            System.out.println("Расходы - " + month.incomeAndExpenses.expenses);
            System.out.println("Доходы - " + month.incomeAndExpenses.income);
            System.out.println("");
        }

    }

    public static void printAllYearInfo() {
        //выводим статистику по каждому году
        for (YearlyReport year : yearReports) {
            int yearInt = yearReports.get(0).year; //год
            System.out.println("Информация о годовом отчёте за " + yearInt + " год:");

            for (int month : year.monthExpenses.keySet())
            {
                int profit = year.monthExpenses.get(month).GetProfit();
                System.out.println("Прибыль за " + monthNames[month - 1] + ": " + profit);
            }

            System.out.println("Средний расход за все месяцы: " + year.GetAverageExpense());
            System.out.println("Средний доход за все месяцы: " + year.GetAverageIncome());
            System.out.println();
        }
    }

    public static void checkMonthAndYearData() {
        // сверка данных
        int yearIncome = 0, yearExpenses = 0,
                monthIncome = 0, monthExpenses = 0;
        for (YearlyReport year : yearReports) {
            if (year.year != monthReports.get(0).year) {
                System.out.println("Прочитаны отчеты не за тот год");
                return;
            }
            for (MonthlyReport month : monthReports) {
                yearIncome = year.monthExpenses.get(month.month).income;
                yearExpenses = year.monthExpenses.get(month.month).expenses;
                monthIncome = month.incomeAndExpenses.income;
                monthExpenses = month.incomeAndExpenses.expenses;

                if (!(yearIncome == monthIncome && monthExpenses ==yearExpenses)) {
                    //если данные не сходятся то сообщение об ошибке
                    System.out.println("Обнаружена ошибка. Месяц: " + monthNames[month.month - 1]);
                    return;
                }

            }
        }
        System.out.println("Сверка данных успешна завершена! Ошибок нет.");
        System.out.println("");
    }
}

