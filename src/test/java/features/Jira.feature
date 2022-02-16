Feature: Jira flow
  #mvn test -Dcucumber.filter.tags="@AddPlace"

  @Only
  Scenario: Add comment
    Given I create jira issue with following data
      | Project Key | RFA                      |
      | Description | Description of the issue |
      | Summary     | Add Comment Scenario     |
      | Issue type  | Bug                      |
    And I call "addIssue" API with "POST" http method
    Then The API call is success with status code 201
    When I add following comment to previously created issue "This is my test comment"
    And I call "addComment" API with "POST" http method
    Then The API call is success with status code 201

  Scenario: Add attachment
    Given I add file "file.txt" to issue "RFA-23"
    When I call "addAttachment" API with "POST" http method
    Then The API call is success with status code 200

  Scenario: Update comment
    Given I create jira issue with following data
      | Project Key | RFA                      |
      | Description | Description of the issue |
      | Summary     | Update Comment Scenario  |
      | Issue type  | Bug                      |
    And I call "addIssue" API with "POST" http method
    Then The API call is success with status code 201
    When I add following comment to previously created issue "This is my test comment"
    And I call "addComment" API with "POST" http method
    Then The API call is success with status code 201
    When I update my previous comment with following text "This is an update of my comment"
    Then The API call is success with status code 200

  Scenario: Delete issue
    Given I want to delete issue "RFA-3"
    And I call "deleteIssue" API with "DELETE" http method
    Then The API call is success with status code 204





