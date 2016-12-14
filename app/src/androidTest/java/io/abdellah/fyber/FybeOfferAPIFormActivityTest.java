package io.abdellah.fyber;

import android.support.test.espresso.AmbiguousViewMatcherException;
import android.support.test.espresso.IdlingPolicies;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.NoMatchingViewException;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.widget.EditText;
import io.abdellah.fyber.fyberform.FybeOfferAPIFormActivity;
import io.abdellah.idling_resource.elapsed_time.ElapsedTimeIdlingResource;
import java.util.concurrent.TimeUnit;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.registerIdlingResources;
import static android.support.test.espresso.Espresso.unregisterIdlingResources;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.anyOf;

@RunWith(AndroidJUnit4.class) public class FybeOfferAPIFormActivityTest {
  public static final String UID_STRING_PARAM = "spiderman";
  public static final String API_KEY_STRING_PARAM = "1c915e3b5d42d05136185030892fbb846c278927";
  public static final String APP_ID_STRING_PARAM = "2070";
  public static final String PUB0_STRING_PARAM = "pub0";

  @Rule public ActivityTestRule<FybeOfferAPIFormActivity> activityRule =
      new ActivityTestRule<>(FybeOfferAPIFormActivity.class);

  // START TEST AGAINST EMPTY FIELDS

  @Test public void appId_IsEmpty() {
    onView(withId(R.id.input_app_id)).perform(clearText());
    onView(withId(R.id.btn_process)).perform(click());
    onView(withId(R.id.input_app_id)).check(matches(withError("Application ID Required")));
  }

  @Test public void apiKey_IsEmpty() {
    onView(withId(R.id.input_api_key)).perform(clearText());
    onView(withId(R.id.btn_process)).perform(click());
    onView(withId(R.id.input_api_key)).check(matches(withError("API Key Required")));
  }

  @Test public void uid_IsEmpty() {
    onView(withId(R.id.input_uid)).perform(clearText());
    onView(withId(R.id.btn_process)).perform(click());
    onView(withId(R.id.input_uid)).check(matches(withError("User ID Required")));
  }

  @Test public void pub0_IsEmpty() {
    onView(withId(R.id.input_pub0)).perform(clearText());
    onView(withId(R.id.btn_process)).perform(click());
    onView(withId(R.id.input_pub0)).check(matches(withError("Pub0 Required")));
  }

  // END TEST AGAINST EMPTY FIELDS

  @Test public void processApiFyber_Will_Success() {

    // Make sure Espresso does not time out
    int waitingTime = 6000;
    IdlingPolicies.setMasterPolicyTimeout(waitingTime * 2, TimeUnit.MILLISECONDS);
    IdlingPolicies.setIdlingResourceTimeout(waitingTime * 2, TimeUnit.MILLISECONDS);

    // Type text
    onView(withId(R.id.input_uid)).perform(clearText(), typeText(UID_STRING_PARAM),
        closeSoftKeyboard());
    onView(withId(R.id.input_api_key)).perform(clearText(), typeText(API_KEY_STRING_PARAM),
        closeSoftKeyboard());
    onView(withId(R.id.input_app_id)).perform(clearText(), typeText(APP_ID_STRING_PARAM),
        closeSoftKeyboard());
    onView(withId(R.id.input_pub0)).perform(clearText(), typeText(PUB0_STRING_PARAM),
        closeSoftKeyboard());

    // Press the button.
    onView(withId(R.id.btn_process)).perform(click());

    // Now we wait
    IdlingResource idlingResource = new ElapsedTimeIdlingResource(waitingTime);
    registerIdlingResources(idlingResource);

    // This view is in a different Activity, no need to tell Espresso.
    try {
      onView(anyOf(withId(R.id.offers_recycler_view), withId(R.id.text_info))).check(
          matches(isDisplayed()));
    } catch (AmbiguousViewMatcherException e) {
    }

    // Clean up
    unregisterIdlingResources(idlingResource);
  }

  /**
   * Create new custom view matchers called withError.
   */

  private static Matcher withError(final String expected) {
    return new TypeSafeMatcher() {
      @Override protected boolean matchesSafely(Object item) {
        if (item instanceof EditText) {
          return ((EditText) item).getError().toString().equals(expected);
        }
        return false;
      }

      @Override public void describeTo(Description description) {
        description.appendText("Not found error message [" + expected + "]");
      }
    };
  }
}
