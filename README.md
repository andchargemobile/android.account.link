# README #

## What is this repository for? ##

* This sdk is meant to simplify the integration of account linking with &Charge on Android
* Version 1.0.0

## How do I get started? ##

Manually: Clone the repository, add the sdk via File -> New -> Import Module...

Or via gradle, go to https://jitpack.io/private#auth, get the auth token of the invited git account, add auth token to $HOME/.gradle/gradle.properties (jitAuthToken=jp_5...)

	allprojects {
		repositories {
			...
			maven { 
				url 'https://jitpack.io'
				credentials { username jitAuthToken }
			}
		}
	}
    
    dependencies {
	        implementation 'com.github.andchargemobile:android.account.link:1.0.0'
	}
	


## How to set up: ##

### 1) Redefine the callback url scheme & host strings in your ids.xml or strings.xml to fit your requirements ###

    <string name="andcharge_callback_scheme" translatable="false">mp</string>
    <string name="andcharge_callback_host" translatable="false">and-charge</string>
    <string name="andcharge_callback_path" translatable="false" />
    
#### In the manifest, if not using path ####

	<intent-filter>
	    ...
	
    		<data android:scheme="@string/andcharge_callback_scheme"
			android:host="@string/andcharge_callback_host" />
	
	</intent-filter>
	
#### If using path ####

	<intent-filter>
	    ...
	
    		<data android:scheme="@string/andcharge_callback_scheme"
			android:host="@string/andcharge_callback_host"
			android:path="@string/andcharge_callback_path"/>
	
	</intent-filter>

### 2) OnCreate of the activity receiving the callback url intent from step 1), create AccountLinkView: ###

    val view = AccountLinkView(this)
    view.showAccountLinkResult(intent)
    view.showAccountLinkInit(viewModel.onAccountLinkInitiated)

#### If you are using android:launchMode="singleTask" ####
    override onNewIntent and pass the new intent to the AccountLinkView: view.onAccountLinkResultReceived(intent)

#### If not using live data ####
    instead of 
    	view.showAccountLinkInit(viewModel.onAccountLinkInitiated)
    pass your result directly
    	view.showAccountLinkInit(result)
	
#### Also, check MainActivity.kt for an example ####


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

Mail questions regarding the android sdk please to roman@and-charge.me
