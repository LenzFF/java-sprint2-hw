public class AccountantMonthData {
    int year; //текущий год
    int month; //месяц

    IncomeAndExpenses incomeAndExpenses; //доходы и расходы
    String maxProfitName; //самый большой доход (имя)
    String maxExpenseName; //самая большая трата (имя)

    int maxProfit; //самый большой доход
    int maxExpense; //самая большая трата

    AccountantMonthData() {
        year = 0;
        month = 0;
        incomeAndExpenses = new IncomeAndExpenses();
        maxProfit = 0;
        maxProfitName = "";
        maxExpense = 0;
        maxExpenseName = "";
    }

    void AddNewEntry(int yearInt, int monthInt, boolean isExpense, int value) {
        //добавляем новую запись
        year = yearInt;
        month = monthInt;
        incomeAndExpenses.PutIncomeOrExpense(isExpense, value);
    }
}
