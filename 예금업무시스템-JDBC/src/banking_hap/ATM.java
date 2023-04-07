package banking_hap;

import java.util.Scanner;

public class ATM {
    public static void screenPrint() {

        System.out.println("\n[초기화면] 원하시는 기능의 번호를 입력하세요.");
        System.out.print("1. 무통장입금 2. 신규계좌개설 3. 카드삽입 4. 카드분실신고 > ");//그 외의 숫자 입력 시 시스템종료 (관리자만)
    }

    public static void screenPrintWhenUseCard() {

        System.out.println("\n[카드 삽입] 원하시는 기능의 번호를 입력하세요. (이 외의 숫자를 입력하면 초기화면으로 돌아갑니다.)");
        System.out.print("1. 입금 2. 출금 3. 송금 4. 거래내역확인 5. 비밀번호변경 6. 분실카드 및 잠금계좌 해제 > ");
    }

    public static void main(String[] args) {
        Banking a = new Banking();
        if(!a.open())
            return;
        Scanner in = new Scanner(System.in);
        int function_num;
        function : while (true) {
            screenPrint();
            function_num = in.nextInt();
            switch (function_num) {
                case 1: // 무통장입금
                    System.out.println("1. 무통장입금을 선택하셨습니다.");
                    a.deposit();
                    break;
                case 2: // 신규계좌개설
                    System.out.println("2. 신규계좌개설을 선택하셨습니다. 신규 계좌 개설 시, 계좌와 연결된 현금카드도 함께 발급하여 드립니다.");
                    System.out.println("기존 고객 또는 신규 고객 여부를 확인하겠습니다.");
                    a.newAccounts();
                    break;
                case 3: { // 카드삽입 거래
                    System.out.println("3. 카드삽입을 선택하셨습니다.");
                    System.out.print("> 카드번호를 입력하세요 : ");
                    int card_num = in.nextInt();
                    if(!a.checkExpireDate(card_num)) break;
                    String account_num = a.switchToAccount(card_num);
                    if(account_num.equals("1")) {
                        System.out.println("유효하지 않은 카드번호 입니다.");
                        break;
                    }
                    screenPrintWhenUseCard();
                    function_num = in.nextInt();
                    switch (function_num) {
                        case 1:
                            System.out.println("1. 카드에 연결된 계좌에 현금을 입금합니다.");
                            a.cardDeposit(account_num);
                            break;
                        case 2:
                            System.out.println("2. 카드에 연결된 계좌에서 현금을 출금합니다.");
                            a.withdraw(account_num);
                            break;
                        case 3:
                            System.out.println("3. 카드에 연결된 계좌에서 다른 계좌로 송금합니다.");
                            a.remittance(account_num);
                            break;
                        case 4:
                            System.out.println("4. 카드에 연결된 계좌의 거래내역을 조회합니다.");
                            a.transactionHistory(account_num);
                            break;
                        case 5:
                            System.out.println("5. 카드에 연결된 계좌의 비밀번호를 변경합니다. 본인확인 일치시 진행, 불일치시 계좌가 잠금됩니다.");
                            if(!a.authentication(account_num))
                                return;
                            a.resetPassword(account_num);
                            break;
                        case 6:
                            System.out.println("6. 정지된 상태의 카드 및 계좌의 잠금을 해제합니다.");
                            a.accountUnlock(account_num);
                            break;
                        default:
                            System.out.println("처음 화면으로");
                            continue;
                    }
                    break;
                }
                case 4: //카드 분실신고
                    System.out.println("4. 카드 분실신고를 선택하셨습니다.");
                    System.out.println("카드 분실 신고 시 연결된 계좌가 잠금상태로 전환됩니다. 본인확인 후 진행합니다.");
                    a.cardLock();
                    break;
                default:
                    break function;
            }
            System.out.println("\n거래가 종료되었습니다. 처음으로 돌아갑니다.");
        }
        a.close();
    }
}
