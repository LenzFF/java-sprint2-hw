import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class YearlyReport {

    ArrayList<AccountantYearData> yearReports;

    YearlyReport() {
        yearReports = new ArrayList<>();
    }

    void readFile(String path) {
        // чтение из файла в yearReports
        // определяем год из имени файла

        String fileContent;
        try {
            fileContent = Files.readString(Path.of(path));
        } catch (IOException e) {
            fileContent = null;
        }

        //разбираем содержимое файла и помещаем в класс yearReports
        if (fileContent != null) {
            //делим на строки
            String[] lines = fileContent.split(System.lineSeparator());

            AccountantYearData newYear = new AccountantYearData();
            newYear.year = GetIntFromFilePath.getYear(path);

            for (int i = 1; i < lines.length; i++) {
                String[] lineContents = lines[i].split(",");

                int month = Integer.parseInt(lineContents[0]);
                int value = Integer.parseInt(lineContents[1]);
                boolean isExpense = Boolean.parseBoolean(lineContents[2]);

                newYear.addNewEntry(month, isExpense, value);
            }

            yearReports.add(newYear);
        }
    }

    void printAllYearsInfo() {
        //выводим статистику по году
        for (AccountantYearData year : yearReports) {
            System.out.println("Информация о годовом отчёте за " + year.year + " год:");

            //статистика за каждый месяц
            for (int i = 0; i < year.getCount(); i++) {
                int profit = year.getProfit(i + 1);
                System.out.println("Прибыль за " + MonthNames.monthNames[i] + ": " + profit);
            }

            System.out.println("Средний расход за все месяцы: " + year.getAverageExpense());
            System.out.println("Средний доход за все месяцы: " + year.getAverageIncome());
            System.out.println();
        }
    }

    int getCount() {
        return yearReports.size();
    }

    void compareWithMonthReport(int yearInt, MonthlyReport monthReports) {
        // сверка данных
        int yearIncome = 0, yearExpenses = 0, monthIncome = 0, monthExpenses = 0;
        AccountantYearData year = null;

        for (AccountantYearData currentYear : yearReports) {
            if (currentYear.year == yearInt) year = currentYear;
        }

        if (year == null) {
            //если отчет за нужный год не прочитан, то выходим
            System.out.println("Прочитаны отчеты не за тот год");
            return;
        }

        if (year.getCount() != monthReports.getCount()) {
            System.out.println("Количество записей в отчетах не совпадает!");
            return;
        }

        for (int i = 1; i <= year.getCount(); i++) {
            // сравниваем доходы и расходы по каждому месяцу
            yearIncome = year.getIncomeByMonth(i);
            yearExpenses = year.getExpensesByMonth(i);

            monthIncome = monthReports.getIncomeByMonth(i);
            monthExpenses = monthReports.getExpensesByMonth(i);

            if (!(yearIncome == monthIncome && monthExpenses == yearExpenses)) {
                //если данные не сходятся то сообщение об ошибке
                System.out.println("Обнаружена ошибка. Месяц: " + MonthNames.monthNames[i - 1]);
                return;
            }
        }

        System.out.println("Сверка данных успешна завершена! Ошибок нет.");
        System.out.println("");
    }
}
