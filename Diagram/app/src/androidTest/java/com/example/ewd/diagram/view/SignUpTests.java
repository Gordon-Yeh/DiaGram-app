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
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.hasErrorText;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.is;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static org.hamcrest.Matchers.not;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class SignUpTests {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void badAccessCode() {
        ViewInteraction appCompatTextView = onView(
                allOf(withId(R.id.sign_up), withText(" Sign up."),isDisplayed()));
        appCompatTextView.perform(click());

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.first_name),
                        isDisplayed()));
        appCompatEditText.perform(click());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.first_name),
                        isDisplayed()));
        appCompatEditText2.perform(replaceText("sam"), closeSoftKeyboard());

        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.last_name),
                        isDisplayed()));
        appCompatEditText3.perform(replaceText("watson"), closeSoftKeyboard());

        ViewInteraction appCompatEditText4 = onView(
                allOf(withId(R.id.username),
                        isDisplayed()));
        appCompatEditText4.perform(replaceText("sam"), closeSoftKeyboard());



        ViewInteraction appCompatEditText5 = onView(
                allOf(withId(R.id.password),
                        isDisplayed()));
        appCompatEditText5.perform(replaceText("1234"), closeSoftKeyboard());



        ViewInteraction appCompatEditText6 = onView(
                allOf(withId(R.id.access_code),
                        isDisplayed()));
        appCompatEditText6.perform(replaceText("patient"), closeSoftKeyboard());




        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.sign_up), withText("go"),
                        isDisplayed()));
        appCompatButton.perform(click());

        ViewInteraction button = onView(
                allOf(withId(R.id.sign_up),
                        isDisplayed()));
        button.check(matches(isDisplayed()));

        ViewInteraction button2 = onView(
                allOf(withId(R.id.sign_up),
                        isDisplayed()));
        button2.check(matches(isDisplayed()));

        ViewInteraction textView = onView(
                allOf(withText("Sign Up"),
                        isDisplayed()));
        textView.check(matches(withText("Sign Up")));

    }


    @Test
    public void takenUsername() throws InterruptedException {
        ViewInteraction appCompatTextView = onView(
                allOf(withId(R.id.sign_up), withText(" Sign up."),isDisplayed()));
        appCompatTextView.perform(click());

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.first_name),
                        isDisplayed()));
        appCompatEditText.perform(click());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.first_name),
                        isDisplayed()));
        appCompatEditText2.perform(replaceText("sam"), closeSoftKeyboard());

        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.last_name),
                        isDisplayed()));
        appCompatEditText3.perform(replaceText("watson"), closeSoftKeyboard());

        ViewInteraction appCompatEditText4 = onView(
                allOf(withId(R.id.username),
                        isDisplayed()));
        appCompatEditText4.perform(replaceText("admin"), closeSoftKeyboard());



        ViewInteraction appCompatEditText5 = onView(
                allOf(withId(R.id.password),
                        isDisplayed()));
        appCompatEditText5.perform(replaceText("1234"), closeSoftKeyboard());



        ViewInteraction appCompatEditText6 = onView(
                allOf(withId(R.id.access_code),
                        isDisplayed()));
        appCompatEditText6.perform(replaceText("patient-00001"), closeSoftKeyboard());




        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.sign_up), withText("go"),
                        isDisplayed()));
        appCompatButton.perform(click());


        ViewInteraction button = onView(
                allOf(withId(R.id.sign_up),
                        isDisplayed()));
        button.check(matches(isDisplayed()));

        ViewInteraction button2 = onView(
                allOf(withId(R.id.sign_up),
                        isDisplayed()));
        button2.check(matches(isDisplayed()));

        ViewInteraction textView = onView(
                allOf(withText("Sign Up"),
                        isDisplayed()));
        textView.check(matches(withText("Sign Up")));

    }

    @Test
    public void emptyFirstName() {
        ViewInteraction appCompatTextView = onView(
                allOf(withId(R.id.sign_up), withText(" Sign up."),
                        isDisplayed()));
        appCompatTextView.perform(click());


        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.first_name),
                        isDisplayed()));
        appCompatEditText.perform(click());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.first_name),
                        isDisplayed()));
        appCompatEditText2.perform(replaceText(""), closeSoftKeyboard());



        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.sign_up), withText("go"),
                        isDisplayed()));
        appCompatButton.perform(click());

        onView(withId(R.id.first_name)).check(matches(hasErrorText("Can't leave field empty.")));

        ViewInteraction textView = onView(
                allOf(withText("Sign Up"),
                        isDisplayed()));
        textView.check(matches(withText("Sign Up")));

    }

    @Test
    public void emptyLastName() {

        ViewInteraction appCompatTextView = onView(
                allOf(withId(R.id.sign_up), withText(" Sign up."),isDisplayed()));
        appCompatTextView.perform(click());

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.first_name),
                        isDisplayed()));
        appCompatEditText.perform(click());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.first_name),
                        isDisplayed()));
        appCompatEditText2.perform(replaceText("sam"), closeSoftKeyboard());

        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.last_name),
                        isDisplayed()));
        appCompatEditText3.perform(replaceText(""), closeSoftKeyboard());

        ViewInteraction appCompatEditText4 = onView(
                allOf(withId(R.id.username),
                        isDisplayed()));
        appCompatEditText4.perform(replaceText(""), closeSoftKeyboard());



        ViewInteraction appCompatEditText5 = onView(
                allOf(withId(R.id.password),
                        isDisplayed()));
        appCompatEditText5.perform(replaceText(""), closeSoftKeyboard());



        ViewInteraction appCompatEditText6 = onView(
                allOf(withId(R.id.access_code),
                        isDisplayed()));
        appCompatEditText6.perform(replaceText(""), closeSoftKeyboard());



        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.sign_up), withText("go"),
                        isDisplayed()));
        appCompatButton.perform(click());

        onView(withId(R.id.last_name)).check(matches(hasErrorText("Can't leave field empty.")));

        ViewInteraction textView = onView(
                allOf(withText("Sign Up"),
                        isDisplayed()));
        textView.check(matches(withText("Sign Up")));

    }


    @Test
    public void emptyUserName() {

        ViewInteraction appCompatTextView = onView(
                allOf(withId(R.id.sign_up), withText(" Sign up."),isDisplayed()));
        appCompatTextView.perform(click());

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.first_name),
                        isDisplayed()));
        appCompatEditText.perform(click());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.first_name),
                        isDisplayed()));
        appCompatEditText2.perform(replaceText("sam"), closeSoftKeyboard());

        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.last_name),
                        isDisplayed()));
        appCompatEditText3.perform(replaceText("man"), closeSoftKeyboard());

        ViewInteraction appCompatEditText4 = onView(
                allOf(withId(R.id.username),
                        isDisplayed()));
        appCompatEditText4.perform(replaceText(""), closeSoftKeyboard());



        ViewInteraction appCompatEditText5 = onView(
                allOf(withId(R.id.password),
                        isDisplayed()));
        appCompatEditText5.perform(replaceText("pass"), closeSoftKeyboard());



        ViewInteraction appCompatEditText6 = onView(
                allOf(withId(R.id.access_code),
                        isDisplayed()));
        appCompatEditText6.perform(replaceText("code"), closeSoftKeyboard());



        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.sign_up), withText("go"),
                        isDisplayed()));
        appCompatButton.perform(click());

        onView(withId(R.id.username)).check(matches(hasErrorText("Can't leave field empty.")));

        ViewInteraction textView = onView(
                allOf(withText("Sign Up"),
                        isDisplayed()));
        textView.check(matches(withText("Sign Up")));

    }


    @Test
    public void emptyPassword() {

        ViewInteraction appCompatTextView = onView(
                allOf(withId(R.id.sign_up), withText(" Sign up."),isDisplayed()));
        appCompatTextView.perform(click());

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.first_name),
                        isDisplayed()));
        appCompatEditText.perform(click());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.first_name),
                        isDisplayed()));
        appCompatEditText2.perform(replaceText("sam"), closeSoftKeyboard());

        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.last_name),
                        isDisplayed()));
        appCompatEditText3.perform(replaceText("man"), closeSoftKeyboard());

        ViewInteraction appCompatEditText4 = onView(
                allOf(withId(R.id.username),
                        isDisplayed()));
        appCompatEditText4.perform(replaceText("user"), closeSoftKeyboard());



        ViewInteraction appCompatEditText5 = onView(
                allOf(withId(R.id.password),
                        isDisplayed()));
        appCompatEditText5.perform(replaceText(""), closeSoftKeyboard());



        ViewInteraction appCompatEditText6 = onView(
                allOf(withId(R.id.access_code),
                        isDisplayed()));
        appCompatEditText6.perform(replaceText("code"), closeSoftKeyboard());



        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.sign_up), withText("go"),
                        isDisplayed()));
        appCompatButton.perform(click());


        onView(withId(R.id.password)).check(matches(hasErrorText("Can't leave field empty.")));

        ViewInteraction textView = onView(
                allOf(withText("Sign Up"),
                        isDisplayed()));
        textView.check(matches(withText("Sign Up")));

    }


    @Test
    public void emptycode() throws InterruptedException {

        ViewInteraction appCompatTextView = onView(
                allOf(withId(R.id.sign_up), withText(" Sign up."),isDisplayed()));
        appCompatTextView.perform(click());

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.first_name),
                        isDisplayed()));
        appCompatEditText.perform(click());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.first_name),
                        isDisplayed()));
        appCompatEditText2.perform(replaceText("sam"), closeSoftKeyboard());

        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.last_name),
                        isDisplayed()));
        appCompatEditText3.perform(replaceText("man"), closeSoftKeyboard());

        ViewInteraction appCompatEditText4 = onView(
                allOf(withId(R.id.username),
                        isDisplayed()));
        appCompatEditText4.perform(replaceText("user"), closeSoftKeyboard());



        ViewInteraction appCompatEditText5 = onView(
                allOf(withId(R.id.password),
                        isDisplayed()));
        appCompatEditText5.perform(replaceText("pass"), closeSoftKeyboard());



        ViewInteraction appCompatEditText6 = onView(
                allOf(withId(R.id.access_code),
                        isDisplayed()));
        appCompatEditText6.perform(replaceText(""), closeSoftKeyboard());


        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.sign_up), withText("go"),
                        isDisplayed()));
        appCompatButton.perform(click());
        onView(withId(R.id.access_code)).check(matches(hasErrorText("Can't leave field empty.")));

        ViewInteraction textView = onView(
                allOf(withText("Sign Up"),
                        isDisplayed()));
        textView.check(matches(withText("Sign Up")));

    }


    @Test
    public void correctSignUp() throws InterruptedException {
        ViewInteraction appCompatTextView = onView(
                allOf(withId(R.id.sign_up), withText(" Sign up."),isDisplayed()));
        appCompatTextView.perform(click());

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.first_name),
                        isDisplayed()));
        appCompatEditText.perform(click());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.first_name),
                        isDisplayed()));
        appCompatEditText2.perform(replaceText("sam"), closeSoftKeyboard());

        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.last_name),
                        isDisplayed()));
        appCompatEditText3.perform(replaceText("watson"), closeSoftKeyboard());

        ViewInteraction appCompatEditText4 = onView(
                allOf(withId(R.id.username),
                        isDisplayed()));
        appCompatEditText4.perform(replaceText("newuser23"), closeSoftKeyboard());



        ViewInteraction appCompatEditText5 = onView(
                allOf(withId(R.id.password),
                        isDisplayed()));
        appCompatEditText5.perform(replaceText("password1"), closeSoftKeyboard());



        ViewInteraction appCompatEditText6 = onView(
                allOf(withId(R.id.access_code),
                        isDisplayed()));
        appCompatEditText6.perform(replaceText("patient-00004"), closeSoftKeyboard());




        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.sign_up), withText("go"),
                        isDisplayed()));
        appCompatButton.perform(click());

        Thread.sleep(1500);
        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.recycler_view_posts),
                        isDisplayed()));
        recyclerView.check(matches(isDisplayed()));

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
