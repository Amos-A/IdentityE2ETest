Feature: Validate that the car details provided in the file correct
  #In order to test the details of cars provided
  #As a user
  #I want to validate these against the details from the website https://motorway.co.uk/


 Background:
   Given I have an input file to extract the registration numbers
   And I have a car output file to compare results

  @validateCars
  Scenario Outline: Validate the details of cars provided in the file
  Given I navigate to the motorway url
  Then I should land on the SellMyCar Page
  When I enter the "<ith>" registration number from list and click value my car button
  Then I should see the CarMileage page
  And the details of the car should match the expected from "<ith>" registration in test file

    Examples:
    |ith|
    |1|
    |2|
    |3|
    |4|
    |5|

