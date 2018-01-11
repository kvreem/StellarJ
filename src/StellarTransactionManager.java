import org.aopalliance.reflect.Code;
import org.stellar.sdk.KeyPair;
import org.stellar.sdk.Server;
import org.stellar.sdk.requests.PaymentsRequestBuilder;
import org.stellar.sdk.responses.AccountResponse;
import org.stellar.sdk.responses.operations.PathPaymentOperationResponse;
import org.stellar.sdk.responses.*;
import org.stellar.sdk.*;
import org.stellar.sdk.xdr.Asset;
import org.stellar.sdk.xdr.AssetType;


import java.io.IOException;

/**
 * RECEIVING ACCOUNT: SECRET - SAKBPSXT2F5MW7XHOTUCFTWP63F2E234TXVD7UZKSDVGICBRF6QEZRUK
 * PUBLIC ID - GDXEDY4435L6QJWHQ2O44ZVS4GBTQPOQXQGUY3LC33GIAB37A2AIKYPP
 *
 * SENDING ACCOUNT: SECRET - SCR2HR4G4UDKBAXW2R73CLL2LHORYARWHHWQCISSBZYS4S4C6ILYWWNT
 * PUBLIC ID - GDBYPSGQNAMVA2BFK7Z7UGWISLME6FJ6XXTHZXQZRCWH2TP2GX5JHO5D
 */

public class StellarTransactionManager {

    private KeyPair destination;
    private KeyPair source;
    AccountResponse account = null;
    Server server = new Server("https://horizon-testnet.stellar.org");


    public StellarTransactionManager(String secretKey, String destinationKey) {
        this.destination = KeyPair.fromAccountId(destinationKey);
        this.source = KeyPair.fromSecretSeed(secretKey);
    }

    public KeyPair getSource() {
        return source;
    }

    public KeyPair getDestination() {
        return destination;
    }

    public boolean doesDestinationAccountExist() {
        try {
            if (server.accounts().account(this.getDestination()).getKeypair() != null) {
                return true;
            } else {
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void sendPaymentToDestinationAccount() {
        Network.useTestNetwork();
        boolean confirmDestination = doesDestinationAccountExist();
        if (confirmDestination) {
            try {
                AccountResponse sourceAccount = server.accounts().account(this.getSource());
                // Start building the transaction.
                Transaction transaction = new Transaction.Builder(sourceAccount)
                        .addOperation(new PaymentOperation.Builder(this.getDestination(), new AssetTypeNative(), "10").build())
                        // A memo allows you to add your own metadata to a transaction. It's
                        // optional and does not affect how Stellar treats the transaction.
                        .addMemo(Memo.text("Test Memo"))
                        .build();
                // Sign the transaction to prove you are actually the person sending it.
                transaction.sign(this.getSource());

                try {
                    SubmitTransactionResponse response = server.submitTransaction(transaction);
                    System.out.println("Success, the payment went through!");
                    System.out.println(response);
                } catch (Exception e) {
                    System.out.println("Something went wrong when sending the payment!");
                    System.out.println(e.getMessage());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * public Builder(Asset sendAsset, java.lang.String sendMax, KeyPair destination, Asset destAsset, java.lang.String destAmount)
     *
     */

//    public void sendPaymentToDestinationAccounInUSD() {
//        Network.useTestNetwork();
//        boolean confirmDestination = doesDestinationAccountExist();
//        if (confirmDestination) {
//            try {
//                AccountResponse sourceAccount = server.accounts().account(this.getSource());
//                // Start building the transaction.
//                Transaction transaction = new Transaction.Builder(sourceAccount).addOperation(new PathPaymentOperation.Builder()
//                // Sign the transaction to prove you are actually the person sending it.
//                transaction.sign(this.getSource());
//
//                try {
//                    SubmitTransactionResponse response = server.submitTransaction(transaction);
//                    System.out.println("Success, the payment went through!");
//                    System.out.println(response);
//                } catch (Exception e) {
//                    System.out.println("Something went wrong when sending the payment!");
//                    System.out.println(e.getMessage());
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }

    public void checkDestinationBalance() {
        try {
            account = server.accounts().account(this.getDestination());
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println();
        System.out.println("Destination Account: " + this.getDestination().getAccountId());

        for (AccountResponse.Balance balance: account.getBalances()) {
            System.out.println(String.format("Balance: %s", balance.getBalance()));
        }
    }

    public void checkSourceBalance() {
        try {
            account = server.accounts().account(this.getSource());
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println();
        System.out.println("Source Account: " + this.getSource().getAccountId());


        for (AccountResponse.Balance balance: account.getBalances()) {
            System.out.println(String.format("Balance: %s", balance.getBalance()));
        }
    }
}