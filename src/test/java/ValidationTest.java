import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.slotegrator.players.api.PlayerGenerator;
import org.slotegrator.players.api.endpoints.CreatePlayerEndpoint;
import org.slotegrator.players.api.endpoints.DeleteOneEndpoint;
import org.slotegrator.players.api.endpoints.GetAllPlayersEndpoint;
import org.slotegrator.players.api.endpoints.GetOnePlayerEndpoint;
import org.slotegrator.user.TestUser;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


@Epic("Player")
@Feature("CRUD")
public class ValidationTest {

    private String accessToken;

    @BeforeClass
    public void authorizeTestUser() {
        accessToken = TestUser.getInstance().getAccessToken();
    }

    @Test(description = "Validate Create Player Response Schema")
    public void createPlayerResponseSchema() {
        var createPlayerEndpoint =  new CreatePlayerEndpoint(accessToken);
        var player = PlayerGenerator.generatePlayer();
        createPlayerEndpoint.post(player);
        createPlayerEndpoint.api()
                .apiResponse()
                .check()
                .isMatchJsonSchema("schemas/create-player-schema.json");
    }

    @Test(description = "Validate Get Player Response Schema")
    public void getPlayerResponseSchema() {
        var createPlayerEndpoint =  new CreatePlayerEndpoint(accessToken);
        var player = PlayerGenerator.generatePlayer();
        createPlayerEndpoint.post(player);

        var getOnePlayerEndpoint = new GetOnePlayerEndpoint(accessToken);
        getOnePlayerEndpoint.post(player.email);
        getOnePlayerEndpoint.api()
                .apiResponse()
                .check()
                .isMatchJsonSchema("schemas/get-player-schema.json");
    }

    //Not Authorized
    @Test(description = "Get Player Not Authorized")
    public void getPlayerWithoutToken() {
        var getOnePlayerEndpoint = new GetOnePlayerEndpoint(null);
        getOnePlayerEndpoint.post("");
        getOnePlayerEndpoint.api().apiResponse().check().isStatus(401);
    }

    @Test(description = "Create Player Not Authorized")
    public void createPlayerWithoutToken() {
        var createPlayerEndpoint = new CreatePlayerEndpoint(null);
        var player = PlayerGenerator.generatePlayer();
        createPlayerEndpoint.post(player);
        createPlayerEndpoint.api().apiResponse().check().isStatus(401);
    }

    @Test(description = "Delete Player Not Authorized")
    public void deletePlayerWithoutToken() {
        var createPlayerEndpoint = new DeleteOneEndpoint(null);
        var player = PlayerGenerator.generatePlayer();
        createPlayerEndpoint.delete(player.id);
        createPlayerEndpoint.api().apiResponse().check().isStatus(401);
    }

    @Test(description = "Get All Players Not Authorized")
    public void getAllPlayersWithoutToken() {
        var getAllPlayersEndpoint = new GetAllPlayersEndpoint(null);
        getAllPlayersEndpoint.api().get();
        getAllPlayersEndpoint.api().apiResponse().check().isStatus(401);
    }

    //Bad Request
    @Test(description = "Create Player Bad Request")
    public void createPlayerBadRequest() {
        var createPlayerEndpoint = new CreatePlayerEndpoint(accessToken);
        createPlayerEndpoint.post(null);
        createPlayerEndpoint.api().apiResponse().check().isStatus(400);
    }

    @Test(description = "Get Null Player Bad Request")
    public void getNullPlayerBadRequest() {
        var getOnePlayerEndpoint = new GetOnePlayerEndpoint(accessToken);
        getOnePlayerEndpoint.post(null);
        getOnePlayerEndpoint.api().apiResponse().check().isStatus(400);
    }

    @Test(description = "Delete Player Bad Request")
    public void deletePlayerBadRequest() {
        var deleteOneEndpoint = new DeleteOneEndpoint(accessToken);
        deleteOneEndpoint.delete(null);
        deleteOneEndpoint.api().apiResponse().check().isStatus(400);
    }
}
