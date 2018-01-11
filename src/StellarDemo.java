import org.stellar.sdk.KeyPair;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class StellarDemo {
    public static final String EXIT_COMMAND = "exit";
    public static void main(String[] args) throws InterruptedException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StellarAccount s = null;
        StellarTransactionManager t = new StellarTransactionManager("SCR2HR4G4UDKBAXW2R73CLL2LHORYARWHHWQCISSBZYS4S4C6ILYWWNT", "GDXEDY4435L6QJWHQ2O44ZVS4GBTQPOQXQGUY3LC33GIAB37A2AIKYPP");

        System.out.println("1: Create Account ");
        System.out.println("2: Activate Account ");
        System.out.println("3: Send Transaction ");
        System.out.println("4: Check Source Balance ");
        System.out.println("5: Check Destination Balance ");
        System.out.println("6: Back to Menu ");

        while (true) {

            System.out.print("> ");
            String input = null;
            try {
                input = br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println(input);

            if (input.length() == EXIT_COMMAND.length() && input.toLowerCase().equals(EXIT_COMMAND)) {
                System.out.println("Exiting.");
                return;
            }

            if (Integer.parseInt(input) == 1) {

                System.out.println("================================= ");
                System.out.println();
                System.out.println("Generate Stellar KeyPair: ");
                Thread.sleep(1000);
                StellarAccountGenerator g = new StellarAccountGenerator();
                KeyPair k = g.generateKeyPair();
                Thread.sleep(1000);
                System.out.println("KeyPair generated... ");
                Thread.sleep(1000);
                System.out.println("Generate Stellar Account: ");
                s = g.createStellarAccount(k);
                s.setSecretSeed();
                s.setAccountId();
                System.out.println("Stellar account created... ");
                System.out.println("================================= ");
                System.out.println();
                System.out.println("Verify the account exists by getting the secret seed...: ");
                System.out.println(s.getSecretSeed());
                System.out.println("Verify the account exists by getting the public address...: ");
                System.out.println(s.getAccountId());
                System.out.println("================================= ");

            } else if (Integer.parseInt(input) == 2) {

                System.out.println("In order to send/receive money activate account by sending Lumens to your account: ");
                System.out.println("Activating Account... ");
                s.activateAccountWithMinimumBalance();
                System.out.println();
                System.out.println("Account activated, view account balance....");
                s.checkAccountBalance();
                System.out.println("================================= ");

            } else if (Integer.parseInt(input) == 3) {

                System.out.println("================================= ");
                System.out.println("Sending Payment ........ ");
                System.out.println("================================= ");
                t.sendPaymentToDestinationAccount();

            } else if (Integer.parseInt(input) == 4) {

                t.checkSourceBalance();

            } else if (Integer.parseInt(input) == 5) {

                t.checkDestinationBalance();

            } else if (Integer.parseInt(input) == 6) {

                System.out.println("1: Create Account ");
                System.out.println("2: Activate Account ");
                System.out.println("3: Send Transaction ");
                System.out.println("4: Check Source Balance ");
                System.out.println("5: Check Destination Balance ");
                System.out.println("6: Back to Menu ");

            }
        }
    }
}