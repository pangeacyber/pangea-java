# Pangea Java SDK examples

This is a quick example about how you use Pangea Java SDK, set up and run it.

## Set up
Set up environment variables `PANGEA_<SERVICE>_TOKEN` and `PANGEA_DOMAIN` with your project token configured on Pangea User Console (token should have access to <service>) and with your pangea domain.

## Run

To run examples, move to example folder you would like to run, i.e. if we want to run audit log example:
```
cd audit/log
```

and then run example with:
```
mvn clean && mvn compile
mvn exec:java -Dexec.mainClass=cloud.pangeacyber.examples.App
```
