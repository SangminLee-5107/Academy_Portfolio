package banking_hap;

import java.sql.*;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.Executor;

public class Banking {

    private Connection conn;
    public boolean open() {

        try {
            conn = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\402-016\\Project_Bank_original\\project_database.db");
            Statement statement = conn.createStatement();
            statement.execute("PRAGMA foreign_keys = ON");
            statement.close();
            return true;
        } catch (SQLException e) {
            System.out.println("Couldn't connect to database: " + e.getMessage());
            return false;
        }
    }

    public void close() {

        try {
            if (conn != null) {
                conn.close();
                System.out.println("프로그램을 종료합니다.");
            }
        } catch (SQLException e) {
            System.out.println("Couldn't close connection: " + e.getMessage());
        }
    }

    public void newAccounts() {
        Scanner sc = new Scanner(System.in);
        String residence_num;
        while(true){
            System.out.print("> 주민번호 13자리를 입력하세요. : ");
            residence_num = sc.next();
            if(residence_num.length() != 13){
                System.out.println("유효하지 않은 주민번호입니다. 13자리로 입력해주세요.");
                continue;
            }
            break;
        }
        residence_num = residence_num.substring(0, 6) + "-" + residence_num.substring(6, 13);
        try {
            if (checkExisting(residence_num) == 1) {
                System.out.println("기존 고객으로 확인되셨습니다. 추가 신규계좌 개설을 진행합니다!");
                createExistingCustomerAccount(residence_num);
            } else if (checkExisting(residence_num) == 2) {
                System.out.println("신규 고객으로 확인되셨습니다. 첫 신규계좌 개설을 진행합니다!");
                createNewCustomerAccount(residence_num);
            }
        } catch (Exception e) {
            System.out.println("newAccounts Exception : " + e.getMessage());
        }
    }

    public int checkExisting(String residence_num) {
        try (Statement statement = conn.createStatement();
             ResultSet result = statement.executeQuery("SELECT residence_num FROM Customers")) {

            while (result.next()) {
                if (residence_num.equals(result.getString(1)))
                    return 1;
            }
            return 2;

        } catch (SQLException e) {
            System.out.println("기존 고객인지 확인 중 Exception 발생 :" + e.getMessage());
            return 3;
        }
    }

