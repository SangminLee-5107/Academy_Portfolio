package banking_hap;

public class TransactionHistory {
    private String transaction_time;
    private String type;
    private int transaction_deposit;
    private int transaction_withdraw;
    private String transaction_name;
    private String transaction_memo;

    public String getTransaction_time() {
        return transaction_time;
    }

    public void setTransaction_time(String transaction_time) {
        this.transaction_time = transaction_time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getTransaction_deposit() {
        return transaction_deposit;
    }

    public void setTransaction_deposit(int transaction_deposit) {
        this.transaction_deposit = transaction_deposit;
    }

    public int getTransaction_withdraw() {
        return transaction_withdraw;
    }

    public void setTransaction_withdraw(int transaction_withdraw) {
        this.transaction_withdraw = transaction_withdraw;
    }

    public String getTransaction_name() {
        return transaction_name;
    }

    public void setTransaction_name(String transaction_name) {
        this.transaction_name = transaction_name;
    }

    public String getTransaction_memo() {
        return transaction_memo;
    }

    public void setTransaction_memo(String transaction_memo) {
        this.transaction_memo = transaction_memo;
    }

    /*
    SELECT transaction_time AS '거래일자',
    CASE
    WHEN out_account IS NULL AND in_account = '1111-111' THEN '입금'
    WHEN out_account = '1111-111' AND in_account IS NULL THEN '출금'
    WHEN out_account = '1111-111' THEN '송금(출금)'
    WHEN in_account = '1111-111' THEN '송금(입금)'
    END AS '거래유형',
    amount AS '금액',
    CASE
    WHEN out_account IS NULL AND in_account = '1111-111' THEN '본인'
    WHEN out_account = '1111-111' AND in_account IS NULL THEN '본인'
    WHEN out_account = '1111-111' AND in_account IN (SELECT account_num FROM Accounts JOIN Transactions
    ON Transactions.in_account = Accounts.account_num)
    THEN (SELECT name FROM Customers JOIN Accounts ON Customers.customer_num = Accounts.customer_num
    JOIN Transactions ON Accounts.account_num = Transactions.in_account)
    WHEN out_account IN (SELECT account_num FROM Accounts JOIN Transactions
    ON Transactions.in_account = Accounts.account_num) AND in_account = '1111-111'
    THEN (SELECT name FROM Customers JOIN Accounts ON Customers.customer_num = Accounts.customer_num
    JOIN Transactions ON Accounts.account_num = Transactions.out_account)
    END AS '거래자명',
    memo AS '기록사항(메모)'
    FROM Transactions
    WHERE out_account = '1111-111' OR in_account = '1111-111'
     */
}