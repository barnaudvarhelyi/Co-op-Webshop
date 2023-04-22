# HomeWorkProject

## Feature details

### As a user I can sign in to the application
  - Providing a username and password
  - Given any parameters missing, the user can't sign in and the application displays a message listing the missing parameters
  - Given the username is not existing, the user can't sign in and the application displays a message about it
  - Given the password is not matching the one stored with the username, the user can't sign in and the application displays message that the password is wrong
  - Given the username and password pair is correct, a token is returned to initiate further requests and the current amount of greenBay dollars the user has
### As a signed in user I can create a sellable item
  - Providing the name, description, photo url, starting price and purchase price for the item
  - Given any parameters missing, the user can't create the item and the application displays a message what's missing
  - Given the price is not a positive whole number, the user can't create the item and the application displays a message about it
  - Given the photo url is not a valid url, the user can't create the item and the application displays a message about it
  - Given all parameter is provided and in good format, the item is created and the application displays the whole created item
### As a signed in user I can list existing sellable items
  - With main information about the sellable item: name, photo url and last offered bid
  - Only the not yet sold items
  - Given no parameters, the application displays the first 20 sellable items that was created
  - Given a whole number (n) page parameter, the application displays the nth 20 sellable items
  - Given the page parameter is not a positive whole number, the application displays a message that the page is not correct
### As a signed in user I can view a specific sellable item
  - With all information about the sellable item: name, description, photo url, all placed bids with usernames, the buying price and seller's name
  - Given the id of an existing sellable item, the application displays all information about the item
  - Given the id of an existing not sellable item, the application displays all information including the buyer's name
  - Given the id of a not existing item, the application displays a message that the item is not found
### As a signed in user I can bid on a sellable item
  - Only if the bidding user have enough greenBay dollars
  - Given the id of a not existing item, the application displays a message that the item is not found
  - Given the id of an existing not sellable item, the application displays a message that the item can't be bought
  - Given the id of an existing sellable item and a bid, and the user has less greenBay dollars than the bid, the application displays a message that there's not enough money on the user's account
  - Given the id of an existing sellable item and a bid that is lower or equal than the last bid, the application displays a message that the bid is too low
  - Given the id of an existing sellable item and a bid that is higher than the last bid but lower than the buying price and the user has more greenBay dollars than the bid, the user places the bid on the item and the application displays all the information about the item
  - Given the id of an existing sellable item and a bid that is equal or higher than the buying price and the user has more greenBay dollars than the bid, the user buys the item that becomes not sellable and the application displays all the information about the item including the buyer's name