package com.example.ewd.diagram.view;



import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.example.ewd.diagram.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasErrorText;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class LogInTests {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void logIn() throws InterruptedException {

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.username),
                        isDisplayed()));
        appCompatEditText.perform(click());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.username),
                        isDisplayed()));
        appCompatEditText2.perform(replaceText("admin"), closeSoftKeyboard());

        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.password),
                        isDisplayed()));
        appCompatEditText3.perform(replaceText("qwe123"), closeSoftKeyboard());

        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.log_in), withText("go"),
                        isDisplayed()));
        appCompatButton.perform(click());

        Thread.sleep(1500);
        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.recycler_view_posts),
                        isDisplayed()));
        recyclerView.check(matches(isDisplayed()));

    }

    @Test
    public void incorrectUserPass() throws InterruptedException {

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.username),
                        isDisplayed()));
        appCompatEditText.perform(click());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.username),
                        isDisplayed()));
        appCompatEditText2.perform(replaceText("wrong"), closeSoftKeyboard());

        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.password),
                        isDisplayed()));
        appCompatEditText3.perform(replaceText("wrong"), closeSoftKeyboard());

        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.log_in), withText("go"),
                        isDisplayed()));
        appCompatButton.perform(click());

        Thread.sleep(1500);
        onView(withId(R.id.username)).check(matches(isDisplayed()));

    }


    @Test
    public void emptyUser() throws InterruptedException {

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.username),
                        isDisplayed()));
        appCompatEditText.perform(click());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.username),
                        isDisplayed()));
        appCompatEditText2.perform(replaceText(""), closeSoftKeyboard());

        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.password),
                        isDisplayed()));
        appCompatEditText3.perform(replaceText("wrong"), closeSoftKeyboard());

        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.log_in), withText("go"),
                        isDisplayed()));
        appCompatButton.perform(click());

        onView(withId(R.id.username)).check(matches(hasErrorText("Can't leave field empty.")));
        onView(withId(R.id.username)).check(matches(isDisplayed()));
        onView(withId(R.id.password)).check(matches(isDisplayed()));
    }



    @Test
    public void emptyPass() throws InterruptedException {

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.username),
                        isDisplayed()));
        appCompatEditText.perform(click());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.username),
                        isDisplayed()));
        appCompatEditText2.perform(replaceText("wrong"), closeSoftKeyboard());

        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.password),
                        isDisplayed()));
        appCompatEditText3.perform(replaceText(""), closeSoftKeyboard());

        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.log_in), withText("go"),
                        isDisplayed()));
        appCompatButton.perform(click());

        onView(withId(R.id.password)).check(matches(hasErrorText("Can't leave field empty.")));
        onView(withId(R.id.username)).check(matches(isDisplayed()));
        onView(withId(R.id.password)).check(matches(isDisplayed()));

    }


    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
