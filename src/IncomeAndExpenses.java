public class IncomeAndExpenses {
    //класс для расходов и доходов
    int income; //доходы
    int expenses; //расходы

    IncomeAndExpenses() {
        income = 0;
        expenses = 0;
    }
    int getProfit() {
        //считаем прибыль (доходы - расходы)
        return income - expenses;
    }

    void putIncomeOrExpense(boolean isExpense, int value) {
        //если передаваемое значение это расходы, то увеличиваем переменную расходов, и наоборот.
        if (isExpense) expenses += value;
        else income += value;
    }

}
