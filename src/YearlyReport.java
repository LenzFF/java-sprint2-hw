import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class YearlyReport {

    int year; //текущий год
    HashMap<Integer, IncomeAndExpenses> monthExpenses; //таблица расходов и доходов и по каждому месяцу

    YearlyReport() {
        year = 0;
        monthExpenses = new HashMap<>();
    }

    boolean readFile(String path) {
        // чтение из файла в monthExpenses
        // определяем год из имени файла

        year = GetIntFromFilePath.GetYear(path);

        String fileContent;
        try {
            fileContent = Files.readString(Path.of(path));
        } catch (IOException e) {
            fileContent = null;

            return false;
        }

        //разбираем содержимое файла и помещаем в класс monthExpenses
        if (fileContent != null) {
            //делим на строки
            String[] lines = fileContent.split(System.lineSeparator());

            monthExpenses.clear();

            for (int i = 1; i < lines.length; i++) {
                String[] lineContents = lines[i].split(",");

                int month = Integer.parseInt(lineContents[0]);
                int value = Integer.parseInt(lineContents[1]);
                boolean isExpense = Boolean.parseBoolean(lineContents[2]);
                IncomeAndExpenses newItem = new IncomeAndExpenses();

                //если в таблице еще нет данных по текущему месяцу, то создаем новую запись
                if (monthExpenses.containsKey(month)) newItem = monthExpenses.get(month);

                //добавляем данные о доходах и расходах
                newItem.PutIncomeOrExpense(isExpense, value);
                monthExpenses.put(month, newItem);

            }
        }
        return true;
    }

    double GetAverageIncome() {
        //считаем средние доходы за все месяцы
        double incomeSum = 0;

        for (Integer month : monthExpenses.keySet()) {
            incomeSum += monthExpenses.get(month).income;
        }
        return incomeSum / monthExpenses.size();
    }

    double GetAverageExpense() {
        //считаем средние расходы за все месяцы
        double expenseSum = 0;

        for (Integer month : monthExpenses.keySet()) {
            expenseSum += monthExpenses.get(month).expenses;
        }
        return expenseSum / monthExpenses.size();
    }
}
