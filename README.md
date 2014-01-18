#cryptotrader

Trading platform for various cryptocurrencies (BTC, LTC)

##Installation
1. Download the sources and configure it as a maven-project in Eclipse.
2. Replace logback.xml with logback.prod.xml to prevent the application from showing debug messages.
3. Run maven install using the provided pom.xml.
4. Add the data-folder ( + optionally a bat-file) to the newly created target/cryptotrade-{version}-folder.
5. Run the jar "java -jar cryptotrader-{version}.jar"
6. ...
7. Profit!