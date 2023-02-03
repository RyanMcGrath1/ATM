import lc.kra.system.keyboard.GlobalKeyboardHook;
import lc.kra.system.keyboard.event.GlobalKeyAdapter;
import lc.kra.system.keyboard.event.GlobalKeyEvent;

import java.util.Scanner;
//https://github.com/kristian/system-hook

public class ATM {
    public static float balance;
    private static boolean run = true;
    private static int sema = 0;
    private static int sectionID;
    public static String input;

    public static void main(String[] args) {
        Menu();
    }


    public static void Menu() {
        sectionID = 0;
        System.out.println("|----------------------------------|");
        System.out.println("         Bank of Ryan ATM\n");
        System.out.println("   1. Deposit");
        System.out.println("   2. Withdraw");
        System.out.println("   3. Check Balance");
        System.out.println("   4. Press Escape to Return");
        System.out.println("|----------------------------------|");

        Listen();
        switch (sema) {
            case 1:
                Deposit();
                break;
            default:
                Menu();
        }

    }

    public static void Deposit() {
        sectionID = 1;
        System.out.println("|----------------------------------|");
        System.out.println("         Deposit Menu\n");
        System.out.println("   Press Enter then Type Deposit Amount: \n\n");
        System.out.println("   Press Escape to Return");
        System.out.println("|----------------------------------|");
        Listen();


    }

    public static void Withdraw() {
        System.out.println("Hello form Withdraw");
    }

    public static void Balance() {
        System.out.println("Hello from Balance");
    }

    public static float verifyDeposit(float amount) {
        if (amount < 0) {
            System.out.println("Invalid deposit amount");
            return 0;
        }
        return amount;
    }


    public static void Listen() {
        run = true;

        GlobalKeyboardHook keyboardHook = new GlobalKeyboardHook(false);
        Scanner scanner = new Scanner(System.in);
        keyboardHook.addKeyListener(new GlobalKeyAdapter() {

            @Override
            public void keyPressed(GlobalKeyEvent event) {
                //sectionID 0 handles interfacing for the Menu() function
                if (sectionID == 0) {
                    if (event.getVirtualKeyCode() == GlobalKeyEvent.VK_ESCAPE) {
                        sema = -1;
                    } else {
                        sema = scanner.nextInt();
                        scanner.reset();
                        run = false;
                    }
                }

                //sectionID 1 handles interfacing for the Deposit() function
                if (sectionID == 1) {
                    if (event.getVirtualKeyCode() == GlobalKeyEvent.VK_ESCAPE) {
                        Menu();
                    }
                    if (event.getVirtualKeyCode() == 13) { // 13 == Enter

                    }
                }

            }

            @Override
            public void keyReleased(GlobalKeyEvent event) {
                //  System.out.println(event);
            }

        });

        try {
            while (run) {
                Thread.sleep(128);
            }
        } catch (InterruptedException e) {
            //Do nothing
        } finally {
            keyboardHook.shutdownHook();
        }
    }


}