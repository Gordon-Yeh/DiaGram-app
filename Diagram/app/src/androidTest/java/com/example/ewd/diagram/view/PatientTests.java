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
import static androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
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
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class PatientTests {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void patientTests() {

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.username),
                        isDisplayed()));
        appCompatEditText.perform(click());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.username),
                        isDisplayed()));
        appCompatEditText2.perform(replaceText("newuser24"), closeSoftKeyboard());


        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.password),
                        isDisplayed()));
        appCompatEditText3.perform(replaceText("password1"), closeSoftKeyboard());


        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.log_in), withText("go"),
                        isDisplayed()));
        appCompatButton.perform(click());

        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction bottomNavigationItemView = onView(
                allOf(withId(R.id.navigation_profile), withContentDescription("Profile"),
                        isDisplayed()));
        bottomNavigationItemView.perform(click());

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.edit), withText("Edit"),
                        isDisplayed()));
        appCompatButton2.perform(click());

        try {
            Thread.sleep(700);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        ViewInteraction appCompatEditText5 = onView(
                allOf(withId(R.id.first_name),
                        isDisplayed()));
        appCompatEditText5.perform(replaceText("sam"), closeSoftKeyboard());


        ViewInteraction appCompatEditText7 = onView(
                allOf(withId(R.id.last_name),
                        isDisplayed()));
        appCompatEditText7.perform(replaceText("watson"), closeSoftKeyboard());


        ViewInteraction appCompatEditText9 = onView(
                allOf(withId(R.id.medications_body),
                        isDisplayed()));
        appCompatEditText9.perform(replaceText("Metformin."), closeSoftKeyboard());


        ViewInteraction appCompatEditText11 = onView(
                allOf(withId(R.id.recent_procedures_body),
                        isDisplayed()));
        appCompatEditText11.perform(replaceText("-"), closeSoftKeyboard());

        ViewInteraction appCompatEditText12 = onView(
                allOf(withId(R.id.conditions_body),
                        isDisplayed()));
        appCompatEditText12.perform(replaceText("Type 2 Diabetes "), closeSoftKeyboard());


        ViewInteraction appCompatButton3 = onView(
                allOf(withId(R.id.edit), withText("Done"),
                        isDisplayed()));
        appCompatButton3.perform(click());

        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction bottomNavigationItemView2 = onView(
                allOf(withId(R.id.navigation_post), withContentDescription("New Post"),
                        isDisplayed()));
        bottomNavigationItemView2.perform(click());


        ViewInteraction appCompatEditText16 = onView(
                allOf(withId(R.id.title),
                        isDisplayed()));
        appCompatEditText16.perform(replaceText("How much protein shoud consume every day?"), closeSoftKeyboard());

        ViewInteraction floatingActionButton = onView(
                allOf(withId(R.id.fab),
                        isDisplayed()));
        floatingActionButton.perform(click());


        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());

        ViewInteraction appCompatTextView = onView(
                allOf(withId(R.id.title), withText("Logout"))).perform(click());

        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

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
