import lc.kra.system.keyboard.GlobalKeyboardHook;
import lc.kra.system.keyboard.event.GlobalKeyAdapter;
import lc.kra.system.keyboard.event.GlobalKeyEvent;

//https://github.com/kristian/system-hook

public class ATM {
    public static float balance;
    private static boolean run = true;


    public static void main(String[] args) {
        Menu();
    }


    public static void Menu() {
        System.out.println("|----------------------------------|");
        System.out.println("         Bank of Ryan ATM\n");
        System.out.println("   1. Deposit");
        System.out.println("   2. Withdraw");
        System.out.println("   3. Check Balance");
        System.out.println("   4. Press Escape to Return");
        System.out.println("|----------------------------------|");

        GlobalKeyboardHook keyboardHook = new GlobalKeyboardHook(true);
        keyboardHook.addKeyListener(new GlobalKeyAdapter() {

            @Override
            public void keyPressed(GlobalKeyEvent event) {
                if (event.getVirtualKeyCode() == GlobalKeyEvent.VK_ESCAPE) {
                    keyboardHook.shutdownHook();
                    run = false;
                }
                switch (event.getVirtualKeyCode()) {
                    case 49 -> {
                        Deposit();
                    }
                    case 50 -> {
                        Withdraw();
                    }
                    case 51 -> {
                        Balance();
                    }
                    default -> {
                    }
                    //do nothing
                    //this should account for non-valid inputs
                }

            }
        });


    }

    public static void Deposit() {
        System.out.println("|----------------------------------|");
        System.out.println("         Deposit Menu\n");
        System.out.println("   Type Deposit Amount: \n\n");
        System.out.println("   Press Escape to Return");
        System.out.println("|----------------------------------|");

        StringBuilder str = new StringBuilder();
        GlobalKeyboardHook depositHook = new GlobalKeyboardHook(true);
        depositHook.addKeyListener(new GlobalKeyAdapter() {

            @Override
            public void keyPressed(GlobalKeyEvent event) {
                if (event.getVirtualKeyCode() == GlobalKeyEvent.VK_ESCAPE) {
                    depositHook.shutdownHook();
                    Menu();
                }
                //Stringify user input for the deposit
                str.append(event.getKeyChar());

                if (event.getVirtualKeyCode() == 13) {
                    //Sanitize input; remove trailing \r
                    str.replace(str.length() - 1, str.length(), "");

                    if (isNumeric(String.valueOf(str))) {
                        if (isPositive(Float.parseFloat(str.toString()))) {

                            //successful deposit
                            depositHook.shutdownHook();
                            balance += Float.parseFloat(str.toString());
                            System.out.println("\rDeposit Complete; Returning to Menu\r");
                            try {
                                Thread.sleep(3000);
                                Menu();
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        } else {

                            //unsuccessful deposit
                            System.out.println("Invalid Entry; Returning to Menu");
                            depositHook.shutdownHook();
                            try {
                                Thread.sleep(3000);
                                Menu();
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
                }
            }
        });
    }

    public static void Withdraw() {
        System.out.println("Hello form Withdraw");
    }

    public static void Balance() {
        System.out.println("|----------------------------------|");
        System.out.printf("\nCurrent Balance:     %.2f\n", balance);
        System.out.println("|----------------------------------|");
    }

    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            Float.parseFloat(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public static boolean isPositive(Float f) {
        if (f > 0) {
            return true;
        }
        return false;
    }


}
