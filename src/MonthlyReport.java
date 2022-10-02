import java.io.IOException;
import java.nio.file.*;
import java.time.Month;
import java.util.*;

public class MonthlyReport {
    int year; //текущий год
    int month; //месяц

    IncomeAndExpenses incomeAndExpenses; //доходы и расходы
    String maxProfitName; //самый большой доход (имя)
    String maxExpenseName; //самая большая трата (имя)

    int maxProfit; //самый большой доход
    int maxExpense; //самая большая трата

    MonthlyReport() {
        year = 0;
        month = 0;
        incomeAndExpenses = new IncomeAndExpenses();
    }

    boolean readFile(String path) {
    // чтение из файла в hashmap expenses
    // определяем год и номер месяца из имени файла

        year = GetIntFromFilePath.GetYear(path);
        month = GetIntFromFilePath.GetMonth(path);

        maxProfit = 0;
        maxProfitName = "";
        maxExpense = 0;
        maxExpenseName = "";

        String fileContent;
        try {
            fileContent = Files.readString(Path.of(path));
        } catch (IOException e) {

            fileContent = null;

            return false;
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
                if (!isExpense && sum > maxProfit) {
                    maxProfit = sum;
                    maxProfitName = lineContents[0];
                }

                if (isExpense && sum > maxExpense) {
                    maxExpense = sum;
                    maxExpenseName = lineContents[0];
                }

                //добавляем новую запись
                incomeAndExpenses.PutIncomeOrExpense(isExpense, quantity * price);
            }
        }
        return true;
    }

}
