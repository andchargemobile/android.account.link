# README #

## What is this repository for? ##

* Quick summary
* Version 1.0.0

This sdk is meant to simplify the integration of account linking with &Charge on Android

## How do I add the sdk? ##

The easiest way to add this to your android project is to: Clone the repository, add the sdk via File -> New -> Import Module...

In the future we might set up a private jitpack repository so anyone with an auth token will be able to import this directly via gradle

## How do I get started? ##

In advance, you can check MainActivity.kt and SimpleMainActivity.kt for examples.
But roughly speaking it's this:


## How to set up: ##

redefine the callback url scheme & host strings in your ids.xml or strings.xml to fit your requirements

    <string name="andcharge_callback_scheme" translatable="false">mp</string>
    <string name="andcharge_callback_host" translatable="false">and-charge</string>
    <string name="andcharge_callback_path" translatable="false" />

In the manifest, your activity should have an intent filter with this data element

  <data android:scheme="@string/andcharge_callback_scheme" android:host="@string/andcharge_callback_host" />


## How to use in the project: ##

    1) OnCreate of the activity receiving the callback url intent, create AccountLinkView:

    val view = AccountLinkView(this)
    view.showAccountLinkResult(intent)
    view.showAccountLinkInit(viewModel.onAccountLinkInitiated)

    2) If you are using android:launchMode="singleTask", override onNewIntent
       and pass the new intent there as well: view.onAccountLinkResultReceived(intent)

    3) Call your backend to and post successful results to viewModel.onAccountLinkInitiated
       alternatively pass the result manually when received view.showAccountLinkInit(result)


## The account linking flow: ##

    1) Your backend initiates an account link and the result AccountLinkInit is passed to AccountLinkView
    2) The sdk parses AccountLinkInit to a deep link for &Charge to handle
    3) The sdk deep links into &Charge or opens a browser
    4) &Charge completes the account link, it can be successful or some error occurs
    5) &Charge deep links into your app with the url you defined in "How to set up" with extra params
    6) You pass the intent to AccountLinkView
    7) AccountLinkView parses: Intent -> AccountLinkResult
    8) A fragment dialog is shown with the result

check for details on the data types etc:
https://github.com/charge-partners/charge-and-partners/blob/master/link_partner_account.md


## Who do I talk to? ##

questions regarding the android sdk please to
roman@and-charge.me
