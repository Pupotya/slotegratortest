package org.slotegrator.core;

import io.qameta.allure.Step;
import org.awaitility.core.ConditionFactory;
import org.awaitility.core.ThrowingRunnable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slotegrator.core.configuration.SiteConfig;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.awaitility.Awaitility.await;
import static org.slotegrator.core.configuration.ConfigInitialization.getSiteConfig;

public class AwaitilityHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(AwaitilityHelper.class);
    private static final SiteConfig config = getSiteConfig();

    private static ConditionFactory aWait(int timeout, int pollInterval, int pollDelay) {
        return await().with().pollInSameThread()
                .conditionEvaluationListener(
                        condition -> LOGGER.debug("{} (elapsed time {}ms, remaining time {}ms)%n",
                                condition.getDescription(),
                                condition.getElapsedTimeInMS(),
                                condition.getRemainingTimeInMS()))
                .with().timeout(timeout, MILLISECONDS)
                .with().pollInterval(pollInterval, MILLISECONDS)
                .with().pollDelay(pollDelay, MILLISECONDS);

    }

    @Step
    public static void aWaitAssert(ThrowingRunnable assertion) {
        aWait(config.awaitTimeout()
                , config.awaitPollInterval()
                , config.awaitPollDelay())
                .ignoreExceptions()
                .untilAsserted(assertion);
    }
}
