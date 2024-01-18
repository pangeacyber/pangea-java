# Pangea Java SDK examples

This is a quick example about how you use Pangea Java SDK, set up and run it.

## Setup

Set up environment variables ([Instructions](https://pangea.cloud/docs/getting-started/integrate/#set-environment-variables)) `PANGEA_<SERVICE>_TOKEN` and `PANGEA_DOMAIN` with your project token configured on Pangea User Console (token should have access to `<service>` [Instructions](https://pangea.cloud/docs/getting-started/configure-services/#configure-a-pangea-service)) and with your Pangea domain.


## Run

To run examples, move to example folder you would like to run, e.g. if we want to run audit log example:

```bash
$ cd audit/log
```

and then run example with:

```bash
$ mvn clean && mvn compile
$ mvn exec:java -Dexec.mainClass=cloud.pangeacyber.examples.App
```
