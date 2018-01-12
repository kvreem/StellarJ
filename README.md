# StellarJ
Messing around with the Stellar API in Java. 

----

## Set Up 
1. Make sure you have the added the `stellar-sdk.jar` as a project dependency. Download [here](https://github.com/stellar/java-stellar-sdk/releases).
2. If you do not have `homebrew` enter this command in terminal `/usr/bin/ruby -e "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/master/install)"`
3. Download Intelij using homebrew `brew cask install caskroom/cask/intellij-idea-ce`
4. Clone this repository `git clone https://github.com/kvreem/StellarJ.git`
5. Open up the project in Intelij and add the JAR as a dependency `cmd + ;` -> `modules` -> `+` -> `import modules` and select the stellar jar file.

If you have another IDE that you like than you don't need to download Intelij. 

----

## Class Structure 

`StellarDemo` - Run this to test program. 

`StellarAccountGenerator` - Generate KeyPairs and store it in StellarAccount object. 

`StellarAccount` - The StellarAccount object 

`StellarTransactionManager` - The class to handle transactions/payments. 

----

### TODO

- Send payments from one currency to another.
