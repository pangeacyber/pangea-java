package cloud.pangeacyber.pangea;

public class Helper {

	public static TestEnvironment loadTestEnvironment(String serviceName, TestEnvironment def) throws Exception {
		serviceName = serviceName.replace("-", "_").toUpperCase();
		String envVarName = String.format("SERVICE_%s_ENV", serviceName);
		String value = System.getenv(envVarName);
		if (value == null || value.isEmpty()) {
			System.out.printf("%s is not set. Return default test environment value: %s\n", envVarName, def);
			return def;
		} else if ("DEV".equals(value)) {
			return TestEnvironment.DEVELOP;
		} else if ("STG".equals(value)) {
			return TestEnvironment.STAGING;
		} else if ("LVE".equals(value)) {
			return TestEnvironment.LIVE;
		} else {
			throw new Exception(String.format("%s not allowed value: %s", envVarName, value));
		}
	}
}
