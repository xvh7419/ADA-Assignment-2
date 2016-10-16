# ADA-Assignment-2
Q1
- getMeta function works. Not all websites have a good meta tag however
- getImage function works but not all images have a height & width tag. 

Possible Changes:
- Get the image itself and measure/get height and width from there. 
- Add in a way to handle invalid links. For example, a link that has no text/xml/html (like a download link) will return an error.
- Add a way to handle malformed links. A malformed link is a URL that doesn't have that http:// or https:// thing at the beginning and so   will return an error.
- Make a method that creates a connection that handles the above exceptions and call that in the get method instead of having to create     connection all the time.
  
Q2
- Functioning correctly. May be some errors due to poor website HTML practice however.

Possible Changes:
- Optimise code so it runs/looks better maybe.
- Change the spider function so that Q3 can use it
- Can remove the line which prints out how many links left to search if it's annoying.

Q3
- Functioning correctly. May be errors due to poor website HTML practice.
- Tested with: SEED URL-https://www.google.co.nz/ KEYWORDS-translate DEPTH-1. Returns 2 websites.

Possible Changes:
- Change Q2 so that Q3 uses that function instead of copying over the dunction with minor changes.
- Can remove the line which prints out how many links left to search if it's annoying.
- Add erroroneous input handling for the user

Q4
- WIP.

Q5
- Will begin after all the code is finished.
