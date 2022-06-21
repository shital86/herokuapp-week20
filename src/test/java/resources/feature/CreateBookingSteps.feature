Feature:As a user I would like to perform a CRUD operation of the application

Scenario: I authorize to the application
Given I am on homepage to authorize on the application
When I send a POST request using a valid payload to authorize application
Then I get a valid response code 200
And I verify if I have a token

Scenario: I create a booking on the application
Given I am on the homepage to create booking on the application
When I send a POST request using a valid payload to booking application
Then I get a valid response code 200

Scenario: I read a newly created booking from the application
Given I am on the homepage to create booking on the application
When I send GET request to booking application
Then I get a valid response code 200
And I verify if booking created with correct details

Scenario: I update a newly created booking from the application
Given I am on the homepage to create booking on the application
When I send PUT request to booking application
Then I get a valid response code 200
And I verify if booking updated with correct details

Scenario: I delete a newly created booking from the application
Given I am on the homepage to create booking on the application
When I send DELETE request to booking application
Then I get a valid response code 201
And I verify if booking deleted from the application