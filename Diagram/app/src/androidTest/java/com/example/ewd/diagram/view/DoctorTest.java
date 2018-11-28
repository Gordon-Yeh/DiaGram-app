package com.example.ewd.diagram.view;



import android.support.test.espresso.matcher.ViewMatchers;
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
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
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
public class DoctorTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void doctorTest() {

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

        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
/*
        ViewInteraction appCompatImageViewA = onView(allOf(has withId(16908768))).perform(click());

        //ViewMatchers.withId(R.id.title), ViewMatchers.withText("Exercise!")

        try {
            Thread.sleep(700);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatEditText4 = onView(
                allOf(withId(R.id.comment),
                        isDisplayed()));
        appCompatEditText4.perform(replaceText("Right!"), closeSoftKeyboard());

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.add_comment), withText("POST"),
                        isDisplayed()));
        appCompatButton2.perform(click());

        pressBack();

        try {
            Thread.sleep(700);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
*/

        ViewInteraction bottomNavigationItemView2 = onView(
                allOf(withId(R.id.navigation_profile), withContentDescription("Profile"),
                        isDisplayed()));
        bottomNavigationItemView2.perform(click());

        ViewInteraction appCompatButton3 = onView(
                allOf(withId(R.id.edit), withText("Edit"),
                        isDisplayed()));
        appCompatButton3.perform(click());

        try {
            Thread.sleep(700);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        ViewInteraction appCompatEditText7 = onView(
                allOf(withId(R.id.experience_body),
                        isDisplayed()));
        appCompatEditText7.perform(replaceText("20 years."), closeSoftKeyboard());


        ViewInteraction appCompatEditText9 = onView(
                allOf(withId(R.id.first_name),
                        isDisplayed()));
        appCompatEditText9.perform(replaceText("Joe"), closeSoftKeyboard());


        ViewInteraction appCompatEditText11 = onView(
                allOf(withId(R.id.last_name),
                        isDisplayed()));
        appCompatEditText11.perform(replaceText("Jackson"), closeSoftKeyboard());



        ViewInteraction appCompatEditText13 = onView(
                allOf(withId(R.id.specializations_body),
                        isDisplayed()));
        appCompatEditText13.perform(replaceText("Cardio\nPhysio"), closeSoftKeyboard());


        ViewInteraction appCompatEditText15 = onView(
                allOf(withId(R.id.department_body),
                        isDisplayed()));
        appCompatEditText15.perform(replaceText("Cardiology"), closeSoftKeyboard());


        ViewInteraction appCompatButton4 = onView(
                allOf(withId(R.id.edit), withText("Done"),
                        isDisplayed()));
        appCompatButton4.perform(click());

        try {
            Thread.sleep(700);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction bottomNavigationItemView3 = onView(
                allOf(withId(R.id.navigation_post), withContentDescription("New Post"),
                        isDisplayed()));
        bottomNavigationItemView3.perform(click());

        ViewInteraction appCompatEditText17 = onView(
                allOf(withId(R.id.title),
                        isDisplayed()));
        appCompatEditText17.perform(click());

        ViewInteraction appCompatEditText18 = onView(
                allOf(withId(R.id.title),
                        isDisplayed()));
        appCompatEditText18.perform(replaceText("Track your calories and macronutrients!"), closeSoftKeyboard());


        ViewInteraction appCompatEditText19 = onView(
                allOf(withId(R.id.post),
                        isDisplayed()));
        appCompatEditText19.perform(replaceText("2000/day!"), closeSoftKeyboard());



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