    public boolean checkOpenDate(String residence_num){
        String recentOpenSql = "SELECT open_date, name FROM Accounts NATURAL JOIN Customers " +
                "WHERE residence_num = '" + residence_num + "' ORDER BY open_date DESC LIMIT 1;";
        int open_year;
        int open_month;
        int open_day;
        String name;
        LocalDate today = LocalDate.now();
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(recentOpenSql)) {
            String[] recent_open = rs.getString(1).split("-");
            open_year = Integer.parseInt(recent_open[0]);
            open_month = Integer.parseInt(recent_open[1]);
            open_day = Integer.parseInt(recent_open[2]);
            name = rs.getString(2);
        } catch (SQLException e) {
            System.out.println("recentOpenSql 실패 : " + e.getMessage());
            return false;
        }
        LocalDate recent_open_date = LocalDate.of(open_year, open_month, open_day);
        LocalDate monthAfter = recent_open_date.plusMonths(1);
        Period period = Period.between(today, monthAfter);
        if (today.isBefore(monthAfter)) {
            System.out.println("대포통장 근절을 위해 단기간(1개월 이내) 다수 계좌 개설이 제한됩니다.");
            System.out.println( name + " 고객님의 최근 기존 신규일은 " + recent_open_date + "으로, "
                    + String.format("%d일", period.getDays()) + " 뒤에 다시 방문해 주시기 바랍니다.");
            return false;
        }
        return true;
    }

    public void createExistingCustomerAccount(String residence_num) {
        if (!checkOpenDate(residence_num)) return;

        try (Statement statement = conn.createStatement();
             ResultSet result = statement.executeQuery("SELECT customer_num, name FROM Customers " +
                     "WHERE residence_num = '" + residence_num + "'")) {
            String customer_num = result.getString(1);
            String name = result.getString(2);
            String account_num = randomAccountNum();
            String password = setPassword();
            statement.executeUpdate("INSERT INTO Accounts(account_num,customer_num,open_date,account_password)" +
                    " VALUES ('" + account_num + "','" + customer_num + "',DATE()," + password + ")");
            int card_num = createCard(account_num);
            if (card_num == -1) return;
            System.out.println(name + " 고객님의 신규 계좌(" + account_num + ") 개설과, 신규 현금카드(" + card_num + ") 발급이 완료되었습니다.");
        } catch (SQLException e) {
            System.out.println("기존 고객의 계좌 생성 중 Exception 발생 :" + e.getMessage());
        }
    }
    public boolean checkExistAccount(String account_num){
        try(Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery("SELECT account_num FROM Accounts")){
            while(result.next()){
                if(account_num.equals(result.getString(1))) return true;
            }
        }catch (SQLException e){
            return false;
        }
        return false;
    }

    public void createNewCustomerAccount(String residence_num) {

        addNewCustomer(residence_num);
        try (Statement statement = conn.createStatement();
             ResultSet result = statement.executeQuery("SELECT customer_num, name FROM Customers WHERE residence_num = '" + residence_num + "'")) {
            String customer_num = result.getString(1);
            String name = result.getString(2);
            String account_num = randomAccountNum();
            String password = setPassword();

            statement.executeUpdate("INSERT INTO Accounts(account_num,customer_num,open_date,account_password)" +
                    " VALUES ('" + account_num + "','" + customer_num + "',DATE()," + password + ")");

            int card_num = createCard(account_num);
            if (card_num == -1) return;

            System.out.println(name + " 고객님의 신규 계좌(" + account_num + ") 개설과, 신규 현금카드(" + card_num + ") 발급이 완료되었습니다.");

        } catch (SQLException e) {
            System.out.println("신규 고객의 계좌 생성 중 Exception 발생 :" + e.getMessage());
        }
    }

    public String randomAccountNum() {
        do {
            String first = Double.toString(Math.random() * 9000 + 1000);
            String second = String.format("%03d", (int) (Math.random() * 1000));
            String random_accountNum = first.substring(0, 4) + "-" + second;

            try (Statement statement = conn.createStatement();
                 ResultSet results = statement.executeQuery("SELECT account_num FROM Accounts WHERE account_num = " + random_accountNum)) {

                results.getString(1);

            } catch (SQLException e) {
                return random_accountNum;
            }
        } while (true);
    }

    public String setPassword() {
        Scanner sc = new Scanner(System.in);
        System.out.print("> 비밀번호를 설정합니다. 설정할 비밀번호를 입력하세요 : ");
        while (true) {
            String firstInput = sc.next();
            if (firstInput.length() != 4) {
                System.out.println("4자리를 입력하세요.");
                continue;
            }
            System.out.print("> 한 번 더 입력해주세요 : ");
            String secondInput = sc.next();

            if (firstInput.equals(secondInput)) {
                return firstInput;
            } else {
                System.out.print("> 입력하신 두 비밀번호가 동일하지 않습니다. 다시 입력해주세요. : ");
            }
        }
    }

    public void addNewCustomer(String residence_num) {
        String name, address, phone;
        System.out.println("신규 회원정보를 등록 후 계좌를 개설합니다.");
        info : while (true) {
            Scanner sc = new Scanner(System.in);
            System.out.print("> 성명 : ");
            name = sc.next();
            System.out.print("> 주소 : ");
            address = sc.next();
            while(true){
                System.out.print("> 핸드폰번호 ex)01012341234 : ");
                phone = sc.next();
                if(phone.length() != 11){
                    System.out.println("핸드폰 번호가 11자리가 아닙니다. 정확히 입력해주세요.");
                    continue;
                }
                phone = phone.substring(0, 3) + "-" + phone.substring(3, 7) + "-" + phone.substring(7, 11);
                System.out.print("성명 : " + name + "\n" +
                        "주소 : " + address + "\n" +
                        "핸드폰번호 : " + phone + "\n" +
                        "> 입력하신 정보가 맞으면 1을, 잘못 입력한 경우 2를 입력해주세요. : ");
                String info_check = sc.next();
                if(info_check.equals("1")){
                    break info;
                }else {
                    System.out.println("회원정보를 다시 입력해주세요.");
                    break;
                }
            }
        }
        try (Statement statement = conn.createStatement()) {
            statement.executeUpdate("INSERT INTO Customers (name, residence_num, address, phone) VALUES " +
                    "('" + name + "','" + residence_num + "','" + address + "','" + phone + "')");
        } catch (SQLException e) {
            System.out.println("신규 고객 등록 중 Exception 발생 :" + e.getMessage());
        }
    }

    public int createCard(String account_num) {
        try (Statement statement = conn.createStatement()) {
            int card_num = randomCardNum();
            if (card_num==0){
                return -1;
            }
            statement.executeUpdate("INSERT INTO Cards VALUES" +
                    "(" + card_num + ",'" + account_num + "','" + LocalDate.now().plusYears(1) + "')");
            return card_num;
        } catch (SQLException e) {
            System.out.println("카드 생성 중 Exception 발생 :" + e.getMessage());
            return -1;
        }
    }

    public int randomCardNum() {
        try (Statement statement = conn.createStatement();
             ResultSet result = statement.executeQuery("SELECT card_num FROM Cards ORDER BY card_num ASC")) {
            int i = 0, random_cardNum, index;
            ArrayList<Integer> cardNumbers = new ArrayList<>();
            while (result.next()) {
                cardNumbers.add(result.getInt(1));
            }
            do {
                random_cardNum = (int) (Math.random() * 900) + 100;
                index = Collections.binarySearch(cardNumbers, random_cardNum);
            } while (index >= 0); //인덱스가잇으면 0~ , 중복이라 반복
            return random_cardNum;
        } catch (SQLException e) {
            System.out.println("카드번호 생성 중 SQLException 발생 :" + e.getMessage());
            return 0;
        }
    }

    public void deposit() {
        Scanner in = new Scanner(System.in);
        String account_num = "";
        while (account_num.length() != 7) {
            System.out.print("> 현금을 입금할 계좌번호 7자리를 입력해주세요. ex) 1234567 : ");
            account_num = in.next();
        }
        account_num = account_num.substring(0, 4) + '-' + account_num.substring(4, 7);
        if(!checkExistAccount(account_num)){
            System.out.println("존재하지 않는 계좌입니다.");
            return;
        }
        System.out.print("> 입금할 금액을 넣어주세요(입력) : ");
        int amount = in.nextInt();
        System.out.print("> 기록사항을 입력해주세요. (입력을 원하지 않으면 1을 입력하세요.) : ");
        String memo = in.next();
        if (memo.equals("1")) {
            memo = " ";
        }
        try (Statement statement = conn.createStatement()) {
            statement.executeUpdate("INSERT INTO Transactions (transaction_time, in_account, amount,memo) " +
                    "VALUES (DATETIME(),'" + account_num + "'," + amount + ",'" + memo + "')");
            balanceUpdate(account_num, "null", amount);
            System.out.print("> 입금된 계좌의 잔액확인을 원하시면 1번, 아니면 2번을 눌러주세요 : ");
            String num = in.next();
            if (num.equals("1")) {
                System.out.println("잔액확인을 위해 본인인증을 시작합니다.");
                if (authentication(account_num)) {
                    printBalance(account_num);
                }
            }
        } catch (SQLException e) {
            System.out.println("해당 계좌가 존재하지 않습니다.");
        }
    }

    public void cardDeposit(String account_num) {
        Scanner in = new Scanner(System.in);
        System.out.print("> 입금할 금액을 넣어주세요(입력) : ");
        int amount = in.nextInt();
        System.out.print("> 기록사항을 입력해주세요. (입력을 원하지 않으면 1을 입력하세요.) : ");
        String memo = in.next();
        if (memo.equals("1")) {
            memo = " ";
        }
        try (Statement statement = conn.createStatement()) {
            statement.executeUpdate("INSERT INTO Transactions (transaction_time, in_account, amount,memo) " +
                    "VALUES (DATETIME(),'" + account_num + "'," + amount + ",'" + memo + "')");
            balanceUpdate(account_num, "null", amount);
            printBalance(account_num);
        } catch (SQLException e) {
            System.out.println("cardDeposit 실행 중 SQLException 발생 :" + e.getMessage());
        }
    }

    public void accountLock(String account_num) {
        try (Statement statement = conn.createStatement()) {
            statement.executeUpdate("UPDATE Accounts SET account_lock = '1' WHERE account_num ='" + account_num + "'");
        } catch (SQLException e) {
            System.out.println("계좌 잠금 중 SQLException 발생 :" + e.getMessage());
        }
    }

    public void accountUnlock(String account_num) {
        try (Statement statement = conn.createStatement();
             ResultSet result = statement.executeQuery("SELECT account_lock FROM Accounts " +
                     "WHERE account_num = '" + account_num + "'")) {
            int account_lock = result.getInt(1);
            if (account_lock != 1) {
                System.out.println("정지되어있는 카드가 아닙니다.");
                return;
            }
            if (!confirmPassword(account_num)) {
                System.out.println("비밀번호를 재설정합니다.");
                if (authentication(account_num)) {
                    resetPassword(account_num);
                    while(!confirmPassword(account_num)){
                        resetPassword(account_num);
                    }
                } else return;
            }
            statement.executeUpdate("UPDATE Accounts SET account_lock = '0' WHERE account_num ='" + account_num + "'");
            System.out.println("분실카드 및 잠금계좌 해제가 정상적으로 완료되었습니다.");
        } catch (SQLException e) {
            System.out.println("accountUnlock 중 SQLException 발생 " + e.getMessage());
        }
    }


    public boolean authentication(String account_num) {
        try (Statement statement = conn.createStatement();
             ResultSet result = statement.executeQuery("SELECT residence_num FROM Customers " +
                     "NATURAL JOIN Accounts WHERE account_num = '" + account_num + "'")) {
            String residence_numDB = result.getString(1);
            int count = 0;
            while (true) {
                if (count != 0) {
                    System.out.println("본인 인증에 " + count + "회 실패하였습니다.");
                    if (count == 3) {
                        accountLock(account_num);
                        System.out.println("본인인증에 실패하여 계정이 잠금되었습니다");
                        return false;
                    }
                }
                Scanner in = new Scanner(System.in);
                System.out.print("> 본인인증을 위한 주민등록번호 13자리를 입력하세요. (Ex) 0001234567890) : ");
                String residence_numIn = in.next();
                if (residence_numIn.length() != 13) {
                    System.out.println("13자리를 입력하지 않았습니다.");
                    count++;
                    continue;
                }
                residence_numIn = residence_numIn.substring(0, 6) + '-' + residence_numIn.substring(6, 13);
                if (residence_numDB.equals(residence_numIn)) break;
                else count++;
            }
            System.out.println("본인인증에 성공하였습니다.");
            return true;
        } catch (SQLException e) {
            System.out.println("본인 인증 중 SQLException 발생 :" + e.getMessage());
            return false;
        }
    }


    public void transactionHistory(String account_num) {
        String sql =
                "SELECT transaction_time AS '거래일자', \n" +
                        "CASE WHEN in_account = '" + account_num + "' \n" +
                        "AND out_account IS NULL THEN '현금(입금)' WHEN out_account = '" + account_num + "' \n" +
                        "AND in_account IS NULL THEN '현금(출금)' WHEN in_account = '" + account_num + "' \n" +
                        "AND out_account IS NOT NULL THEN '송금(입금)' ELSE '송금(출금)' END AS '거래유형',\n" +
                        "CASE WHEN in_account = '" + account_num + "' THEN amount ELSE 0 END AS '입금액',\n" +
                        "CASE WHEN out_account = '" + account_num + "' THEN amount ELSE 0 END AS '출금액',\n" +
                        "CASE WHEN out_account IS NULL OR in_account IS NULL THEN '본인'\n" +
                        "ELSE (SELECT name FROM Accounts NATURAL JOIN Customers \n" +
                        "WHERE account_num = (CASE WHEN in_account = '" + account_num + "' THEN out_account ELSE in_account END))\n" +
                        "END AS '예금주명',\n" +
                        "IFNULL(memo, '') AS '기록사항(메모)'\n" +
                        "FROM Transactions \n" +
                        "WHERE (in_account = '" + account_num + "' OR out_account = '" + account_num + "') ";
        String message = "";
        String completed_sql;
        while(true) {
            Scanner sc = new Scanner(System.in);
            System.out.println("1번 : 전체내역 조회");
            System.out.println("2번 : 거래인 지정 조회");
            System.out.println("3번 : 금액 지정 조회");
            System.out.println("4번 : 기간별 조회");
            System.out.println("5번 : 입금 내역 조회");
            System.out.println("6번 : 출금 내역 조회");
            System.out.print("> 원하시는 조회 조건을 선택하세요 : ");
            int type = sc.nextInt();
            switch (type) {
                case 1://전체 조회
                    completed_sql = sql;
                    break;
                case 2://거래인 지정 조회
                    System.out.print("> 특정인과의 거래내역을 조회합니다. 특정 거래인의 이름을 입력하세요 : ");
                    String name = sc.next();
                    message = "해당 거래인이 거래내역에 검색되지 않습니다.";
                    completed_sql = sql + "AND 예금주명 = '" + name + "'";
                    break;
                case 3://금액 지정 조회
                    System.out.print("> 특정 금액 이상의 거래내역을 조회합니다. 금액을 지정하여 입력해주세요 : ");
                    int amount = sc.nextInt();
                    completed_sql = sql + "AND amount > " + amount;
                    message = amount + "원 이상의 거래내역이 존재하지 않습니다.";
                    break;
                case 4://기간 별 조회
                    System.out.println("원하는 기간을 지정하여 거래내역을 조회합니다. 확인하고자 하는 기간을 입력해주세요. ex)20211230");
                    System.out.print("> 조회 시작 일자 : ");
                    String start_date = sc.next();
                    int start_int = Integer.valueOf(start_date);
                    start_date = start_date.substring(0, 4) + "-" + start_date.substring(4, 6) + "-" + start_date.substring(6, 8);
                    System.out.print("> 조회 종료 일자 : ");
                    String end_date = sc.next();
                    int end_int = Integer.valueOf(end_date);
                    end_date = end_date.substring(0, 4) + "-" + end_date.substring(4, 6) + "-" + end_date.substring(6, 8) + " 23:59:59";
                    completed_sql = sql + "AND (거래일자 >= '" + start_date + "' AND 거래일자 <= '" + end_date + "')";
                    if (start_int > end_int){
                        System.out.println("시작 일자가 종료 일자보다 큽니다.");
                        return;
                    }
                    message = start_date + " ~ " + end_date.substring(0, 10) + " 사이의 거래내역이 존재하지 않습니다.";
                    break;
                case 5://입금 내역 조회
                    System.out.println("입금 내역만 조회합니다.");
                    completed_sql = sql + "AND 입금액 != 0";
                    message = "입금 내역이 존재하지 않습니다.";
                    break;
                case 6://출금 내역 조회
                    System.out.println("출금 내역만 조회합니다.");
                    completed_sql = sql + "AND 출금액 != 0";
                    message = "출금 내역이 존재하지 않습니다.";
                    break;
                default:
                    System.out.println("유효하지 않은 숫자입니다. 처음 조회 화면으로 돌아갑니다.");
                    continue;
            }
            break;
        }
        try (Statement statement = conn.createStatement();
             ResultSet results = statement.executeQuery(completed_sql)) {
            ArrayList<TransactionHistory> transactions = new ArrayList<>();
            while (results.next()) {
                TransactionHistory transaction = new TransactionHistory();
                transaction.setTransaction_time(results.getString(1));
                transaction.setType(results.getString(2));
                transaction.setTransaction_deposit(results.getInt(3));
                transaction.setTransaction_withdraw(results.getInt(4));
                transaction.setTransaction_name(results.getString(5));
                transaction.setTransaction_memo(results.getString(6));
                transactions.add(transaction);
            }
            if (transactions.isEmpty()) {
                System.out.println(message);
                return;
            }
            System.out.println("----------------------------------------------------------------------------------------");
            System.out.printf("%11s %11s %10s %10s %11s %11s", "거래시간", "유형", "입금액", "출금액", "거래자명", "메모\n");
            System.out.println("----------------------------------------------------------------------------------------");
            for (TransactionHistory transaction : transactions) {
                System.out.printf("%-20s %-10s %-12s %-12s %-11s %-14s\n", transaction.getTransaction_time(), transaction.getType(),
                        transaction.getTransaction_deposit(), transaction.getTransaction_withdraw(),
                        transaction.getTransaction_name(), transaction.getTransaction_memo());
            }
            System.out.println("----------------------------------------------------------------------------------------");
        } catch (SQLException e) {
            System.out.println("거래 내역 조회 중 SQLException 발생 :" + e.getMessage());
            e.printStackTrace();
        }
    }


    public void balanceUpdate(String in_account, String out_account, int amount) {
        try (Statement statement = conn.createStatement()) {
            if (!in_account.equals("null")) {
                statement.executeUpdate("Update Accounts Set balance = balance + " + amount + " Where account_num = '" + in_account + "'");
            }
            if (!out_account.equals("null")) {
                statement.executeUpdate("Update Accounts Set balance = balance - " + amount + " Where account_num = '" + out_account + "'");
            }
        } catch (SQLException e) {
            System.out.println("잔액 업데이트 중 SQLException 발생 :" + e.getMessage());
        }
    }

    public void printBalance(String account_num) {
        String print = "SELECT balance FROM Accounts WHERE account_num = '" + account_num + "'";
        try (Statement statement = conn.createStatement();
             ResultSet results = statement.executeQuery(print)) {
            String balance = results.getString(1);
            System.out.println("\n<거래정상완료> " + account_num + " 계좌의 현재 잔액은 " + balance + "원 입니다.");
        } catch (SQLException e) {
            System.out.println("printBalance Query failed: " + e.getMessage());
        }
    }

    public String switchToAccount(int card_num) {
        String CARD_SWITCH = "SELECT Accounts.account_num FROM Accounts JOIN Cards " +
                "ON cards.account_num = Accounts.account_num WHERE card_num = " + card_num;
        String account;
        try (Statement statement = conn.createStatement();
             ResultSet results = statement.executeQuery(CARD_SWITCH)) {
            account = results.getString(1);
            return account;
        } catch (SQLException e) {
            return "1";
        }
    }

    public void withdraw(String account_num) {
        try (Statement statement = conn.createStatement();
             ResultSet results = statement.executeQuery("SELECT account_lock FROM " +
                     "Accounts WHERE account_num = '" + account_num + "'")) {
            int account_lock = results.getInt(1);
            if (account_lock == 1) {
                System.out.println("해당 계좌는 잠금상태로, 출금이 불가합니다.");
                return;
            }
            Scanner in = new Scanner(System.in);
            System.out.print("> 출금할 금액을 입력하세요. : ");
            int amount = in.nextInt();
            if (!confirmPassword(account_num)) {
                System.out.println("비밀번호를 재설정합니다.");
                if (authentication(account_num)) {
                    resetPassword(account_num);
                    while(!confirmPassword(account_num)){
                        resetPassword(account_num);
                    }
                } else return;
            }
            Scanner sc = new Scanner(System.in);
            System.out.print("> 기록사항을 입력해주세요.(입력을 원하지 않을 경우 1을 입력하세요) : ");
            String memo = sc.next();
            if (memo.equals("1")) {
                memo = " ";
            }
            statement.execute("INSERT INTO Transactions (transaction_time, out_account, in_account, amount, memo) " +
                    "VALUES (datetime(), '" + account_num + "', NULL, " + amount + ", '" + memo + "')");
            balanceUpdate("NULL", account_num, amount);
            printBalance(account_num);
        } catch (SQLException e) {
            System.out.println("출금 중 SQLException 발생 :" + e.getMessage());
            e.printStackTrace();
        }
    }

    public void remittance(String account_num) {
        try (Statement statement = conn.createStatement();
             ResultSet results = statement.executeQuery("SELECT account_lock FROM Accounts " +
                     "WHERE account_num = '" + account_num + "'")) {
            int account_lock = results.getInt(1); //계좌잠금여부
            if (account_lock == 1) {
                System.out.println("해당 계좌는 잠금상태로, 출금이 불가합니다.");
                return;}
            Scanner in = new Scanner(System.in);
            String in_account;
           do {System.out.print("> 받는 분의 계좌번호를 7자리로 기입해주세요. ex) 1234567 : ");
               in_account = in.next();
            } while (in_account.length() != 7);
            in_account = in_account.substring(0, 4) + '-' + in_account.substring(4, 7);
            if(!checkExistAccount(account_num)){
                System.out.println("존재하지 않는 계좌입니다.");
                return; }
            System.out.print("> 송금할 금액을 입력하세요. : ");
            int amount = in.nextInt();
            if (!confirmPassword(in_account)) {
                System.out.println("비밀번호를 재설정합니다.");
                if (authentication(account_num)) {
                    resetPassword(account_num);
                    while(!confirmPassword(account_num)){
                        resetPassword(account_num);}
                } else return;}
            Scanner sc = new Scanner(System.in);
            System.out.print("> 기록사항을 입력해주세요.(입력을 원하지 않을 경우 1을 입력하세요) : ");
            String memo = sc.next();
            if (memo.equals("1")) {
                memo = " ";}
            statement.execute("INSERT INTO Transactions " +
                    "(transaction_time, out_account, in_account, amount, memo) VALUES (datetime(), '" +
                    account_num + "', '" + in_account + "', " + amount + ", '" + memo + "')");
            balanceUpdate(in_account, account_num, amount);
            printBalance(account_num);
        } catch (SQLException e) {
            System.out.println("송금 중 SQLException 발생 :" + e.getMessage());
            e.printStackTrace();
        }
    }

    public boolean confirmPassword(String account_num) {
        String sql = "SELECT account_password FROM Accounts WHERE account_num = '" + account_num + "'";
        try (Statement statement = conn.createStatement();
             ResultSet results = statement.executeQuery(sql)) {
            String password = results.getString(1);
            int count = 0;
            String input_password;
            do {
                if (count == 3) {
                    System.out.print("비밀번호 입력 " + count + "번 실패. 본인확인 후 비밀번호를 재설정합니다.");
                    return false;
                }
                Scanner sc = new Scanner(System.in);
                System.out.print("> 비밀번호를 입력하세요 : ");
                input_password = sc.next();
                count++;
                if (password.equals(input_password)) break;
                if (!password.equals(input_password) && count != 3) {
                    System.out.println("비밀번호 입력 " + count + "번 실패. 잘못된 비밀번호입니다. 다시 시도하거나 비밀번호를 재설정하세요.");
                    System.out.print("> 비밀번호를 재설정 하시겠습니까? 재설정(1 입력), 비밀번호 재입력(2 입력) : ");
                    while (true) {
                        String reset = sc.next();
                        if (reset.equals("1")) {
                            return false;
                        } else if (reset.equals("2")) {
                            break;
                        } else {
                            System.out.print("> 1 또는 2를 입력하세요 : ");
                        }
                    }
                }
            } while (!password.equals(input_password));
            System.out.println("비밀번호가 확인되었습니다.");
            return true;
        } catch (SQLException e) {
            System.out.println("비밀번호 확인 중 SQLException 발생 :" + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public void resetPassword(String account_num) {
        try (Statement statement = conn.createStatement()) {
            Scanner sc = new Scanner(System.in);
            while (true) {
                System.out.print("> 비밀번호를 재설정합니다. 재설정할 비밀번호를 입력하세요 : ");
                String update_password = sc.next();
                if (update_password.length() != 4) {
                    System.out.println("4자리를 입력하세요.");
                    continue;
                }
                System.out.print("> 한 번 더 입력해주세요 : ");
                String update_password2 = sc.next();
                if (update_password.equals(update_password2)) {
                    String update = "UPDATE Accounts SET account_password = " + update_password
                            + " WHERE account_num = '" + account_num + "'";
                    statement.executeUpdate(update);
                    System.out.println("비밀번호가 재설정 되었습니다.");
                    break;
                } else {
                    System.out.print("> 입력하신 비밀번호가 동일하지 않습니다. 다시 입력해주세요. : ");
                }
            }
        } catch (SQLException e) {
            System.out.println("비밀번호 재설정 중 SQLException 발생 :" + e.getMessage());
            e.printStackTrace();
        }
    }

    public void cardLock() {
        Scanner in = new Scanner(System.in);
        String residence_num;
        while(true){
            System.out.print("> 주민번호 13자리를 입력하세요. : ");
            residence_num = in.next();
            if(residence_num.length() != 13){
                System.out.println("유효하지 않은 주민번호입니다. 13자리로 입력해주세요.");
                continue;
            }
            break;
        }
        residence_num = residence_num.substring(0, 6) + "-" + residence_num.substring(6, 13);
        if(checkExisting(residence_num) ==2){
            System.out.println("해당 주민번호로 발급된 카드가 없습니다.");
            return;
        }else if(checkExisting(residence_num) ==3){
            System.out.println("카드 분실신고 업무가 종료됩니다.");
        }
        ArrayList<Integer> card_numbers;
        try {
            card_numbers = showCardNums(residence_num);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return;
        }
        System.out.print("> 몇 번째 카드의 정지를 원하는지 입력해주세요. : ");
        int num = in.nextInt();
        int card_num = card_numbers.get(num - 1);
        String account_num = switchToAccount(card_num);
        accountLock(account_num);
        System.out.print(card_num + " 카드와 연결된 계좌가 정지되었습니다.");
    }


    public ArrayList<Integer> showCardNums(String residence_num) throws SQLException {
        ArrayList<Integer> card_numbers = new ArrayList<>();
        try (Statement statement = conn.createStatement();
             ResultSet result = statement.executeQuery("SELECT card_num FROM Accounts NATURAL JOIN Customers " +
                     "NATURAL JOIN Cards WHERE residence_num = '" + residence_num + "' AND account_lock != 1")) {
            int i = 1;
            while (result.next()) {
                card_numbers.add(result.getInt(1));
                System.out.println(i++ + ": " + result.getInt(1));
            }
            return card_numbers;
        } catch (SQLException e) {
            throw new SQLException("showCardNum 실행 중 SQLException 발생 :");
        }
    }
    public boolean checkExpireDate(int card_num){
        try(Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery("SELECT expire_date FROM Cards WHERE card_num = "+card_num)){
            LocalDate now = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate expiredate = LocalDate.parse(result.getString(1),formatter);
            int compare_result = now.compareTo(expiredate);
            if(compare_result > 0) {
                System.out.println("만료된 카드입니다 거래를 종료합니다.");
                return false;
            }
            else {
                System.out.println("만료되지 않은 카드입니다 거래가 진행됩니다.");
                return true;
            }
        }catch (SQLException e){
            System.out.println("checkExpireDate 함수 진행중 Exception이 발생했습니다. :"+e.getMessage());
            return false;
        }
    }
}

