Functional Flow 
----------------
1. User search with existing GitHub username and get all the public GitHub repositories for the same users 
2. After getting a successful search result if user click on the repository name link, should open actual github 
   repository in a separate tab

3. User search with existing github username having no public repository should not get any result 
4. On a successful search, the user should get a public repository name and its description or '-' if the description 
   is not available in the result
5. User searches by using enter key should work
6. User search with non-existing username should display the correct error message 'Github user not found'
7. The user provides an input which triggers unhandled/unknown error should display the generic error message 
8. User search without providing username should not fetch any result and display the correct error message 
9. On valid search, the application should display the correct number of repositories fetched for the given user
10. On page reload the searched result should get cleared and allow the user to search again

Non-Functional Flow
--------------------
1. On the application page below elements should be available in the given order: 
	\
	a. header: Get Github Repos \
	b. label: Github Username \
	c. textbox: without default value and editable \
	d. button: with the text 'Go' \
	e. placeholder text: No repos for search result
2. If search take more time then there should be a timeout for the request 
3. Display loading spinner until application fetches the results for the given username
4. On a successful search, the system should display 'Success!' text in green colour

Suggestions
-------------
1. The searched result can be displayed in the tabular format with table headers having 'Repository name' and 'Description'

2. No need to display 'No repos' text by default, it can be displayed when there are no public repositories fetched for 
    the given user 
3. Success/Failure message can stay on the page until the user take the correct action, currently, it disappears after 
    some time
4. We can have focused into the search text box on page load as a user have to manually click into before typing username 
5. When user try to navigate by providing any endpoint e.g. http://localhost:3000/test there should be 404 error page 
6. There are error into console panel on page load 
7. As we are using the public api which has limit of certain requests but doing forward we can either show number of 
    allowed requests or limit user after request quoata is reached.
8. Should app allow Adam to filter only his students or any user from the github? going forward we can implement feature
    where Adam can only filter only his students repo not other users


