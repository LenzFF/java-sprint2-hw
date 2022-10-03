import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.time.Month;
import java.util.*;

public class MonthlyReport {
    //список для месяцев
    private ArrayList<AccountantMonthData> monthReports;

    MonthlyReport() {
        monthReports = new ArrayList<>();
    }

    void readFile(String path) {
        // чтение из файла в hashmap expenses

        AccountantMonthData newData = new AccountantMonthData();

        String fileContent;
        try {
            fileContent = Files.readString(Path.of(path));
        } catch (IOException e) {
            fileContent = null;
        }

        //разбираем содержимое файла и помещаем в класс Expense
        if (fileContent != null) {
            //делим на строки

            String[] lines = fileContent.split(System.lineSeparator());

            for (int i = 1; i < lines.length; i++) {
                String[] lineContents = lines[i].split(",");

                boolean isExpense = Boolean.parseBoolean(lineContents[1]);
                int quantity = Integer.parseInt(lineContents[2]);
                int price = Integer.parseInt(lineContents[3]);
                int sum = quantity * price;

                //сразу определяем самые большие расходы и доходы
                if (!isExpense && sum > newData.maxProfit) {
                    newData.maxProfit = sum;
                    newData.maxProfitName = lineContents[0];
                }

                if (isExpense && sum > newData.maxExpense) {
                    newData.maxExpense = sum;
                    newData.maxExpenseName = lineContents[0];
                }

                // определяем год и номер месяца из имени файла
                int year = GetIntFromFilePath.GetYear(path);
                int month = GetIntFromFilePath.GetMonth(path);
                //добавляем новую запись
                //newData.incomeAndExpenses.PutIncomeOrExpense(isExpense, quantity * price);

                newData.AddNewEntry(year, month, isExpense, sum);
            }
            //добавляем в список
            monthReports.add(newData);
        }
    }

    int GetCount() {
        return monthReports.size();
    }

    private void printMonthInfo(AccountantMonthData month) {
        //выводи статистику под каждому месяцу

        System.out.println("Год " + month.year + ". Месяц - " + MonthNames.monthNames[month.month - 1]);

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

    void printAllMonthInfo() {
        //выводи статистику под каждому месяцу
        System.out.println("Информация о всех месячных отчётах");
        for (AccountantMonthData month : monthReports) {
            printMonthInfo(month);
        }
    }

    int GetIncomeByMonth(int index) {
        // возвращаем доход за месяц
        return monthReports.get(index - 1).incomeAndExpenses.income;
    }

    int GetExpensesByMonth(int index) {
        // возвращаем расход за месяц
        return monthReports.get(index - 1).incomeAndExpenses.expenses;
    }
}
