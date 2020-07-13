Functional Flow 
----------------
1. User search with existing GitHub username and get all the public GitHub repositories for the same users 
2. After getting a successful search result if user click on the repository name link, should navigate to actual github 
repository
3. User search with non-existing username should display the correct error message 'Github user not found'
4. User search with existing github username having no public repository should not get any result and display 'No Repos'
5. On a successful search, the user should get a public repository name and its description or '-' if the description 
   is not available
6. User searches by using enter key should work
7. The user provides an input which triggers unhandled/unknown error should display the generic error message 
'Something went wrong'
8. User search without providing username should not fetch any result and display the correct error message 
9. On valid search, the application should display the correct number of repositories fetched for the given user
10. On page reload the searched result should get cleared and allow the user to search again

Non-Functional Flow
--------------------
1. On the application page below elements should be available in the given order: 
	\
	a. header: Get Github Repos \
	b. label: Github Username \
	c. text box: without default value and editable \
	d. button: with the text 'Go' \
	e. placeholder text: No repos for search result
2. If search take more time than expected then there should be a timeout for the request 
3. Display loading spinner until application fetches the results for the given username
4. On a successful search, the system should display message in bol text and green colour
5. On an unsuccessful search/error the system should display error in Bold text and red color
6. Application should be browser compatible

Suggestions/Observations
------------------------
1. The searched result can be displayed in the tabular format with table headers having 'Repository name' and 
'Description'
2. No need to display 'No repos' text by default, it can be displayed when there are no public repositories fetched for 
    the given user 
3. There should be defaulted focus inside text box as user going to enter text before search.
4. There are error into console panel on page load.
5. As github username max length is 39 characters we can have same limited in the application.
6. As we are using the public api which has the API rate limit of certain requests but doing forward we can either show 
number of 
    allowed requests or restrict user once API rate limit reached.
7. Should app allow Adam to filter only his students or any user from the github? going forward we can implement feature
    where Adam can only filter his students repo not other users.


