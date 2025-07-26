import io.qameta.allure.Allure;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import org.assertj.core.api.SoftAssertions;
import org.slotegrator.core.AssertionUtils;
import org.slotegrator.core.AwaitilityHelper;
import org.slotegrator.player.api.models.PlayerDTO;
import org.slotegrator.player.PlayerGenerator;
import org.slotegrator.player.PlayerUtils;
import org.slotegrator.player.api.endpoints.CreatePlayerEndpoint;
import org.slotegrator.player.api.endpoints.DeleteOneEndpoint;
import org.slotegrator.player.api.endpoints.GetAllPlayersEndpoint;
import org.slotegrator.player.api.endpoints.GetOnePlayerEndpoint;
import org.slotegrator.player.api.models.GetOnePlayerResponseBody;
import org.slotegrator.user.TestUser;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;


@Epic("Player")
@Feature("CRUD")
public class PlayerTest {

    private String accessToken;

    @BeforeSuite
    public void checkVariables() {

    }

    @BeforeClass
    public void authorizeTestUser() {
        accessToken = TestUser.getInstance().getAccessToken();
    }

    @Test(description = "Access Token should be present")
    public void login() {
        AssertionUtils.assertThatNotNull("Access Token", accessToken);
    }

    @Test(description = "Create 12 Players", dependsOnMethods = "login")
    public void createTwelvePlayers() {
        var players = createPlayersConcurrently(12);
        var ids = players.stream().map(player -> player.id).toList();

        var allPlayersResponse = new GetAllPlayersEndpoint(accessToken).get();
        var allPlayersIds = Arrays.stream(allPlayersResponse).map(player -> player.id).toList();
        AssertionUtils.assertContainsAll("all players contains ids of 12 created", allPlayersIds, ids);
    }

    @Test(description = "Create Same Players", dependsOnMethods = "login")
    public void createSamePlayers() {
        var player = PlayerGenerator.generatePlayer();
        var responseFirst = new CreatePlayerEndpoint(accessToken).post(player);
        var responseSecond = new CreatePlayerEndpoint(accessToken).post(player);
        AssertionUtils.assertThatNotEquals("different id", responseFirst.id, responseSecond.id);
    }

    @Test(description = "Get Player", dependsOnMethods = "login")
    public void getPlayer() {
        var player = PlayerGenerator.generatePlayer();
        var createPlayerResponse = new CreatePlayerEndpoint(accessToken).post(player);

        var getPlayerResponse = new GetOnePlayerEndpoint(accessToken).post(createPlayerResponse.email);

        var softly = new SoftAssertions();
        softly.assertThat(createPlayerResponse.id).isEqualTo(getPlayerResponse.id);
        softly.assertThat(createPlayerResponse.email).isEqualTo(getPlayerResponse.email);
        softly.assertThat(createPlayerResponse.name).isEqualTo(getPlayerResponse.name);
        softly.assertThat(createPlayerResponse.username).isEqualTo(getPlayerResponse.username);
        softly.assertAll();
    }

    @Test(description = "Get and Sort Players By Name", dependsOnMethods = "login")
    public void getAllPlayersAndSortByName() {
        var allPlayersResponse = new GetAllPlayersEndpoint(accessToken).get();
        var playersList = new ArrayList<PlayerDTO>();

        for (GetOnePlayerResponseBody responseBody : allPlayersResponse) {
            playersList.add(PlayerUtils.mapResponseToPlayer(responseBody));
        }

        playersList.sort(Comparator.comparing(player -> player.name));

        var softly = new SoftAssertions();
        for (int i = 0; i < playersList.size() - 1; i++) {
            String current = playersList.get(i).name;
            String next = playersList.get(i + 1).name;
            softly.assertThat(current.compareTo(next) <= 0)
                    .as(String.format("List is not sorted at index %d: %s > %s", i, current, next))
                    .isEqualTo(true);
        }
        softly.assertAll();
    }

    @Test(description = "Delete One Player", dependsOnMethods = "login")
    public void deletePlayer() {
        var player = PlayerGenerator.generatePlayer();
        var createPlayerResponse = new CreatePlayerEndpoint(accessToken).post(player);
        var deleted = new DeleteOneEndpoint(accessToken).delete(createPlayerResponse.id);
        var softly = new SoftAssertions();
        softly.assertThat(createPlayerResponse.id).isEqualTo(deleted.id);
        softly.assertThat(createPlayerResponse.email).isEqualTo(deleted.email);
        softly.assertAll();
    }

    @Test(description = "Delete All Existing Player", dependsOnMethods = {"getAllPlayersAndSortByName", "deletePlayer"})
    public void deleteAllPlayers() {
        var getAllEndpoint = new GetAllPlayersEndpoint(accessToken);
        var allPlayersResponse = getAllEndpoint.get();

        deletePlayersConcurrently(allPlayersResponse);

        AwaitilityHelper.aWaitAssert(
                () -> AssertionUtils.assertThatListIsEmpty("List should be empty",
                        Arrays.stream(getAllEndpoint.get()).toList()));
    }

    @Step
    private List<PlayerDTO> createPlayersConcurrently(Integer count) {
        try (ExecutorService executor = Executors.newFixedThreadPool(4)) {
            var createEndpoint = new CreatePlayerEndpoint(accessToken);
            List<CompletableFuture<PlayerDTO>> tasks = IntStream.range(0, count)
                    .mapToObj(i -> CompletableFuture.supplyAsync(() ->
                            Allure.step("Creating player " + i, () -> {
                                var player = PlayerGenerator.generatePlayer();
                                return createEndpoint.post(player);
                            }), executor))
                    .toList();

            return tasks.stream()
                    .map(CompletableFuture::join)
                    .toList();
        }
    }

    @Step
    private void deletePlayersConcurrently(GetOnePlayerResponseBody[] players) {
        var deleteOneEndpoint = new DeleteOneEndpoint(accessToken);

        try (ExecutorService executor = Executors.newFixedThreadPool(4)) {
            List<CompletableFuture<Void>> tasks = Arrays.stream(players)
                    .map(response ->
                            CompletableFuture.runAsync(() -> {
                                var deleted = deleteOneEndpoint.delete(response.id);
                                AssertionUtils.assertThatNotNull("Player Not Deleted " + response.id, deleted);
                                }, executor))
                    .toList();
            tasks.forEach(CompletableFuture::join);
        }
    }

}
