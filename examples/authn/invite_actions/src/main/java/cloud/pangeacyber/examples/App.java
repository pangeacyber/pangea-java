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
    private static final String emailInviteDelete = String.format(
		"user.email+invite_del%s@pangea.cloud",
		randomValue
	);
	private static final String emailInviteKeep = String.format(
		"user.email+invite_keep%s@pangea.cloud",
		randomValue
	);
	private static final Profile profileInitial = new Profile();
	private static final Profile profileUpdate = new Profile();

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
            // Invite 1
            System.out.println("Invite user 1...");
            UserInviteResponse inviteResp1 = client.user().invite(
                new UserInviteRequest.Builder(
                    userEmail,
                    emailInviteKeep,
                    "https://someurl.com/callbacklink",
                    "somestate")
                    .build()
            );
            System.out.println("Invite ID: " + inviteResp1.getResult().getID());

            // Invite 2
            UserInviteResponse inviteResp2 = null;
            System.out.println("Invite user 2...");
            inviteResp2 = client.user().invite(
                new UserInviteRequest.Builder(
                    userEmail,
                    emailInviteDelete,
                    "https://someurl.com/callbacklink",
                    "somestate"
                    ).build()
                    );
            System.out.println("Invite ID: " + inviteResp2.getResult().getID());

            // List invites
            System.out.println("List invites...");
            UserInviteListResponse inviteListResp1 = client.user().invite().list(
                new UserInviteListRequest.Builder().build()
            );
            System.out.println(String.format("There is %d invites.", inviteListResp1.getResult().getCount()));

            // Delete invite
            System.out.println("Delete invite...");
            UserInviteDeleteResponse deleteResp = client.user().invite().delete(inviteResp2.getResult().getID());
            System.out.println("Delete invite success.");

            // List invites
            System.out.println("List invites...");
            inviteListResp1 = client.user().invite().list(
                new UserInviteListRequest.Builder().build()
            );
            System.out.println(String.format("There is %d invites.", inviteListResp1.getResult().getCount()));
		} catch (PangeaAPIException | PangeaException e) {
            System.out.println("Something went wrong.");
			System.out.println(e.toString());
			System.exit(1);
		}
    }
}
