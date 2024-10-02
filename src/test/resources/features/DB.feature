Feature: DB TESTING
  Background: Database connectivity established
    * Database connection established
  @DB
  Scenario:  test the validity of social links
    * Query prepared sozial links table
    * the results of the social links table are compared
    * Database closed

#US08
  @O8
  Scenario: Insert new delivery charge and verify

    * I insert the new delivery charge into the database
    * Verify that 1 added to the table
    * Database closed


    Scenario: Enter the necessary data in the deliverycategories table
      * Prepare query to insert 3 data entry into the deliverycategories table
      * Verify bulk 3 data added to the table
      * Database closed
