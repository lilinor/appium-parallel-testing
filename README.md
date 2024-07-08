# Appium parallel testing
Parallel testing for Android using Appium with TestNG

A [Riddle](https://github.com/lilinor/appium-parallel-testing/tree/master/Apps) application is used for testing. It runs 2 same tests on 2 devices.
The application needs to be already installed on the device but you can change those [2 lines](https://github.com/lilinor/appium-parallel-testing/blob/807df5f63658cde3575799f6952e9282b1372ce6/src/test/java/ParallelTests.java#L32L33) 
to `UiAutomator2Options().setApp($PWD/Apps/appriddle.apk)` where `$PWD`is the absolute path where your project is located

Configuring devices used for testing is [there](https://github.com/lilinor/appium-parallel-testing/blob/8e8494d54deca179d9bba6e46ae0263e4344e37e/testSuite.xml#L6). Replace the `udid` by the ADB serial number of the device.

Run the tests :

1/ Start the devices and connect it to your computer/CI server

2/ Start appium server

`appium`

3/ Run project 

`mvn test`

To see an example of :
- test sharding (splitting a set of tests and run each batch on several devices), please refer to the [test_sharding](https://github.com/lilinor/appium-parallel-testing/tree/test_sharding) branch
- implementing retries to avoid flaky tests, please refer to the [flaky_tests](https://github.com/lilinor/appium-parallel-testing/tree/flaky_tests) branch.
