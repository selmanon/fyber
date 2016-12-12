package io.abdellah.fyber;

import io.abdellah.fyber.utilities.ApiParamsUtility;
import org.junit.Test;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ApiParamsUtilityUnitTest {
  @Test public void calculateAuthHashKey_isCorrect() throws Exception {
    assertThat(ApiParamsUtility.calculateAuthHashKey("2070", "json", "pub0", "1471855583",
        "spiderman"),
        equalTo("a213222eb6d9e50052179a94f98c9324cd5e5d94"));
  }
}