import java.util.HashMap;

public class AccountantYearData {
    int year; //текущий год
    private HashMap<Integer, IncomeAndExpenses> monthExpenses; //таблица расходов и доходов и по каждому месяцу

    AccountantYearData() {
        year = 0;
        monthExpenses = new HashMap<>();
    }

    void addNewEntry(int month, boolean isExpense, int value) {
        //добавляем новую запись в таблицу
        IncomeAndExpenses newMonth = new IncomeAndExpenses();

        if (monthExpenses.containsKey(month)) newMonth = monthExpenses.get(month);

        newMonth.putIncomeOrExpense(isExpense, value);
        monthExpenses.put(month, newMonth);
    }

    int getCount() {
        //возвращаем количество записей в таблице
        return monthExpenses.size();
    }

    double getAverageIncome() {
        //считаем средние доходы за все месяцы
        double incomeSum = 0;

        for (Integer month : monthExpenses.keySet()) {
            incomeSum += monthExpenses.get(month).income;
        }
        return incomeSum / monthExpenses.size();
    }

    double getAverageExpense() {
        //считаем средние расходы за все месяцы
        double expenseSum = 0;

        for (Integer month : monthExpenses.keySet()) {
            expenseSum += monthExpenses.get(month).expenses;
        }
        return expenseSum / monthExpenses.size();
    }

    int getProfit(Integer month) {
        //прибыль за месяц
        return monthExpenses.get(month).getProfit();
    }

    int getIncomeByMonth(int monthInt) {
        //доходы за месяц
        return monthExpenses.get(monthInt).income;
    }

    int getExpensesByMonth(int monthInt) {
        //расходы за месяц
        return monthExpenses.get(monthInt).expenses;
    }
}
