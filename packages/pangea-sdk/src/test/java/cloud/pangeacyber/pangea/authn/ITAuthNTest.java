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
import cloud.pangeacyber.pangea.exceptions.*;
import java.util.HashMap;
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
	private static final Profile profileOld = new Profile();
	private static final Profile profileNew = new Profile();
	private static String userID = ""; // Will be set once user is created

	@Before
	public void setUp() throws ConfigException {
		this.cfg = Config.fromIntegrationEnvironment(environment);
		client = new AuthNClient.Builder(cfg).build();

		profileOld.put("name", "User name");
		profileOld.put("country", "Argentina");
		profileNew.put("age", "18");
	}

	@Test
	public void testA_UserActions() throws PangeaException, PangeaAPIException {
		try {
			// Create User 1
			UserCreateResponse createResp1 = client
				.user()
				.create(new UserCreateRequest.Builder(emailTest, passwordOld, IDProvider.PASSWORD).build());
			assertTrue(createResp1.isOk());
			assertNotNull(createResp1.getResult());
			assertNotNull(createResp1.getResult().getID());
			userID = createResp1.getResult().getID();
			assertEquals(new HashMap<String, Object>(), createResp1.getResult().getProfile());

			// Create user 2
			UserCreateResponse createResp2 = client
				.user()
				.create(
					new UserCreateRequest.Builder(emailDelete, passwordOld, IDProvider.PASSWORD)
						.setProfile(profileOld)
						.build()
				);
			assertTrue(createResp2.isOk());
			assertEquals(profileOld, createResp2.getResult().getProfile());

			// User login (delete user)
			UserLoginResponse loginDelResp = client.user().login().Password(emailDelete, passwordOld, new Profile());
			assertTrue(loginDelResp.isOk());
			assertNotNull(loginDelResp.getResult().getActiveToken());
			assertNotNull(loginDelResp.getResult().getRefreshToken());

			// Logout (delete user)
			SessionLogoutResponse logoutResp = client.session().logout(createResp2.getResult().getID());
			assertTrue(logoutResp.isOk());

			// Delete user (delete user)
			UserDeleteResponse deleteResp1 = client.user().deleteByEmail(emailDelete);
			assertTrue(deleteResp1.isOk());
			assertNull(deleteResp1.getResult());

			// User login
			UserLoginResponse loginResp = client.user().login().Password(emailTest, passwordOld);
			assertTrue(loginResp.isOk());
			assertNotNull(loginResp.getResult().getActiveToken());
			assertNotNull(loginResp.getResult().getRefreshToken());

			// Token check
			ClientTokenCheckResponse checkResp = client
				.client()
				.token()
				.check(loginResp.getResult().getActiveToken().getToken());
			assertTrue(checkResp.isOk());

			// User verify
			UserVerifyResponse verifyResp = client.user().verify(IDProvider.PASSWORD, emailTest, passwordOld);
			assertTrue(verifyResp.isOk());
			assertTrue(verifyResp.getResult().getID().equals(userID));

			// Update password
			ClientPasswordChangeResponse passUpdateResp = client
				.client()
				.password()
				.change(loginResp.getResult().getActiveToken().getToken(), passwordOld, passwordNew);
			assertTrue(passUpdateResp.isOk());
			assertNull(passUpdateResp.getResult());

			// Update profile
			// Get profile by email. Should be empty because it was created without profile parameter
			UserProfileGetResponse profileGetResp = client.user().profile().getByEmail(emailTest);
			assertTrue(profileGetResp.isOk());
			assertNotNull(profileGetResp.getResult());
			assertEquals(userID, profileGetResp.getResult().getID());
			assertEquals(emailTest, profileGetResp.getResult().getEmail());
			assertEquals(new Profile(), profileGetResp.getResult().getProfile());

			// Get by ID
			profileGetResp = client.user().profile().getByID(userID);
			assertTrue(profileGetResp.isOk());
			assertNotNull(profileGetResp.getResult());
			assertEquals(userID, profileGetResp.getResult().getID());
			assertEquals(emailTest, profileGetResp.getResult().getEmail());
			assertEquals(new Profile(), profileGetResp.getResult().getProfile());

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
				.update(
					new UserUpdateRequest.Builder().setEmail(emailTest).setDisabled(false).setRequireMFA(false).build()
				);
			assertTrue(userUpdateResp.isOk());
			assertEquals(userID, userUpdateResp.getResult().getID());
			assertEquals(emailTest, userUpdateResp.getResult().getEmail());
			assertEquals(false, userUpdateResp.getResult().getDisabled());
			assertEquals(false, userUpdateResp.getResult().getRequireMFA());

			// Client session refresh (refresh and active token)
			ClientSessionRefreshResponse refreshResp = client
				.client()
				.session()
				.refresh(
					loginResp.getResult().getRefreshToken().getToken(),
					loginResp.getResult().getActiveToken().getToken()
				);
			assertTrue(refreshResp.isOk());
			assertNotNull(refreshResp.getResult().getActiveToken());
			assertNotNull(refreshResp.getResult().getRefreshToken());

			// Client session refresh (only refresh token)
			refreshResp = client.client().session().refresh(refreshResp.getResult().getRefreshToken().getToken());
			assertTrue(refreshResp.isOk());
			assertNotNull(refreshResp.getResult().getActiveToken());
			assertNotNull(refreshResp.getResult().getRefreshToken());

			// User password reset
			UserPasswordResetResponse resetResp = client.user().password().reset(userID, passwordNew);
			assertTrue(resetResp.isOk());

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
			UserLoginResponse loginResp = client.user().login().Password(emailTest, passwordNew);
			assertTrue(loginResp.isOk());
			assertNotNull(loginResp.getResult().getActiveToken());
			assertNotNull(loginResp.getResult().getRefreshToken());
			String token = loginResp.getResult().getActiveToken().getToken();

			// List client sessions
			ClientSessionListResponse listResp = client
				.client()
				.session()
				.list(new ClientSessionListRequest.Builder(token).build());
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
			UserLoginResponse loginResp = client.user().login().Password(emailTest, passwordNew);
			assertTrue(loginResp.isOk());
			assertNotNull(loginResp.getResult().getActiveToken());
			assertNotNull(loginResp.getResult().getRefreshToken());
			String token = loginResp.getResult().getActiveToken().getToken();

			// Session list
			SessionListResponse listResp = client.session().list(new SessionListRequest.Builder().build());
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

		try {
			// List users invites
			UserInviteListResponse inviteListResp1 = client
				.user()
				.invite()
				.list(new UserInviteListRequest.Builder().build());
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
		try {
			UserListResponse userListResp1 = client.user().list(new UserListRequest.Builder().build());
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
}
