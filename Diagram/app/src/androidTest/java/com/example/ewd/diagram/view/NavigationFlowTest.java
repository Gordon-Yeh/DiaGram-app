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
public class NavigationFlowTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void navigationFlowTest() throws InterruptedException {
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

        ViewInteraction bottomNavigationItemView0 = onView(
                allOf(withId(R.id.navigation_home), withContentDescription("Home"),
                        isDisplayed()));
        bottomNavigationItemView0.perform(click());


        ViewInteraction bottomNavigationItemView = onView(
                allOf(withId(R.id.navigation_timeline), withContentDescription("Timeline"),
                        isDisplayed()));
        bottomNavigationItemView.perform(click());

        ViewInteraction textView = onView(
                allOf(withText("Timeline "),
                        isDisplayed()));
        textView.check(matches(withText("Timeline ")));

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.largeLabel), withText("Timeline"),
                        isDisplayed()));
        textView2.check(matches(withText("Timeline")));

        ViewInteraction bottomNavigationItemView2 = onView(
                allOf(withId(R.id.navigation_profile), withContentDescription("Profile"),
                        isDisplayed()));
        bottomNavigationItemView2.perform(click());

        ViewInteraction textView3 = onView(
                allOf(withText("User"),
                        isDisplayed()));
        textView3.check(matches(withText("User")));

        ViewInteraction textView4 = onView(
                allOf(withId(R.id.largeLabel), withText("Profile"),
                        isDisplayed()));
        textView4.check(matches(withText("Profile")));

        ViewInteraction bottomNavigationItemView3 = onView(
                allOf(withId(R.id.navigation_post), withContentDescription("New Post"),
                        isDisplayed()));
        bottomNavigationItemView3.perform(click());

        ViewInteraction textView5 = onView(
                allOf(withId(R.id.largeLabel), withText("New Post"),
                        isDisplayed()));
        textView5.check(matches(withText("New Post")));

        pressBack();

        ViewInteraction editText = onView(
                allOf(withId(R.id.username),
                        isDisplayed()));
        editText.check(matches(isDisplayed()));

        ViewInteraction editText2 = onView(
                allOf(withId(R.id.password),
                        isDisplayed()));
        editText2.check(matches(isDisplayed()));

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
