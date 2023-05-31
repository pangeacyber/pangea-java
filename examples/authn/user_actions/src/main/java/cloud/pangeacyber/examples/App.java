package cloud.pangeacyber.examples;

import cloud.pangeacyber.pangea.authn.AuthNClient;
import cloud.pangeacyber.pangea.exceptions.ConfigException;
import cloud.pangeacyber.pangea.exceptions.PangeaAPIException;
import cloud.pangeacyber.pangea.exceptions.PangeaException;
import cloud.pangeacyber.pangea.authn.models.*;
import cloud.pangeacyber.pangea.authn.requests.*;
import cloud.pangeacyber.pangea.authn.responses.*;
import cloud.pangeacyber.pangea.Config;
import java.util.Random;

public class App
{

    private static Random random = new Random();
	private static final String randomValue = Integer.toString(random.nextInt(10000000));
	private static final String userEmail = String.format("user.email+test%s@pangea.cloud", randomValue);
	private static final String passwordInitial = "My1s+Password";
	private static final String passwordNew = "My1s+Password_new";
	private static final Profile profileInitial = new Profile();
	private static final Profile profileUpdate = new Profile();
	private static String userID = ""; // Will be set once user is created

    public static void main( String[] args )
    {

        Config cfg = null;
        try {
            cfg = Config.fromEnvironment(AuthNClient.serviceName);
        } catch (ConfigException e){
            System.out.println(e);
            System.exit(1);
        }

        profileInitial.put("name", "User name");
		profileInitial.put("country", "Argentina");
		profileUpdate.put("age", "18");

        AuthNClient client = new AuthNClient.Builder(cfg).build();

		try {
			// Create User 1
            System.out.println("Creating user...");
			UserCreateResponse createResp1 = client.user().create(
                new UserCreateRequest.Builder(userEmail, passwordInitial, IDProvider.PASSWORD)
                .setProfile(profileInitial)
                .build()
            );
			userID = createResp1.getResult().getID();
            System.out.println("User create success. ID: " + userID);

			// User login
            System.out.println("Login user...");
			UserLoginResponse loginResp = client.user().login().Password(userEmail, passwordInitial);
            System.out.println("Login user success. Token: " + loginResp.getResult().getActiveToken().getToken());

			// Update password
            System.out.println("Update user password...");
			ClientPasswordChangeResponse passUpdateResp = client.client().password().change(
                loginResp.getResult().getActiveToken().getToken(), passwordInitial, passwordNew
            );
            System.out.println("Update password success.");

			// Update profile
            System.out.println("Get profile by email...");
			// Get profile by email. Should be empty because it was created without profile parameter
			UserProfileGetResponse profileGetResp = client.user().profile().getByEmail(userEmail);
            System.out.println("Get profile success.");

            System.out.println("Get profile by ID...");
			profileGetResp = client.user().profile().getByID(userID);
            System.out.println("Get profile success.");

            System.out.println("Update profile...");
			// Update profile
            // Add one new field to profile
			UserProfileUpdateResponse profileUpdateResp = client.user().profile().update(
                new UserProfileUpdateRequest.Builder(profileInitial).setEmail(userEmail).build()
                );

            System.out.println("Update profile success.");

			// User update
            System.out.println("User update...");
			UserUpdateResponse userUpdateResp = client.user().update(
                new UserUpdateRequest.Builder().setEmail(userEmail).setDisabled(false).setRequireMFA(false).build()
                );
            System.out.println("Update user success.");

            // List users
            System.out.println("Listing users...");
            UserListResponse userListResp1 = client.user().list(new UserListRequest.Builder().build());
            System.out.println(String.format("There is %d users", userListResp1.getResult().getCount()));

            // Delete user
            System.out.println("Deleting user...");
			UserDeleteResponse deleteResp1 = client.user().deleteByEmail(userEmail);
            System.out.println("User delete success");

            // List users
            System.out.println("Listing users...");
            userListResp1 = client.user().list(new UserListRequest.Builder().build());
            System.out.println(String.format("There is %d users", userListResp1.getResult().getCount()));

		} catch (PangeaAPIException | PangeaException e) {
            System.out.println("Something went wrong.");
			System.out.println(e.toString());
			System.exit(1);
		}

    }
}
