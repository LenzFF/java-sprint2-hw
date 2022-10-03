import java.util.HashMap;

public class AccountantYearData {
    int year; //текущий год
    private HashMap<Integer, IncomeAndExpenses> monthExpenses; //таблица расходов и доходов и по каждому месяцу

    AccountantYearData() {
        year = 0;
        monthExpenses = new HashMap<>();
    }

    void AddNewEntry(int month, boolean isExpense, int value) {
        //добавляем новую запись в таблицу
        IncomeAndExpenses newMonth = new IncomeAndExpenses();

        if (monthExpenses.containsKey(month)) newMonth = monthExpenses.get(month);

        newMonth.PutIncomeOrExpense(isExpense, value);
        monthExpenses.put(month, newMonth);
    }

    int GetCount() {
        //возвращаем количество записей в таблице
        return monthExpenses.size();
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

    int GetProfit(Integer month) {
        //прибыль за месяц
        return monthExpenses.get(month).GetProfit();
    }

    int GetIncomeByMonth(int monthInt) {
        //доходы за месяц
        return monthExpenses.get(monthInt).income;
    }

    int GetExpensesByMonth(int monthInt) {
        //расходы за месяц
        return monthExpenses.get(monthInt).expenses;
    }
}
