## Project
* You are expected to add a UI layer to the project using Compose.
* You will display a list that will show all of the `ClothingItems` from the `ClothingRepository` class.
    * Each item in the Composible list needs to show the `title` and the `image` properties of the `ClothingItem` object
* On clicking of an item in the list will open a detail view that will show the selected `ClothingItem`
    * The detail view needs to show the `title`, `price`, `description`, and `image` properties of the `ClothingItem` object

## Notes
There is already `ClothingViewModel` with a `ClothingRepository` setup as a dependency. The `ClothingRepository` has a function to get all `ClothingItem` objects as well as getting a single `ClothingItem` by id. <br>
Feel free to make any changes that will help you build the UI layer in Compose <br>
You are free to suggest and implement any library or code snippet that you think that is going to be useful
for the codebase. But please be sure to add detailed information about your suggestions and why they would be useful.

_______________________________________
## Things I would have loved to do to improve my solution;

* Add UI test to test for the following cases:
   1. Launching the ClothingListScreen
   2. Testing the search functionality by inputting a search query and verifying that the search method on the view model is called with the correct query.
   3. Testing the sort functionality by clicking on the sort button and selecting a sort filter, then verifying that the setSortFilter method on the view model is called with the correct filter.
   4. Testing navigation by clicking on a clothing item and verifying that the details screen is displayed with correct data.

* Add more animations
