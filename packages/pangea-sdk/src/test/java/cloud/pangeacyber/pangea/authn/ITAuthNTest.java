package cloud.pangeacyber.pangea.authn;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import cloud.pangeacyber.pangea.Config;
import cloud.pangeacyber.pangea.TestEnvironment;
import cloud.pangeacyber.pangea.authn.models.*;
import cloud.pangeacyber.pangea.authn.requests.*;
import cloud.pangeacyber.pangea.authn.responses.*;
import cloud.pangeacyber.pangea.authn.results.FlowUpdateResult;
import cloud.pangeacyber.pangea.exceptions.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ITAuthNTest {

	AuthNClient client;
	Config cfg;
	TestEnvironment environment = TestEnvironment.LIVE;
	private static Random random = new Random();
	private static final String randomValue = Integer.toString(random.nextInt(10000000));
	private static final String emailTest = String.format("user.email+test%s@pangea.cloud", randomValue);
	private static final String emailDelete = String.format("user.email+delete%s@pangea.cloud", randomValue);
	private static final String emailInviteDelete = String.format("user.email+invite_del%s@pangea.cloud", randomValue);
	private static final String emailInviteKeep = String.format("user.email+invite_keep%s@pangea.cloud", randomValue);
	private static final String passwordOld = "My1s+Password";
	private static final String passwordNew = "My1s+Password_new";
	private final Profile profileOld = new Profile();
	private final Profile profileNew = new Profile();
	private static String userID = ""; // Will be set once user is created
	private final String cbURI = "https://someurl.com/callbacklink";
	String time;

	@Before
	public void setUp() throws ConfigException {
		this.cfg = Config.fromIntegrationEnvironment(environment);
		client = new AuthNClient.Builder(cfg).build();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
		time = dtf.format(LocalDateTime.now());

		profileOld.put("first_name", "Name");
		profileOld.put("last_name", "User");
		profileNew.put("first_name", "NameUpdated");
	}

	private FlowUpdateResult flowHandlePasswordPhase(String flowID, String password)
		throws PangeaException, PangeaAPIException {
		FlowUpdateResponse response =
			this.client.flow()
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

	private FlowUpdateResult flowHandleProfilePhase(String flowID) throws PangeaException, PangeaAPIException {
		FlowUpdateResponse response =
			this.client.flow()
				.update(
					new FlowUpdateRequest.Builder(
						flowID,
						FlowChoice.PROFILE,
						new FlowUpdateDataProfile.Builder(this.profileOld).build()
					)
						.build()
				);
		return response.getResult();
	}

	private FlowUpdateResult flowHandleAgreementsPhase(String flowID, FlowUpdateResult result)
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
			this.client.flow()
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

	private boolean choiceIsAvailable(FlowChoiceItem[] choices, String choice) {
		for (FlowChoiceItem flowChoiceItem : choices) {
			if (flowChoiceItem.getChoice().equals(choice)) {
				return true;
			}
		}
		return false;
	}

	private FlowCompleteResponse userCreateAndLogin(String email, String password)
		throws PangeaException, PangeaAPIException {
		FlowType[] flowTypes = { FlowType.SIGNUP, FlowType.SIGNIN };
		FlowStartResponse startResponse =
			this.client.flow()
				.start(
					new FlowStartRequest.Builder().setEmail(email).setFlowType(flowTypes).setCbURI(this.cbURI).build()
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

		return this.client.flow().complete(flowID);
	}

	private FlowCompleteResponse login(String email, String password) throws PangeaException, PangeaAPIException {
		FlowType[] flowTypes = { FlowType.SIGNUP, FlowType.SIGNIN };
		FlowStartResponse startResponse =
			this.client.flow()
				.start(
					new FlowStartRequest.Builder().setEmail(email).setFlowType(flowTypes).setCbURI(this.cbURI).build()
				);

		String flowID = startResponse.getResult().getFlowID();

		FlowUpdateResponse updateResponse =
			this.client.flow()
				.update(
					new FlowUpdateRequest.Builder(
						flowID,
						FlowChoice.PASSWORD,
						new FlowUpdateDataPassword.Builder(password).build()
					)
						.build()
				);

		return this.client.flow().complete(flowID);
	}

	@Test
	public void testA_UserActions() throws PangeaException, PangeaAPIException {
		try {
			// Create User 1
			FlowCompleteResponse createResp1 = this.userCreateAndLogin(emailTest, passwordOld);

			// Create user 2
			FlowCompleteResponse createResp2 = this.userCreateAndLogin(emailDelete, passwordOld);

			// Logout (delete user)
			SessionLogoutResponse logoutResp = client
				.session()
				.logout(createResp2.getResult().getActiveToken().getID());
			assertTrue(logoutResp.isOk());

			// Delete user (delete user)
			UserDeleteResponse deleteResp1 = client.user().deleteByEmail(emailDelete);
			assertTrue(deleteResp1.isOk());
			assertNull(deleteResp1.getResult());

			// Token check
			ClientTokenCheckResponse checkResp = client
				.client()
				.token()
				.check(createResp1.getResult().getActiveToken().getToken());
			assertTrue(checkResp.isOk());

			// Update password
			ClientPasswordChangeResponse passUpdateResp = client
				.client()
				.password()
				.change(createResp1.getResult().getActiveToken().getToken(), passwordOld, passwordNew);
			assertTrue(passUpdateResp.isOk());
			assertNull(passUpdateResp.getResult());

			// Update profile
			// Get profile by email. Should be empty because it was created without profile parameter
			UserProfileGetResponse profileGetResp = client.user().profile().getByEmail(emailTest);
			assertTrue(profileGetResp.isOk());
			assertNotNull(profileGetResp.getResult());
			userID = profileGetResp.getResult().getID();
			assertEquals(emailTest, profileGetResp.getResult().getEmail());
			assertEquals(this.profileOld, profileGetResp.getResult().getProfile());

			// Get by ID
			profileGetResp = client.user().profile().getByID(userID);
			assertTrue(profileGetResp.isOk());
			assertNotNull(profileGetResp.getResult());
			assertEquals(userID, profileGetResp.getResult().getID());
			assertEquals(emailTest, profileGetResp.getResult().getEmail());
			assertEquals(this.profileOld, profileGetResp.getResult().getProfile());

			// Update profile
			UserProfileUpdateResponse profileUpdateResp = client
				.user()
				.profile()
				.update(new UserProfileUpdateRequest.Builder(profileOld).setEmail(emailTest).build());
			assertTrue(profileGetResp.isOk());
			assertNotNull(profileUpdateResp.getResult());
			assertEquals(userID, profileUpdateResp.getResult().getID());
			assertEquals(emailTest, profileUpdateResp.getResult().getEmail());
			assertEquals(profileOld, profileUpdateResp.getResult().getProfile());

			// Add one new field to profile
			profileUpdateResp =
				client.user().profile().update(new UserProfileUpdateRequest.Builder(profileNew).setId(userID).build());
			assertTrue(profileGetResp.isOk());
			assertNotNull(profileUpdateResp.getResult());
			assertEquals(userID, profileUpdateResp.getResult().getID());
			assertEquals(emailTest, profileUpdateResp.getResult().getEmail());
			Profile finalProfile = new Profile();
			finalProfile.putAll(profileOld);
			finalProfile.putAll(profileNew);
			assertEquals(finalProfile, profileUpdateResp.getResult().getProfile());

			// User update
			UserUpdateResponse userUpdateResp = client
				.user()
				.update(new UserUpdateRequest.Builder().setEmail(emailTest).setDisabled(false).build());
			assertTrue(userUpdateResp.isOk());
			assertEquals(userID, userUpdateResp.getResult().getID());
			assertEquals(emailTest, userUpdateResp.getResult().getEmail());

			// Client session refresh (refresh and active token)
			ClientSessionRefreshResponse refreshResp = client
				.client()
				.session()
				.refresh(
					createResp1.getResult().getRefreshToken().getToken(),
					createResp1.getResult().getActiveToken().getToken()
				);
			assertTrue(refreshResp.isOk());
			assertNotNull(refreshResp.getResult().getActiveToken());
			assertNotNull(refreshResp.getResult().getRefreshToken());

			// Client session refresh (only refresh token)
			refreshResp = client.client().session().refresh(refreshResp.getResult().getRefreshToken().getToken());
			assertTrue(refreshResp.isOk());
			assertNotNull(refreshResp.getResult().getActiveToken());
			assertNotNull(refreshResp.getResult().getRefreshToken());

			// Client session logout
			ClientSessionLogoutResponse logoutResp2 = client
				.client()
				.session()
				.logout(refreshResp.getResult().getActiveToken().getToken());
			assertTrue(logoutResp2.isOk());
		} catch (PangeaAPIException e) {
			System.out.println(e.toString());
			assertTrue(false);
		}
	}

	@Test
	public void testB_ClientSessionList_n_Invalidate() throws PangeaException, PangeaAPIException {
		try {
			// User login
			FlowCompleteResponse loginResp = this.login(emailTest, passwordNew);
			assertTrue(loginResp.isOk());
			assertNotNull(loginResp.getResult().getActiveToken());
			assertNotNull(loginResp.getResult().getRefreshToken());
			String token = loginResp.getResult().getActiveToken().getToken();

			FilterSessionList filter = new FilterSessionList();

			// List client sessions
			ClientSessionListResponse listResp = client
				.client()
				.session()
				.list(new ClientSessionListRequest.Builder(token).setFilter(filter).build());
			assertTrue(listResp.isOk());
			assertTrue(listResp.getResult().getSessions().length > 0);

			for (SessionItem session : listResp.getResult().getSessions()) {
				try {
					// Invalidate client sessions
					ClientSessionInvalidateResponse invalidateResp = client
						.client()
						.session()
						.invalidate(token, session.getId());
					assertTrue(invalidateResp.isOk());
				} catch (PangeaAPIException e) {
					System.out.println(
						String.format("Failed to invalidate session_id[%s] token[%s]", session.getId(), token)
					);
					System.out.println(e.toString());
				}
			}
		} catch (PangeaAPIException e) {
			System.out.println(e.toString());
			assertTrue(false);
		}
	}

	@Test
	public void testC_SessionList_n_Invalidate() throws PangeaException, PangeaAPIException {
		try {
			// User login
			FlowCompleteResponse loginResp = this.login(emailTest, passwordNew);
			assertTrue(loginResp.isOk());
			assertNotNull(loginResp.getResult().getActiveToken());
			assertNotNull(loginResp.getResult().getRefreshToken());
			String token = loginResp.getResult().getActiveToken().getToken();

			FilterSessionList filter = new FilterSessionList();

			// Session list
			SessionListResponse listResp = client
				.session()
				.list(new SessionListRequest.Builder().setFilter(filter).build());
			assertTrue(listResp.isOk());
			assertTrue(listResp.getResult().getSessions().length > 0);

			for (SessionItem session : listResp.getResult().getSessions()) {
				try {
					// invalidate sessions
					SessionInvalidateResponse invalidateResp = client.session().invalidate(session.getId());
					assertTrue(invalidateResp.isOk());
				} catch (PangeaAPIException e) {
					System.out.println(
						String.format("Failed to invalidate session_id[%s] token[%s]", session.getId(), token)
					);
					System.out.println(e.toString());
				}
			}
		} catch (PangeaAPIException e) {
			System.out.println(e.toString());
			assertTrue(false);
		}
	}

	@Test
	public void testD_InviteActions() throws PangeaException, PangeaAPIException {
		try {
			// Invite 1
			UserInviteResponse inviteResp1 = client
				.user()
				.invite(
					new UserInviteRequest.Builder(
						emailTest,
						emailInviteKeep,
						"https://someurl.com/callbacklink",
						"somestate"
					)
						.build()
				);
			assertTrue(inviteResp1.isOk());
			assertNotNull(inviteResp1.getResult());
		} catch (PangeaAPIException e) {
			System.out.println(e.toString());
			assertTrue(false);
		}

		UserInviteResponse inviteResp2 = null;
		try {
			// Invite 2
			inviteResp2 =
				client
					.user()
					.invite(
						new UserInviteRequest.Builder(
							emailTest,
							emailInviteDelete,
							"https://someurl.com/callbacklink",
							"somestate"
						)
							.build()
					);
			assertTrue(inviteResp2.isOk());
			assertNotNull(inviteResp2.getResult());
		} catch (PangeaAPIException e) {
			System.out.println(e.toString());
			assertTrue(false);
		}

		try {
			// Delete invite
			UserInviteDeleteResponse deleteResp = client.user().invite().delete(inviteResp2.getResult().getID());
			assertTrue(deleteResp.isOk());
		} catch (PangeaAPIException e) {
			System.out.println(e.toString());
			assertTrue(false);
		}

		FilterUserInviteList filter = new FilterUserInviteList();

		try {
			// List users invites
			UserInviteListResponse inviteListResp1 = client
				.user()
				.invite()
				.list(new UserInviteListRequest.Builder().setFilter(filter).build());
			assertTrue(inviteListResp1.isOk());
			assertNotNull(inviteListResp1.getResult().getInvites());
			assertTrue(inviteListResp1.getResult().getInvites().length > 0);

			for (UserInvite userInvite : inviteListResp1.getResult().getInvites()) {
				// Delete invite
				UserInviteDeleteResponse deleteResp = client.user().invite().delete(userInvite.getID());
				assertTrue(deleteResp.isOk());
			}
		} catch (PangeaAPIException e) {
			System.out.println(e.toString());
			assertTrue(false);
		}
	}

	@Test
	public void testE_ListUsers() throws PangeaException, PangeaAPIException {
		FilterUserList filter = new FilterUserList();

		try {
			UserListResponse userListResp1 = client
				.user()
				.list(new UserListRequest.Builder().setFilter(filter).build());
			assertTrue(userListResp1.isOk());
			assertTrue(userListResp1.getResult().getUsers().length > 0);

			for (User user : userListResp1.getResult().getUsers()) {
				try {
					UserDeleteResponse deleteResp = client.user().deleteByID(user.getID());
					assertTrue(deleteResp.isOk());
				} catch (PangeaAPIException e) {
					System.out.println(String.format("Failed to delete user ID: %s\n", user.getID()));
					System.out.println(e.toString());
				}
			}
		} catch (PangeaAPIException e) {
			System.out.println(e.toString());
			assertTrue(false);
		}
	}

	public void agreementsCycle(AgreementType type) throws PangeaAPIException, PangeaException {
		String name = type + "_" + System.currentTimeMillis();
		String text = "This is agreement text";
		boolean active = false;

		// Create agreement
		AgreementCreateResponse createResponse = client
			.agreements()
			.create(new AgreementCreateRequest.Builder(type, name, text).setActive(active).build());
		assertEquals(type.toString(), createResponse.getResult().getType());
		assertEquals(name, createResponse.getResult().getName());
		assertEquals(text, createResponse.getResult().getText());
		assertEquals(active, createResponse.getResult().isActive());
		String id = createResponse.getResult().getID();
		assertNotNull(id);

		// Update agreement
		String new_name = name + "_v2";
		String new_text = text + " v2";

		AgreementUpdateResponse updateResponse = client
			.agreements()
			.update(
				new AgreementUpdateRequest.Builder(type, id)
					.setName(new_name)
					.setText(new_text)
					.setActive(active)
					.build()
			);
		assertEquals(new_name, updateResponse.getResult().getName());
		assertEquals(new_text, updateResponse.getResult().getText());
		assertEquals(active, updateResponse.getResult().isActive());

		// List
		FilterAgreementList filter = new FilterAgreementList();
		AgreementListResponse listResponse = client
			.agreements()
			.list(new AgreementListRequest.Builder().setFilter(filter).build());
		assertTrue(listResponse.getResult().getCount() > 0);
		assertTrue(listResponse.getResult().getAgreements().length > 0);
		int count = listResponse.getResult().getCount();

		// Delete
		AgreementDeleteResponse deleteResponse = client
			.agreements()
			.delete(new AgreementDeleteRequest.Builder(type, id).build());

		// List again
		AgreementListResponse listResponseAfterDelete = client
			.agreements()
			.list(new AgreementListRequest.Builder().build());
		assertEquals(count - 1, (int) listResponseAfterDelete.getResult().getCount());
	}

	@Test
	public void test_AgreementsCycleEULA() throws PangeaException, PangeaAPIException {
		try {
			agreementsCycle(AgreementType.EULA);
		} catch (PangeaAPIException e) {
			System.out.println(e.toString());
			assertTrue(false);
		}
	}

	@Test
	public void test_AgreementsCyclePrivacyPolicy() throws PangeaException, PangeaAPIException {
		try {
			agreementsCycle(AgreementType.PRIVACY_POLICY);
		} catch (PangeaAPIException e) {
			System.out.println(e.toString());
			assertTrue(false);
		}
	}
}
