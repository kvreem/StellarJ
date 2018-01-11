import org.stellar.sdk.KeyPair;
public class StellarAccountGenerator {

    public StellarAccountGenerator() {

    }

    public StellarAccount createStellarAccount(KeyPair k) {
        StellarAccount s = new StellarAccount(k);
        return s;
    }

    public KeyPair generateKeyPair() {
        KeyPair k = KeyPair.random();

        return k;
    }
}