import org.stellar.sdk.KeyPair;
import org.stellar.sdk.Server;
import org.stellar.sdk.responses.AccountResponse;


import java.net.*;
import java.io.*;
import java.util.*;

public class StellarAccount {

    private String accountId;
    private String secretSeed;
    private KeyPair k;
    private String STELLAR_BOT = "https://horizon-testnet.stellar.org/friendbot?addr=%s";


    public StellarAccount(KeyPair k) {
        this.k = k;
    }

    public String getAccountId() {
        return accountId;
    }

    public String getSecretSeed() {
        return secretSeed;
    }

    public void setAccountId() {
        this.accountId = k.getAccountId();
    }

    public void setSecretSeed() {
        this.secretSeed = new String(this.k.getSecretSeed());
    }

    public void activateAccountWithMinimumBalance() {

        String url = String.format(STELLAR_BOT, this.getAccountId());
        InputStream response = null;
        try {
            response = new URL(url).openStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String body = new Scanner(response, "UTF-8").useDelimiter("\\A").next();
        System.out.println("Account activated: " + body);
    }

    public void checkAccountBalance() {
        Server server = new Server("https://horizon-testnet.stellar.org");
        AccountResponse account = null;
        try {
            account = server.accounts().account(KeyPair.fromAccountId(this.getAccountId()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Balances for account " + this.getAccountId());
        for (AccountResponse.Balance balance: account.getBalances()) {
            System.out.println(String.format(
                    "Type: %s, Code: %s, Balance: %s",
                    balance.getAssetType(),
                    balance.getAssetCode(),
                    balance.getBalance()));
        }
    }
}