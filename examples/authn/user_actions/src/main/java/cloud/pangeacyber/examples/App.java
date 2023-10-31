package cloud.pangeacyber.examples;

import cloud.pangeacyber.pangea.authn.AuthNClient;
import cloud.pangeacyber.pangea.exceptions.ConfigException;
import cloud.pangeacyber.pangea.exceptions.PangeaAPIException;
import cloud.pangeacyber.pangea.exceptions.PangeaException;
import cloud.pangeacyber.pangea.authn.models.*;
import cloud.pangeacyber.pangea.authn.requests.*;
import cloud.pangeacyber.pangea.authn.responses.*;
import cloud.pangeacyber.pangea.authn.results.FlowUpdateResult;
import cloud.pangeacyber.pangea.Config;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
    private static final String cbURI = "https://someurl.com/callbacklink";
	private static String userID = ""; // Will be set once user is created
    static AuthNClient client;

	private static FlowUpdateResult flowHandlePasswordPhase(String flowID, String password)
		throws PangeaException, PangeaAPIException {
		FlowUpdateResponse response =
			client.flow()
				.update(
					new FlowUpdateRequest.Builder(
						flowID,
						FlowChoice.PASSWORD,
						new FlowUpdateDataPassword.Builder(password).build()
					)
						.build()
				);
		return response.getResult();
	}

	private static FlowUpdateResult flowHandleProfilePhase(String flowID) throws PangeaException, PangeaAPIException {
		FlowUpdateResponse response =
			client.flow()
				.update(
					new FlowUpdateRequest.Builder(
						flowID,
						FlowChoice.PROFILE,
						new FlowUpdateDataProfile.Builder(profileInitial).build()
					)
						.build()
				);
		return response.getResult();
	}

	private static FlowUpdateResult flowHandleAgreementsPhase(String flowID, FlowUpdateResult result)
		throws PangeaException, PangeaAPIException {
		// Iterate over flow_choices in response.result
		List<String> agreed = new ArrayList<String>();
		for (FlowChoiceItem flowChoiceItem : result.getFlowChoices()) {
			if (flowChoiceItem.getChoice().equals(FlowChoice.AGREEMENTS.toString())) {
				Map<String, Object> agreements = (Map<String, Object>) flowChoiceItem.getData().get("agreements");
				if (agreements != null) {
					// Iterate over agreements and append the "id" values to agreed list
					for (Object agreementObj : agreements.values()) {
						if (agreementObj instanceof Map) {
							Map<?, ?> agreement = (Map<?, ?>) agreementObj;
							Object idObj = agreement.get("id");
							if (idObj instanceof String) {
								agreed.add((String) idObj);
							}
						}
					}
				}
			}
		}

		FlowUpdateResponse response =
			client.flow()
				.update(
					new FlowUpdateRequest.Builder(
						flowID,
						FlowChoice.AGREEMENTS,
						new FlowUpdateDataAgreements.Builder(agreed).build()
					)
						.build()
				);
		return response.getResult();
	}

	private static boolean choiceIsAvailable(FlowChoiceItem[] choices, String choice) {
		for (FlowChoiceItem flowChoiceItem : choices) {
			if (flowChoiceItem.getChoice().equals(choice)) {
				return true;
			}
		}
		return false;
	}

	private static FlowCompleteResponse userCreateAndLogin(String email, String password)
		throws PangeaException, PangeaAPIException {
		FlowType[] flowTypes = { FlowType.SIGNUP, FlowType.SIGNIN };
		FlowStartResponse startResponse =
			client.flow()
				.start(
					new FlowStartRequest.Builder().setEmail(email).setFlowType(flowTypes).setCbURI(cbURI).build()
				);

		String flowID = startResponse.getResult().getFlowID();
		FlowUpdateResult result = null;
		String flowPhase = "initial";
		FlowChoiceItem[] choices = startResponse.getResult().getFlowChoices();

		while (!flowPhase.equals("phase_completed")) {
			if (choiceIsAvailable(choices, FlowChoice.PASSWORD.toString())) {
				result = flowHandlePasswordPhase(flowID, password);
			} else if (choiceIsAvailable(choices, FlowChoice.PROFILE.toString())) {
				result = flowHandleProfilePhase(flowID);
			} else if (choiceIsAvailable(choices, FlowChoice.AGREEMENTS.toString()) && result != null) {
				result = flowHandleAgreementsPhase(flowID, result);
			} else {
				System.out.printf("Phase %s not handled\n", result.getFlowPhase());
				throw new PangeaException("Phase not handled", null);
			}
			flowPhase = result.getFlowPhase();
			choices = result.getFlowChoices();
		}

		return client.flow().complete(flowID);
	}

    public static void main( String[] args )
    {

        Config cfg = null;
        try {
            cfg = Config.fromEnvironment(AuthNClient.serviceName);
        } catch (ConfigException e){
            System.out.println(e);
            System.exit(1);
        }

		profileInitial.put("first_name", "Name");
		profileInitial.put("last_name", "User");
		profileUpdate.put("first_name", "NameUpdated");

        client = new AuthNClient.Builder(cfg).build();

		try {
            // Create User 1
            System.out.println("Creating user...");
            FlowCompleteResponse createResp1 = userCreateAndLogin(userEmail, passwordInitial);
            System.out.println("Login user success. Token: " + createResp1.getResult().getActiveToken().getToken());

			// Update password
            System.out.println("Update user password...");
			ClientPasswordChangeResponse passUpdateResp = client.client().password().change(
                createResp1.getResult().getActiveToken().getToken(), passwordInitial, passwordNew
            );
            System.out.println("Update password success.");

			// Update profile
            System.out.println("Get profile by email...");
			// Get profile by email. Should be empty because it was created without profile parameter
			UserProfileGetResponse profileGetResp = client.user().profile().getByEmail(userEmail);
            userID = profileGetResp.getResult().getID();
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
                new UserUpdateRequest.Builder().setEmail(userEmail).setDisabled(false).build()
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
