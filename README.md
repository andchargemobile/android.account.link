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

### A) Redefine the callback url scheme, host and path strings in your ids.xml or strings.xml to fit your requirements ###

        <string name="andcharge_callback_scheme" translatable="false">mp</string>
        <string name="andcharge_callback_host" translatable="false">and-charge</string>
        <string name="andcharge_callback_path" translatable="false" />

### B) In the manifest, your activity should have an intent filter with this data element ###

If you dont use path

        <data android:scheme="@string/andcharge_callback_scheme"
        android:host="@string/andcharge_callback_host" />
		
If you use path

        <data android:scheme="@string/andcharge_callback_scheme"
        android:host="@string/andcharge_callback_host"
        android:path="@string/andcharge_callback_path"/>
		
### C) Then, follow these steps: ###
		
	1) Call your backend so it initiates the account link with &Charge
    2) Pass the result to AndChargeUrlParser to get the &Charge deep link
	3) Pass the deep link to OpenAndChargeLinkCommand
    4) &Charge will try to complete the account linking. Then, &Charge will open this app with the callback url defined in your strings and add extra query parameters depending on the result of the account linking. 
	5) Retrieve the intent (or the intentData). Use AndChargeUrlParser to convert Intent -> AccountLinkResult
    6) Show AccountLinkResult, for example by showing AccountLinkResultDialog
	
for more details check:
https://github.com/charge-partners/charge-and-partners/blob/master/link_partner_account.md

## Who do I talk to? ##

questions regarding the android sdk please to
roman@and-charge.me
